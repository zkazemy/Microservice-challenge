package com.kazemi.challenge.challenge;

import com.kazemi.challenge.challenge.model.Category;
import com.kazemi.challenge.challenge.model.Movie;
import com.kazemi.challenge.challenge.service.AwardAcademyService;
import com.kazemi.challenge.challenge.service.CategoryService;
import com.kazemi.challenge.challenge.service.MovieRateService;
import com.kazemi.challenge.challenge.service.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final MovieService movieService;
    private final AwardAcademyService awardAcademyService;
    private final CategoryService categoryService;
    private final MovieRateService movieRateService;

    @Override
    public void run(String... args) throws Exception {

        Category bestPicture = categoryService.saveCategory("Best Picture");
        Category leadingRole = categoryService.saveCategory("Actor -- Leading Role");
        Category supportRole = categoryService.saveCategory("Actor -- Supporting Role");
        Category direction = categoryService.saveCategory("Art Direction");
        Category documentary = categoryService.saveCategory("Documentary");

        Movie movie = movieService.saveMovieByTitle("Biutiful");
        if (movie != null) {
            awardAcademyService.saveAwardAcademy(movie, 1980, false, bestPicture);
            awardAcademyService.saveAwardAcademy(movie, 1980, false, leadingRole);
            awardAcademyService.saveAwardAcademy(movie, 1980, true, supportRole);
            awardAcademyService.saveAwardAcademy(movie, 1980, false, direction);
            movieRateService.saveMovieRate(movie, 1L, (double) 2);
            movieRateService.saveMovieRate(movie, 2L, (double) 4);
        }

        movie = movieService.saveMovieByTitle("The King's Speech");
        if (movie != null) {
            awardAcademyService.saveAwardAcademy(movie, 2010, true, bestPicture);
            awardAcademyService.saveAwardAcademy(movie, 2010, true, supportRole);
            awardAcademyService.saveAwardAcademy(movie, 2010, false, direction);
            movieRateService.saveMovieRate(movie, 1L, (double) 2);
            movieRateService.saveMovieRate(movie, 2L, (double) 4);
            movieRateService.saveMovieRate(movie, 3L, (double) 8);
        }

        movie = movieService.saveMovieByTitle("Rabbit Hole");
        if (movie != null) {
            awardAcademyService.saveAwardAcademy(movie, 2010, true, supportRole);
            awardAcademyService.saveAwardAcademy(movie, 2010, false, direction);
            movieRateService.saveMovieRate(movie, 1L, (double) 10);
        }

        movie = movieService.saveMovieByTitle("The Fighter");
        if (movie != null) {
            awardAcademyService.saveAwardAcademy(movie, 2009, true, supportRole);
            awardAcademyService.saveAwardAcademy(movie, 2009, false, direction);
            awardAcademyService.saveAwardAcademy(movie, 2009, true, documentary);
            movieRateService.saveMovieRate(movie, 1L, (double) 4);
        }

        movie = movieService.saveMovieByTitle("Inception");
        if (movie != null) {
            awardAcademyService.saveAwardAcademy(movie, 2005, true, bestPicture);
            movieRateService.saveMovieRate(movie, 1L, (double) 4);
        }
    }
}
