package com.hashedin.hu22.controllers;

import com.hashedin.hu22.entities.User;
import com.hashedin.hu22.service.MovieService;
import com.hashedin.hu22.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @PostMapping("/add")
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> addUser(@RequestBody User user){
        return userService.addUser(user);
    }

    @GetMapping("/access/{userId}/{userRole}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    public String giveAccessToUser(@PathVariable int userId, @PathVariable String userRole, Principal principal){
        return userService.giveNewAccessToUser(userId,userRole,principal);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody User user){
        return userService.loginUser(user);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Object> getAllUsers() {
        return userService.fetchAllUser();
    }

    @PutMapping("/add/movieRating/{movie_id}/{rating}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<Object>  addMovieRating(@PathVariable int movie_id, @PathVariable int rating, Principal principal) {
        if(rating > 5) {
            return new ResponseEntity<>("Movie rating must be below 5",HttpStatus.BAD_REQUEST);
        }
        else if(rating <= 0){
            return new ResponseEntity<>("Movie rating must be greater then 0", HttpStatus.BAD_REQUEST);
        }
        return movieService.addMovieRating(movie_id, rating, principal);
    }


}
