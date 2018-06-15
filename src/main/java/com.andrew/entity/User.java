package com.andrew.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique=true)
    private String ldapUid;

    @JsonIgnore
    @ManyToMany
    private List<User> following;

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

    public List<User> getFollowing() {
        if (following == null)
            following = new ArrayList<>();
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public void addFollowing(User toFollow) {
        if (following == null)
            following = new ArrayList<>();
        following.add(toFollow);
    }

    public String getLdapUid() {
        return ldapUid;
    }

    public void setLdapUid(String ldapUid) {
        this.ldapUid = ldapUid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("id", id).
                append("name", name).
                append("ldapUid", ldapUid).
                toString();
    }
}
