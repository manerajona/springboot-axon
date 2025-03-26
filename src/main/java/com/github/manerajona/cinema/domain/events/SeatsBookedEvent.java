package com.github.manerajona.cinema.domain.events;

import com.github.manerajona.cinema.domain.entities.Seat;
import com.github.manerajona.cinema.domain.vo.BookingRef;
import com.github.manerajona.cinema.domain.vo.CustomerId;
import com.github.manerajona.cinema.domain.vo.MovieSessionId;

import java.time.LocalDate;
import java.util.Set;

public record SeatsBookedEvent(
        LocalDate on,
        MovieSessionId movieSessionId,
        BookingRef bookingRef,
        CustomerId customerId,
        Set<Seat> seats) {
}