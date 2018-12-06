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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import system.entity.FinalRout;
import system.entity.Station;
import system.entity.Ticket;
import system.entity.UserProfile;
import system.service.api.*;

import java.time.LocalDate;
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
    public String getHomePage(Model model){
        model.addAttribute("stationsFrom", stationService.findAll());
        model.addAttribute("stationsTo", stationService.findAll());

        return "home";
    }

    @GetMapping(value = "/search")
    @ResponseBody
    public String getSearchResult(@RequestParam("from") String from,
                                  @RequestParam("to") String to,
                                  @RequestParam("date")
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                  @RequestParam("count") Integer count){
        //TODO check input

        Station stationFrom = stationService.findByName(from);
        Station stationTo = stationService.findByName(to);

        Set<FinalRout> finalRoutSet = finalRoutService.findByStationToStationOnDate(stationFrom, stationTo, date);
        Map<Long, LocalTime> mapDeparture = finalRoutService.getMapDepartureByStation(finalRoutSet, stationFrom);
        Map<Long, LocalTime> mapArrival = finalRoutService.getMapArrivalByStation(finalRoutSet, stationTo);
        Map<Long, LocalTime> mapTimeInTravel = finalRoutService.getMapTimeInTravel(finalRoutSet, stationFrom, stationTo);
        Map<Long, Integer> mapPrice = finalRoutService.getMapPriceInCustomRout(finalRoutSet, stationFrom, stationTo);
        Map<Long, Integer> mapPlaces = ticketService.getMapFreePlacesInCustomRout(finalRoutSet, stationFrom, stationTo);

        JsonArray jsonValues = new JsonArray();

        JsonObject json;
        for (FinalRout finalRout: finalRoutSet) {
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

            jsonValues.add(json);
        }
        return jsonValues.toString();
    }

    @Secured(value={"ROLE_USER"})
    @PostMapping(value = {"/preorder", "/home/preorder"})
    public String getBuyTicketPage(@AuthenticationPrincipal User activeUser,
                                   @RequestParam("stationFrom") String stationFromId,
                                   @RequestParam("stationTo") String stationToId,
                                   @RequestParam("routId") Long routId, Model model) {
        UserData user = userService.findByUsername(activeUser.getUsername());
        UserProfile profile = user.getUserProfile();

        if (profile == null || profile.getSurname().isEmpty()) {
            model.addAttribute("userForm", new UserProfile());
            model.addAttribute("selectedTab", "profile-tab");
            return "userprofile";
        }

        Station stationFrom = stationService.findByName(stationFromId);
        Station stationTo = stationService.findByName(stationToId);

        FinalRout finalRout = finalRoutService.findById(routId);

        Integer price = routService.getPriceInRoutBetweenDepartureAndDestination(finalRout.getRout(), stationFrom, stationTo);

        model.addAttribute("stationFrom", stationFrom);
        model.addAttribute("stationTo", stationTo);
        model.addAttribute("rout", finalRout);

        model.addAttribute("user", profile);

        model.addAttribute("price", price);

        return "ticket";
    }

    @Secured(value={"ROLE_USER"})
    @PostMapping(value = "/buy/passengers", params = "valid")
    @ResponseBody
    public String isRegisteredUser(@RequestParam("surname") String surname,
                                   @RequestParam("firstname") String firstname,
                                   @RequestParam("date")
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        UserProfile userProfile = userService.findByNamesAndDate(surname, firstname, date);

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
    public String addNewPassengerToOrder(@RequestParam("surname") String surname,
                                         @RequestParam("firstname") String firstname,
                                         @RequestParam("date")
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        UserProfile user = new UserProfile();
        user.setSurname(surname);
        user.setFirstname(firstname);
        user.setBirthDate(date);

        if (isPassengerInOrder(user))
            return "This user has already added to order!";

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
    public String buyTicket(@AuthenticationPrincipal User activeUser,
                            @RequestParam("stationFrom") Long stationFromId,
                            @RequestParam("stationTo") Long stationToId,
                            @RequestParam("routId") Long routId,
                            @RequestParam("price") Integer price) {

        FinalRout finalRout = finalRoutService.findById(routId);

        UserData user = userService.findByUsername(activeUser.getUsername());
        UserProfile userProfile = user.getUserProfile();

        preOrder.add(0, userProfile);

        Station start = stationService.findById(stationFromId);
        Station end = stationService.findById(stationToId);

        List<Ticket> tickets = ticketService.formTickets(preOrder, start, end, finalRout, price);

        String result = ticketService.save(tickets);

        return result;
    }
}
