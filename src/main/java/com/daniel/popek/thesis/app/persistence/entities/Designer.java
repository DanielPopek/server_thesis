package com.daniel.popek.thesis.app.persistence.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Designer {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String salt;
    private Boolean active;
    private Timestamp registrationDate;
    private String activationCode;
    private String apiKey;

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
    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
    @Column(name = "api_key")
    public String getApiKey() { return apiKey; }

    public void setApiKey(String apiKey) { this.apiKey = apiKey; }

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
    @Column(name = "activation_code")
    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
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
    @Column(name = "registration_date")
    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Timestamp registrationDate) {
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
                Objects.equals(registrationDate, designer.registrationDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, email, password, salt, active, registrationDate);
    }

}
