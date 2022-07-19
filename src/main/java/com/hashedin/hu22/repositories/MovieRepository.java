package com.hashedin.hu22.repositories;

import com.hashedin.hu22.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie,Integer> {
}
