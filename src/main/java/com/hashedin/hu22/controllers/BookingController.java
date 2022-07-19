package com.hashedin.hu22.controllers;

import com.hashedin.hu22.BookDTO;
import com.hashedin.hu22.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/book")
public class BookingController {

    @Autowired
    BookingService bookingService;


    @PostMapping("/addToCart")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<Object> addMovieToCart(@RequestBody BookDTO bookDTO,Principal principal){
        return bookingService.addMovieToCart(bookDTO, principal);
    }

    @PostMapping("/checkoutCart/{cartId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<Object> addMovieToCart(@PathVariable int cartId ,Principal principal){
        return bookingService.moveCartToCheckout(cartId, principal);
    }

    @GetMapping("/cartById/{cart_id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<Object> getCartById(@PathVariable int cart_Id, Principal principal) {
        return bookingService.getCartById(cart_Id, principal);
    }

    @GetMapping("/allCartItem")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<Object> getAllCartItem(Principal principal) {
        return bookingService.getAllCartItem(principal);
    }

    @GetMapping("/allBookingItem")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<Object> getAllBookedTicket(Principal principal) {
        return bookingService.getAllBookedTicket(principal);
    }

    @GetMapping("TicketById/{Ticket_id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_USER')")
    public ResponseEntity<Object> getBookingTicketById(@PathVariable int Ticket_Id, Principal principal) {
        return bookingService.getBookingTicketById(Ticket_Id, principal);
    }
}
