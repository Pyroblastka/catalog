package com.pyro.repositories.classification;


import com.pyro.entities.classification.Klass;
import com.pyro.entities.classification.Phylum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PhylumRepository extends JpaRepository<Phylum, Long> {
    Phylum findByName(String name);
    long countByName(String name);
    long count();
    List<Phylum> findByNameContainsIgnoreCase(String letter);


    @Query(nativeQuery = true, value =
            "delete from phylum where phylum.id not in " +
                    "( select phylum_id from " +
                    "( select phylum_id, count(*) as p_count from klass group by phylum_id) as empty " +
                    " where  p_count >0);")
    @Modifying
    @Transactional
    void deleteEmpty();
}
