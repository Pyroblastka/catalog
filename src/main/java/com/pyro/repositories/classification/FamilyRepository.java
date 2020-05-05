package com.pyro.repositories.classification;


import com.pyro.entities.Product;
import com.pyro.entities.classification.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {
    Family findByName(String name);
    long countByName(String name);
    long count();
    List<Family> findByNameContainsIgnoreCase(String letter);

}
