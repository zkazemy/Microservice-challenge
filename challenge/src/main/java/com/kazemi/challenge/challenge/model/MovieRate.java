package com.kazemi.challenge.challenge.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * The {@code MovieRate} represents the rate of the movie by users
 *
 * @author Zahra Kazemi
 */
@Entity
@Table(name = "tbl_movie_rate")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieRate extends BaseEntity {

    @Column(name = "user_Id")
    Long userId;

    @Column(name = "rate")
    Double rate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", foreignKey = @ForeignKey(name = "fk_movie_rate_movie"), nullable = false)
    Movie movie;

}
