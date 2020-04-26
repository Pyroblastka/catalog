package com.pyro.entities.classification;

import com.pyro.entities.AbstractEntity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "class_order")
public class Order extends AbstractEntity {

    @Column
    private String name;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Family> Families;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Klass klass;

    public Order(String name) {
        this.name = name;
    }

    public Order() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Family> getFamilies() {
        return Families;
    }

    public void setFamilies(List<Family> families) {
        Families = families;
    }

    public Klass getKlass() {
        return klass;
    }

    public void setKlass(Klass klass) {
        this.klass = klass;
    }
}
