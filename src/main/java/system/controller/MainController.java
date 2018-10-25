package system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import system.entity.Station;
import system.service.api.StationService;

import java.util.List;

/**
 * Controller for {@link system.entity.Station}'s pages.
 */

@Controller
public class MainController {
    @Autowired
    public StationService stationService;


    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String welcome(Model model){
        model.addAttribute("stationFrom", stationService.findAll());
        model.addAttribute("stationTo", stationService.findAll());
        return "home";
    }
}
