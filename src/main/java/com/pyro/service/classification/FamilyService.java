package com.pyro.service.classification;

import com.pyro.entities.classification.Family;
import com.pyro.entities.classification.Genus;
import com.pyro.entities.classification.Order;
import com.pyro.repositories.classification.FamilyRepository;
import com.pyro.repositories.classification.GenusRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FamilyService {

    @Autowired
    FamilyRepository familyRepository;
    @Autowired
    OrderService orderService;

    @Transactional
    public Family save(Family family) {

        Family localObject = familyRepository.findByName(family.getName());
        if (localObject != null) {
            return localObject;
        }

        Order order = family.getOrder();
        if(order!=null)
        {
            family.setOrder(orderService.save(order));
        }

        family = familyRepository.saveAndFlush(family);
        return family;
    }
}
