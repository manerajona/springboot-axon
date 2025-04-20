package com.github.manerajona.cinema.domain.commands;

import com.github.manerajona.cinema.domain.vo.MovieSessionId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateMovieSessionCommand(
        @TargetAggregateIdentifier MovieSessionId movieSessionId,
        String movieTitle,
        Short screenNumber,
        LocalDate showtimeDate,
        LocalTime showtimeTime,
        Character seatsMaxCol,
        Short seatsMaxRow) {

    public CreateMovieSessionCommand withMovieSessionId(MovieSessionId movieSessionId) {
        return this.movieSessionId == movieSessionId ? this : new CreateMovieSessionCommand(
                movieSessionId, this.movieTitle, this.screenNumber, this.showtimeDate, this.showtimeTime, this.seatsMaxCol, this.seatsMaxRow);
    }
}