package com.pyro.init;

import com.pyro.entities.Product;
import com.pyro.entities.Role;
import com.pyro.entities.User;
import com.pyro.entities.classification.*;
import com.pyro.repositories.ProductRepository;
import com.pyro.repositories.RoleRepository;
import com.pyro.repositories.classification.*;
import com.pyro.service.DBFileStorageService;
import com.pyro.service.UserService;
import com.pyro.service.classification.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
    private GenusRepository genusRepository;


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

    @Autowired
    private UserService userService;

    @Autowired
    private DBFileStorageService imageService;

    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {

        if (roleRepository.findAll().size() < 1) {
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
            Kingdom kingdom = new Kingdom("Растения");
            Phylum phylum = new Phylum("Цветковые");
            Klass klass = new Klass("Двудольные");
            com.pyro.entities.classification.Order order =
                    new com.pyro.entities.classification.Order("Гвоздичноцветные");
                Family family = new Family("Кактусовые");
            Genus genus = new Genus("Маммиллярия");
            genus.setFamily( family);
            family.setOrder(order);
            order.setKlass(klass);
            klass.setPhylum(phylum);
            phylum.setKingdom(kingdom);



            //Images

            String cactus;
            try {
                cactus = loadImage("static/images/cactus.jpg");
            } catch (Exception e) {

                System.out.println("files were not loaded");
                e.printStackTrace();
                return;
            }

            //Products

            Product product = new Product("Маммиллярия",
                    "Одним из самых популярных кактусов среди цветоводов является маммиллярия, " +
                            "которая отличается своей неприхотливостью и нетребовательностью к уходу. Родом данные кактусы из " +
                            "южной части Северной Америки, а также из Мексики, причем в природных условиях они предпочитают " +
                            "расти на меловых и известковых скалах.", cactus,
                    genus);


           /* kingdomRepository.saveAndFlush(kingdom);
            phylumRepository.saveAndFlush(phylum);
            orderRepository.saveAndFlush(order);
            klassRepository.saveAndFlush(klass);
            familyRepository.saveAndFlush(family);
            genusRepository.saveAndFlush(genus);
*/

            productRepository.save(product);

        }
    }

    public String loadImage(String pathName) throws IOException {
        File resource = new ClassPathResource(
                pathName).getFile();
        Path path = Paths.get(resource.getPath());
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {

            System.out.println("\n_____cannot read bytes_____!");
            e.printStackTrace();
            System.out.println("\n___________________________!");

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
