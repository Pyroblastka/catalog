package com.pyro.service.classification;

import com.pyro.entities.classification.Klass;
import com.pyro.entities.classification.Order;
import com.pyro.entities.classification.Phylum;
import com.pyro.repositories.classification.KlassRepository;
import com.pyro.repositories.classification.OrderRepository;
import com.pyro.repositories.classification.PhylumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class KlassService {

    @Autowired
    KlassRepository klassRepository;
    @Autowired
    PhylumService phylumService;

    @Transactional
    public Klass save(Klass klass) {

        Klass localObject = klassRepository.findByName(klass.getName());
        if (localObject != null) {
            return localObject;
        }

        Phylum phylum = klass.getPhylum();
        if(phylum!=null)
        {
            klass.setPhylum(phylumService.save(phylum));
        }

        klass = klassRepository.saveAndFlush(klass);
        return klass;
    }
}
