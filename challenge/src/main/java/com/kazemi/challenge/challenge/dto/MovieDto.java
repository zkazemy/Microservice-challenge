package com.kazemi.challenge.challenge.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MovieDto {

    String imdbID;

    String title;

    Double rate;

    BigDecimal boxOffice;

}
