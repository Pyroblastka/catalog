package com.pyro.entities.classification;

import com.pyro.entities.AbstractEntity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "class_order")
public class Family extends AbstractEntity {

    @Column
    private String name;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Genus> genuses;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Order order;

    public Family(String name) {
        this.name = name;
    }

    public Family() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Genus> getGenuses() {
        return genuses;
    }

    public void setGenuses(List<Genus> genuses) {
        this.genuses = genuses;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
