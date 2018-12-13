package system.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
import java.util.stream.Collectors;

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

    @GetMapping(value = "/stations")
    public String getStationsPage(Model model) {
        model.addAttribute("stationForm", new Station());
        model.addAttribute("stations", stationService.findAll());
        model.addAttribute("selectedTab", "station-tab");

        return "stations";
    }

    @GetMapping(value = "/routs")
    public String getRoutsPage(Model model) {
        model.addAttribute("stationsFrom", stationService.findAll());
        model.addAttribute("stationsTo", stationService.findAll());

        model.addAttribute("routForm", new Rout());
        model.addAttribute("routs", routService.findAll());
        model.addAttribute("selectedTab", "rout-tab");

        return "routs";
    }

    @GetMapping(value = "/sections")
    public String getSectionsPage(Model model) {
        model.addAttribute("routs", routService.findAll());
        model.addAttribute("sections", null);
        model.addAttribute("searchSections", null);
        model.addAttribute("stationsFrom", stationService.findAll());
        model.addAttribute("stationsTo", stationService.findAll());
        model.addAttribute("sectionForm", new RoutSection());
        model.addAttribute("routForSection", null);

        model.addAttribute("selectedTab", "section-tab");

        return "sections";
    }

    @GetMapping(value = "/stations", params = "list")
    @ResponseBody
    public List<Station> getListStations() {
        return stationService.findAll();
    }

    @PostMapping(value = "/stations", params = "change")
    @ResponseBody
    public Station changeStation(@RequestParam("stationId") Long stationId) {
        Station stationForChange = stationService.findById(stationId);

        return stationForChange;
    }

    @PostMapping(value = "/stations", params = "delete")
    @ResponseBody
    public String deleteStation(@RequestParam("stationId") Long stationId) {
        Station stationForDelete = stationService.findById(stationId);

        stationService.delete(stationForDelete);

        return stationForDelete.getStationName();
    }

    @PostMapping(value = "/stations", params = "save")
    @ResponseBody
    public String saveStation(@Valid @ModelAttribute("stationForm") Station station,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Save station " + station.getStationName() + " failed: name must be not empty.";
        } else if (stationService.findByName(station.getStationName()) != null) {
            return "Save station " + station.getStationName() + " failed: such name already exists.";
        }

        stationService.save(station);

        return  station.getStationName();
    }

    @GetMapping(value = "/routs", params = "list")
    @ResponseBody
    public List<Rout> getListRouts() {
        return routService.findAll();
    }

    @PostMapping(value = "/routs", params = "change")
    @ResponseBody
    public Rout changeRout(@RequestParam("routId") Long routId) {
        Rout routForChange = routService.findById(routId);

        return routForChange;
    }

    @PostMapping(value = "/routs", params = "delete")
    @ResponseBody
    public String deleteRout(@RequestParam("routId") Long routId) {
        Rout routForChange = routService.findById(routId);

        routService.delete(routForChange);

        return routForChange.getRoutName();
    }

    @PostMapping(value = "/routs", params = "save")
    @ResponseBody
    public String saveRout(@Valid @ModelAttribute("routForm") Rout rout,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Fields are required";
        }

        rout.setStartStation(stationService.findByName(rout.getStartStation().getStationName()));
        rout.setEndStation(stationService.findByName(rout.getEndStation().getStationName()));

        routService.save(rout);

        return rout.getRoutName();
    }

    @GetMapping(value = "/sections/rout", params = "list")
    @ResponseBody
    public String getRoutSectionsForRout(@RequestParam("routForSearch") Long routId) {
        Rout rout = routService.findById(routId);
        List<RoutSection> routSections = routService.getRoutSectionInRout(rout);

        String message = routService.isRoutWellBuilt(rout) ? "Rout was built well" : "Rout has errors";
        JsonObject jsonObject = new JsonObject();
        JsonArray jsonValues = new JsonArray();

        jsonObject.addProperty("buildMessage", message);

        JsonObject json;
        for (RoutSection routSection: routSections) {
            json = new JsonObject();

            json.addProperty("id", routSection.getId());
            json.addProperty("departure", routSection.getDeparture().getStationName());
            json.addProperty("destination", routSection.getDestination().getStationName());
            json.addProperty("departureTime", routSection.getDepartureTime().toString());
            json.addProperty("arrivalTime", routSection.getArrivalTime().toString());
            json.addProperty("distance", routSection.getDistance());
            json.addProperty("price", routSection.getPrice());

            jsonValues.add(json);
        }

        jsonObject.addProperty("sections", jsonValues.toString());

        return jsonObject.toString();
    }

    @PostMapping(value = "/sections/form", params = "back")
    @ResponseBody
    public String formBackRoutSection(@RequestParam("rout") Long routId,
                                      @RequestParam("backRout") Long backId) {
        Rout rout = routService.findById(routId);
        Rout back = routService.findById(backId);

        String result = routService.formBackRout(rout, back);

        return result;
    }

    @PostMapping(value = "/sections", params = "change")
    @ResponseBody
    public RoutSection changeRoutSection(@RequestParam("sectionId") Long sectionId) {
        RoutSection sectionForChange = routSectionService.findById(sectionId);
        return sectionForChange;
    }

    @PostMapping(value = "/sections", params = "delete")
    @ResponseBody
    public String deleteRoutSectionFromRout(@RequestParam("sectionId") Long sectionId,
                                            @RequestParam("routId") Long routId) {
        RoutSection sectionForDelete = routSectionService.findById(sectionId);
        Rout rout = routService.findById(routId);

        rout.getRoutSections().remove(sectionForDelete);
        routService.save(rout);

        return sectionForDelete.getDeparture().getStationName()+" "+
                sectionForDelete.getDestination().getStationName();
    }

    @PostMapping(value = "/sections", params = "save")
    @ResponseBody
    public String saveRoutSection(@Valid @ModelAttribute("sectionForm") RoutSection routSection,
                                 @RequestParam("routId") Long routId,
                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Fields are required";
        }
        Rout rout = routService.findById(routId);

        Station departure = stationService.findByName(routSection.getDeparture().getStationName());
        routSection.setDeparture(departure);
        Station destination = stationService.findByName(routSection.getDestination().getStationName());
        routSection.setDestination(destination);

        if (routSection.getId() == null) {
            if (routService.getRoutSectionByRoutAndDepartureStation(rout, departure) != null) {
                return "Such departure already exists in rout.";
            }
            if (routService.getRoutSectionByRoutAndDestinationStation(rout, destination) != null) {
                return "Such destination already exists in rout.";
            }
        }

        routSectionService.save(routSection);

        if (!rout.getRoutSections().stream().map(e->e.getId()).collect(Collectors.toList()).contains(routSection.getId())) {
            rout.getRoutSections().add(routSection);
            routService.save(rout);
        }

        return routSection.getDeparture().getStationName()+" "+routSection.getDestination().getStationName();
    }

    @GetMapping(value = "/sections/list")
    @ResponseBody
    public List<RoutSection> getRoutSections(@RequestParam("sectionFrom") Station sectionFrom,
                                             @RequestParam("sectionTo") Station sectionTo) {
        Station departure = stationService.findByName(sectionFrom.getStationName());
        Station destination = stationService.findByName(sectionTo.getStationName());

        List<RoutSection> searchSections = routSectionService.findByDepartureAndDestination(departure, destination);

        return searchSections;
    }

    @PostMapping(value = "/sections/all", params = "add")
    @ResponseBody
    public String addRoutSection(@RequestParam("sectionId") Long routSectionId,
                                 @RequestParam("routId") Long routId) {
        RoutSection sectionForAdd = routSectionService.findById(routSectionId);

        Rout rout = routService.findById(routId);

        if (routService.getRoutSectionByRoutAndDepartureStation(rout, sectionForAdd.getDeparture()) != null) {
            return "Such departure already exists in rout.";
        }
        if (routService.getRoutSectionByRoutAndDestinationStation(rout, sectionForAdd.getDestination()) != null) {
            return "Such destination already exists in rout.";
        }

        rout.getRoutSections().add(sectionForAdd);
        routService.save(rout);

        return sectionForAdd.getDeparture().getStationName()+" "+
                sectionForAdd.getDestination().getStationName();
    }

    @PostMapping(value = "/sections/all", params = "delete")
    @ResponseBody
    public String deleteRoutSection(@RequestParam("sectionId") Long routSectionId) {
        RoutSection sectionForDelete = routSectionService.findById(routSectionId);

        routSectionService.delete(sectionForDelete);

        return sectionForDelete.getDeparture().getStationName()+" "+
                sectionForDelete.getDestination().getStationName();
    }
}
