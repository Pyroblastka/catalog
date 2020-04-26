package com.pyro.entities.classification;

import com.pyro.entities.AbstractEntity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Klass extends AbstractEntity {

    @Column
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Order> orders;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Phylum phylum;

    public Klass(String name) {
        this.name = name;
    }

    public Klass() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
