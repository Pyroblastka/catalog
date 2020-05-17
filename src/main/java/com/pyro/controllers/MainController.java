package com.pyro.controllers;

import com.pyro.entities.User;
import com.pyro.entities.classification.Kingdom;
import com.pyro.repositories.MessageRepository;
import com.pyro.repositories.ProductRepository;
import com.pyro.repositories.RoleRepository;
import com.pyro.repositories.classification.KingdomRepository;
import com.pyro.service.DBFileStorageService;
import com.pyro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private DBFileStorageService imageService;

    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserService userService;

    @Autowired
    KingdomRepository kingdomRepository;

    @Autowired
    MessageRepository messageRepository;


    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        model.addAttribute("kingdoms", kingdomRepository.findAll());
        return "index";
    }


    @GetMapping("/registration")
    public String reg(Model model) {
        return "registration";
    }

    @GetMapping("/plants")
    public String plants() {
        return "redirect:/products/kingdom?catId="+kingdomRepository.findByName("Растения").getId();
    }

    @GetMapping("/animals")
    public String animals() {
        return "redirect:/products/kingdom?catId="+kingdomRepository.findByName("Животные").getId();
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          Model model) throws IOException {


        if (userService.findByUsername(username) != null) {
            model.addAttribute("error", "Пользователь " + username + " уже зарегистрирован");
            return "registration";
        }
        if (!(username.matches("^[a-zA-Z0-9]+$"))) {
            model.addAttribute("error", "Имя пользователя может содержать только латиницу и цифры");
            return "registration";
        }
        User user = new User(1, username, password, Arrays.asList(roleRepository.findByName("ROLE_USER")));
        user.setSrc((String.valueOf( imageService.getFile("noimage.jpg").getId())));
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/about")
    public String about() {
        return "/";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        String referrer = request.getHeader("Referer");
        request.getSession().setAttribute("url_prior_login", referrer);
        return "login";
    }

    @GetMapping("/403")
    public String error403() {
        return "error/403";
    }
}
