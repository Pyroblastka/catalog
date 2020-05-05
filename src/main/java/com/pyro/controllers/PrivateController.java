package com.pyro.controllers;

import com.pyro.entities.Product;
import com.pyro.entities.User;
import com.pyro.repositories.MessageRepository;
import com.pyro.service.DBFileStorageService;
import com.pyro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;

@Controller
public class PrivateController {

    @Autowired
    UserService userService;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    private DBFileStorageService imageService;


    @GetMapping("/private")
    public String privateCab(Model model, Principal principal) {

        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "private";
    }

    @PostMapping("/private/pic")
    public String add(@RequestParam("image") MultipartFile image, Principal principal, HttpServletRequest request) throws IOException {

        User user = userService.findByUsername(principal.getName());
        Long sid = null;
        if (image.getOriginalFilename().compareTo("") != 0) {//Сохранить изображение есть пришло с формы
            sid = imageService.storeFile(image).getId();
            user.setSrc(String.valueOf(sid));
        }

        if (sid == null && user.getSrc() == null) {
            String noimage = loadImage("static/images/noimage.jpg");
            user.setSrc(noimage);
        }
        userService.save(user);
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }


    public  String loadImage(String pathName) throws IOException {
        File resource = new ClassPathResource(
                pathName).getFile();
        Path path = Paths.get(resource.getPath());
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
            System.out.println("\n_____cannot read bytes_____!");
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
