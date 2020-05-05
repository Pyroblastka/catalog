package com.pyro.repositories.classification;


import com.pyro.entities.classification.Klass;
import com.pyro.entities.classification.Phylum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhylumRepository extends JpaRepository<Phylum, Long> {
    Phylum findByName(String name);
    long countByName(String name);
    long count();
    List<Phylum> findByNameContainsIgnoreCase(String letter);
}
