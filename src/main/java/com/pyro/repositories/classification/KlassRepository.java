package com.pyro.repositories.classification;


import com.pyro.entities.classification.Klass;
import com.pyro.entities.classification.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface KlassRepository extends JpaRepository<Klass, Long> {
    Klass findByName(String name);
    long countByName(String name);
    long count();
    List<Klass> findByNameContainsIgnoreCase(String letter);

    @Query(nativeQuery = true, value =
            "delete from klass where klass.id not in " +
                    "( select klass_id from " +
                    "( select klass_id, count(*) as p_count from class_order group by klass_id) as empty " +
                    " where  p_count >0);")
    @Modifying
    @Transactional
    void deleteEmpty();
}
