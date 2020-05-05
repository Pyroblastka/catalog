package com.pyro.api;

import com.pyro.entities.Product;
import com.pyro.repositories.ProductRepository;
import com.pyro.repositories.classification.KingdomRepository;
import com.pyro.service.ClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Transient;
import java.io.IOException;
import java.util.List;

@RestController
public class DataController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    KingdomRepository kingdomRepository;

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
}
