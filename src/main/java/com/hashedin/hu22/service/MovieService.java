package com.hashedin.hu22.service;

import com.hashedin.hu22.entities.Movie;
import com.hashedin.hu22.entities.MovieTheater;
import com.hashedin.hu22.entities.User;
import com.hashedin.hu22.repositories.MovieRepository;
import com.hashedin.hu22.repositories.MovieTheaterRepository;
import com.hashedin.hu22.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieTheaterRepository movieTheaterRepository;

    public ResponseEntity<Object> getMovieById(int id){
        try{
            Movie movie = movieRepository.findById(id).get();
            return new ResponseEntity<>(movie, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error while fetching movie details", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Object> addMovieToTheater(int movieTheater_Id, int movie_Id) {
        try {
            MovieTheater movieTheater = movieTheaterRepository.findById(movieTheater_Id).get();
            Movie movie = movieRepository.findById(movie_Id).get();
            List<MovieTheater> movieTheatersList = movie.getTheatersLocation();
            movieTheatersList.add(movieTheaterRepository.getById(movieTheater_Id));
            movie.setTheatersLocation(movieTheatersList);

            movieRepository.save(movie);
            return new ResponseEntity<>(movie,HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error while adding movies to Theaters",HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Object> addMovieRating(int movieId, int rating, Principal principal) {
        try{
            Movie movie = movieRepository.getById(movieId);
            User user = userRepository.findByUserName(principal.getName()).get();
            int average = (movie.getAverageRating() + rating) /  (movie.getUserRating().size() + 1);
            movie.setAverageRating(average);
            List<Movie> ratedMovieSet = user.getMovieRated();
            ratedMovieSet.add(movie);
            user.setMovieRated(ratedMovieSet);
            userRepository.save(user);
            Movie savedMovie = movieRepository.save(movie);
            return new ResponseEntity<>(savedMovie, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error while saving user rating",HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Object> getMovieTheaterById(int id){
        try{
            MovieTheater movieTheater = movieTheaterRepository.findById(id).get();
            return new ResponseEntity<>(movieTheater, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error while fetching movie theater details", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Object> addNewMovie(Movie movie){
        try{
            Movie savedMovie = movieRepository.save(movie);
            return new ResponseEntity<>(savedMovie, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error while saving data",HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Object> addMovieTheater(MovieTheater movieTheater) {
        try{
            MovieTheater savedMovieTheater = movieTheaterRepository.save(movieTheater);
            return new ResponseEntity<>(savedMovieTheater,HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>("Error while saving movie Theater details", HttpStatus.BAD_REQUEST);
        }
    }

}
