package com.github.manerajona.cinema.domain.commands;

import com.github.manerajona.cinema.domain.entities.Seat;
import com.github.manerajona.cinema.domain.vo.MovieId;
import com.github.manerajona.cinema.domain.vo.MovieSessionId;
import com.github.manerajona.cinema.domain.vo.ScreenId;
import com.github.manerajona.cinema.domain.vo.Showtime;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Set;

public record CreateMovieSessionCommand(
        @TargetAggregateIdentifier MovieSessionId movieSessionId,
        Showtime showTime, MovieId movieId,
        ScreenId screenId, Set<Seat> seatingPlan) {
}