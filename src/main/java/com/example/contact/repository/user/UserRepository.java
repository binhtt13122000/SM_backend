package com.example.contact.repository.user;

import com.example.contact.entities.users.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);

    @Query("select u.name, u.phone, u.email from UserEntity u where u.name like %?1%")
    Page<Object[]> findByNameContaining(String name, Pageable pageable);

    @Query("select u.name, u.phone, u.email from UserEntity u")
    Page<Object[]> findAllUser(Pageable pageable);

    @Query("select u.username from UserEntity u")
    List<String> findAllUsername();
}
