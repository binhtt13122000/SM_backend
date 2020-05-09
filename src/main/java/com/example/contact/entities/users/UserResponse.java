package com.example.contact.entities.users;

import com.example.contact.entities.role.Role;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class UserResponse {
    final private String username;
    final private String password;
    final private String name;
    final private String email;
    final private String phone;
    final private Set<String> roles = new HashSet<>();

    public UserResponse(UserEntity userEntity) {
        this.username = userEntity.getUsername();
        this.password = userEntity.getPassword();
        this.name = userEntity.getName();
        this.email = userEntity.getEmail();
        this.phone = userEntity.getPhone();
        Set<Role> roles = userEntity.getRoles();
        for (Role role: roles) {
            this.roles.add(role.getName());
        }
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Set<String> getRoles() {
        return roles;
    }
}
