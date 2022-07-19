package com.hashedin.hu22.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "BOOK_MOVIE")
public class BookMovie {
    @Id
    @GeneratedValue
    private int id;

    private int NoOfTicket;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie ticketOfMovie;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "MovieTheater_id", nullable = false)
    private MovieTheater preferredTheaterTicket;

    private int totalCost;

}
