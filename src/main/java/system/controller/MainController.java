package system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import system.entity.FinalRout;
import system.entity.Station;
import system.service.api.FinalRoutService;
import system.service.api.RoutService;
import system.service.api.StationService;
import system.service.api.TicketService;

import java.sql.Date;
import java.sql.Time;
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

        return "home";
    }

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.POST)
    public String getSearchResult(@RequestParam("stationsFrom") Station stationFrom,
                                  @RequestParam("stationsTo") Station stationTo,
                                  @RequestParam("date") Date date, Model model){
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

        Map<Long, Time> mapDeparture = new HashMap<>(); // Long - finalRout.id
        Map<Long, Time> mapArrival = new HashMap<>(); // Long - finalRout.id
        Map<Long, Time> mapTimeInTravel = new HashMap<>();

        for (FinalRout finalRout: finalRoutSet) {
            mapDeparture.put(finalRout.getId(), routService.getRoutSectionByRoutAndDepartureStation(finalRout.getRout(), stationFrom).getDepartureTime());
            mapArrival.put(finalRout.getId(), routService.getRoutSectionByRoutAndDestinationStation(finalRout.getRout(), stationTo).getArrivalTime());
            mapTimeInTravel.put(finalRout.getId(), new Time(mapArrival.get(finalRout.getId()).getTime() - mapDeparture.get(finalRout.getId()).getTime()));
        }

        model.addAttribute("arrivals", mapArrival);
        model.addAttribute("departures", mapDeparture);
        model.addAttribute("times", mapTimeInTravel);

        model.addAttribute("stationFrom", stationFrom);
        model.addAttribute("stationTo", stationTo);

        return "home";
    }

    @RequestMapping(value = {"/buy", "/home/buy"}, method = RequestMethod.POST)
    public String getBuyTicketPage(@RequestParam("stationFrom") Long stationFromId,
                                   @RequestParam("stationTo") Long stationToId,
                                   @RequestParam("routId") Long routId, Model model) {

        if (false) {//TODO checking free place
            return "home";
        }

        Station stationFrom = stationService.findById(stationFromId);
        Station stationTo = stationService.findById(stationToId);

        FinalRout finalRout = finalRoutService.findById(routId);

        model.addAttribute("rout", finalRout);
        model.addAttribute("stationFrom", stationFrom);
        model.addAttribute("stationTo", stationTo);

        return "ticket";
    }
}
