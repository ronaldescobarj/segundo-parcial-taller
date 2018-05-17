package com.ucbcba.demo.Controllers;

import com.ucbcba.demo.entities.User;
import com.ucbcba.demo.services.SecurityService;
import com.ucbcba.demo.services.UserService;
import com.ucbcba.demo.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class LoginController {
    private final UserService userService;

    private final SecurityService securityService;

    private final CityService cityService;

    @Autowired
    public LoginController(UserService userService, SecurityService securityService, CityService cityService) {
        this.userService = userService;
        this.securityService = securityService;
        this.cityService = cityService;
    }

    //@Autowired
    //private UserValidator userValidator;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registrationInit(Model model) {
        model.addAttribute("cities", cityService.listAllCities());
        model.addAttribute("user", new User());
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String createNewUser(Model model, @Valid User user, BindingResult bindingResult) {
//        userValidator.validate(userForm, bindingResult);
        User emailExists= userService.findByEmail(user.getEmail());
        User userExists = userService.findByUsername(user.getUsername());
        String password = user.getPassword();
        String confirmation = user.getPasswordConfirm();
        if (emailExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the email provided");
            return "registration";
        }
        if (userExists != null) {
            bindingResult
                    .rejectValue("username", "error.user",
                            "There is already a user registered with the username provided");
            return "registration";
        }
        if(password.compareTo(confirmation)==1) {
            bindingResult
                    .rejectValue("passwordConfirm","error.user",
                            "Passwords does not match");
            return "registration";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", "Invalid information, please try again");
            return "registration";

        } else {
            userService.save(user);
            securityService.autologin(user.getUsername(), user.getPasswordConfirm());
            model.addAttribute("successMessage", "User has been registered successfully");
            //model.addAttribute("user", new User());
        }
        return "login";
        //return "registration";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("errorMessage", "Your username and password is invalid.");
            return "login";
        }
        if (logout != null) {
            model.addAttribute("logoutSuccessfully", "You have been logged out successfully.");
            return "login";
        }
        return "login";
    }

    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "home";
    }
}