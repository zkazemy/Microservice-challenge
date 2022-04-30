package com.kazemi.challenge.challenge.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * The {@code MovieRate} represents the information of the films nominated for
 * the Oscars along with determining whether to win or not.
 *
 * @author Zahra Kazemi
 */
@Entity
@Table(name = "tbl_award_academy", indexes = {@Index(name = "IDX_MOVIEID", columnList = "movie_id")})
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AwardAcademy extends BaseEntity {

    @Column(name = "year")
    Integer year;

    @Column(name = "won")
    Boolean won;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", foreignKey = @ForeignKey(name = "fk_award_academy_category"), nullable = false)
    Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", foreignKey = @ForeignKey(name = "fk_award_academy_movie"), nullable = false)
    Movie movie;
}
