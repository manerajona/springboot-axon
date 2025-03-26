package com.github.manerajona.cinema.domain.events;

import com.github.manerajona.cinema.domain.entities.Seat;
import com.github.manerajona.cinema.domain.vo.MovieId;
import com.github.manerajona.cinema.domain.vo.MovieSessionId;
import com.github.manerajona.cinema.domain.vo.ScreenId;
import com.github.manerajona.cinema.domain.vo.Showtime;

import java.util.Set;

public record MovieSessionCreatedEvent(
        MovieSessionId id,
        Showtime showTime,
        MovieId movieId,
        ScreenId screenId,
        Set<Seat> seatingPlan) {
}