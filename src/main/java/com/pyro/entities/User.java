package com.pyro.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer")
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
public class User extends AbstractEntity {

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    List<Role> roles;
    @Column(name = "enabled")
    private int enabled;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String src;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> favorits;

    public User() {
    }

    public User(int enabled, String username, String password, List<Role> roles) {
        this.enabled = enabled;
        this.username = username;
        this.password = password;
        this.roles = roles;
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
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public List<Product> getFavorits() {
        return favorits;
    }

    public void setFavorits(List<Product> favorits) {
        this.favorits = favorits;
    }
}
