package com.pyro.repositories;

import com.pyro.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CatRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
    List<Category> findByNameContainsIgnoreCase(String name);
    List<Category> findByKindomContainsIgnoreCase(String Kindom);
    List<Category> findByPhylumContainsIgnoreCase(String Phylum);
    List<Category> findByClasContainsIgnoreCase(String clas);
    List<Category> findByOrderContainsIgnoreCase(String order);
    List<Category> findByFamilyContainsIgnoreCase(String family);
    List<Category> findByGenusContainsIgnoreCase(String genus);
}
