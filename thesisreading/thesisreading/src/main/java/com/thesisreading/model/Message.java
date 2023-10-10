package com.thesisreading.model;

import com.google.gson.annotations.Expose;
import lombok.Builder;

@Builder
public class Message {
    @Expose
    private Integer messageId;//评论ID


    @Expose
    private Integer paperId;//论文ID


    @Expose
    private String content;//评论内容


    @Expose
    private String userId;//用户ID

    private static final long serialVersionUID = 1L;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getPaperId() { return paperId; }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", messageId=").append(messageId);
        sb.append(", paperId=").append(paperId);
        sb.append(", content=").append(content);
        sb.append(", userId=").append(userId);
        sb.append("]");
        return sb.toString();
    }
}
