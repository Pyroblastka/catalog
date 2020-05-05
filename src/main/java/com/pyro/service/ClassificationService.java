package com.pyro.service;


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

    public FamilyRepository getFamilyRepository() {
        return familyRepository;
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
