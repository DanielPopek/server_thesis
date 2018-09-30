package com.daniel.popek.thesis.app.model.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Conversation {
    private Integer id;
    private String hash;
    private String name;
    private Integer designer_id;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "hash",nullable = false)
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "designer_id")
    public Integer getDesigner_id() {
        return designer_id;
    }

    public void setDesigner_id(Integer designer_id) {
        this.designer_id = designer_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conversation that = (Conversation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(hash, that.hash);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, hash);
    }


}
