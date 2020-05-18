package com.pyro.service;


import com.pyro.entities.Product;
import com.pyro.entities.classification.AbstractStair;
import com.pyro.repositories.ProductRepository;
import com.pyro.repositories.classification.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassificationService {

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
    private ProductRepository productRepository;

    public void cleanEmpty(Product product) {
        genusRepository.deleteEmpty();
        familyRepository.deleteEmpty();
        orderRepository.deleteEmpty();
        klassRepository.deleteEmpty();
        phylumRepository.deleteEmpty();
        kingdomRepository.deleteEmpty();
        /*Hibernate.initialize(product);
        Genus genus = product.getGenus();
        if(genus.getProducts().size() < 1) {
            if( genus.getFamily().getGenuses().size() < 1) {
                if( genus.getFamily().getOrder().getFamilies().size() < 1) {
                    if( genus.getFamily().getOrder().getKlass().getOrders().size() < 1) {
                        if( genus.getFamily().getOrder().getKlass().getPhylum().getKlasses().size() < 1) {
                            if( genus.getFamily().getOrder().getKlass().getPhylum().getKlasses().size() < 1) {
                                phylumRepository.deleteById(genus.getFamily().getOrder().getKlass().getId());
                            }
                            phylumRepository.deleteById(genus.getFamily().getOrder().getKlass().getPhylum().getId());
                        }
                        klassRepository.deleteById(genus.getFamily().getOrder().getKlass().getId());
                    }
                    orderRepository.deleteById(genus.getFamily().getOrder().getId());
                }
                familyRepository.deleteById( genus.getFamily().getId());
            }
            genusRepository.deleteById(genus.getId());
        }
        */
    }
    public  List<? extends AbstractStair> findByNameContainsIgnoreCase(String letter) {

        List<? extends AbstractStair> stairs = kingdomRepository.findByNameContainsIgnoreCase(letter);

        if (stairs.size() == 0) {
            stairs = phylumRepository.findByNameContainsIgnoreCase(letter);
            if (stairs.size() == 0) {
                stairs = klassRepository.findByNameContainsIgnoreCase(letter);
                if (stairs.size() == 0) {
                    stairs = orderRepository.findByNameContainsIgnoreCase(letter);
                    if (stairs.size() == 0) {
                        stairs = familyRepository.findByNameContainsIgnoreCase(letter);
                        if (stairs.size() == 0) {
                            stairs = genusRepository.findByNameContainsIgnoreCase(letter);
                        }
                    }
                }
            }
        }

        return stairs;
    }
}
