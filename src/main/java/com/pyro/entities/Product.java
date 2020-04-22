package com.pyro.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class Product extends AbstractEntity {

    @Column
    private String name;

    @Column
    private String src;

    @Column(length = 1024)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    Category category;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Message> messages;


    public Product() {
    }

    public Product(String name, String description, String image ,Category category) {
        this.name = name;
        this.description = description;
        this.category= category;
        if (image.contains("http://localhost:9090/getFile/")) {
            this.src = image;
        } else {
            this.src = "http://localhost:9090/getFile/" + image;
        }
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String image) {
        if (image.contains("http://localhost:9090/getFile/")) {
            this.src = image;
        } else {
            this.src = "http://localhost:9090/getFile/" + image;
        }
    }

    public void addComment(Message message) {
        this.messages.add(message);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
}
