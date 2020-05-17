package com.pyro.api;

import com.pyro.entities.Message;
import com.pyro.entities.Product;
import com.pyro.entities.User;
import com.pyro.repositories.MessageRepository;
import com.pyro.repositories.ProductRepository;
import com.pyro.repositories.UserRepository;
import com.pyro.repositories.classification.KingdomRepository;
import com.pyro.service.ClassificationService;
import com.pyro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Transient;
import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DataController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    KingdomRepository kingdomRepository;
    @Autowired
    UserRepository userService;
    @Autowired
    ClassificationService classificationService;
    @Autowired
    private MessageRepository messageRepository;

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
        userService.saveAndFlush(user);
        return result;
    }

    @PostMapping("/private/vote")
    public Map vote(@RequestBody Map data, Principal principal) {
        if (principal == null)
            return null;

        long messageId = Long.valueOf((String) data.get("messageId"));
        User user = userService.findByUsername(principal.getName());
        Message message = messageRepository.findById(messageId).get();

        if(message.getVotes()==null){
            message.setVotes(new HashMap<>());
        }
        short val = message.getVotes().getOrDefault(user.getId(), (short)0);
        if(data.get("dir").equals("up")){
            if(val < 1){
                message.getVotes().put(user.getId(), ++val);
                message.setRating(message.getRating()+1);
                message = messageRepository.saveAndFlush(message);
            }
        }

        if(data.get("dir").equals("down")){
            if(val > -1){
                message.getVotes().put(user.getId(), --val);
                message.setRating(message.getRating()-1);
                message = messageRepository.saveAndFlush(message);
            }
        }


        Map<String, Integer> output = new HashMap<>();
        output.put("rating", message.getRating());
        output.put("cval", (int) val);
        return output;
    }
}
