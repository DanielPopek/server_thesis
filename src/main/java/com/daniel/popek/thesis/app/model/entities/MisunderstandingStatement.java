package com.daniel.popek.thesis.app.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class MisunderstandingStatement {
    private Integer id;
    private String value;
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
    @Column(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MisunderstandingStatement that = (MisunderstandingStatement) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, value);
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
}
