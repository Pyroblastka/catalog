package com.pyro.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Discussion extends AbstractEntity {

    @Column
    private String name;

    @Column
    private final Date date;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Message> messages;

    public Discussion() {
        this.date = new Date();
    }

    public Discussion(String name) {
        this();
        this.name = name;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Date getFoundationDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
