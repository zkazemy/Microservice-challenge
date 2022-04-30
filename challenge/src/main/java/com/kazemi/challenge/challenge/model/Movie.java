package com.kazemi.challenge.challenge.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code MovieRate} represents a movie information.
 *
 * @author Zahra Kazemi
 */
@Entity
@Table(name = "tbl_movie" , indexes = {@Index(name = "IDX_IMDBID", columnList = "imdb_id")})
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Movie extends BaseEntity {

    @Column(name = "imdb_id", unique = true)
    String imdbID;

    @Column(name = "title")
    String title;

    @Column(name = "rate")
    Double rate;

    @Column(name = "box_office")
    BigDecimal boxOffice;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    List<MovieRate> rates = new ArrayList<>();

}
