package com.pyro.service.classification;

import com.pyro.entities.classification.Family;
import com.pyro.entities.classification.Genus;
import com.pyro.repositories.classification.GenusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GenusService {

    @Autowired
    GenusRepository genusRepository;
    @Autowired
    FamilyService familyService;

    @Transactional
    public Genus save(Genus genus) {

        Genus localObject = genusRepository.findByName(genus.getName());
        if (localObject != null) {
            return localObject;
        }

        Family family = genus.getFamily();
        if(family!=null)
        {
            genus.setFamily(familyService.save(family));
        }

        genus = genusRepository.saveAndFlush(genus);
        return genus;
    }
}
