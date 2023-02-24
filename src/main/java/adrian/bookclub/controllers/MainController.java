package adrian.bookclub.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import adrian.bookclub.models.LoginUser;
import adrian.bookclub.models.User;
import adrian.bookclub.services.UserService;

@Controller
public class MainController {

    @Autowired UserService userService;
    
    @RequestMapping("/")
    public String index(@ModelAttribute("newUser")User newUser,
    @ModelAttribute("newLogin")LoginUser loginUser
    ){
        // Bind empty User and LoginUser objects to the JSP
        // to capture the form input
        return "index.jsp";
    }

    @PostMapping("/register")
    public String register(
        @Valid 
        @ModelAttribute("newUser")User newUser, 
        BindingResult result, 
        Model model, 
        HttpSession session){
        User user = userService.register(newUser, result);
        if(result.hasErrors()){
            model.addAttribute("newLogin", new LoginUser());
            return "index.jsp";
        }
        session.setAttribute("userId", user.getId());
        return "redirect:/books";
    }

    @PostMapping("/login")
    public String login(
        @Valid 
        @ModelAttribute("newLogin")LoginUser newLogin, 
        BindingResult result, 
        Model model,
         HttpSession session){
        // login a new user via service
        User userToLogin = userService.login(newLogin, result);
        if(result.hasErrors()){
            model.addAttribute("newUser", new User());
            return "index.jsp";
        }
        session.setAttribute("userId", userToLogin.getId());
        return "redirect:/books";
    }

    @GetMapping("/welcome")
    public String welcome(HttpSession session, Model model) {
        // If no userId is found, redirect to log out
        if(session.getAttribute("userId") == null){
            return "redirect:/logout";
        }
        // We get the userId from session and cast as Long
        Long userId = (Long) session.getAttribute("userId");
        // add user retrieved via the service to our model
        model.addAttribute("user", userService.findById(userId));
        return "welcome.jsp";

    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Set userId to null
        session.setAttribute("userId", null);
        // redirect to log in/register page
        return "redirect:/";
    }
}