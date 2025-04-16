package com.github.manerajona.cinema.domain.events;

import com.github.manerajona.cinema.domain.vo.*;

public record MovieSessionCreatedEvent(
        MovieSessionId id,
        Showtime showTime,
        MovieId movieId,
        ScreenId screenId,
        SeatingPlan seatingPlan) implements DomainEvent {
}