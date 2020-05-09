package com.example.contact.service.users;



import com.example.contact.entities.users.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

    UserEntity findByUsername(String username);

    Page<Object[]> findAllUsers(Pageable pageable);

    Optional<UserEntity> findById(Long id);

    void save(UserEntity studentEntity);

    void remove(UserEntity studentEntity);

    Page<Object[]> findByName(String name, Pageable pageable);
}
