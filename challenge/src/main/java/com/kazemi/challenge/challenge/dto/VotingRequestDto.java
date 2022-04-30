package com.kazemi.challenge.challenge.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VotingRequestDto {

    @NotEmpty
    String imdbID;

    @NotNull
    Long userId;

    @NotNull
    @Positive
    @Range(min = 1, max = 10)
    Double rate;

}
