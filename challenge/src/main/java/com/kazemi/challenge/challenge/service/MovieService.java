package com.kazemi.challenge.challenge.service;


import com.fasterxml.jackson.databind.node.ObjectNode;
import com.kazemi.challenge.challenge.model.Movie;
import com.kazemi.challenge.challenge.repository.MovieRepository;
import com.kazemi.challenge.challenge.util.Util;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * The {@code MovieService} provides several methods for working with Movie entity
 *
 * @author Zahra Kazemi
 */

@Service
@AllArgsConstructor
@Slf4j
public class MovieService implements CrudService<Movie, Long> {

    private final MovieRepository movieRepository;

    @Override
    public Set<Movie> findAll() {
        Set<Movie> movies = new HashSet<>();
        movieRepository.findAll().forEach(movies::add);
        return movies;
    }


    @Override
    public Movie findById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    @Override
    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public void delete(Movie movie) {
        movieRepository.delete(movie);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Movie> user = movieRepository.findById(id);
        user.ifPresent(u -> movieRepository.deleteById(id));
    }

    /**
     * This method find a movie' inforamtio from OMDB .
     * <p>If calling the API encounters an error or return null then noting will be returned.
     *
     * @param title the name of the movie
     * @return movie entity based on the information obtained by API calls.
     */
    public Movie saveMovieByTitle(String title) {

        Optional<Movie> movieByTitle = movieRepository.findMovieByTitle(title);

        if (movieByTitle.isPresent())
            return movieByTitle.get();

        Optional<Movie> movieInfo = getMovieInformationFromOMDB(title);

        if (movieInfo.isPresent()) {
            Movie movie = movieInfo.get();
            return movieRepository.save(Movie.builder().imdbID(movie.getImdbID()).title(movie.getTitle())
                    .rate(movie.getRate()).boxOffice(movie.getBoxOffice()).build());
        }

        return null;
    }

    /**
     * This method retrieves movie information through an OMDB API.
     * <p>If calling the API encounters an error noting will be returned.
     *
     * @param title the name of the movie
     * @return an optional movie entity based on the information obtained by API calls.
     */
    public Optional<Movie> getMovieInformationFromOMDB(String title) {

        try {
            String apiUrl = "http://www.omdbapi.com/?apikey=fce25a3b&t=" + title;

            RestTemplate template = new RestTemplate();

            log.info("OMDb API called with URL: {}", apiUrl);
            ResponseEntity<ObjectNode> response =
                    template.getForEntity(apiUrl, ObjectNode.class);

            ObjectNode jsonObject = response.getBody();
            log.debug("OMDb API response:{}", jsonObject);

            return Optional.ofNullable(Movie.builder()
                    .title(title)
                    .imdbID(jsonObject.path("imdbID").asText())
                    .rate(jsonObject.path("imdbRating").asDouble())
                    .boxOffice(Util.convertFormattedStringToAmount(jsonObject.path("BoxOffice").asText()))
                    .build());
        } catch (Exception ex) {
            return Optional.ofNullable(null);
        }
    }
}


