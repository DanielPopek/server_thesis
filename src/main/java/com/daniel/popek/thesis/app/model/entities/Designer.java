package com.daniel.popek.thesis.app.model.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class Designer {
    private Integer id;
    private String email;
    private String password;
    private String salt;
    private Boolean active;
    private Boolean deleted;
    private Date registrationDate;

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
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "salt")
    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Basic
    @Column(name = "active")
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Basic
    @Column(name = "deleted")
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Basic
    @Column(name = "registration_date")
    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Designer designer = (Designer) o;
        return Objects.equals(id, designer.id) &&
                Objects.equals(email, designer.email) &&
                Objects.equals(password, designer.password) &&
                Objects.equals(salt, designer.salt) &&
                Objects.equals(active, designer.active) &&
                Objects.equals(deleted, designer.deleted) &&
                Objects.equals(registrationDate, designer.registrationDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, email, password, salt, active, deleted, registrationDate);
    }
}
