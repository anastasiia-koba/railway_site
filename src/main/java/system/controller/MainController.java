package system.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import system.entity.*;
import system.service.api.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * Controller for search {@link system.entity.FinalRout} and {@link system.entity.Ticket}'s pages.
 */
@Controller
public class MainController {
    @Autowired
    private StationService stationService;

    @Autowired
    private FinalRoutService finalRoutService;

    @Autowired
    private RoutService routService;

    @Autowired
    private UserService userService;

    @Autowired
    private TicketService ticketService;

    private List<UserProfile> preOrder = new ArrayList<>();

    @GetMapping(value = {"/", "/home"})
    public String getHomePage(Model model) {
        model.addAttribute("stationsFrom", stationService.findAll());
        model.addAttribute("stationsTo", stationService.findAll());

        return "home";
    }

    @GetMapping(value = "/search")
    @ResponseBody
    public String getSearchResult(@RequestParam("from") String from,
                                  @RequestParam("to") String to,
                                  @RequestParam("date")
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime) {
        Station stationFrom = stationService.findByName(from);
        Station stationTo = stationService.findByName(to);

        LocalDate date = dateTime.toLocalDate();
        LocalTime time = dateTime.toLocalTime();
        Set<FinalRout> finalRoutSet = finalRoutService.findByStationToStationOnDate(stationFrom, stationTo, date);
        Map<Long, LocalTime> mapDeparture = finalRoutService.getMapDepartureByStation(finalRoutSet, stationFrom);
        Map<Long, LocalTime> mapArrival = finalRoutService.getMapArrivalByStation(finalRoutSet, stationTo);
        Map<Long, LocalTime> mapTimeInTravel = finalRoutService.getMapTimeInTravel(finalRoutSet, stationFrom, stationTo);
        Map<Long, Integer> mapPlaces = ticketService.getMapFreePlacesInCustomRout(finalRoutSet, stationFrom, stationTo);
        Map<Long, Integer> mapPrice = finalRoutService.getMapPriceInCustomRout(finalRoutSet, stationFrom, stationTo);

        JsonArray jsonValues = new JsonArray();

        JsonObject json;
        for (FinalRout finalRout: finalRoutSet) {
            if (time.compareTo(mapDeparture.get(finalRout.getId())) > 0)
                continue;

            json = new JsonObject();

            json.addProperty("id", finalRout.getId());
            json.addProperty("routName", finalRout.getRout().getRoutName());
            json.addProperty("startStation", finalRout.getRout().getStartStation().getStationName());
            json.addProperty("endStation", finalRout.getRout().getEndStation().getStationName());
            json.addProperty("departureTime", mapDeparture.get(finalRout.getId()).toString());
            json.addProperty("arrivalTime", mapArrival.get(finalRout.getId()).toString());
            json.addProperty("travelTime", mapTimeInTravel.get(finalRout.getId()).toString());
            json.addProperty("price", mapPrice.get(finalRout.getId()).toString());
            json.addProperty("freePlace", mapPlaces.get(finalRout.getId()).toString());
            json.addProperty("available", finalRoutService.isDepartureTimeIn10Minutes(finalRout, stationFrom));

            jsonValues.add(json);
        }
        return jsonValues.toString();
    }

    @Secured(value={"ROLE_USER"})
    @GetMapping(value = {"/preorder", "/home/preorder", "/buy/**", "/home/buy/**", "/preorder", "/home/preorder"})
    public ModelAndView getPageOrderWithoutData() {
        ModelAndView errorPage = new ModelAndView("errors");
        String errorMsg = "First you need to choose rout";
        errorPage.addObject("errorMsg", errorMsg);
        return errorPage;
    }
    
    @Secured(value={"ROLE_USER"})
    @PostMapping(value = {"/preorder", "/home/preorder"})
    public String getBuyTicketPage(@AuthenticationPrincipal User activeUser,
                                   @RequestParam("stationFrom") String stationFromId,
                                   @RequestParam("stationTo") String stationToId,
                                   @RequestParam("routId") Long routId, Model model) {
        UserData user = userService.findByUsername(activeUser.getUsername());
        UserProfile profile = user.getUserProfile();

        Station stationFrom = stationService.findByName(stationFromId);
        Station stationTo = stationService.findByName(stationToId);

        FinalRout finalRout = finalRoutService.findById(routId);

        Integer price = routService.getPriceInRoutBetweenDepartureAndDestination(finalRout.getRout(), stationFrom, stationTo);

        model.addAttribute("stationFrom", stationFrom);
        model.addAttribute("stationTo", stationTo);
        model.addAttribute("rout", finalRout);
        model.addAttribute("userForm", new UserProfile());
        model.addAttribute("departureTime", finalRoutService.getTimeDepartureByStation(finalRout, stationFrom));
        model.addAttribute("arrivalTime", finalRoutService.getTimeArrivalByStation(finalRout, stationTo));

        model.addAttribute("user", profile);

        model.addAttribute("price", price);

        if (profile == null || profile.getSurname().isEmpty()) {
            preOrder = new ArrayList<>();
        } else {
            preOrder = new ArrayList<>();
            preOrder.add(profile);
        }

        return "ticket";
    }

