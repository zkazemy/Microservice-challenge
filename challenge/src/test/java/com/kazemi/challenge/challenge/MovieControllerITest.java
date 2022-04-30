package com.kazemi.challenge.challenge;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kazemi.challenge.challenge.dto.MovieAwardResponseDto;
import com.kazemi.challenge.challenge.dto.TopMovieResponseDto;
import com.kazemi.challenge.challenge.dto.VotingRequestDto;
import com.kazemi.challenge.challenge.dto.VotingResponseDto;
import com.kazemi.challenge.challenge.exception.BusinessExceptionType;
import com.kazemi.challenge.challenge.model.Movie;
import com.kazemi.challenge.challenge.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
public class MovieControllerITest {

    @Autowired
    protected MockMvc mvc;
    @Autowired
    protected ObjectMapper json;
    @Autowired
    protected MovieService movieService;

    @Test
    @DisplayName("movie not fount ---> it will be thrown an exception")
    void voteToMovieNotExistedThrowException() throws Exception {

        //arrange
        VotingRequestDto requestDto = VotingRequestDto.builder().imdbID("tt116").rate(2d).userId(4L).build();

        //act
        BaseResponseDto<VotingResponseDto> response = vote(requestDto, "/voting");

        //assert
        assertThat(response.getResponse()).isNull();
        assertThat(response.isSuccessful()).isFalse();
        assertThat(response.getErrorData().getErrorCode())
                .isEqualTo(BusinessExceptionType.NOT_FOUND.getCode());
    }

    @Test
    @DisplayName("duplicate vote --->  it will be thrown an exception")
    void voteToMovieIsDuplicatedThrowException() throws Exception {

        //arrange
        VotingRequestDto requestDto = VotingRequestDto.builder().imdbID("tt1164999").rate(2d).userId(4L).build();
        //act
        vote(requestDto, "/voting");

        //arrange
        requestDto = VotingRequestDto.builder().imdbID("tt1164999").rate(4.6d).userId(4L).build();
        //act
        BaseResponseDto<VotingResponseDto> response = vote(requestDto, "/voting");

        //assert
        assertThat(response.getResponse()).isNull();
        assertThat(response.isSuccessful()).isFalse();
        assertThat(response.getErrorData().getErrorCode())
                .isEqualTo(BusinessExceptionType.DUPLICATE_VOTE.getCode());

    }

    @Test
    @DisplayName("vot to a movie successfully")
    void voteToMovieSuccessfully() throws Exception {

        //arrange
        Movie newMovie = movieService.save(Movie.builder()
                .imdbID(UUID.randomUUID().toString().substring(1, 10))
                .title("The Black Eyes")
                .boxOffice(BigDecimal.valueOf(45345345))
                .rate(0d).build());

        VotingRequestDto requestDto = VotingRequestDto.builder().imdbID(newMovie.getImdbID()).rate(2d).userId(4L).build();
        //act
        BaseResponseDto<VotingResponseDto> response = vote(requestDto, "/voting");

        //assert
        assertThat(response.getResponse()).isNotNull();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.getResponse().getRate()).isEqualTo(2.0);

    }

    @Test
    @DisplayName("movie not fount  ---> it will be thrown an exception")
    void checkAwardForMovieNotExistedThrowException() throws Exception {

        //arrange
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                .get(TestUtils.USER_RESOURCE_PATH + "/win-checker/{title}", "test")
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //act
        BaseResponseDto<VotingResponseDto> response = json.readValue
                (resultActions.andReturn().getResponse().getContentAsString(),
                        new TypeReference<BaseResponseDto<VotingResponseDto>>() {
                        });

        //assert
        assertThat(response.getResponse()).isNull();
        assertThat(response.isSuccessful()).isFalse();
        assertThat(response.getErrorData().getErrorCode())
                .isEqualTo(BusinessExceptionType.NOT_FOUND.getCode());

    }

    @Test
    @DisplayName("movie without a best picture award  ---> it is not a winner")
    void checkAwardForMovieWithoutBestPictureAward() throws Exception {

        BaseResponseDto<MovieAwardResponseDto> response = checkMovieAward("The Fighter");

        //assert
        assertThat(response.getResponse()).isNotNull();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.getResponse().getWon()).isEqualTo(false);

    }

    @Test
    @DisplayName("movie with a best picture award  ---> it is a winner")
    void checkAwardForMovieWithBestPictureAward() throws Exception {

        BaseResponseDto<MovieAwardResponseDto> response = checkMovieAward("Inception");

        //assert
        assertThat(response.getResponse()).isNotNull();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.getResponse().getWon()).isEqualTo(true);

    }

    @Test
    @DisplayName("10 top-rated movies")
    void select10TopRatedMovies() throws Exception {

        //arrange
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                .get(TestUtils.USER_RESOURCE_PATH + "/top-movies")
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //act
        BaseResponseDto<TopMovieResponseDto> response = json.readValue
                (resultActions.andReturn().getResponse().getContentAsString(),
                        new TypeReference<BaseResponseDto<TopMovieResponseDto>>() {
                        });
        //assert
        assertThat(response.getResponse()).isNotNull();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.getResponse().getMovies()).isNotNull();
        assertThat(response.getResponse().getMovies().size()).isGreaterThan(0);

    }

    private BaseResponseDto<MovieAwardResponseDto> checkMovieAward(String movieTitle) throws Exception {

        //arrange
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                .get(TestUtils.USER_RESOURCE_PATH + "/win-checker/{title}", movieTitle)
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //act
        return json.readValue
                (resultActions.andReturn().getResponse().getContentAsString(),
                        new TypeReference<BaseResponseDto<MovieAwardResponseDto>>() {
                        });
    }

    private BaseResponseDto<VotingResponseDto> vote(VotingRequestDto requestDto, String apiName) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                .post(TestUtils.USER_RESOURCE_PATH + apiName)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        BaseResponseDto<VotingResponseDto> response = json.readValue
                (resultActions.andReturn().getResponse().getContentAsString(),
                        new TypeReference<BaseResponseDto<VotingResponseDto>>() {
                        });

        return response;

    }

}
