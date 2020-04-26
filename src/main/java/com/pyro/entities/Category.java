package com.pyro.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Category extends AbstractEntity {

    @Column
    private String name;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Product> products;

    public Category(String name) {
        this.name = name;
    }

    public Category() {
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
