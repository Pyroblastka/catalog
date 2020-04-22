package com.pyro.init;

import com.pyro.entities.Category;
import com.pyro.entities.Product;
import com.pyro.entities.Role;
import com.pyro.entities.User;
import com.pyro.repositories.CatRepository;
import com.pyro.repositories.ProductRepository;
import com.pyro.repositories.RoleRepository;
import com.pyro.service.DBFileStorageService;
import com.pyro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

//@RequiredArgsConstructor
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class Initializer implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CatRepository catRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private DBFileStorageService imageService;

    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {

        if (roleRepository.findAll().size() == 0) {
            System.out.println("\n______________INITIALIZATION______________");

            //roles
            Role role = new Role();
            role.setName("ROLE_ADMIN");
            roleRepository.saveAndFlush(role);
            role = new Role();
            role.setName("ROLE_USER");
            roleRepository.saveAndFlush(role);

            //users
            User user = new User();
            user.setUsername("admin");
            user.setPassword("1234");
            user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_ADMIN")));
            userService.save(user);
            user = new User();
            user.setUsername("user");
            user.setPassword("1234");
            userService.save(user);

            //Categories
            Category category = new Category();
            category.setName("Луковичные");
            catRepository.saveAndFlush(category);
            category = new Category();
            category.setName("Кактусы");
            catRepository.saveAndFlush(category);
            category = new Category();
            category.setName("Хищные");
            catRepository.saveAndFlush(category);

            //Images
            String onion = loadImage("C:\\Users\\IK\\Desktop\\catalog\\src\\main\\resources\\static\\images\\onion.jpg");
            String cactus = loadImage("C:\\Users\\IK\\Desktop\\catalog\\src\\main\\resources\\static\\images\\cactus.jpg");
            String predator = loadImage("C:\\Users\\IK\\Desktop\\catalog\\src\\main\\resources\\static\\images\\predator.jpg");

            //Products
            Product product = new Product("Зефирантес",
                    "Растение зефирантес (Zephyranthes) относится к семейству Амариллисовые. Этот род объединяет примерно 35 видов, которые в природных условиях можно повстречать в Центральной и Южной Америке, при этом они предпочитают расти во влажных местностях.", onion,
                    catRepository.findByName("Луковичные"));
            productRepository.saveAndFlush(product);

            product = new Product("Саррацения",
                    "Саррацения — это болотное, корневищное, травянистое растение является многолетником. Оно входит в число наиболее больших плотоядных растений.", predator,
                    catRepository.findByName("Хищные"));
            productRepository.saveAndFlush(product);

            product = new Product("Маммиллярия",
                    "Одним из самых популярных кактусов среди цветоводов является маммиллярия, "+
                            "которая отличается своей неприхотливостью и нетребовательностью к уходу. Родом данные кактусы из "+
                            "южной части Северной Америки, а также из Мексики, причем в природных условиях они предпочитают "+
                            "расти на меловых и известковых скалах.", cactus,
                    catRepository.findByName("Кактусы"));
            productRepository.saveAndFlush(product);

        }
    }

    public  String loadImage(String pathName) {
        Path path = Paths.get(pathName);
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
