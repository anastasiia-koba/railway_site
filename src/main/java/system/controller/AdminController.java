package system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import system.entity.Rout;
import system.entity.RoutSection;
import system.entity.Station;
import system.service.api.RoutSectionService;
import system.service.api.RoutService;
import system.service.api.StationService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * Controller for {@link system.entity.Station, system.entity.Rout}'s pages.
 */
@Secured(value={"ROLE_ADMIN"})
@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    @Autowired
    private StationService stationService;

    @Autowired
    private RoutSectionService routSectionService;

    @Autowired
    private RoutService routService;

    @GetMapping
    public String getAdminPage(Model model){
        model.addAttribute("stationForm", new Station());
        model.addAttribute("stations", stationService.findAll());

        model.addAttribute("routForm", new Rout());
        model.addAttribute("routs", routService.findAll());

        model.addAttribute("sections", null);
        model.addAttribute("searchSections", null);
        model.addAttribute("stationsFrom", stationService.findAll());
        model.addAttribute("stationsTo", stationService.findAll());
        model.addAttribute("routSectionForm", new RoutSection());
        model.addAttribute("routForSection", null);

        model.addAttribute("selectedTab", "station-tab");

        return "admin";
    }

    @RequestMapping(value = "/stations", method = RequestMethod.POST, params = "change")
    public String changeStation(@ModelAttribute Station station, Model model) {
        Station stationForChange = stationService.findById(station.getId());
        model.addAttribute("stationForm", stationForChange);

        model.addAttribute("stations", stationService.findAll());

        model.addAttribute("routForm", new Rout());
        model.addAttribute("routs", routService.findAll());

        model.addAttribute("stationsFrom", stationService.findAll());
        model.addAttribute("stationsTo", stationService.findAll());
        model.addAttribute("sections", null);
        model.addAttribute("searchSections", null);
        model.addAttribute("routSectionForm", new RoutSection());
        model.addAttribute("routForSection", null);

        model.addAttribute("selectedTab", "station-tab");

        return "admin";
    }

    @RequestMapping(value = "/stations", method = RequestMethod.POST, params = "delete")
    public String deleteStation(@ModelAttribute Station station, Model model) {
        Station stationForDelete = stationService.findById(station.getId());

        stationService.delete(stationForDelete);

        model.addAttribute("stationForm", new Station());
        model.addAttribute("stations", stationService.findAll());

        model.addAttribute("routForm", new Rout());
        model.addAttribute("routs", routService.findAll());

        model.addAttribute("stationsFrom", stationService.findAll());
        model.addAttribute("stationsTo", stationService.findAll());
        model.addAttribute("sections", null);
        model.addAttribute("searchSections", null);
        model.addAttribute("routSectionForm", new RoutSection());
        model.addAttribute("routForSection", null);

        model.addAttribute("selectedTab", "station-tab");

        return "admin";
    }

    @RequestMapping(value = "/stations", method = RequestMethod.POST, params = "save")
    public String addStation(@Valid @ModelAttribute("stationForm") Station station,
                           BindingResult bindingResult, Model model) {
        model.addAttribute("stations", stationService.findAll());

        model.addAttribute("routForm", new Rout());
        model.addAttribute("routs", routService.findAll());

        model.addAttribute("stationsFrom", stationService.findAll());
        model.addAttribute("stationsTo", stationService.findAll());
        model.addAttribute("sections", null);
        model.addAttribute("searchSections", null);
        model.addAttribute("routSectionForm", new RoutSection());
        model.addAttribute("routForSection", null);

        model.addAttribute("selectedTab", "station-tab");

        if (bindingResult.hasErrors()) {
            return "admin";
        }

        stationService.save(station);

        model.addAttribute("stationForm", new Station());
        model.addAttribute("stations", stationService.findAll());

        return "admin";
    }

    @RequestMapping(value = "/routs", method = RequestMethod.POST, params = "change")
    public String changeRout(@ModelAttribute Rout rout, Model model) {
        Rout routForChange = routService.findById(rout.getId());
        model.addAttribute("routForm", routForChange);

        model.addAttribute("routs", routService.findAll());

        model.addAttribute("stationForm", new Station());
        model.addAttribute("stations", stationService.findAll());

        model.addAttribute("stationsFrom", stationService.findAll());
        model.addAttribute("stationsTo", stationService.findAll());
        model.addAttribute("sections", null);
        model.addAttribute("searchSections", null);
        model.addAttribute("routSectionForm", new RoutSection());
        model.addAttribute("routForSection", null);

        model.addAttribute("selectedTab", "rout-tab");

        return "admin";
    }

    @RequestMapping(value = "/routs", method = RequestMethod.POST, params = "delete")
    public String deleteRout(@Valid @ModelAttribute Rout rout, Model model) {
        Rout routForChange = routService.findById(rout.getId());

        routService.delete(routForChange);

        model.addAttribute("stationForm", new Station());
        model.addAttribute("stations", stationService.findAll());

        model.addAttribute("routForm", new Rout());
        model.addAttribute("routs", routService.findAll());

        model.addAttribute("stationsFrom", stationService.findAll());
        model.addAttribute("stationsTo", stationService.findAll());
        model.addAttribute("sections", null);
        model.addAttribute("searchSections", null);
        model.addAttribute("routSectionForm", new RoutSection());
        model.addAttribute("routForSection", null);

        model.addAttribute("selectedTab", "rout-tab");

        return "admin";
    }

    @RequestMapping(value = "/routs", method = RequestMethod.POST, params = "save")
    public String addRout(@Valid @ModelAttribute("routForm") Rout rout,
                          BindingResult bindingResult, Model model) {
        model.addAttribute("stationForm", new Station());
        model.addAttribute("stations", stationService.findAll());

        model.addAttribute("routs", routService.findAll());

        model.addAttribute("stationsFrom", stationService.findAll());
        model.addAttribute("stationsTo", stationService.findAll());
        model.addAttribute("sections", null);
        model.addAttribute("searchSections", null);
        model.addAttribute("routSectionForm", new RoutSection());
        model.addAttribute("routForSection", null);

        model.addAttribute("selectedTab", "rout-tab");

        if (bindingResult.hasErrors()) {
            return "admin";
        }

        rout.setStartStation(stationService.findByName(rout.getStartStation().getStationName()));
        rout.setEndStation(stationService.findByName(rout.getEndStation().getStationName()));

        routService.save(rout);

        model.addAttribute("routForm", new Rout());
        model.addAttribute("routs", routService.findAll());

        return "admin";
    }

    @RequestMapping(value = "/sections", method = RequestMethod.POST)
    public String getRoutSectionsForRout(@RequestParam("routForSearch") Long routId,
                                         Model model) {
        model.addAttribute("stations", stationService.findAll());
        model.addAttribute("stationForm", new Station());

        model.addAttribute("routForm", new Rout());
        model.addAttribute("routs", routService.findAll());

        model.addAttribute("searchSections", null);
        model.addAttribute("stationsFrom", stationService.findAll());
        model.addAttribute("stationsTo", stationService.findAll());
        model.addAttribute("routSectionForm", new RoutSection());
        model.addAttribute("routForSection", null);

        model.addAttribute("selectedTab", "section-tab");

        Rout rout = routService.findById(routId);
        rout = routService.findById(rout.getId());
        Set<RoutSection> routSections = routService.getRoutSectionInRout(rout);

        model.addAttribute("sections", routSections);
        model.addAttribute("routForSection", rout);

        return "admin";
    }

    @RequestMapping(value = "/sections", method = RequestMethod.POST, params = "change")
    public String changeRoutSection(@ModelAttribute("section") RoutSection routSection,
                                    @RequestParam("routId") Long routId, Model model) {
        RoutSection sectionForChange = routSectionService.findById(routSection.getId());
        model.addAttribute("routSectionForm", sectionForChange);

        Rout rout = routService.findById(routId);

        model.addAttribute("stationForm", new Station());
        model.addAttribute("stations", stationService.findAll());

        model.addAttribute("routForm", new Rout());
        model.addAttribute("routs", routService.findAll());

        model.addAttribute("searchSections", null);
        model.addAttribute("stationsFrom", stationService.findAll());
        model.addAttribute("stationsTo", stationService.findAll());
        model.addAttribute("sections", routService.getRoutSectionInRout(rout));
        model.addAttribute("routForSection", rout);

        model.addAttribute("selectedTab", "section-tab");

        return "admin";
    }

    @RequestMapping(value = "/sections", method = RequestMethod.POST, params = "delete")
    public String deleteRoutSectionFromRout(@ModelAttribute RoutSection routSection,
                                    @RequestParam("routId") Long routId, Model model) {
        RoutSection sectionForDelete = routSectionService.findById(routSection.getId());
        Rout rout = routService.findById(routId);

        rout.getRoutSections().remove(sectionForDelete);
        routService.save(rout);

        model.addAttribute("stationForm", new Station());
        model.addAttribute("stations", stationService.findAll());

        model.addAttribute("routForm", new Rout());
        model.addAttribute("routs", routService.findAll());

        model.addAttribute("searchSections", null);
        model.addAttribute("stationsFrom", stationService.findAll());
        model.addAttribute("stationsTo", stationService.findAll());
        model.addAttribute("routSectionForm", new RoutSection());


        model.addAttribute("sections", routService.getRoutSectionInRout(rout));
        model.addAttribute("routForSection", rout);

        model.addAttribute("selectedTab", "section-tab");

        return "admin";
    }

    @RequestMapping(value = "/sections", method = RequestMethod.POST, params = "save")
    public String saveRoutSection(@Valid @ModelAttribute("routSectionForm") RoutSection routSection,
                                 @RequestParam("routId") Long routId,
                                 BindingResult bindingResult, Model model) {
        model.addAttribute("stationForm", new Station());
        model.addAttribute("stations", stationService.findAll());

        model.addAttribute("stationsFrom", stationService.findAll());
        model.addAttribute("stationsTo", stationService.findAll());

        model.addAttribute("routForm", new Rout());
        model.addAttribute("routs", routService.findAll());

        Rout rout = routService.findById(routId);
        model.addAttribute("routForSection", rout);
        model.addAttribute("sections", routService.getRoutSectionInRout(rout));
        model.addAttribute("searchSections", null);

        model.addAttribute("selectedTab", "section-tab");

        if (bindingResult.hasErrors()) {
            return "admin";
        }

        Station departure = stationService.findByName(routSection.getDeparture().getStationName());
        routSection.setDeparture(departure);
        Station destination = stationService.findByName(routSection.getDestination().getStationName());
        routSection.setDestination(destination);

        routSectionService.save(routSection);

        model.addAttribute("routSectionForm", new RoutSection());
        model.addAttribute("sections", routService.getRoutSectionInRout(rout));

        return "admin";
    }

    @RequestMapping(value = "/sections/all", method = RequestMethod.POST)
    public String getRoutSections(@RequestParam("rout.id") Long routId,
                                  @RequestParam("sectionFrom") Station sectionFrom,
                                  @RequestParam("sectionTo") Station sectionTo,
                                  Model model) {
        model.addAttribute("stations", stationService.findAll());
        model.addAttribute("stationForm", new Station());

        model.addAttribute("routForm", new Rout());
        model.addAttribute("routs", routService.findAll());

        model.addAttribute("stationsFrom", stationService.findAll());
        model.addAttribute("stationsTo", stationService.findAll());
        model.addAttribute("routSectionForm", new RoutSection());
        model.addAttribute("routForSection", null);

        model.addAttribute("selectedTab", "section-tab");

        Rout rout = routService.findById(routId);
        rout = routService.findById(rout.getId());
        Set<RoutSection> routSections = routService.getRoutSectionInRout(rout);

        model.addAttribute("sections", routSections);
        model.addAttribute("routForSection", rout);

        Station departure = stationService.findByName(sectionFrom.getStationName());
        Station destination = stationService.findByName(sectionTo.getStationName());

        List<RoutSection> searchSections = routSectionService.findByDepartureAndDestination(departure, destination);
        model.addAttribute("searchSections", searchSections);

        return "admin";
    }

    @RequestMapping(value = "/sections/all", method = RequestMethod.POST, params = "add")
    public String addRoutSection(@RequestParam("sectionId") Long routSectionId,
                                    @RequestParam("routId") Long routId, Model model) {
        RoutSection sectionForAdd = routSectionService.findById(routSectionId);

        Rout rout = routService.findById(routId);
        rout.getRoutSections().add(sectionForAdd);
        routService.save(rout);

        List<RoutSection> searchSections = routSectionService.findByDepartureAndDestination(sectionForAdd.getDeparture(),
                                                                                            sectionForAdd.getDestination());
        model.addAttribute("searchSections", searchSections);

        model.addAttribute("stationForm", new Station());
        model.addAttribute("stations", stationService.findAll());

        model.addAttribute("routForm", new Rout());
        model.addAttribute("routs", routService.findAll());

        model.addAttribute("routSectionForm", new RoutSection());
        model.addAttribute("stationsFrom", stationService.findAll());
        model.addAttribute("stationsTo", stationService.findAll());
        model.addAttribute("sections", routService.getRoutSectionInRout(rout));
        model.addAttribute("routForSection", rout);

        model.addAttribute("selectedTab", "section-tab");

        return "admin";
    }

    @RequestMapping(value = "/sections/all", method = RequestMethod.POST, params = "delete")
    public String deleteRoutSection(@RequestParam("sectionId") Long routSectionId,
                                 @RequestParam("routId") Long routId, Model model) {
        RoutSection sectionForDelete = routSectionService.findById(routSectionId);

        Rout rout = routService.findById(routId);

        List<Rout> listRouts = routService.findByRoutSection(sectionForDelete);
        for (Rout deleteFrom : listRouts) {
            deleteFrom.getRoutSections().remove(sectionForDelete);
            routService.save(deleteFrom);
        }

        routSectionService.delete(sectionForDelete);

        List<RoutSection> searchSections = routSectionService.findByDepartureAndDestination(sectionForDelete.getDeparture(),
                sectionForDelete.getDestination());
        model.addAttribute("searchSections", searchSections);

        model.addAttribute("stationForm", new Station());
        model.addAttribute("stations", stationService.findAll());

        model.addAttribute("routForm", new Rout());
        model.addAttribute("routs", routService.findAll());

        model.addAttribute("routSectionForm", new RoutSection());
        model.addAttribute("stationsFrom", stationService.findAll());
        model.addAttribute("stationsTo", stationService.findAll());
        model.addAttribute("sections", routService.getRoutSectionInRout(rout));
        model.addAttribute("routForSection", rout);

        model.addAttribute("selectedTab", "section-tab");

        return "admin";
    }
}
