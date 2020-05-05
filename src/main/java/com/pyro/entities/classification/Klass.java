package com.pyro.entities.classification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pyro.entities.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Klass extends AbstractStair {

    @JsonIgnore
    @OneToMany(mappedBy = "klass", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Order> orders;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "phylum_id", nullable = true)
    Phylum phylum;
    @Column
    private String name;

    public Klass(String name) {
        this.name = name;
        this.orders = new ArrayList<>();
    }

    public Klass() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public List<Order> getOrders() {
        return orders;
    }

    @JsonIgnore
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @JsonIgnore
    public Phylum getPhylum() {
        return phylum;
    }

    @JsonIgnore
    public void setPhylum(Phylum phylum) {
        this.phylum = phylum;
    }

    @Override
    public List<Product> getAllProducts() {
        ArrayList<Product> list = new ArrayList<>();
        for (Order i : this.orders) {
            list.addAll(i.getAllProducts());
        }
        return list;
    }
}
