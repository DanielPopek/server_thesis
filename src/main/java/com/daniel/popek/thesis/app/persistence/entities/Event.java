package com.daniel.popek.thesis.app.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Event {
    private Integer id;
    private String name;
    private Intent intentByIntentId;

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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "intent_id", referencedColumnName = "id", nullable = false)
    public Intent getIntentByIntentId() {
        return intentByIntentId;
    }

    public void setIntentByIntentId(Intent intentByIntentId) {
        this.intentByIntentId = intentByIntentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id) &&
                Objects.equals(name, event.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }
}
