package system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import system.entity.FinalRout;
import system.entity.Ticket;
import system.entity.UserProfile;
import system.entity.Train;
import system.service.api.*;

import javax.validation.Valid;
import java.util.Set;

/**
 * Controller {@link UserProfile} in {@link Train} pages.
 */
@Controller
@RequestMapping(value = "/admin/passengers")
public class PassengerController {

    @Autowired
    private UserService userService;

    @Autowired
    private TrainService trainService;

    @Autowired
    private RoutService routService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private FinalRoutService finalRoutService;

    @GetMapping
    public String getPassengerPage(Model model){
        model.addAttribute("trains", trainService.findAll());
        model.addAttribute("routs", routService.findAll());
        model.addAttribute("finalRoutForm", new FinalRout());

        return "passengers";
    }

    @PostMapping
    public String getPassengerPage(@Valid @ModelAttribute("finalRoutForm") FinalRout rout,
                                   BindingResult bindingResult, Model model){

        model.addAttribute("trains", trainService.findAll());
        model.addAttribute("routs", routService.findAll());

        if (bindingResult.hasErrors()) {
            return "passengers";
        }

        rout = finalRoutService.findByRoutAndTrainAndDate(rout.getRout(), rout.getTrain(), rout.getDate());
        model.addAttribute("tickets", ticketService.findByFinalRout(rout));
        model.addAttribute("finalRoutForm", new FinalRout());

        return "passengers";
    }
}
