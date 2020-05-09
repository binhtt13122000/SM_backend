package com.example.contact.validation;

import com.example.contact.entities.role.Role;
import com.example.contact.entities.users.UserEntity;
import com.example.contact.service.users.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

@Service
public class Validation {
    @Autowired
    private MyUserDetailsService userDetailsService;
    public boolean isExistedUsername(String username){
        List<String> usernameList = userDetailsService.findAllUsername();
        for (String u:
             usernameList) {
            System.out.println(u);
        }
        return usernameList.contains(username);
    }

    public boolean isValidPhoneNum(String phone){
        String pattern = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
        return phone.startsWith("0") && phone.length() == 10 && phone.matches(pattern);
    }

    public boolean isAdmin(UserEntity userEntity){
        Set<Role> roleSet = userEntity.getRoles();
        Iterator<Role> iterator = roleSet.iterator();
        while (iterator.hasNext()){
            if(iterator.next().getId() == 1){
                return true;
            }
        }
        return false;
    }
}
