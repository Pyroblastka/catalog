package com.pyro.repositories.classification;


import com.pyro.entities.classification.Kingdom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface KingdomRepository extends JpaRepository<Kingdom, Long> {
    Kingdom findByName(String name);
    long countByName(String name);
    long count();
    List<Kingdom> findByNameContainsIgnoreCase(String letter);

    @Query(nativeQuery = true, value =
            "delete from kingdom where kingdom.id not in " +
                    "( select kingdom_id from " +
                    "( select kingdom_id, count(*) as p_count from phylum group by kingdom_id) as empty " +
                    " where  p_count >0);")
    @Modifying
    @Transactional
    void deleteEmpty();
}
