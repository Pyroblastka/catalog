package com.pyro.controllers;


import com.pyro.entities.classification.AbstractStair;
import com.pyro.entities.classification.Genus;
import com.pyro.repositories.UserRepository;
import com.pyro.repositories.classification.*;
import com.pyro.service.UserService;
import com.pyro.service.classification.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Field;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    GenusRepository genusRepository;

    @Autowired
    private FamilyRepository familyRepository;
    @Autowired
    private KlassRepository klassRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PhylumRepository phylumRepository;
    @Autowired
    private KingdomRepository kingdomRepository;
    @Autowired
    private ProductService productRepository;

    @GetMapping("/products/{category}")
    public String getProductList(@PathVariable String category,
                                 @RequestParam(value = "catId", required = false) Long catId,
                                 Model model) throws IllegalAccessException {

        List<? extends AbstractStair> downStairs;
        switch (category.toLowerCase()) {
            case "product":
                return "redirect:/product?productId=" + catId;
            case "genus":
                downStairs = genusRepository.findAll();
                model.addAttribute("catname", "Род");
                break;
            case "family":
                downStairs = familyRepository.findAll();
                model.addAttribute("catname", "Семейство");

                break;
            case "order":
                downStairs = orderRepository.findAll();
                model.addAttribute("catname", "Отряд");
                break;
            case "klass":
                downStairs = klassRepository.findAll();
                model.addAttribute("catname", "Класс");
                break;
            case "phylum":
                downStairs = phylumRepository.findAll();
                model.addAttribute("catname", "Тип");
                break;
            case "kingdom":
                downStairs = kingdomRepository.findAll();
                model.addAttribute("catname", "Царство");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + category.toLowerCase());
        }

        String classNameForPath = downStairs.get(0).getClass().getName();
        if (catId != null) {

            downStairs.removeIf(o -> o.getId() != catId);
            List lowerList = getLowerList(downStairs.get(0));
            model.addAttribute("products", lowerList);
            String attr = model.asMap().get("catname").toString();
            model.addAttribute("catname", attr+" "+ downStairs.get(0).getName());
            classNameForPath = lowerList.get(0).getClass().getName();
        } else {
            model.addAttribute("products", downStairs);
        }

        String[] split = classNameForPath.split("\\.");
        model.addAttribute("path", split[split.length - 1].toLowerCase());

        model.addAttribute("kingdomCount", kingdomRepository.count());
        model.addAttribute("phylumCount", phylumRepository.count());
        model.addAttribute("klassCount", klassRepository.count());
        model.addAttribute("orderCount", orderRepository.count());
        model.addAttribute("familyCount", familyRepository.count());
        model.addAttribute("genusCount", genusRepository.count());

        return "products";
    }

    private List getLowerList(AbstractStair object) throws IllegalAccessException {

        if (object.getClass().equals(Genus.class)) {
            return ((Genus) object).getProducts();
        }
        for (Field field : object.getClass().getDeclaredFields()) {
            Class t = field.getType();
            if (t.equals(List.class)) {
                List<AbstractStair> output;
                boolean originalFlag = field.isAccessible();
                field.setAccessible(true);
                output = (List<AbstractStair>) field.get(object);
                field.setAccessible(originalFlag);

                return output;
            }
        }
        return null;
    }

}