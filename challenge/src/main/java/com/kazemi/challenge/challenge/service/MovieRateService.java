package com.kazemi.challenge.challenge.service;

import com.kazemi.challenge.challenge.dto.DtoMapper;
import com.kazemi.challenge.challenge.dto.VotingRequestDto;
import com.kazemi.challenge.challenge.dto.TopMovieResponseDto;
import com.kazemi.challenge.challenge.dto.VotingResponseDto;
import com.kazemi.challenge.challenge.exception.BusinessException;
import com.kazemi.challenge.challenge.exception.BusinessExceptionType;
import com.kazemi.challenge.challenge.model.Movie;
import com.kazemi.challenge.challenge.model.MovieRate;
import com.kazemi.challenge.challenge.repository.MovieRateRepository;
import com.kazemi.challenge.challenge.repository.MovieRepository;
import com.kazemi.challenge.challenge.util.Util;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * The {@code MovieRateService} provides several methods for working with MovieRate entity.
 * Inorder to voting to a movie by a user, this class provides several methods.
 *
 * @author Zahra Kazemi
 */

@Service
@AllArgsConstructor
@Slf4j
public class MovieRateService implements CrudService<MovieRate, Long> {

    private final MovieRateRepository movieRateRepository;
    private final MovieRepository movieRepository;
    private final DtoMapper dtoMapper;


    @Override
    public Set<MovieRate> findAll() {
        Set<MovieRate> movieRates = new HashSet<>();
        movieRateRepository.findAll().forEach(movieRates::add);
        return movieRates;
    }

    @Override
    public MovieRate findById(Long id) {
        return movieRateRepository.findById(id).orElse(null);
    }

    @Override
    public MovieRate save(MovieRate movieRates) {
        return movieRateRepository.save(movieRates);
    }

    @Override
    public void delete(MovieRate movieRates) {
        movieRateRepository.delete(movieRates);
    }

    @Override
    public void deleteById(Long id) {
        Optional<MovieRate> movie = movieRateRepository.findById(id);
        movie.ifPresent(u -> movieRateRepository.deleteById(id));
    }

    public MovieRate saveMovieRate(Movie movie, Long userId, Double rate) {
        Optional<MovieRate> movieRate = movieRateRepository.findFirstByMovie_ImdbIDAndUserId(movie.getImdbID(), userId);
        return (movieRate.isPresent() ? movieRate.get() : movieRateRepository.save(MovieRate.builder()
                .userId(userId)
                .movie(movie)
                .rate(rate)
                .build()));
    }

    /**
     * This method persists a user vote for a specific movie in the data storage.
     * Then, calculate the new movie's rate and persist on the {@code Rate} property
     * of the movie. This action helps to access the rate of a movie immediately rather
     * than calculating it by querying from data storage
     *
     * <p>If calling the API encounters an error noting will be returned.
     *
     * @param request an object consist of the userId, imdbID, and rate.
     * @return new movie's rate after user's vote.
     * @throws NotFoundException   if the {@code movie} was not found.
     * @throws DuplicatedException if the {@code user} has already voted for the movie.
     */
    @Transactional
    public VotingResponseDto voteToMovie(VotingRequestDto request) {

        Movie movie = movieRepository.findByImdbID(request.getImdbID()).orElseThrow(() -> {
            log.error("movie was not found");
            throw new BusinessException(BusinessExceptionType.NOT_FOUND);
        });

        movieRateRepository.findFirstByMovie_ImdbIDAndUserId(request.getImdbID(), request.getUserId()).ifPresent(m -> {
            log.error("The user's vote is duplicated");
            throw new BusinessException(BusinessExceptionType.DUPLICATE_VOTE);
        });

        movieRateRepository.save(MovieRate.builder()
                .movie(movie)
                .rate(request.getRate())
                .userId(request.getUserId())
                .build());

        Double newMovieRate =
                saveNewRateToMovie(request.getImdbID(), movie);

        return VotingResponseDto.builder()
                .rate(newMovieRate)
                .build();
    }

    /**
     * This method fetches the n top-rated movie from the data storage.
     *
     * <p>The parameter of this method is optional which means if the caller
     * does not set a value, by default value 10 will be used. Otherwise
     * the value of the parameter will be used
     *
     * @param count an optional argument, specifies the number of top-rated movies.
     * @return n top-rated movies
     */
    public TopMovieResponseDto getTopMovies(Optional<Integer> count) {

        int movieCount = count.isPresent()
                ? count.get()
                : 10;

        List<Movie> topMovies = movieRepository.findTopMovies(PageRequest.of(0, movieCount));

        topMovies.sort((firstMovie, secondMovie) ->
                secondMovie.getBoxOffice().compareTo(firstMovie.getBoxOffice()));

        return TopMovieResponseDto.builder()
                .movies(dtoMapper.movieDtos(topMovies))
                .build();

    }

    private Double saveNewRateToMovie(String imdbID, Movie movie) {

        Double newMovieRate = Util.roundUpToTwoDecimalPlaces(
                movieRateRepository.getMovieRateAverage(imdbID));

        movie.setRate(newMovieRate);
        movieRepository.save(movie);
        return newMovieRate;
    }


}
