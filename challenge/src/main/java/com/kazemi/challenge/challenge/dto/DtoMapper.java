package com.kazemi.challenge.challenge.dto;

import com.kazemi.challenge.challenge.model.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
/**
 * The {@code DtoMapper} represents to automate the mapping
 * process of DTOs into entities on Spring Boot APIs and vice versa
 *
 * @author Zahra Kazemi
 */
@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
@Component
public abstract class DtoMapper {

    public List<MovieDto> movieDtos(List<Movie> movieList) {

        if (CollectionUtils.isEmpty(movieList))
            return null;

        List<MovieDto> movieDtos = new ArrayList<>();
        movieList.stream().forEach(e -> movieDtos.add(movieDto(e)));
        return movieDtos;
    }

    public Movie movie(MovieDto movieDto) {

        if (movieDto == null)
            return null;

        return Movie
                .builder()
                .imdbID(movieDto.getImdbID())
                .title(movieDto.getTitle())
                .boxOffice(movieDto.getBoxOffice())
                .rate(movieDto.getRate())
                .build();
    }

    public MovieDto movieDto(Movie movie) {

        if (movie == null)
            return null;

        return MovieDto
                .builder()
                .imdbID(movie.getImdbID())
                .title(movie.getTitle())
                .boxOffice(movie.getBoxOffice())
                .rate(movie.getRate())
                .build();
    }

}
