package com.pyro.repositories;

import com.pyro.entities.Category;
import com.pyro.entities.Product;
import com.pyro.entities.classification.Genus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);
    List<Product> findByGenus(Genus genus);
    List<Product> findByNameContainsIgnoreCase(String letter);

}
