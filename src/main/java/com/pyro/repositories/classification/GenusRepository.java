package com.pyro.repositories.classification;


import com.pyro.entities.classification.Family;
import com.pyro.entities.classification.Genus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GenusRepository extends JpaRepository<Genus, Long> {
    Genus findByName(String name);
    long countByName(String name);
    long count();


    @Query(nativeQuery = true, value =
            "delete from genus where genus.id not in " +
                    "( select genus_id from " +
                    "( select genus_id, count(*) as p_count from product group by genus_id) as empty " +
                    " where  p_count >0);")
    @Modifying
    @Transactional
    void deleteEmpty();

    List<Genus> findByNameContainsIgnoreCase(String letter);

}
