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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.*;

@Controller
public class PrivateController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    DiscussionRepository discussionRepository;

    @Autowired
    private DBFileStorageService imageService;


    @GetMapping("/private")
    public String privateCab(Model model, Principal principal) {

        User user = userRepository.findByUsername(principal.getName());

        model.addAttribute("user", user);
        List<String> headers = new ArrayList<>();
        if (user.getUsername().equals("admin")) {
            List<Message> appeals = messageRepository.findByTextContaining("@admin");
            appeals.addAll(messageRepository.findByTextContaining("@moderator"));
            for (Message message : appeals) {
                headers.add(message.getHeader());
            }
        } else {
            headers = messageRepository.findHeadersByUserIdGroupByHeader(user.getId());
            model.addAttribute("statistic", getStatistic(user));
        }
        Map<String, String> map = new HashMap<>();

        for (String i : headers) {
            Product product = productRepository.findByName(i);
            if (product != null) {
                map.put(i, "/product?productId=" + product.getId());
            } else {
                Discussion discussion = discussionRepository.findByName(i);
                if(discussion!=null)
                map.put(i, "/discussion/" + discussion.getId());
            }
        }

        model.addAttribute("messages", map);

        return "private";
    }

    @PostMapping("/private/pic")
    public String add(@RequestParam("image") MultipartFile image, Principal principal, HttpServletRequest request) throws IOException {

        User user = userRepository.findByUsername(principal.getName());
        Long sid = null;
        if (image.getOriginalFilename().compareTo("") != 0) {//Сохранить изображение есть пришло с формы
            sid = imageService.storeFile(image).getId();
            user.setSrc(String.valueOf(sid));
        }

        if (sid == null && user.getSrc() == null) {
            String noimage = loadImage("static/images/noimage.jpg");
            user.setSrc(noimage);
        }
        userRepository.saveAndFlush(user);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    private Map<String, String> getStatistic(User user) {
        Map<String, String> map = new HashMap<>();

        List<Message> byUser = messageRepository.findByUser(user);

        map.put("allMessageCount", String.valueOf(byUser.size()));

        int minus = 0;
        int plus = 0;
        for (Message message : byUser) {
            Short cval = message.getVotes().getOrDefault(user.getId(), Short.valueOf("0"));
            if (cval < 0) {
                minus++;
            }
            if (cval > 0) {
                plus++;
            }
        }
        map.put("minus", String.valueOf(minus));
        map.put("plus", String.valueOf(plus));

        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        Date weekAgo = new Date(new Date().getTime() - (7 * DAY_IN_MS));
        byUser.removeIf(element -> element.getDate().before(weekAgo));
        map.put("weekMessageCount", String.valueOf(byUser.size()));

        return map;
    }

    private String loadImage(String pathName) throws IOException {
        ClassPathResource resource = new ClassPathResource(pathName);
        Path path = Paths.get(resource.getPath());
        byte[] content = null;
        try {
            content = FileCopyUtils.copyToByteArray(resource.getInputStream());
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
