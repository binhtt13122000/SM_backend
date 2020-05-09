package com.example.contact.controllers.login;

import com.example.contact.entities.authentication.AuthenticationRequest;
import com.example.contact.entities.authentication.AuthenticationResponse;
import com.example.contact.entities.users.UserEntity;
import com.example.contact.security.jwtutils.JwtUtil;
import com.example.contact.service.users.MyUserDetailsService;
import com.example.contact.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
public class LoginController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private Validation validation;

    //    @GetMapping("/users")
//    @RolesAllowed("ROLE_ADMIN")
//    public ResponseEntity<?> findAllUser(){
//        List<UserEntity> userEntityList = userDetailsService.findAllUsers();
//        if(userEntityList.isEmpty()){
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } else {
//            return new ResponseEntity<>(userEntityList, HttpStatus.OK);
//        }
//    }

    @PostMapping("/register")
    public ResponseEntity addNewUser(@RequestBody UserEntity userEntity) {
        if(validation.isExistedUsername(userEntity.getUsername()) || validation.isAdmin(userEntity)){
            return  new ResponseEntity(userEntity, HttpStatus.BAD_REQUEST);
        }
        userDetailsService.save(userEntity);
        return new ResponseEntity(userEntity, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        }catch(BadCredentialsException e) {
            throw new Exception("Incorrect username and password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
