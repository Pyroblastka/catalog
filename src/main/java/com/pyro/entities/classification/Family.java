package com.pyro.entities.classification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pyro.entities.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Family extends AbstractStair {

    @Column
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "family", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Genus> genuses;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="order_id", nullable=true)
    Order order;

    public Family(String name) {
        this.name = name;
        this.genuses = new ArrayList<>();
    }

    public Family() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public List<Genus> getGenuses() {
        return genuses;
    }

    public void setGenuses(List<Genus> genuses) {
        this.genuses = genuses;
    }

    @JsonIgnore
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public List<Product> getAllProducts(){
        ArrayList<Product> list =new ArrayList<>();
        for(Genus i : this.genuses){
            list.addAll(i.getProducts());
        }
        return list;
    }
}
