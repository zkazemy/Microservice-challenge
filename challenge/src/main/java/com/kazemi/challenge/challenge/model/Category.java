package com.kazemi.challenge.challenge.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * The {@code MovieRate} represents the categories in which a movie is nominated
 *
 * @author Zahra Kazemi
 */
@Entity
@Table(name = "tbl_category")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category extends BaseEntity {

    @Column(name = "name")
    String name;

}
