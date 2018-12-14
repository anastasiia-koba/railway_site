package system.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import system.entity.UserData;
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

    private String attrForm = "userForm";
    private String regView = "registration";
    private String tab = "selectedTab";

    @GetMapping(value = "/registration")
    public String register(Model model){
        model.addAttribute(attrForm, new UserData());

        return regView;
    }

    @PostMapping(value = "/registration")
    public String register(@Valid @ModelAttribute("userForm") UserData userData,
                               BindingResult bindingResult, Model model){

        if (bindingResult.hasErrors()){
            return regView;
        } else if (userService.findByUsername(userData.getUsername()) != null) {
            bindingResult.rejectValue("username", "username.duplicate","Such username already exists.");
            return regView;
        }

        userService.createUser(userData);
        log.info("User {} has logged in", userData.getUsername());

        securityService.autoLogin(userData.getUsername(), userData.getPassword());

        model.addAttribute(attrForm, new UserProfile());
        model.addAttribute(tab, "profile-tab");
        return "userprofile";
    }

    @GetMapping(value = "/login")
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
        UserData user = userService.findByUsername(activeUser.getUsername());

        UserProfile profile = user.getUserProfile();
        if (profile == null) {
            profile = new UserProfile();
            profile.setUserData(user);
        }

        model.addAttribute(attrForm, profile);
        model.addAttribute(tab, "profile-tab");

        return "userprofile";
    }

    @PostMapping(value = "/user/profile")
    @ResponseBody
    public String changeUserprofile(@AuthenticationPrincipal User activeUser,
            @Valid @ModelAttribute("userForm") UserProfile userProfile,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Errors in fields";
        }

        UserData userData = userService.findByUsername(activeUser.getUsername());
        userProfile.setUserData(userData);
        if (userProfile.getId() == null) {
            userService.createProfile(userProfile);
        } else
            userService.saveProfile(userProfile);

        return "Changes were saved";
    }

    @GetMapping(value = "/user/account")
    public String getAccountPage(@AuthenticationPrincipal User activeUser, Model model) {
        UserData user = userService.findByUsername(activeUser.getUsername());
        model.addAttribute(attrForm, user);
        model.addAttribute(tab, "account-tab");

        return "account";
    }

    @PostMapping(value = "/user/account")
    @ResponseBody
    public String changeAccount(@Valid @ModelAttribute("userForm") UserData userData,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "Errors in fields";
        }
//TODO Error during managed flush [Validation failed
        userService.saveUser(userData);

        return "Changes were saved";
    }

    @GetMapping(value = "/user/tickets")
    public String getUserTickets(@AuthenticationPrincipal User activeUser, Model model) {
        UserData user = userService.findByUsername(activeUser.getUsername());
        UserProfile profile = user.getUserProfile();

        model.addAttribute("tickets", ticketService.findByUser(profile));
        model.addAttribute("selectedTab", "ticket-tab");

        return "userTickets";
    }

    @GetMapping(value = "/user/tickets", params = "pdf")
    public ModelAndView printTicket(@RequestParam("ticketId") Long ticketId) {
        return new ModelAndView("pdfView", "ticket", ticketService.findById(ticketId));
    }
}
