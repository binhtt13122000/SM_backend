package com.example.contact.controllers.users;

import com.example.contact.entities.role.Role;
import com.example.contact.entities.users.UserDetail;
import com.example.contact.entities.users.UserEntity;
import com.example.contact.entities.users.UserRequest;
import com.example.contact.entities.users.UserResponse;
import com.example.contact.service.users.MyUserDetailsService;
import com.example.contact.service.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class MainController {
    @Autowired
    private MyUserDetailsService userService;

    //ok
    @GetMapping("/users")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity getAllUsers(
            @RequestParam Optional<Integer> offset,
            @RequestParam Optional<Integer> limit,
            @RequestParam Optional<String> sort,
            @RequestParam Optional<String> q) {
        Pageable pageable = PageRequest.of(offset.orElse(0), limit.orElse(10), Sort.Direction.ASC, sort.orElse("id"));
        Page<Object[]> userEntities;
        if (q.isPresent()) {
            userEntities = userService.findByName(q.get(), pageable);
        } else {
            userEntities = userService.findAllUsers(pageable);
        }
        if (userEntities.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity(userEntities, HttpStatus.OK);
        }
    }

    //only user access or admin
    //ok
    @GetMapping("/users/{id}")
    public ResponseEntity getUserById(@PathVariable("id") Long id) {
        Optional<UserEntity> userEntity = userService.findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        if (userEntity.isPresent()) {
            if(userEntity.get().getUsername().equals(authentication.getName()) || isAdmin(authentication)){
                return new ResponseEntity(new UserResponse(userEntity.get()), HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            }
        } else {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
    }

    @PostMapping("/user")
    public ResponseEntity addNewUser(@RequestBody UserRequest userEntity) {
        userService.save(userEntity);
        return new ResponseEntity(userEntity, HttpStatus.CREATED);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity updateUser(@PathVariable("id") Long id, @RequestBody UserEntity userEntity) {
        Optional<UserEntity> currentUserEntity = userService.findById(id);
        if (!currentUserEntity.isPresent()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        currentUserEntity.get().setName(userEntity.getName());
        currentUserEntity.get().setEmail(userEntity.getEmail());
        currentUserEntity.get().setPhone(userEntity.getPhone());
        userService.save(currentUserEntity.get());
        return new ResponseEntity(currentUserEntity.get(), HttpStatus.OK);
    }

    //ok
    @DeleteMapping("/user/{id}")
    @RolesAllowed("ROLE_ADMIN")
    public ResponseEntity removeUser(@PathVariable("id") Long id) {
        Optional<UserEntity> userEntity = userService.findById(id);
        if (!userEntity.isPresent()) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        userService.remove(userEntity.get());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    private boolean isAdmin(Authentication authentication){
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
