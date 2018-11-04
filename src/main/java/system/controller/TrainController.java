package system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import system.entity.FinalRout;
import system.service.api.FinalRoutService;
import system.service.api.RoutService;
import system.service.api.StationService;
import system.service.api.TrainService;

/**
 * Controller for {@link system.entity.Train}'s pages.
 */
@Controller
@RequestMapping(value = "/admin/trains")
public class TrainController {
    @Autowired
    private StationService stationService;

    @Autowired
    private TrainService trainService;

    @Autowired
    private RoutService routService;

    @Autowired
    private FinalRoutService finalRoutService;

    @GetMapping
    public String getAdminTrainsPage(Model model){
        model.addAttribute("finalRouts", finalRoutService.findAll());

        return "trains";
    }
}
