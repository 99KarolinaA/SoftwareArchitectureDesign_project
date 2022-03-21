package mk.ukim.finki.dians.parking_application.web.controller;

import mk.ukim.finki.dians.parking_application.model.exceptions.InvalidUserCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * MVC Controller which provides login page.
 * The controller is mapped to the "/login" path.
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    /**
     * Response to a GET request
     * @param model object from the Model class which makes
     *              parameters accessible to the view page
     * @return html view of the login page
     */
    @GetMapping
    public String getLoginPage(Model model) {

        model.addAttribute("bodyContent", "login");
        return "master-template";
    }

    /**
     * GET response mapped to the "/error" path
     * Used only if the user fails to login
     * @param model object from the Model class which makes
     *              parameters accessible to the view page
     * @param exception the exception thrown when the error occurs
     * @return html view of the login page
     */
    @GetMapping("/error")
    public String getErrorLoginPage(Model model, InvalidUserCredentialsException exception) {

        model.addAttribute("hasError", true);
        model.addAttribute("error", exception.getMessage());
        model.addAttribute("bodyContent", "login");
        return "master-template";
    }

}
