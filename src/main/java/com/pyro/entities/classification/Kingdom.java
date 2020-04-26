package com.pyro.entities.classification;

import com.pyro.entities.AbstractEntity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Kingdom extends AbstractEntity {

    @Column
    private String name;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Phylum> phylums;

    public Kingdom(String name) {
        this.name = name;
    }

    public Kingdom() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Phylum> getPhylums() {
        return phylums;
    }

    public void setPhylums(List<Phylum> phylums) {
        this.phylums = phylums;
    }
}
