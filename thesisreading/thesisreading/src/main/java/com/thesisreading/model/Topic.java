package com.thesisreading.model;

import com.google.gson.annotations.Expose;
import lombok.Builder;

import java.io.Serializable;
import java.util.Date;

@Builder
public class Topic implements Serializable {

    @Expose
    private String topicId;//主题ID

    @Expose
    private String courseId;//课程ID

    @Expose
    private String teacherId;//出题教师ID

    @Expose
    private String topic;//主题名

    @Expose
    private String keyword;//关键词

    @Expose
    private Date creationTime;//创建时间

    @Expose
    private Integer studentNum;//已选学生人数

    @Expose
    private Integer maxNum;//人数上限 1-20

    private static final long serialVersionUID = 1L;

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Integer getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(Integer studentNum) {
        this.studentNum = studentNum;
    }

    public Integer getMaxNum() { return maxNum; }

    public void setMaxNum(Integer maxNum) { this.maxNum = maxNum; }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", topicId=").append(topicId);
        sb.append(", courseId=").append(courseId);
        sb.append(", teacherId=").append(teacherId);
        sb.append(", topic=").append(topic);
        sb.append(", keyword=").append(keyword);
        sb.append(", creationTime=").append(creationTime);
        sb.append(", studentNum=").append(studentNum);
        sb.append("]");
        return sb.toString();
    }
}
