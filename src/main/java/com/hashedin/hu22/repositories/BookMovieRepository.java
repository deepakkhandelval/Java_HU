package com.hashedin.hu22.repositories;

import com.hashedin.hu22.entities.BookMovie;
import com.hashedin.hu22.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookMovieRepository extends JpaRepository<BookMovie,Integer> {
    @Query("select b from BookMovie b where b.user = :user")
    public List<BookMovie> findByUser(User user);
}
