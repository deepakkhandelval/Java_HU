package com.hashedin.hu22.service;

import com.hashedin.hu22.entities.User;
import com.hashedin.hu22.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.hashedin.hu22.common.UserConstant.ADMIN_ACCESS;
import static com.hashedin.hu22.common.UserConstant.MODERATOR_ACCESS;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<Object> addUser(User user){
        try{
            user.setRoles(user.getRoles());
            String encryptedPwd = passwordEncoder.encode(user.getDob());
            user.setPassword(encryptedPwd);
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>("Error occurred while saving user",HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<Object> fetchAllUser(){
        try{
            List<User> userList =  userRepository.findAll();
            return new ResponseEntity<>(userList, HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity<>("Error while fetching user data",HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<Object> loginUser(User user){
        User userData = userRepository.findByUserName(user.getUserName()).get();
        if(passwordEncoder.matches(user.getPassword(), userData.getPassword())){
            return new ResponseEntity<>("User login successful",HttpStatus.OK);
        }
        return new ResponseEntity<>("Bad credentials",HttpStatus.BAD_REQUEST);

    }

    public String giveNewAccessToUser(int userId, String userRole, Principal principal){
        User user = userRepository.findById(userId).get();
        List<String> activeRoles = getRolesByLoggedInUser(principal);
        String newRole="";
        if(activeRoles.contains(userRole)){
            newRole = user.getRoles()+","+userRole;
            user.setRoles(newRole);
        }
        userRepository.save(user);
        return "Hi" + user.getUserName() + " New Role assign to you by " + principal.getName();
    }

    private List<String> getRolesByLoggedInUser(Principal principal){
        String roles = getLoggedInUser(principal).getRoles();
        List<String> assignRoles = Arrays.stream(roles.split(",")).collect(Collectors.toList());
        if(assignRoles.contains("ROLE_ADMIN")){
            return Arrays.stream(ADMIN_ACCESS).collect(Collectors.toList());
        }
        if(assignRoles.contains("ROLE_MODERATOR")){
            return Arrays.stream(MODERATOR_ACCESS).collect(Collectors.toList());
        }

        return Collections.emptyList();

    }

    private User getLoggedInUser(Principal principal) {
        return userRepository.findByUserName(principal.getName()).get();
    }


}
