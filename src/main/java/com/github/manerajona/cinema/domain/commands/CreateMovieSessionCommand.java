package com.github.manerajona.cinema.domain.commands;

import com.github.manerajona.cinema.domain.vo.*;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CreateMovieSessionCommand(
        @TargetAggregateIdentifier MovieSessionId movieSessionId,
        Showtime showTime,
        MovieId movieId,
        ScreenId screenId,
        SeatingPlan seatingPlan) {
}