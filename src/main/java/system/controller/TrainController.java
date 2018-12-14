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
import java.util.List;

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

    private String view = "trains";

    @GetMapping
    public String getAdminTrainsPage(Model model){
        model.addAttribute("finalRouts", finalRoutService.findAll());
        model.addAttribute("trainForm", new Train());

        model.addAttribute("selectedTab", "train-tab");

        return view;
    }

    @RequestMapping(value = "/date", method = RequestMethod.POST)
    public String getTrainsByDatePage(@RequestParam("calendar") LocalDate date,
                                      Model model){
        model.addAttribute("finalRouts", finalRoutService.findByDate(date));
        model.addAttribute("trains", trainService.findAll());
        model.addAttribute("trainForm", new Train());

        model.addAttribute("selectedTab", "trains-tab");

        return view;
    }

    @GetMapping(params = "list")
    @ResponseBody
    public List<Train> getListTrains() {
        return trainService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, params = "change")
    @ResponseBody
    public Train changeTrain(@RequestParam("trainId") Long trainId) {
        return trainService.findById(trainId);
    }

    @RequestMapping(method = RequestMethod.POST, params = "delete")
    @ResponseBody
    public String deleteTrain(@RequestParam("trainId") Long trainId) {
        Train trainForDelete = trainService.findById(trainId);

        trainService.delete(trainForDelete);

        return "Train " + trainForDelete.getTrainName() + " was deleted";
    }

    @RequestMapping(method = RequestMethod.POST, params = "save")
    @ResponseBody
    public String saveTrain(@Valid @ModelAttribute("trainForm") Train train,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Fields are required";
        }

        trainService.save(train);

        return "Train " + train.getTrainName() + " was saved";
    }
}
