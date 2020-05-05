package com.pyro.repositories.classification;


import com.pyro.entities.classification.Kingdom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KingdomRepository extends JpaRepository<Kingdom, Long> {
    Kingdom findByName(String name);
    long countByName(String name);
    long count();
    List<Kingdom> findByNameContainsIgnoreCase(String letter);
}
