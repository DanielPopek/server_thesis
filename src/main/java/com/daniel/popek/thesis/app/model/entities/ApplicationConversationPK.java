package com.daniel.popek.thesis.app.model.entities;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class ApplicationConversationPK implements Serializable {
    private Integer applicationId;
    private Integer conversationId;

    @Column(name = "application_id")
    @Id
    public Integer getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    @Column(name = "conversation_id")
    @Id
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
        ApplicationConversationPK that = (ApplicationConversationPK) o;
        return Objects.equals(applicationId, that.applicationId) &&
                Objects.equals(conversationId, that.conversationId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(applicationId, conversationId);
    }
}
