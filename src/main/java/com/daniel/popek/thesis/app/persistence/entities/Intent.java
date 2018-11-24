package com.daniel.popek.thesis.app.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
public class Intent {
    private Integer id;
    private String name;
    private String hash;
    private Boolean root=false;
    private Collection<Answersample> answersamplesById;
    private Intent intentByIntentId;
    private Collection<Intent> intentsById;
    private Collection<Event> eventByEventId;
   // private Event eventByEventId;
    private Conversation conversationByConversationId;
    private Collection<Trainingsample> trainingsamplesById;
    private Collection<MisunderstandingStatement> misunderstandingStatementsById;

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
    @Column(name = "hash")
    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Basic
    @Column(name = "root")
    public Boolean getRoot() {
        return root;
    }

    public void setRoot(Boolean root) {
        this.root = root;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Intent intent = (Intent) o;
        return Objects.equals(id, intent.id) &&
                Objects.equals(name, intent.name) &&
                Objects.equals(hash, intent.hash);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, hash);
    }

    @OneToMany(mappedBy = "intentByIntentId",cascade = {CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH,CascadeType.REMOVE})
    public Collection<Answersample> getAnswersamplesById() {
        return answersamplesById;
    }


    public void setAnswersamplesById(Collection<Answersample> answersamplesById) {
        this.answersamplesById = answersamplesById;
    }

    @OneToMany(mappedBy = "intentByIntentId",cascade = {CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH,CascadeType.REMOVE})
    public Collection<MisunderstandingStatement> getMisunderstandingStatementsById() {
        return misunderstandingStatementsById;
    }

    public void setMisunderstandingStatementsById(Collection<MisunderstandingStatement> misunderstandingStatementsById) {
        this.misunderstandingStatementsById = misunderstandingStatementsById;
    }

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH})
    @JoinColumn(name = "intent_id", referencedColumnName = "id")
    public Intent getIntentByIntentId() {
        return intentByIntentId;
    }

    public void setIntentByIntentId(Intent intentByIntentId) {
        this.intentByIntentId = intentByIntentId;
    }

    @OneToMany(mappedBy = "intentByIntentId",cascade = {CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH,CascadeType.REMOVE})
    public Collection<Intent> getIntentsById() {
        return intentsById;
    }

    public void setIntentsById(Collection<Intent> intentsById) {
        this.intentsById = intentsById;
    }

//    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH,CascadeType.REMOVE})
//    @JoinColumn(name = "event_id", referencedColumnName = "id")
//    public Event getEventByEventId() {
//        return eventByEventId;
//    }
//
//    public void setEventByEventId(Event eventByEventId) {
//        this.eventByEventId = eventByEventId;
//    }

    @OneToMany(mappedBy = "intentByIntentId",cascade = {CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH,CascadeType.REMOVE})
    public Collection<Event> getEventByEventId() {
        return eventByEventId;
    }

    public void setEventByEventId(Collection<Event> eventByEventId) {
        this.eventByEventId = eventByEventId;
    }


    @ManyToOne
    @JoinColumn(name = "conversation_id", referencedColumnName = "id", nullable = true)
    public Conversation getConversationByConversationId() {
        return conversationByConversationId;
    }

    public void setConversationByConversationId(Conversation conversationByConversationId) {
        this.conversationByConversationId = conversationByConversationId;
    }

    @OneToMany(mappedBy = "intentByIntentId",cascade = {CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH,CascadeType.REMOVE})
    public Collection<Trainingsample> getTrainingsamplesById() {
        return trainingsamplesById;
    }

    public void setTrainingsamplesById(Collection<Trainingsample> trainingsamplesById) {
        this.trainingsamplesById = trainingsamplesById;
    }
}
