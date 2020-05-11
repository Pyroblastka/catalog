package com.pyro.api;

import com.pyro.entities.Product;
import com.pyro.entities.User;
import com.pyro.repositories.ProductRepository;
import com.pyro.repositories.classification.KingdomRepository;
import com.pyro.service.ClassificationService;
import com.pyro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Transient;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
public class DataController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    KingdomRepository kingdomRepository;
    @Autowired
    UserService userService;
    @Autowired
    ClassificationService classificationService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transient
    @RequestMapping("/searchq")
    public List search(@RequestParam(value = "value") String value) throws IOException {
        try {
            List<Product> products = productRepository.findByNameContainsIgnoreCase(value);
            if (products.size() > 0)
                return products;
            List stairs = classificationService.findByNameContainsIgnoreCase(value);
            if (stairs.size() > 0)
                return stairs;
            throw new Exception();
        } catch (Exception e) {
            return kingdomRepository.findAll();
        }
    }

    @PostMapping("/private/favorite")
    public boolean favorite(@RequestBody Map data, Principal principal) {
        if (principal == null)
            return false;
        long productId = Long.valueOf((String) data.get("productId"));

        User user = userService.findByUsername(principal.getName());
        Product object = productRepository.getOne(productId);
        boolean result;
        if (user.getFavorits().contains(object)) {
            user.getFavorits().remove(object);
            result = false;
        } else {
            user.getFavorits().add(object);
            result = true;
        }
        userService.save(user);
        return result;
    }
}
