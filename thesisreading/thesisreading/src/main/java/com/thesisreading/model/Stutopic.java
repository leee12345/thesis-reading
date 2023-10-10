package com.thesisreading.model;

import com.google.gson.annotations.Expose;
import lombok.Builder;

import java.io.Serializable;
import java.util.Date;

@Builder
public class Stutopic implements Serializable {

    @Expose
    private String stutopicId;//StuTopicID


    @Expose
    private String studentId;//学号


    @Expose
    private String topicId;//主题ID


    @Expose
    private Date selectTime;//选题时间

    @Expose
    private String flag;//是否已上传论文：0 未上传 1 已上传

    private static final long serialVersionUID = 1L;

    public String getStutopicId() {
        return stutopicId;
    }

    public void setStutopicId(String stutopicId) {
        this.stutopicId = stutopicId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public Date getSelectTime() {
        return selectTime;
    }

    public void setSelectTime(Date selectTime) {
        this.selectTime = selectTime;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", stutopicId=").append(stutopicId);
        sb.append(", studentId=").append(studentId);
        sb.append(", topicId=").append(topicId);
        sb.append(", selectTime=").append(selectTime);
        sb.append(", flag=").append(flag);
        sb.append("]");
        return sb.toString();
    }
}
