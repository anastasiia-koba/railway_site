package system.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import system.entity.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import system.service.api.SecurityService;
import system.service.api.TicketService;
import system.service.api.UserService;

import javax.validation.Valid;


/**
 * Controller for {@link system.entity.UserProfile}'s pages.
 */
@Slf4j
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private TicketService ticketService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String register(Model model){
        model.addAttribute("userForm", new UserProfile());

        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String register(@Valid @ModelAttribute("userForm") UserProfile userForm,
                               BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            return "registration";
        } else if (userService.findByUsername(userForm.getUsername()) != null) {
            bindingResult.rejectValue("username", "username.duplicate","Such username already exists.");
            return "registration";
        }

        userService.save(userForm);
        log.info("User {} has logged in", userForm.getUsername());

        securityService.autoLogin(userForm.getUsername(), userForm.getConfirmPassword());

        return "redirect:/home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout){
        if (error != null){
            model.addAttribute("error", "Login or password is incorrect.");
            log.info("Wrong attempt to log in");
        }

        if (logout != null){
            model.addAttribute("message", "Logged out successfully.");
        }

        return "login";
    }

    @GetMapping(value = "/user/profile")
    public String getUserprofilePage(@AuthenticationPrincipal User activeUser, Model model) {
        UserProfile user = userService.findByUsername(activeUser.getUsername());
        model.addAttribute("userForm", user);
        model.addAttribute("selectedTab", "profile-tab");

        return "userprofile";
    }

    @PostMapping(value = "/user/profile")
    @ResponseBody
    public String changeUserprofile(@Valid @ModelAttribute("userForm") UserProfile user,
                                  BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "Errors in fileds";
        }

        userService.save(user);

        return "Success";
    }

    @GetMapping(value = "/user/tickets")
    public String getUserTickets(@AuthenticationPrincipal User activeUser, Model model) {
        UserProfile user = userService.findByUsername(activeUser.getUsername());
        model.addAttribute("tickets", ticketService.findByUser(user));
        model.addAttribute("selectedTab", "ticket-tab");

        return "userTickets";
    }
}
