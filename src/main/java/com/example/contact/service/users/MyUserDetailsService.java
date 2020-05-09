package com.example.contact.service.users;


import com.example.contact.entities.users.UserDetail;
import com.example.contact.entities.users.UserEntity;
import com.example.contact.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService, UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Page<Object[]> findAllUsers(Pageable pageable) {
        return userRepository.findAllUser(pageable);
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void save(UserEntity userEntity) {
        userRepository.save(userEntity);
    }

    @Override
    public void remove(UserEntity userEntity) {
        userRepository.delete(userEntity);
    }

    @Override
    public Page<Object[]> findByName(String name, Pageable pageable) {
        return userRepository.findByNameContaining(name, pageable);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserEntity userEntity = findByUsername(s);
        if(userEntity == null){
            throw new UsernameNotFoundException(s);
        }
        return new UserDetail(userEntity);
    }
}
