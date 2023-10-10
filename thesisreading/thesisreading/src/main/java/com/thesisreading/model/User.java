package com.thesisreading.model;

import lombok.Builder;

import java.io.Serializable;

@Builder
public class User implements Serializable {

    private String userId;//用户ID

    private String password;//用户密码

    private Integer role;//用户类型

    private static final long serialVersionUID = 1L;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", password=").append(password);
        sb.append(", role=").append(role);
        sb.append("]");
        return sb.toString();
    }
}