    @Secured(value={"ROLE_USER"})
    @PostMapping(value = "/buy/passengers", params = "valid")
    @ResponseBody
    public String isRegisteredUser(@Valid @ModelAttribute("userForm") UserProfile profile) {
        UserProfile userProfile = userService.findByNamesAndDate(profile.getSurname(), profile.getFirstname(),
                profile.getBirthDate());

        if (userProfile != null) {
            return userProfile.getId().toString();
        } else {
            return "no user";
        }
    }

    @Secured(value={"ROLE_USER"})
    @GetMapping(value = "/buy/passengers", params = "list")
    @ResponseBody
    public List<UserProfile> getListPassengerInOrder() {
        return preOrder;
    }

    @Secured(value={"ROLE_USER"})
    @PostMapping(value = "/buy/passengers/add")
    @ResponseBody
    public String addPassengerToOrder(@RequestParam("passenger") Long userId) {
        UserProfile user = userService.findProfileById(userId);
        preOrder.add(user);

        return "User was added to order";
    }

    private boolean isPassengerInOrder(UserProfile user) {
        for (UserProfile userProfile: preOrder) {
            if (userProfile.getSurname().equals(user.getSurname()) &&
            userProfile.getFirstname().equals(user.getFirstname()) &&
            userProfile.getBirthDate().equals(user.getBirthDate()))
                return true;
        }

        return false;
    }

    @Secured(value={"ROLE_USER"})
    @PostMapping(value = "/buy/passengers/add", params = "new")
    @ResponseBody
    public String addNewPassengerToOrder(@Valid @ModelAttribute("userForm") UserProfile profile) {
        UserProfile user = new UserProfile();
        user.setSurname(profile.getSurname());
        user.setFirstname(profile.getFirstname());
        user.setBirthDate(profile.getBirthDate());

        if (isPassengerInOrder(user))
            return "Error: this user has already added to order!";

        preOrder.add(user);

        return "User was added to order";
    }

    @Secured(value={"ROLE_USER"})
    @PostMapping(value = "/buy/passengers/change")
    @ResponseBody
    public UserProfile getPassengerInOrder(@RequestParam("id") String id,
                                         @RequestParam("surname") String surname,
                                         @RequestParam("firstname") String firstname,
                                         @RequestParam("birthDay")
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        UserProfile user = new UserProfile();
        for (UserProfile userProfile: preOrder) {
            if (userProfile.getSurname().equals(surname) &&
                    userProfile.getFirstname().equals(firstname) &&
                    userProfile.getBirthDate().equals(date))
             user = userProfile;
        }

        return user;
    }

    @Secured(value={"ROLE_USER"})
    @PostMapping(value = "/buy/passengers/delete")
    @ResponseBody
    public String deletePassengerFromOrder(@RequestParam("surname") String surname,
                                           @RequestParam("firstname") String firstname,
                                           @RequestParam("birthDay")
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        for (UserProfile userProfile: preOrder) {
            if (userProfile.getSurname().equals(surname) &&
                    userProfile.getFirstname().equals(firstname) &&
                    userProfile.getBirthDate().equals(date)) {
                preOrder.remove(userProfile);
                break;
            }
        }

        return "User "+surname+" "+firstname+" was deleted from order";
    }

    @Secured(value={"ROLE_USER"})
    @ResponseBody
    @PostMapping(value = {"/buy", "/home/buy"}, params = "purchase")
    public String buyTicket(@RequestParam("stationFrom") Long stationFromId,
                            @RequestParam("stationTo") Long stationToId,
                            @RequestParam("routId") Long routId,
                            @RequestParam("price") Integer price) {

        FinalRout finalRout = finalRoutService.findById(routId);

        Station start = stationService.findById(stationFromId);
        Station end = stationService.findById(stationToId);

        List<Ticket> tickets = ticketService.formTickets(preOrder, start, end, finalRout, price);

        if (tickets == null)
            return "Error: no users in order";

        return ticketService.save(tickets);
    }

    @Secured(value={"ROLE_USER"})
    @GetMapping(value = "/buy/map")
    @ResponseBody
    public List<RoutSection> getMapOfOrder(@RequestParam("stationFrom") Long stationFromId,
                                           @RequestParam("stationTo") Long stationToId,
                                           @RequestParam("routId") Long routId) {
        Station start = stationService.findById(stationFromId);
        Station end = stationService.findById(stationToId);
        FinalRout finalRout = finalRoutService.findById(routId);

        return routService.getRoutSectionsInRoutBetweenDepartureAndDestination(finalRout.getRout(),
                start, end);
    }

}
