package system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import system.entity.FinalRout;
import system.service.api.FinalRoutService;
import system.service.api.RoutService;
import system.service.api.TrainService;

import javax.validation.Valid;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Controller for {@link system.entity.FinalRout}'s pages.
 */
@Secured(value={"ROLE_ADMIN"})
@Controller
@RequestMapping(value = "/admin/finalrouts")
public class FinalRoutController {

    @Autowired
    private FinalRoutService finalRoutService;

    @Autowired
    private TrainService trainService;

    @Autowired
    private RoutService routService;

    @GetMapping
    public String getAdminFinalRoutsPage(Model model) {
        model.addAttribute("finalRoutForm", new FinalRout());
        model.addAttribute("trains", trainService.findAll());
        model.addAttribute("routs", routService.findAll());

        Set<FinalRout> finalRouts = finalRoutService.findAll();
        Map<Long, LocalTime> mapDeparture = new HashMap<>(); // Long - finalRout.id
        Map<Long, LocalTime> mapArrival = new HashMap<>(); // Long - finalRout.id

        for (FinalRout finalRout : finalRouts) {
            mapDeparture.put(finalRout.getId(), routService.getRoutSectionByRoutAndDepartureStation(finalRout.getRout(),
                            finalRout.getRout().getStartStation()).getDepartureTime());
            mapArrival.put(finalRout.getId(), routService.getRoutSectionByRoutAndDestinationStation(finalRout.getRout(),
                            finalRout.getRout().getEndStation()).getArrivalTime());
        }

        model.addAttribute("finalRouts", finalRouts);
        model.addAttribute("arrivals", mapArrival);
        model.addAttribute("departures", mapDeparture);

        return "routs";
    }

    @PostMapping(params = "change")
    public String changeFinalRout(@ModelAttribute FinalRout currentFinalRout, Model model) {
        currentFinalRout = finalRoutService.findById(currentFinalRout.getId());

        model.addAttribute("finalRoutForm", currentFinalRout);
        model.addAttribute("trains", trainService.findAll());
        model.addAttribute("routs", routService.findAll());

        Set<FinalRout> finalRouts = finalRoutService.findAll();
        Map<Long, LocalTime> mapDeparture = new HashMap<>(); // Long - finalRout.id
        Map<Long, LocalTime> mapArrival = new HashMap<>(); // Long - finalRout.id

        for (FinalRout finalRout : finalRouts) {
            mapDeparture.put(finalRout.getId(), routService.getRoutSectionByRoutAndDepartureStation(finalRout.getRout(),
                    finalRout.getRout().getStartStation()).getDepartureTime());
            mapArrival.put(finalRout.getId(), routService.getRoutSectionByRoutAndDestinationStation(finalRout.getRout(),
                    finalRout.getRout().getEndStation()).getArrivalTime());
        }

        model.addAttribute("finalRouts", finalRouts);
        model.addAttribute("arrivals", mapArrival);
        model.addAttribute("departures", mapDeparture);

        return "routs";
    }

    @PostMapping(params = "delete")
    public String deleteFinalRout(@ModelAttribute FinalRout currentFinalRout, Model model) {
        currentFinalRout = finalRoutService.findById(currentFinalRout.getId());

        finalRoutService.delete(currentFinalRout);

        model.addAttribute("finalRoutForm", currentFinalRout);
        model.addAttribute("trains", trainService.findAll());
        model.addAttribute("routs", routService.findAll());

        Set<FinalRout> finalRouts = finalRoutService.findAll();
        Map<Long, LocalTime> mapDeparture = new HashMap<>(); // Long - finalRout.id
        Map<Long, LocalTime> mapArrival = new HashMap<>(); // Long - finalRout.id

        for (FinalRout finalRout : finalRouts) {
            mapDeparture.put(finalRout.getId(), routService.getRoutSectionByRoutAndDepartureStation(finalRout.getRout(),
                    finalRout.getRout().getStartStation()).getDepartureTime());
            mapArrival.put(finalRout.getId(), routService.getRoutSectionByRoutAndDestinationStation(finalRout.getRout(),
                    finalRout.getRout().getEndStation()).getArrivalTime());
        }

        model.addAttribute("finalRouts", finalRouts);
        model.addAttribute("arrivals", mapArrival);
        model.addAttribute("departures", mapDeparture);

        return "routs";
    }

    @PostMapping(params = "save")
    public String saveFinalRout(@Valid @ModelAttribute FinalRout finalRout,
                                BindingResult bindingResult, Model model) {
        model.addAttribute("finalRoutForm", new FinalRout());
        model.addAttribute("trains", trainService.findAll());
        model.addAttribute("routs", routService.findAll());

        Set<FinalRout> finalRouts = finalRoutService.findAll();
        Map<Long, LocalTime> mapDeparture = new HashMap<>(); // Long - finalRout.id
        Map<Long, LocalTime> mapArrival = new HashMap<>(); // Long - finalRout.id

        for (FinalRout routs : finalRouts) {
            mapDeparture.put(routs.getId(), routService.getRoutSectionByRoutAndDepartureStation(routs.getRout(),
                    routs.getRout().getStartStation()).getDepartureTime());
            mapArrival.put(routs.getId(), routService.getRoutSectionByRoutAndDestinationStation(routs.getRout(),
                    routs.getRout().getEndStation()).getArrivalTime());
        }

        model.addAttribute("finalRouts", finalRouts);
        model.addAttribute("arrivals", mapArrival);
        model.addAttribute("departures", mapDeparture);

        if (bindingResult.hasErrors()) {
            return "routs";
        }

        if (finalRout.getId() != null){
            FinalRout finalRoutForChange = finalRoutService.findById(finalRout.getId());
            finalRoutForChange.setTrain(finalRout.getTrain());
            finalRoutForChange.setRout(finalRout.getRout());
            finalRoutForChange.setDate(finalRout.getDate());
            finalRoutService.save(finalRoutForChange);
            finalRout = finalRoutForChange;
        } else {
            finalRoutService.create(finalRout);
        }

        model.addAttribute("finalRouts", finalRoutService.findAll());
        model.addAttribute("arrivals", mapArrival.put(finalRout.getId(),routService.getRoutSectionByRoutAndDestinationStation(finalRout.getRout(),
                finalRout.getRout().getEndStation()).getArrivalTime()));
        model.addAttribute("departures", mapArrival.put(finalRout.getId(), routService.getRoutSectionByRoutAndDestinationStation(finalRout.getRout(),
                finalRout.getRout().getEndStation()).getArrivalTime()));

        return "routs";
    }
}
