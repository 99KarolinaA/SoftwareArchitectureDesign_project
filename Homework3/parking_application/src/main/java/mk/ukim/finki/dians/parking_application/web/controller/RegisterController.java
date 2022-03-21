package mk.ukim.finki.dians.parking_application.web.controller;

import mk.ukim.finki.dians.parking_application.model.enumeration.Role;
import mk.ukim.finki.dians.parking_application.model.exceptions.InvalidArgumentsException;
import mk.ukim.finki.dians.parking_application.model.exceptions.PasswordsDoNotMatchException;
import mk.ukim.finki.dians.parking_application.model.exceptions.UsernameExistsException;
import mk.ukim.finki.dians.parking_application.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * MVC Controller which provides user registration.
 * Dependency injection of the UserService.
 * The controller is mapped to the "/register" path.
 */
@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    /**
     * A method which is a response to a GET request
     *              (request for the registration page)
     * @param error not required parameter which lets a know
     *              whether a error has occured
     * @param model object from the Model class which makes
     *              parameters accessible to the view page
     * @return html view of the registration page
     */
    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model) {

        if (error != null && !error.isEmpty()) {

            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        model.addAttribute("bodyContent", "register");
        return "master-template";
    }

    /**
     * POST request on the register page
     * @param username username of the user is registering
     * @param password user's password
     * @param repeatedPassword second input of the password
     *                         for verification/confirmation
     * @param name user's first name
     * @param surname user's last name
     * @param model object from the Model class which makes
     *              parameters accessible to the view page
     * @return redirect to the login page if the registration is
     *         successful or the registration page if it's not
     */
    @PostMapping
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String repeatedPassword,
                           @RequestParam String name,
                           @RequestParam String surname,
                           Model model) {

        try {

            this.userService.register(username, password, repeatedPassword, name, surname);
            return "redirect:/login";
        }
        catch (PasswordsDoNotMatchException | UsernameExistsException | InvalidArgumentsException exception) {

            model.addAttribute("hasError", true);
            model.addAttribute("error", exception.getMessage());
            model.addAttribute("bodyContent", "register");
            return "master-template";
        }
    }
}
