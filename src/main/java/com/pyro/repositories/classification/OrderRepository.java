package com.pyro.repositories.classification;


import com.pyro.entities.classification.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByName(String name);
    long countByName(String name);
    long count();
    List<Order> findByNameContainsIgnoreCase(String letter);
}
