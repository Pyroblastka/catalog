package com.pyro.entities.classification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pyro.entities.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Phylum extends AbstractStair {

    @JsonIgnore
    @OneToMany(mappedBy = "phylum", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Klass> Klasses;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "kingdom_id", nullable = true)
    Kingdom kingdom;
    @Column
    private String name;

    public Phylum(String name) {
        this.name = name;
        this.Klasses = new ArrayList<>();
    }

    public Phylum() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.Klasses = new ArrayList<>();
    }


    @JsonIgnore
    public List<Klass> getKlasses() {
        return Klasses;
    }

    @JsonIgnore
    public void setKlasses(List<Klass> klasses) {
        Klasses = klasses;
    }

    @JsonIgnore
    public Kingdom getKingdom() {
        return kingdom;
    }

    @JsonIgnore
    public void setKingdom(Kingdom kingdom) {
        this.kingdom = kingdom;
    }

    @Override
    public List<Product> getAllProducts() {
        ArrayList<Product> list = new ArrayList<>();
        for (Klass i : this.Klasses) {
            list.addAll(i.getAllProducts());
        }
        return list;
    }
}
