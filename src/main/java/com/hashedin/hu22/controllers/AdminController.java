package com.hashedin.hu22.controllers;

import com.hashedin.hu22.entities.Movie;
import com.hashedin.hu22.entities.MovieTheater;
import com.hashedin.hu22.entities.User;
import com.hashedin.hu22.service.MovieService;
import com.hashedin.hu22.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @PostMapping("/add")
    public ResponseEntity<Object> addUser(@RequestBody User admin){
        if(!admin.getRoles().contains("ROLE_ADMIN")){
            return new ResponseEntity<>("Admin role not valid", HttpStatus.BAD_REQUEST);
        }
        return userService.addUser(admin);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    @PostMapping("/add/movie")
    public ResponseEntity<Object> addMovie(@RequestBody Movie movie) {
        return movieService.addNewMovie(movie);
    }

    @GetMapping("/getMovie/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<Object> getMovie(@PathVariable int id){
        return movieService.getMovieById(id);
    }

    @GetMapping("/getMovieTheater/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<Object> getMovieTheater(@PathVariable int id){
        return movieService.getMovieTheaterById(id);
    }

    @PostMapping("/add/movieTheater")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    public ResponseEntity<Object> addMovieTheater(@RequestBody MovieTheater movieTheater){
        return movieService.addMovieTheater(movieTheater);
    }

    @PutMapping("/add/movieToTheater/{movieId}/{movieTheaterId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_MODERATOR')")
    public ResponseEntity<Object> addMovieToTheater(@PathVariable int movieId, @PathVariable int movieTheaterId) {
        return movieService.addMovieToTheater(movieTheaterId, movieId);
    }
}
