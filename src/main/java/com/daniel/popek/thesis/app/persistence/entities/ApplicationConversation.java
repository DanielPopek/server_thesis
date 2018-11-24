package com.daniel.popek.thesis.app.persistence.entities;


import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "application_conversation", schema = "chatrooster", catalog = "")
@IdClass(ApplicationConversationPK.class)
public class ApplicationConversation {
    private Integer applicationId;
    private Integer conversationId;
//    private Application applicationByApplicationId;
//    private Conversation conversationByConversationId;

    @Id
    @Column(name = "application_id")
    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    @Id
    @Column(name = "conversation_id")
    public Integer getConversationId() {
        return conversationId;
    }

    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationConversation that = (ApplicationConversation) o;
        return Objects.equals(applicationId, that.applicationId) &&
                Objects.equals(conversationId, that.conversationId);
//        return that.applicationByApplicationId.equals(((ApplicationConversation) o).applicationByApplicationId)
//                &&that.conversationByConversationId.equals(((ApplicationConversation) o).conversationByConversationId);
    }

    @Override
    public int hashCode() {

//        return Objects.hash(applicationByApplicationId.getId(), conversationByConversationId.getId());
        return Objects.hash(applicationId,conversationId);
    }

//    @ManyToOne
//    @JoinColumn(name = "application_id", referencedColumnName = "id", nullable = false)
//    public Application getApplicationByApplicationId() {
//        return applicationByApplicationId;
//    }
//
//    public void setApplicationByApplicationId(Application applicationByApplicationId) {
//        this.applicationByApplicationId = applicationByApplicationId;
//    }
//
//    @ManyToOne
//    @JoinColumn(name = "conversation_id", referencedColumnName = "id", nullable = false)
//    public Conversation getConversationByConversationId() {
//        return conversationByConversationId;
//    }
//
//    public void setConversationByConversationId(Conversation conversationByConversationId) {
//        this.conversationByConversationId = conversationByConversationId;
//    }
}
