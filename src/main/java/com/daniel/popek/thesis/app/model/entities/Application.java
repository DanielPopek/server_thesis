package com.daniel.popek.thesis.app.model.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Application {
    private Integer id;
    private String name;
    private String description;
    private Integer designerId;
    private Timestamp registrationDate;
    private Timestamp lastModificationDate;
    private String token;
//    private Collection<ApplicationConversation> applicationConversationsById;

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

    @Basic
    @Column(name = "designer_id")
    public Integer getDesignerId() {
        return designerId;
    }

    public void setDesignerId(Integer designer_id) {
        this.designerId = designer_id;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Basic
    @Column(name = "registration_date")
    public Timestamp getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Timestamp registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Basic
    @Column(name = "last_modification_date")
    public Timestamp getLastModificationDate() { return lastModificationDate; }

    public void setLastModificationDate(Timestamp lastModificationDate) { this.lastModificationDate = lastModificationDate; }

    @Basic
    @Column(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

//    @OneToMany(mappedBy = "applicationId")
//    public Collection<ApplicationConversation> getApplicationConversationsById() {
//        return applicationConversationsById;
//    }
//
//    public void setApplicationConversationsById(Collection<ApplicationConversation> applicationConversationsById) {
//        this.applicationConversationsById = applicationConversationsById;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Application that = (Application) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name);
    }


}
