package com.pyro.repositories;

import com.pyro.entities.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
    Discussion findByName(String name);
    List<Discussion> findByNameContainsIgnoreCase(String name);
}
