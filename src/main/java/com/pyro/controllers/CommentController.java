package com.pyro.controllers;

import com.pyro.entities.Discussion;
import com.pyro.entities.Message;
import com.pyro.entities.Product;
import com.pyro.entities.User;
import com.pyro.repositories.DiscussionRepository;
import com.pyro.repositories.MessageRepository;
import com.pyro.repositories.ProductRepository;
import com.pyro.repositories.UserRepository;
import com.pyro.service.DBFileStorageService;
import com.pyro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.Date;

@Controller
public class CommentController {

    @Autowired
    DiscussionRepository discussionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    private DBFileStorageService imageService;
    @Autowired
    private ProductRepository productService;

    @PostMapping("/sendcomment")
    public String add(@RequestParam("comment") String msg,
                      @RequestParam("product") String product,
                      @RequestParam("isDiscussion") String isDiscussion,
                      HttpServletRequest request,
                      @RequestParam(value = "msg", required = false) long messageId,
                      Principal principal) throws IOException {

        if (messageId != 0L) {

            Message message = messageRepository.getOne(messageId);
            message.setText(msg);
            messageRepository.saveAndFlush(message);
            String referer = request.getHeader("Referer");
            return "redirect:" + referer;
        }

        User user = userRepository.findByUsername(principal.getName());
        Message message = new Message(user, product, msg, new Date());

        message = messageRepository.saveAndFlush(message);

        if (isDiscussion.equals("false")) {
            Product product1 = productService.findByName(product);
            product1.getMessages().add(message);
            productService.saveAndFlush(product1);
            return "redirect:/product?productId=" + product1.getId();
        } else {

            Discussion disc = discussionRepository.findByName(product);
            disc.getMessages().add(message);
            discussionRepository.saveAndFlush(disc);

            return "redirect:/discussion/" + disc.getId();
        }
    }

    @GetMapping("/deletecomment")
    public String remove(@RequestParam(value = "msgId") long messageId, HttpServletRequest request) {


        Message msg = messageRepository.findById(messageId).get();


        Product product = productService.findByName(msg.getHeader());
        if (product != null) {
            product.getMessages().removeIf(e -> e.getId() == messageId);
            productService.saveAndFlush(product);
        } else {
            Discussion discussion = discussionRepository.findByName(msg.getHeader());
            discussion.getMessages().removeIf(e -> e.getId() == messageId);
            discussionRepository.saveAndFlush(discussion);
        }

        messageRepository.delete(msg);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
