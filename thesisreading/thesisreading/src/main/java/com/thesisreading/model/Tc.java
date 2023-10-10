package com.thesisreading.model;

import com.google.gson.annotations.Expose;
import lombok.Builder;

import java.io.Serializable;

@Builder
public class Tc implements Serializable {

    @Expose
    private String tcId;//TCID

    @Expose
    private String courseId;//课程ID

    @Expose
    private String teacherId;//教师ID


    @Expose
    private String teaIdentity;//身份


    @Expose
    private Integer teachHour;//授课学时

    private static final long serialVersionUID = 1L;

    public String getTcId() {
        return tcId;
    }

    public void setTcId(String tcId) {
        this.tcId = tcId;
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

    public String getTeaIdentity() {
        return teaIdentity;
    }

    public void setTeaIdentity(String teaIdentity) {
        this.teaIdentity = teaIdentity;
    }

    public Integer getTeachHour() {
        return teachHour;
    }

    public void setTeachHour(Integer teachHour) {
        this.teachHour = teachHour;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", tcId=").append(tcId);
        sb.append(", courseId=").append(courseId);
        sb.append(", teacherId=").append(teacherId);
        sb.append(", teaIdentity=").append(teaIdentity);
        sb.append(", teachHour=").append(teachHour);
        sb.append("]");
        return sb.toString();
    }
}
