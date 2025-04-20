package com.github.manerajona.cinema.domain.events;

import com.github.manerajona.cinema.domain.entities.Customer;
import com.github.manerajona.cinema.domain.entities.Seat;
import com.github.manerajona.cinema.domain.vo.BookingRef;
import com.github.manerajona.cinema.domain.vo.MovieSessionId;

import java.util.Set;

public record SeatsBookedEvent(
        MovieSessionId movieSessionId,
        BookingRef bookingRef,
        Customer customer,
        Set<Seat> seats) implements DomainEvent {
}