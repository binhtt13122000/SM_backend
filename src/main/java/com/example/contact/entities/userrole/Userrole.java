package com.example.contact.entities.userrole;

import com.example.contact.entities.role.Role;
import com.example.contact.entities.users.UserEntity;

import javax.persistence.*;

@Entity
@Table(name = "user_role")
public class Userrole {
    @EmbeddedId
    private UserroleId userroleId;

    @ManyToOne
    @MapsId("userId")
    private UserEntity userEntity;

    @ManyToOne
    @MapsId("roleId")
    private Role role;
}
