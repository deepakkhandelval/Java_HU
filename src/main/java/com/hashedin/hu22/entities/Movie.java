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
@Table(name = "MOVIE_DETAILS")
public class Movie {
    @Id
    @GeneratedValue
    private int id;

    private String movieName;
    private String movieLanguage;
    private String genre;
    private String directorName;
    private String producersNameList;
    private String castNames;
    private String movieDuration;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "User_MovieRating",
            joinColumns = {@JoinColumn(name = "USER_AUTH_Id")},
            inverseJoinColumns = {@JoinColumn(name = "MOVIE_Id")}
    )
    private List<User> userRating = new ArrayList<>();

    private int averageRating;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,mappedBy = "ticketOfMovie")
    private List<BookMovie> ticketSold = new ArrayList<>();


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "movieDetails")
    private List<Cart> movieAddedToCart = new ArrayList<>();
//    theater

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "movieShow",
        joinColumns = {@JoinColumn(name = "MOVIE_DETAILS_Id")},
        inverseJoinColumns = {@JoinColumn(name = "Theater_Location_Id")})
    private List<MovieTheater> theatersLocation = new ArrayList<>();
//    timing

}
