package com.pyro.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Message extends AbstractEntity implements Comparable<Message> {

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "customer", nullable = false)
    private User user;

    @Column
    private Date date;

    @Column(length = 1024)
    private String text;

    @Column
    private int rating;
    @Column
    private String header;

    public Message() {
    }

    public Message(User user, String header, String text, Date date) {
        this.user = user;
        this.header = header;
        this.text = text;
        this.date = date;
        this.rating = 0;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void incrementRating() {
        this.rating++;
    }

    public void decrementRating() {
        this.rating--;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int compareTo(Message o) {
        return this.date.before(o.date) ? 1 : this.date.after(o.date) ? -1 : 0;
    }
}
