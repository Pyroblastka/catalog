package com.pyro.controllers;


import com.pyro.entities.Message;
import com.pyro.entities.Product;
import com.pyro.entities.User;
import com.pyro.entities.classification.Genus;
import com.pyro.repositories.MessageRepository;
import com.pyro.repositories.ProductRepository;
import com.pyro.repositories.UserRepository;
import com.pyro.repositories.classification.*;
import com.pyro.service.ClassificationService;
import com.pyro.service.DBFileStorageService;
import com.pyro.service.UserService;
import com.pyro.service.classification.ProductService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
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
    DBFileStorageService fileStorageService;
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    private DBFileStorageService imageService;
    @Autowired
    private ProductService productService;

    @GetMapping("/admin")
    public String admin(Model model) {
        return "admin";
    }


    @GetMapping("/admin/product")
    public String get(Model model) {

        model.addAttribute("defaultProduct", new Product());

        return "addproduct";
    }

    @PostMapping("/admin/product")
    public String add(@ModelAttribute("product") Product product,
                      @RequestParam("image") MultipartFile image) throws IOException {

        Long sid = null;
        if (image.getOriginalFilename().compareTo("") != 0) {//Сохранить изображение есть пришло с формы
            sid = fileStorageService.storeFile(image).getId();
            product.setSrc(String.valueOf(sid));
        }
            if(product.getId()!=0){
                Product product1 = productRepository.findById(product.getId()).get();
                product.setSrc(product1.getSrc());
            }

        if (sid == null && product.getSrc() == null) {
            String noimage = loadImage("static/images/noimage.jpg");
            product.setSrc(noimage);
        }
        productService.save(product);
        return "redirect:/products/genus?catId=" + product.getGenus().getId();
    }

    @RequestMapping(value = "/admin/deleteproduct", method = RequestMethod.POST)
    public String deleteProduct(@RequestParam("id") Long id, @RequestParam("category") Long catId) {

        Product product = productRepository.findById(id).get();
        productRepository.deleteById(id);
        return "redirect:/products/kingdom";
    }


    @RequestMapping(value = "/admin/editproduct", method = RequestMethod.GET)
    public String editProduct(@RequestParam("id") Long id, Model model) {
        Product product = productRepository.findById(id).get();
        Hibernate.initialize(product);
        model.addAttribute("defaultProduct", product);
        return "addproduct";
    }

    @GetMapping("/admin/deleteMessage")
    public String deleteMessage(@RequestParam("header") String header, Principal principal, Model model) {
        List<Message> byHeader = messageRepository.findByHeader(header);
        byHeader.forEach(message -> {
            message.setText(message.getText().replace('@',' '));
            messageRepository.saveAndFlush(message);
        });
        return "redirect:/private";
    }



    public String loadImage(String pathName) throws IOException {
        ClassPathResource resource = new ClassPathResource(pathName);
        Path path = Paths.get(resource.getPath());
        byte[] content = null;
        try {
            content =  FileCopyUtils.copyToByteArray(resource.getInputStream());
        } catch (final IOException e) {
            System.out.println("\n_____cannot read bytes_____!");
            e.printStackTrace();
        }


        MultipartFile result = new MockMultipartFile(path.getFileName().toString(),
                path.getFileName().toString(), "image/jpeg", content);
        try {
            return String.valueOf(imageService.storeFile(result).getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}