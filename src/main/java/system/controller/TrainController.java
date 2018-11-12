package system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import system.entity.Train;
import system.service.api.FinalRoutService;
import system.service.api.TrainService;

import javax.validation.Valid;
import java.time.LocalDate;

/**
 * Controller for {@link system.entity.Train}'s pages.
 */
@Secured(value={"ROLE_ADMIN"})
@Controller
@RequestMapping(value = "/admin/trains")
public class TrainController {

    @Autowired
    private TrainService trainService;

    @Autowired
    private FinalRoutService finalRoutService;

    @GetMapping
    public String getAdminTrainsPage(Model model){
        model.addAttribute("finalRouts", finalRoutService.findAll());
        model.addAttribute("trains", trainService.findAll());
        model.addAttribute("trainForm", new Train());

        model.addAttribute("selectedTab", "oneTrain-tab");

        return "trains";
    }

    @RequestMapping(value = "/date", method = RequestMethod.POST)
    public String getTrainsByDatePage(@RequestParam("calendar") LocalDate date,
                                      Model model){
        model.addAttribute("finalRouts", finalRoutService.findByDate(date));
        model.addAttribute("trains", trainService.findAll());
        model.addAttribute("trainForm", new Train());

        model.addAttribute("selectedTab", "allTrains-tab");

        return "trains";
    }

    @RequestMapping(method = RequestMethod.POST, params = "change")
    public String changeTrain(@ModelAttribute Train train, Model model) {
        Train trainForChange = trainService.findById(train.getId());
        model.addAttribute("trainForm", trainForChange);

        model.addAttribute("finalRouts", finalRoutService.findAll());
        model.addAttribute("trains", trainService.findAll());

        model.addAttribute("selectedTab", "oneTrain-tab");

        return "trains";
    }

    @RequestMapping(method = RequestMethod.POST, params = "delete")
    public String deleteTrain(@ModelAttribute Train train, Model model) {
        Train trainForDelete = trainService.findById(train.getId());

        trainService.delete(trainForDelete);

        model.addAttribute("finalRouts", finalRoutService.findAll());
        model.addAttribute("trains", trainService.findAll());
        model.addAttribute("trainForm", new Train());

        model.addAttribute("selectedTab", "oneTrain-tab");

        return "trains";
    }

    @RequestMapping(method = RequestMethod.POST, params = "save")
    public String addTrain(@Valid @ModelAttribute("trainForm") Train train,
                           BindingResult bindingResult, Model model) {
        model.addAttribute("trains", trainService.findAll());
        model.addAttribute("finalRouts", finalRoutService.findAll());

        model.addAttribute("selectedTab", "oneTrain-tab");

        if (bindingResult.hasErrors()) {
            return "admin";
        }

        if (train.getId() != null){
            Train trainForChange = trainService.findById(train.getId());
            trainForChange.setTrainName(train.getTrainName());
            trainForChange.setPlacesNumber(train.getPlacesNumber());
            trainService.save(trainForChange);
        } else {
            trainService.create(train);
        }

        model.addAttribute("trainForm", new Train());
        model.addAttribute("trains", trainService.findAll());

        return "trains";
    }
}
