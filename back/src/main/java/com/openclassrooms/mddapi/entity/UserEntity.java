package com.openclassrooms.mddapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name="users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max=30)
    private String name;

    @NotNull
    @Size(max=63)
    private String email;

    @NotNull
    @Size(max=60)
    private String password;

    @NotNull
    private String roles;

    @ManyToMany
    @JoinTable(
            name="subscriptions",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="topic_id")
    )
    Set<TopicEntity> subscriptions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Set<TopicEntity> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<TopicEntity> subscriptions) {
        this.subscriptions = subscriptions;
    }


}

