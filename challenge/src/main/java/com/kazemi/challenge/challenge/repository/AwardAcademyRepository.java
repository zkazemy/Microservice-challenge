package com.kazemi.challenge.challenge.repository;

import com.kazemi.challenge.challenge.model.AwardAcademy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AwardAcademyRepository extends JpaRepository<AwardAcademy, Long> {

    Optional<AwardAcademy> findFirstByMovie_TitleAndCategory_NameAndWonIsTrue(String title, String categoryName);

    @Query("select a from AwardAcademy a where a.movie.imdbID = :imdbId and a.category.name = :category")
    Optional<AwardAcademy> findFirstByImdbIdAndCategoryName(String imdbId, String category);
}
