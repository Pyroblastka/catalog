package com.pyro.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pyro.entities.classification.Genus;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table
public class Product extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)

    @JsonIgnore
    Genus genus;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    List<Message> messages;
    @Column
    private String name;
    @Column
    private String src;
    @Column(length = 1024)
    private String description;


    public Product() {
    }

    public Product(String name, String description, String image, Genus genus) {
        this.name = name;
        this.description = description;
        this.genus = genus;
        if (image.contains("/getFile/")) {
            this.src = image;
        } else {
            this.src = "/getFile/" + image;
        }
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String image) {
        if (image.contains("/getFile/")) {
            this.src = image;
        } else {
            this.src = "/getFile/" + image;
        }
    }

    public void addComment(Message message) {
        this.messages.add(message);
    }

    @JsonIgnore
    public List<Message> getMessages() {
        return messages;
    }

    @JsonIgnore
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @JsonIgnore
    public Genus getGenus() {
        return genus;
    }

    @JsonIgnore
    public void setGenus(Genus genus) {
        this.genus = genus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public Genus getCategory() {
        return genus;
    }

    public Map<String, String> getHierarchy() {
        Map<String, String> map = new HashMap<>();

        map.put("genus", genus.getName());
        map.put("family", genus.getFamily().getName());
        map.put("order", genus.getFamily().getOrder().getName());
        map.put("klass", genus.getFamily().getOrder().getKlass().getName());
        map.put("phylum", genus.getFamily().getOrder().getKlass().getPhylum().getName());
        map.put("kingdom", genus.getFamily().getOrder().getKlass().getPhylum().getKingdom().getName());

        return map;
    }

}
