package com.github.manerajona.cinema.domain.events;

import com.github.manerajona.cinema.domain.entities.Movie;
import com.github.manerajona.cinema.domain.entities.Screen;
import com.github.manerajona.cinema.domain.vo.MovieSessionId;
import com.github.manerajona.cinema.domain.vo.SeatingPlan;
import com.github.manerajona.cinema.domain.vo.Showtime;

public record MovieSessionCreatedEvent(
        MovieSessionId movieSessionId,
        Movie movie,
        Screen screen,
        Showtime showTime,
        SeatingPlan seatingPlan) implements DomainEvent {
}