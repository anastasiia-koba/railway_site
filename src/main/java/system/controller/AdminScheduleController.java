package system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import system.service.api.ScheduleSender;

/**
 * Controller for {@link system.service.impl.ScheduleSenderImpl}'s pages.
 */
@Secured(value={"ROLE_ADMIN"})
@Controller
@RequestMapping(value = "/admin")
public class AdminScheduleController {

    @Autowired
    private ScheduleSender scheduleSender;

    @GetMapping(value = "/schedule")
    public String getRealSchedulePage() {
        return "realSchedule";
    }

    @PostMapping(value = "/schedule")
    public String sendText() {
        scheduleSender.sendMessage("Hello MQ");
        return "realSchedule";
    }
}
