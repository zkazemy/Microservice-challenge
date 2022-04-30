package com.kazemi.challenge.challenge.service;

import com.kazemi.challenge.challenge.dto.MovieAwardResponseDto;
import com.kazemi.challenge.challenge.exception.BusinessException;
import com.kazemi.challenge.challenge.exception.BusinessExceptionType;
import com.kazemi.challenge.challenge.model.AwardAcademy;
import com.kazemi.challenge.challenge.model.Category;
import com.kazemi.challenge.challenge.model.Movie;
import com.kazemi.challenge.challenge.repository.AwardAcademyRepository;
import com.kazemi.challenge.challenge.repository.MovieRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * For getting information about movies which nominated, the {@code AwardAcademyService}
 * provides several methods
 *
 * @author Zahra Kazemi
 */

@Service
@AllArgsConstructor
@Slf4j
public class AwardAcademyService implements CrudService<AwardAcademy, Long> {

    /**
     * A constant holding the default name of category
     */
    public static final String DEFAULT_CATEGORY = "Best Picture";

    private final AwardAcademyRepository awardAcademyRepository;
    private final MovieRepository movieRepository;

    @Override
    public Set<AwardAcademy> findAll() {
        Set<AwardAcademy> awardAcademies = new HashSet<>();
        awardAcademyRepository.findAll().forEach(awardAcademies::add);
        return awardAcademies;
    }

    @Override
    public AwardAcademy findById(Long id) {
        return awardAcademyRepository.findById(id).orElse(null);
    }

    @Override
    public AwardAcademy save(AwardAcademy awardAcademy) {
        return awardAcademyRepository.save(awardAcademy);
    }

    @Override
    public void delete(AwardAcademy awardAcademy) {
        awardAcademyRepository.delete(awardAcademy);
    }

    @Override
    public void deleteById(Long id) {
        Optional<AwardAcademy> awardAcademy = awardAcademyRepository.findById(id);
        awardAcademy.ifPresent(u -> awardAcademyRepository.deleteById(id));
    }

    public AwardAcademy saveAwardAcademy(Movie movie, int year, boolean won, Category category) {

        Optional<AwardAcademy> awardAcademy = awardAcademyRepository.findFirstByImdbIdAndCategoryName(movie.getImdbID(), category.getName());

        return (awardAcademy.isPresent() ? awardAcademy.get() : awardAcademyRepository.save(AwardAcademy.builder()
                .year(year)
                .won(won)
                .movie(movie)
                .category(category)
                .build()));
    }

    /**
     * This method is used to inquire about the award of a film.
     *
     * <p>The second parameter of this method is optional which means if the caller
     * does not set a value, by default value 'Best Picture' will be used. Otherwise
     * the value of the parameter will be used.
     *
     * @param title    movie title.
     * @param category an optional parameter for Identifying the category name for
     *                 which the film has been nominated for an award
     * @return whether the film won an award in the desired category or not.
     * @throws NotFoundException if the {@code movie} was not found.
     */
    public MovieAwardResponseDto hasMovieAward(String title, Optional<String> category) {

        movieRepository.findMovieByTitle(title).orElseThrow(() -> {
            log.error("movie was not found");
            throw new BusinessException(BusinessExceptionType.NOT_FOUND);
        });

        String categoryName = category.isPresent()
                ? category.get()
                : DEFAULT_CATEGORY;

        boolean won =
                awardAcademyRepository.findFirstByMovie_TitleAndCategory_NameAndWonIsTrue(title, categoryName).isPresent();

        return MovieAwardResponseDto.builder()
                .won(won)
                .build();

    }

}
