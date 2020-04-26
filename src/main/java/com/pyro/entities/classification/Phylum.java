package com.pyro.entities.classification;

import com.pyro.entities.AbstractEntity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Phylum extends AbstractEntity {

    @Column
    private String name;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Klass> Klasses;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Kingdom kingdom;

    public Phylum(String name) {
        this.name = name;
    }

    public Phylum() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Klass> getKlasses() {
        return Klasses;
    }

    public void setKlasses(List<Klass> klasses) {
        Klasses = klasses;
    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public void setKingdom(Kingdom kingdom) {
        this.kingdom = kingdom;
    }
}
