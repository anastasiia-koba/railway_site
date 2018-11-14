package system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import system.entity.FinalRout;
import system.entity.Station;
import system.entity.Ticket;
import system.entity.UserProfile;
import system.service.api.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * Controller for {@link system.entity.FinalRout}'s pages.
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

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String welcome(Model model){
        model.addAttribute("stationsFrom", stationService.findAll());
        model.addAttribute("stationsTo", stationService.findAll());

        model.addAttribute("routs", null);

        model.addAttribute("arrivals", null);
        model.addAttribute("departures", null);

        model.addAttribute("stationFrom", new Station());
        model.addAttribute("stationTo", new Station());

        model.addAttribute("price", null);

        return "home";
    }

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.POST)
    public String getSearchResult(@RequestParam("stationsFrom") Station stationFrom,
                                  @RequestParam("stationsTo") Station stationTo,
                                  @RequestParam("date") String strDate, Model model){
        LocalDate date = LocalDate.parse(strDate);

        model.addAttribute("stationsFrom", stationService.findAll());
        model.addAttribute("stationsTo", stationService.findAll());

        model.addAttribute("routs", null);

        model.addAttribute("arrivals", null);
        model.addAttribute("departures", null);

        model.addAttribute("stationFrom", new Station());
        model.addAttribute("stationTo", new Station());

        //TODO check input

        stationFrom = stationService.findByName(stationFrom.getStationName());
        stationTo = stationService.findByName(stationTo.getStationName());

        Set<FinalRout> finalRoutSet = finalRoutService.findByStationToStationOnDate(stationFrom, stationTo, date);
        model.addAttribute("routs", finalRoutSet);

        Map<Long, LocalTime> mapDeparture = finalRoutService.getMapDepartureByStation(finalRoutSet, stationFrom);
        Map<Long, LocalTime> mapArrival = finalRoutService.getMapArrivalByStation(finalRoutSet, stationTo);
        Map<Long, LocalTime> mapTimeInTravel = finalRoutService.getMapTimeInTravel(finalRoutSet, stationFrom, stationTo);
        Map<Long, Integer> mapPrice = finalRoutService.getMapPriceInCustomRout(finalRoutSet, stationFrom, stationTo);
        Map<Long, Integer> mapPlaces = ticketService.getMapFreePlacesInCustomRout(finalRoutSet, stationFrom, stationTo);

        model.addAttribute("arrivals", mapArrival);
        model.addAttribute("departures", mapDeparture);
        model.addAttribute("times", mapTimeInTravel);

        model.addAttribute("stationFrom", stationFrom);
        model.addAttribute("stationTo", stationTo);

        model.addAttribute("prices", mapPrice);
        model.addAttribute("freePlaces", mapPlaces);

        return "home";
    }

    @Secured(value={"ROLE_USER"})
    @RequestMapping(value = {"/buy", "/home/buy"}, method = RequestMethod.POST)
    public String getBuyTicketPage(@AuthenticationPrincipal User activeUser,
                                   @RequestParam("stationFrom") Long stationFromId,
                                   @RequestParam("stationTo") Long stationToId,
                                   @RequestParam("routId") Long routId, Model model) {

        UserProfile user = userService.findByUsername(activeUser.getUsername());

        Station stationFrom = stationService.findById(stationFromId);
        Station stationTo = stationService.findById(stationToId);

        FinalRout finalRout = finalRoutService.findById(routId);

        model.addAttribute("stationFrom", stationFrom);
        model.addAttribute("stationTo", stationTo);

        Set<FinalRout> finalRoutSet = finalRoutService.findByStationToStationOnDate(stationFrom, stationTo, finalRout.getDate());
        model.addAttribute("routs", finalRoutSet);

        Map<Long, LocalTime> mapDeparture = finalRoutService.getMapDepartureByStation(finalRoutSet, stationFrom);
        Map<Long, LocalTime> mapArrival = finalRoutService.getMapArrivalByStation(finalRoutSet, stationTo);
        Map<Long, LocalTime> mapTimeInTravel = finalRoutService.getMapTimeInTravel(finalRoutSet, stationFrom, stationTo);
        Map<Long, Integer> mapPrice = finalRoutService.getMapPriceInCustomRout(finalRoutSet, stationFrom, stationTo);
        Map<Long, Integer> mapPlaces = ticketService.getMapFreePlacesInCustomRout(finalRoutSet, stationFrom, stationTo);

        model.addAttribute("arrivals", mapArrival);
        model.addAttribute("departures", mapDeparture);
        model.addAttribute("times", mapTimeInTravel);

        model.addAttribute("prices", mapPrice);
        model.addAttribute("freePlaces", mapPlaces);

//        if (ticketService.isAnyBodyInFinalRoutWithUserData(finalRout, user)) {
//            model.addAttribute("error", "User with such surname, firstname and birth date has already register. ");
//            return "home";
//        }

        Integer price = routService.getPriceInRoutBetweenDepartureAndDestination(finalRout.getRout(), stationFrom, stationTo);

        model.addAttribute("rout", finalRout);

        model.addAttribute("user", user);

        model.addAttribute("price", price);

        return "ticket";
    }

    @Secured(value={"ROLE_USER"})
    @RequestMapping(value = {"/buy", "/home/buy"}, method = RequestMethod.POST, params = "purchase")
    public String buyTicket(@AuthenticationPrincipal User activeUser,
                            @RequestParam("stationFrom") Long stationFromId,
                            @RequestParam("stationTo") Long stationToId,
                            @RequestParam("routId") Long routId,
                            @RequestParam("price") Integer price, Model model) {

        UserProfile user = userService.findByUsername(activeUser.getUsername());

        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setFinalRout(finalRoutService.findById(routId));
        ticket.setStartStation(stationService.findById(stationFromId));
        ticket.setEndStation(stationService.findById(stationToId));
        ticket.setPrice(price);

        ticketService.save(ticket);

        return "redirect:/home";
    }
}
