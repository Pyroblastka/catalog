package com.pyro.entities.classification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pyro.entities.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Kingdom extends AbstractStair {

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "kingdom_id", nullable = true)
    List<Phylum> phylums;
    @Column
    private String name;

    public Kingdom(String name) {
        this.name = name;
        this.phylums = new ArrayList<>();
    }

    public Kingdom() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public List<Phylum> getPhylums() {
        return phylums;
    }

    @JsonIgnore
    public void setPhylums(List<Phylum> phylums) {
        this.phylums = phylums;
    }

    @Override
    public List<Product> getAllProducts() {
        ArrayList<Product> list = new ArrayList<>();
        for (Phylum i : this.phylums) {
            list.addAll(i.getAllProducts());
        }
        return list;
    }
}
