package com.example.contact.service.users;



import com.example.contact.entities.users.UserEntity;

import java.util.List;

public interface UserService {
    List<UserEntity> findAllUsers();

    UserEntity findByUsername(String username);
}
