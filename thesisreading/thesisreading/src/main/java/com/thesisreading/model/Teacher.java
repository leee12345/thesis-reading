package com.thesisreading.model;

import com.google.gson.annotations.Expose;
import lombok.Builder;

import java.io.Serializable;

@Builder
public class Teacher implements Serializable {

    @Expose
    private String teacherId;//教师ID

    @Expose
    private String name;//姓名

    @Expose
    private String sex;//性别

    @Expose
    private String protitle;//教师职称

    @Expose
    private String phone;//手机号

    private static final long serialVersionUID = 1L;

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getProtitle() {
        return protitle;
    }

    public void setProtitle(String protitle) {
        this.protitle = protitle;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", teacherId=").append(teacherId);
        sb.append(", name=").append(name);
        sb.append(", sex=").append(sex);
        sb.append(", protitle=").append(protitle);
        sb.append(", phone=").append(phone);
        sb.append("]");
        return sb.toString();
    }
}
