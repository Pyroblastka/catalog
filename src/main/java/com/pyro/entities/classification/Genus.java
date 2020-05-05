package com.pyro.entities.classification;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pyro.entities.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Genus extends AbstractStair {

    @OneToMany(mappedBy = "genus", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    List<Product> products;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "family_id", nullable = true)
    Family family;

    @Column
    private String name;

    public Genus(String name) {
        this.name = name;
        this.products = new ArrayList<>();
    }

    public Genus() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public List<Product> getAllProducts() {
        return products;
    }

    @JsonIgnore
    public Family getFamily() {
        return family;
    }

    public void setFamily(Family family) {
        this.family = family;
    }

    public String loadAllHierarchy() {

        StringBuilder sb = new StringBuilder();
        sb.append(this.name);
        sb.append(this.family.getName());
        sb.append(this.family.order.getName());
        sb.append(this.family.order.klass.getName());
        sb.append(this.family.order.klass.phylum.getName());
        sb.append(this.family.order.klass.phylum.kingdom.getName());
        return sb.toString();
    }
}
