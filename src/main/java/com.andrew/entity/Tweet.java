package com.andrew.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
public class Tweet {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(targetEntity=User.class)
    private User user;

    @Column(length=140)
    private String message;

    @CreationTimestamp
    private LocalDateTime created;

    public Long getId() {
      return id;
    }

    public void setId(Long id) {
      this.id = id;
    }

    public User getUser() {
      return user;
    }

    public void setUser(User user) {
      this.user = user;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

    public LocalDateTime getCreated() {
      return created;
    }

    public void setCreated(LocalDateTime created) {
      this.created = created;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).
                append("id", id).
                append("message", message).
                append("created", created).
                toString();
    }
}
