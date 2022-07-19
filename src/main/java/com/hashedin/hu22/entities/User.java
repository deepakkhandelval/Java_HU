package com.hashedin.hu22.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "USER_AUTH")
public class User {
    @Id
    @GeneratedValue
    private int id;
    private String firstName;
    private String lastName;
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String dob;
    private String mobileNumber;
    private String email;
    private String genre;
    private String userName;
    private String password;
    private boolean active;
    private String roles;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userRating")
    private List<Movie> movieRated = new ArrayList<>();


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "user")
    private List<BookMovie> movieTicket = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userDetails")
    private List<Cart> cartItems = new ArrayList<>();

}
