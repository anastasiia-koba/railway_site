package system.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import system.entity.FinalRout;
import system.entity.Station;
import system.service.api.FinalRoutService;
import system.service.api.ScheduleSender;
import system.service.api.StationService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Set;

/**
 * Controller for {@link system.service.impl.ScheduleSenderImpl}'s pages.
 */
@Secured(value={"ROLE_ADMIN"})
@Controller
@RequestMapping(value = "/admin")
public class AdminScheduleController {

    @Autowired
    private StationService stationService;

    @Autowired
    private FinalRoutService finalRoutService;

    @Autowired
    private ScheduleSender scheduleSender;

    private static final int STATUS_ON_TIME = 1;
    private static final int STATUS_DELAYED = 2;
    private static final int STATUS_CANCELED = 3;

    @GetMapping(value = "/schedule")
    public String getRealSchedulePage(Model model) {
        model.addAttribute("stations", stationService.findAll());
        model.addAttribute("station", new Station());

        model.addAttribute("arrivals", null);
        model.addAttribute("departures", null);

        return "realSchedule";
    }

    @GetMapping(value = "/schedule/list")
    @ResponseBody
    public String getScheduleList(@RequestParam("station") Station station,
                                  @RequestParam("date")
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        station = stationService.findByName(station.getStationName());
        Set<FinalRout> finalRoutSet = finalRoutService.findByStationAndDate(station, date);

        Map<Long, LocalTime> mapDeparture = finalRoutService.getMapDepartureByStation(finalRoutSet, station);
        Map<Long, LocalTime> mapArrival = finalRoutService.getMapArrivalByStation(finalRoutSet, station);


        JsonArray jsonValues = new JsonArray();

        JsonObject json;
        for (FinalRout finalRout: finalRoutSet) {
            json = new JsonObject();

            json.addProperty("id", finalRout.getId());
            json.addProperty("routName", finalRout.getRout().getRoutName());
            json.addProperty("startStation", finalRout.getRout().getStartStation().getStationName());
            json.addProperty("endStation", finalRout.getRout().getEndStation().getStationName());
            json.addProperty("departureTime", mapDeparture.get(finalRout.getId()).toString());
            json.addProperty("arrivalTime", mapArrival.get(finalRout.getId()).toString());

            jsonValues.add(json);
        }
        return jsonValues.toString();
    }

    private JsonObject formMessage(Long stationId, Long routId, LocalDate date, int status) {
        JsonObject json = new JsonObject();
        json.addProperty("stationId", stationId);
        json.addProperty("finalRoutId", routId);
        json.addProperty("date", date.toString());
        json.addProperty("status", status);

        return json;
    }

    @PostMapping(value = "/schedule/sendOnTime")
    @ResponseBody
    public String sendOnTime(@RequestParam("station") Station station,
                           @RequestParam("rout") Long routId,
                           @RequestParam("date")
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        station = stationService.findByName(station.getStationName());

        JsonObject json = formMessage(station.getId(), routId, date, STATUS_ON_TIME);

        scheduleSender.sendMessage(json.toString());
        return "Send 'On Time' to Tablo for station "+station.getStationName()+" on date "+date;
    }

    @PostMapping(value = "/schedule/sendDelayed")
    @ResponseBody
    public String sendDelayed(@RequestParam("station") Station station,
                              @RequestParam("rout") Long routId,
                              @RequestParam("date")
                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        station = stationService.findByName(station.getStationName());

        JsonObject json = formMessage(station.getId(), routId, date, STATUS_DELAYED);

        scheduleSender.sendMessage(json.toString());
        return "Send 'Delayed' to Tablo for station "+station.getStationName()+" on date "+date;
    }

    @PostMapping(value = "/schedule/sendCanceled")
    @ResponseBody
    public String sendCanceled(@RequestParam("station") Station station,
                           @RequestParam("rout") Long routId,
                           @RequestParam("date")
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        station = stationService.findByName(station.getStationName());

        JsonObject json = formMessage(station.getId(), routId, date, STATUS_CANCELED);

        scheduleSender.sendMessage(json.toString());
        return "Send 'Canceled' to Tablo for station "+station.getStationName()+" on date "+date;
    }
}
