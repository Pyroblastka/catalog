package com.pyro.controllers;


import com.pyro.entities.Discussion;
import com.pyro.entities.User;
import com.pyro.repositories.DiscussionRepository;
import com.pyro.repositories.UserRepository;
import com.pyro.service.classification.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@Controller
public class DiscussionController {

    @Autowired
    DiscussionRepository discussionRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    private ProductService productRepository;

    @GetMapping("/discussion")
    public String getDiscussionList(Model model) {
        model.addAttribute("discussions", discussionRepository.findAll());
        return "discussions";
    }

    @PostMapping("/discussion/new")
    public String createDiscussion(@RequestParam("topic") String topic, Model model) {
        Discussion discussion = new Discussion(topic);
        discussionRepository.saveAndFlush(discussion);
        return "redirect:/discussion/" + discussion.getId();
    }


    @PostMapping("admin/deletedisscussion")
    public String createDiscussion(@RequestParam("id") long id) {

        discussionRepository.deleteById(id);
        return "redirect:/discussion";
    }

    @GetMapping("/discussion/{discussion}")
    public String getDiscussion(@PathVariable String discussion, Principal principal, Model model) throws IllegalAccessException {

        Discussion disc = discussionRepository.findByName(discussion);
        if (disc == null) {
            Optional<Discussion> byId = discussionRepository.findById(Long.valueOf(discussion));

            if (byId.equals(Optional.empty())) {
                return "redirect:/discussion";
            } else {
                disc = byId.get();
            }
        }
        if (principal != null) {
            User user = userRepository.findByUsername(principal.getName());
            model.addAttribute("userId", user.getId());
        }
        model.addAttribute("isDiscussion", "true");
        model.addAttribute("product", disc);
        return "discussion";
    }
}
