package com.hashedin.hu22.service;

import com.hashedin.hu22.BookDTO;
import com.hashedin.hu22.entities.*;
import com.hashedin.hu22.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Random;

@Service
public class BookingService {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieTheaterRepository movieTheaterRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    BookMovieRepository bookMovieRepository;

    public ResponseEntity<Object> addMovieToCart(BookDTO bookDTO, Principal principal){
        try{
            Cart cart = new Cart();

            Movie movie = movieRepository.getById(bookDTO.getMovieId());
            MovieTheater movieTheater = movieTheaterRepository.getById(bookDTO.getMovieTheaterId());
            if(!movieTheater.getMovieShows().contains(movie)) {
                return new ResponseEntity<>("Movie not running in given theater",HttpStatus.BAD_REQUEST);
            }

            User user = userRepository.findByUserName(principal.getName()).get();

            int totalCart = cartRepository.findAll().size();

            cart.setUserDetails(user);
            cart.setId(totalCart + 1);
            cart.setMovieDetails(movie);
            cart.setPreferredTheater(movieTheater);
            cart.setNoOfTicket(bookDTO.getNoOfTicket());
            Cart savedCart = cartRepository.save(cart);

            return new ResponseEntity<>(savedCart, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>("Error while adding to cart",HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Object> moveCartToCheckout(int cartId, Principal principal){
        try{
            Cart cartItem = cartRepository.getById(cartId);
            User user = userRepository.findByUserName(principal.getName()).get();

            if(cartItem.getUserDetails() != user) {
                return new ResponseEntity<>("Not allowed", HttpStatus.NOT_ACCEPTABLE);
            }

            int totalTicketCost = cartItem.getPreferredTheater().getTicketCost() * cartItem.getNoOfTicket();
            System.out.println(cartItem.getPreferredTheater().getTicketCost());
            System.out.println(cartItem.getNoOfTicket());
            int totalBook = bookMovieRepository.findAll().size();
            BookMovie bookMovie = new BookMovie();
            bookMovie.setUser(user);
            bookMovie.setTicketOfMovie(cartItem.getMovieDetails());
            bookMovie.setPreferredTheaterTicket(cartItem.getPreferredTheater());
            bookMovie.setNoOfTicket(cartItem.getNoOfTicket());
            bookMovie.setTotalCost(totalTicketCost);
            bookMovie.setId(totalBook + 1);
            BookMovie savedBookMovie = bookMovieRepository.save(bookMovie);
            return new ResponseEntity<>(savedBookMovie,HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error while checkout", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Object> getCartById(int cartId, Principal principal) {
        try {
            Cart cart = cartRepository.getById(cartId);
            User user = userRepository.findByUserName(principal.getName()).get();

            if(cart.getUserDetails() != user) {
                return new ResponseEntity<>("Not valid cart Id",HttpStatus.NOT_ACCEPTABLE);
            }
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error while fetching cart details", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Object> getAllCartItem(Principal principal) {
        try {
            User user = userRepository.findByUserName(principal.getName()).get();
            List<Cart> cart = cartRepository.findByUser(user);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error while fetching cart details", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Object> getAllBookedTicket(Principal principal) {
        try {
            User user = userRepository.findByUserName(principal.getName()).get();
            List<BookMovie> bookMovies = bookMovieRepository.findByUser(user);
            return new ResponseEntity<>(bookMovies, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error while fetching cart details", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Object> getBookingTicketById(int bookingTicketId, Principal principal) {
        try {
            BookMovie bookMovie = bookMovieRepository.getById(bookingTicketId);
            User user = userRepository.findByUserName(principal.getName()).get();

            if(bookMovie.getUser() != user) {
                return new ResponseEntity<>("Not valid cart Id",HttpStatus.NOT_ACCEPTABLE);
            }
            return new ResponseEntity<>(bookMovie, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Error while fetching Booking Ticket details", HttpStatus.BAD_REQUEST);
        }
    }
}
