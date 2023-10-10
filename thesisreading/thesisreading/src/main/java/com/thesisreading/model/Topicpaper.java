package com.thesisreading.model;

import com.google.gson.annotations.Expose;
import lombok.Builder;

import java.io.Serializable;

@Builder
public class Topicpaper implements Serializable {

    @Expose
    private Integer topicpaperId;//topicPaperID


    @Expose
    private String stutopicId;//StuTopicID

    @Expose
    private Integer paperId;//论文ID


    @Expose
    private Double relevancy;//相关程度 0-1

    private static final long serialVersionUID = 1L;

    public Integer getTopicpaperId() {
        return topicpaperId;
    }

    public void setTopicpaperId(Integer topicpaperId) {
        this.topicpaperId = topicpaperId;
    }

    public String getStutopicId() {
        return stutopicId;
    }

    public void setStutopicId(String stutopicId) {
        this.stutopicId = stutopicId;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public Double getRelevancy() {
        return relevancy;
    }

    public void setRelevancy(Double relevancy) {
        this.relevancy = relevancy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", topicpaperId=").append(topicpaperId);
        sb.append(", stutopicId=").append(stutopicId);
        sb.append(", paperId=").append(paperId);
        sb.append(", relevancy=").append(relevancy);
        sb.append("]");
        return sb.toString();
    }
}
