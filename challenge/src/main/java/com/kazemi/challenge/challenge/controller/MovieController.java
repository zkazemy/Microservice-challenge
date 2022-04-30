package com.kazemi.challenge.challenge.controller;

import com.kazemi.challenge.challenge.BaseResponseDto;
import com.kazemi.challenge.challenge.dto.MovieAwardResponseDto;
import com.kazemi.challenge.challenge.dto.VotingRequestDto;
import com.kazemi.challenge.challenge.dto.TopMovieResponseDto;
import com.kazemi.challenge.challenge.dto.VotingResponseDto;
import com.kazemi.challenge.challenge.service.AwardAcademyService;
import com.kazemi.challenge.challenge.service.MovieRateService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static io.undertow.util.StatusCodes.*;

@RestController
@RequestMapping({"/api/v1/movies"})
@AllArgsConstructor
public class MovieController {

    private final MovieRateService movieRateService;
    private final AwardAcademyService awardAcademyService;

    @ApiOperation(value = "add new vote")
    @PostMapping(path = "/voting")
    @ResponseBody
    @ApiImplicitParam(name = "Locale", value = "Locale", required = false, paramType = "header", dataTypeClass = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = OK, message = "Successfully perform the operation"),
            @ApiResponse(code = BAD_REQUEST, message = "The Request property was not provide correctly"),
            @ApiResponse(code = INTERNAL_SERVER_ERROR, message = "Internal server error."),
            @ApiResponse(code = CONFLICT, message = "Internal server error: not found, duplicate vote")
    })
    public ResponseEntity<BaseResponseDto<VotingResponseDto>> vote(@RequestBody @Valid VotingRequestDto requestDto) {
        return ResponseEntity.ok(BaseResponseDto.of(movieRateService.voteToMovie(requestDto)));
    }

    @ApiOperation(value = "check the movie won an award")
    @GetMapping(path = {"/win-checker/{title}", "/win-checker/{title}/{category}"})
    @ResponseBody
    @ApiImplicitParam(name = "Locale", value = "Locale", required = false, paramType = "header", dataTypeClass = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = OK, message = "Successfully perform the operation"),
            @ApiResponse(code = BAD_REQUEST, message = "The Request property was not provide correctly"),
            @ApiResponse(code = INTERNAL_SERVER_ERROR, message = "Internal server error."),
            @ApiResponse(code = CONFLICT, message = "Internal server error: not found")
    })
    public ResponseEntity<BaseResponseDto<MovieAwardResponseDto>> hasMovieAward(@PathVariable String title,
                                                                                @PathVariable(name = "category", required = false) Optional<String> category) {
        return ResponseEntity.ok(BaseResponseDto.of(awardAcademyService.hasMovieAward(title, category)));
    }

    @ApiOperation(value = "get top-rated movies. If the count is not specified, then it returns 10 top-rated movies ordered by box office value.")
    @GetMapping(path = {"/top-movies", "/top-movies/{count}"})
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(code = OK, message = "Successfully perform the operation"),
            @ApiResponse(code = BAD_REQUEST, message = "The Request property was not provide correctly"),
            @ApiResponse(code = INTERNAL_SERVER_ERROR, message = "Internal server error.")
    })
    public ResponseEntity<BaseResponseDto<TopMovieResponseDto>> getTopMovies(@PathVariable(name = "count", required = false) Optional<Integer> count) {
        return ResponseEntity.ok(BaseResponseDto.of(movieRateService.getTopMovies(count)));
    }

}
