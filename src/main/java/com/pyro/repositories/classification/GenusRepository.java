package com.pyro.repositories.classification;


import com.pyro.entities.classification.Family;
import com.pyro.entities.classification.Genus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenusRepository extends JpaRepository<Genus, Long> {
    Genus findByName(String name);
    long countByName(String name);
    long count();
    List<Genus> findByNameContainsIgnoreCase(String letter);



}
