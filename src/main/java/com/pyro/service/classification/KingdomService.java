package com.pyro.service.classification;

import com.pyro.entities.classification.Kingdom;
import com.pyro.entities.classification.Phylum;
import com.pyro.repositories.classification.KingdomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.tools.Diagnostic;
import java.util.List;

@Service
public class KingdomService {


    @Autowired
    KingdomRepository kingdomRepository;

    @Transactional
    public Kingdom save(Kingdom kingdom) {

        Kingdom localKingdom  = kingdomRepository.findByName(kingdom.getName());
        if(localKingdom!= null)
        {
            return localKingdom;
        }
        return kingdomRepository.saveAndFlush(kingdom);
    }

}
