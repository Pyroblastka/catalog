package com.pyro.repositories.classification;


import com.pyro.entities.classification.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByName(String name);
    long countByName(String name);
    long count();
    List<Order> findByNameContainsIgnoreCase(String letter);

    @Query(nativeQuery = true, value =
            "delete from class_order where class_order.id not in " +
                    "( select order_id from " +
                    "( select order_id, count(*) as p_count from family group by order_id) as empty " +
                    " where  p_count >0);")
    @Modifying
    @Transactional
    void deleteEmpty();
}
