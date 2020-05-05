package com.pyro.service.classification;

import com.pyro.entities.Product;
import com.pyro.entities.classification.Genus;
import com.pyro.entities.classification.Kingdom;
import com.pyro.entities.classification.Phylum;
import com.pyro.repositories.ProductRepository;
import com.pyro.repositories.classification.GenusRepository;
import com.pyro.repositories.classification.KingdomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {


    @Autowired
    public ProductRepository productRepository;
    @Autowired
    GenusService genusService;

    @Transactional
    public void save(Product product) {
        Genus genus = product.getGenus();
        if(genus!=null){
            product.setGenus(genusService.save(genus));
        }
        productRepository.saveAndFlush(product);
    }

}
