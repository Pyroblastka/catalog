package com.pyro.repositories.classification;


import com.pyro.entities.Product;
import com.pyro.entities.classification.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {
    Family findByName(String name);
    long countByName(String name);
    long count();
    List<Family> findByNameContainsIgnoreCase(String letter);

    @Query(nativeQuery = true, value =
            "delete from family where family.id not in " +
                    "( select family_id from " +
                    "( select family_id, count(*) as p_count from genus group by family_id) as empty " +
                    " where  p_count >0);")
    @Modifying
    @Transactional
    void deleteEmpty();
}
