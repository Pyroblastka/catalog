package com.pyro.entities.classification;

import com.pyro.entities.AbstractEntity;
import com.pyro.entities.Product;

import javax.persistence.*;
import java.util.List;

@Entity(name = "class_order")
public class Genus extends AbstractEntity {

    @Column
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Product> products;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Family family;

    public Genus(String name) {
        this.name = name;
    }

    public Genus() {   }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    
}
