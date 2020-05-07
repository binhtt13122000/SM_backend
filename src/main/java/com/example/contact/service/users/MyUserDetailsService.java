package com.example.contact.service.users;


import com.example.contact.entities.users.UserDetail;
import com.example.contact.entities.users.UserEntity;
import com.example.contact.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService, UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username);
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
