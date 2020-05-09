package com.example.contact.entities.userrole;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserroleId implements Serializable {
    @Column(name = "[user_id]")
    private Long userId;
    @Column(name = "role_id")
    private Long roleId;

    public UserroleId(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public UserroleId() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
