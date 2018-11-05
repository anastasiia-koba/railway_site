package system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import system.entity.FinalRout;
import system.entity.Station;
import system.service.api.FinalRoutService;
import system.service.api.RoutService;
import system.service.api.StationService;
import system.service.api.TrainService;

import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Controller {@link FinalRout} for {@link system.entity.Station} pages.
 */
@Controller
public class ScheduleController {

    @Autowired
    private StationService stationService;

    @Autowired
    private TrainService trainService;

    @Autowired
    private RoutService routService;

    @Autowired
    private FinalRoutService finalRoutService;

    @RequestMapping(value = "/schedule", method = RequestMethod.GET)
    public String getSchedule(Model model){
        model.addAttribute("stations", stationService.findAll());
        model.addAttribute("station", new Station());

        return "schedule";
    }

    @RequestMapping(value = "/schedule", method = RequestMethod.POST)
    public String getScheduleByStation(@ModelAttribute Station station,
                                       @RequestParam("date") Date date, Model model){
        model.addAttribute("stations", stationService.findAll());

        station = stationService.findByName(station.getStationName());
        model.addAttribute("station", station);

        Set<FinalRout> finalRoutSet = finalRoutService.findByStationAndDate(station, date);
        model.addAttribute("trains", finalRoutSet);

        return "schedule";
    }
}
