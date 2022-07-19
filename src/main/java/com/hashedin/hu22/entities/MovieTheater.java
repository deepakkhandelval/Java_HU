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
@Table(name = "Theater_Location")
public class MovieTheater {
    @Id
    @GeneratedValue
    private int id;
    private String theaterName;
    private String address;
    private String city;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "theatersLocation")
    private List<Movie> movieShows = new ArrayList<>();

    private int ticketCost;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "preferredTheaterTicket")
    private List<BookMovie> BookingDetails = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "preferredTheater")
    private List<Cart> cartItems = new ArrayList<>();

}
