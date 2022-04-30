package com.kazemi.challenge.challenge.repository;

import com.kazemi.challenge.challenge.model.MovieRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRateRepository extends JpaRepository<MovieRate, Long> {

    Optional<MovieRate> findFirstByMovie_ImdbIDAndUserId(String imdbId, Long userId);

    @Query("select avg(mr.rate) from MovieRate mr where mr.movie.imdbID = :imdbId ")
    Double getMovieRateAverage(String imdbId);

}
