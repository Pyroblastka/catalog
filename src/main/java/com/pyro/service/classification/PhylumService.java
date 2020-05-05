package com.pyro.service.classification;

import com.pyro.entities.classification.Kingdom;
import com.pyro.entities.classification.Klass;
import com.pyro.entities.classification.Phylum;
import com.pyro.repositories.classification.KlassRepository;
import com.pyro.repositories.classification.PhylumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PhylumService {

    @Autowired
    PhylumRepository phylumRepository;
    @Autowired
    KingdomService kingdomService;

    @Transactional
    public Phylum save(Phylum phylum) {

        Phylum localObject = phylumRepository.findByName(phylum.getName());
        if (localObject != null) {
            return localObject;
        }

        Kingdom kingdom = phylum.getKingdom();
        if(kingdom!=null)
        {
            phylum.setKingdom(kingdomService.save(kingdom));
        }

        phylum = phylumRepository.saveAndFlush(phylum);
        return phylum;
    }
}
