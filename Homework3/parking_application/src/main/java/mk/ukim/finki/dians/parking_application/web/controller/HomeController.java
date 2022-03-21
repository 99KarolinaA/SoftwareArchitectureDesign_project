package mk.ukim.finki.dians.parking_application.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * MVC Controller which handles the home page
 * The controller is mapped to the "/" path.
 */
@Controller
@RequestMapping("/")
public class HomeController {

    /**
     * This method is a response to the home page request
     * @param model object from the Model class which makes
     *              parameters accessible to the view page
     * @return html view of the home page
     */
    @GetMapping
    public String getHomePage(Model model) {

        model.addAttribute("bodyContent", "home");
        return "master-template";
    }

}
