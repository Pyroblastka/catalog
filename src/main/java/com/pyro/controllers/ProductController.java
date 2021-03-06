package com.pyro.controllers;


import com.pyro.entities.Message;
import com.pyro.entities.Product;
import com.pyro.entities.User;
import com.pyro.entities.classification.AbstractStair;
import com.pyro.entities.classification.Genus;
import com.pyro.repositories.ProductRepository;
import com.pyro.repositories.UserRepository;
import com.pyro.repositories.classification.GenusRepository;
import com.pyro.service.ClassificationService;
import com.pyro.service.UserService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    GenusRepository genusRepository;

    @Autowired
    ClassificationService classificationService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/product")
    public String getProduct(@RequestParam(value = "productId", required = true) Long productId,
                             @RequestParam(value = "bought", required = false) String bought,
                             Model model, Principal principal) {


        Product product = productRepository.getOne(productId);
        Hibernate.initialize(product);
        //Short aShort = product.getMessages().get(0).getVotes().get(69L);
        Collections.sort(product.getMessages(), Message::compareTo);
        if (principal != null) {
            User user = userRepository.findByUsername(principal.getName());
            model.addAttribute("userId", user.getId());
            if (user.getFavorits().contains(product))
                model.addAttribute("starChecked", true);
        } else {
            model.addAttribute("starChecked", false);
        }
        model.addAttribute("isDiscussion", "false");

        model.addAttribute("product", product);

        return "product";
    }

    @GetMapping("/productq")
    public String getProductByName(@RequestParam(value = "productName", required = true) String productName,
                                   Model model) {
        if (productName == null || productName == "") {
            return "redirect:/index";
        }
        Product product = null;
        List<Product> products = productRepository.findByNameContainsIgnoreCase(productName);
        if (products.size() > 0) {
            model.addAttribute("product", products.get(0));
            return "product";
        } else {
            try {
                AbstractStair stair = classificationService.findByNameContainsIgnoreCase(productName).get(0);
                String[] category = stair.getClass().getName().split("\\.");
                return "redirect:/products/" + category[category.length - 1].toLowerCase() + "?catId=" + stair.getId();
            } catch (Exception e) {
                return "redirect:/index";
            }
        }
    }

    @GetMapping("/products")
    public String getProductList(@RequestParam(value = "catId", required = true) Long catId,
                                 Model model) {
        classificationService.cleanEmpty(null);
        Genus genus = genusRepository.findById(catId).get();
        List<Product> list = productRepository.findByGenus(genus);
        model.addAttribute("products", list);
        return "products";

    }

}