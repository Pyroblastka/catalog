package com.pyro.repositories.classification;


import com.pyro.entities.classification.Klass;
import com.pyro.entities.classification.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KlassRepository extends JpaRepository<Klass, Long> {
    Klass findByName(String name);
    long countByName(String name);
    long count();
    List<Klass> findByNameContainsIgnoreCase(String letter);
}
