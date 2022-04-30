package com.kazemi.challenge.challenge.repository;

import com.kazemi.challenge.challenge.model.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findMovieByTitle(String movieTitle);

    Optional<Movie> findByImdbID(String imdbId);

    @Query("select m  from Movie m order by m.rate desc")
    List<Movie> findTopMovies(Pageable pageable);

}

