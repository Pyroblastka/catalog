package com.pyro.controllers;

import com.pyro.entities.Message;
import com.pyro.entities.Product;
import com.pyro.entities.User;
import com.pyro.repositories.MessageRepository;
import com.pyro.repositories.ProductRepository;
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
    UserService userService;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    private DBFileStorageService imageService;
    @Autowired
    private ProductRepository productService;

    @PostMapping("/sendcomment")
    public String add(@RequestParam("comment") String msg,
                      @RequestParam("product") String product,
                      HttpServletRequest request,
                      @RequestParam(value = "msg", required = false) long messageId,
                      Principal principal) throws IOException {

        if (messageId != 0L){

            Message message = messageRepository.getOne(messageId);
            message.setText(msg);
            messageRepository.saveAndFlush(message);
            String referer = request.getHeader("Referer");
            return "redirect:"+ referer;
        }


            User user = userService.findByUsername(principal.getName());

        Message message = new Message(user, product, msg, new Date());
        message = messageRepository.saveAndFlush(message);
        Product product1 = productService.findByName(product);
        product1.getMessages().add(message);
        productService.saveAndFlush(product1);

        return "redirect:/product?productId=" + product1.getId();
    }

    @GetMapping("/deletecomment")
    public String remove(@RequestParam(value = "msgId") long messageId, HttpServletRequest request) {


        Message msg = messageRepository.findById(messageId).get();


        Product product = productService.findByName(msg.getHeader());
        product.getMessages().removeIf(e -> e.getId()==messageId);
        productService.saveAndFlush(product);
        messageRepository.delete(msg);
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }
}
