package com.pyro.entities.classification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pyro.entities.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "class_order")
public class Order extends AbstractStair {

    @JsonIgnore
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Family> Families;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "klass_id", nullable = true)
    Klass klass;
    @Column
    private String name;

    public Order(String name) {
        this.name = name;
        this.Families = new ArrayList<>();
    }

    public Order() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public List<Family> getFamilies() {
        return Families;
    }

    @JsonIgnore
    public void setFamilies(List<Family> families) {
        Families = families;
    }

    @JsonIgnore
    public Klass getKlass() {
        return klass;
    }

    @JsonIgnore
    public void setKlass(Klass klass) {
        this.klass = klass;
    }

    @Override
    public List<Product> getAllProducts() {
        ArrayList<Product> list = new ArrayList<>();
        for (Family i : this.getFamilies()) {
            list.addAll(i.getAllProducts());
        }
        return list;
    }
}
