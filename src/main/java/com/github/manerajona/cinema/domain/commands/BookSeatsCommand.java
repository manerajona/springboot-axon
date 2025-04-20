package com.github.manerajona.cinema.domain.commands;

import com.github.manerajona.cinema.domain.entities.Seat;
import com.github.manerajona.cinema.domain.vo.MovieSessionId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Set;

public record BookSeatsCommand(
        @TargetAggregateIdentifier MovieSessionId movieSessionId,
        String customerEmail,
        Set<Seat> requestedSeats) {

    public BookSeatsCommand withMovieSessionId(MovieSessionId movieSessionId) {
        return this.movieSessionId == movieSessionId ? this : new BookSeatsCommand(
                movieSessionId, this.customerEmail, this.requestedSeats);
    }
}