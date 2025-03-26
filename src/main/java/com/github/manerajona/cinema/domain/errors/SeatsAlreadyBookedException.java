package com.github.manerajona.cinema.domain.errors;

import com.github.manerajona.cinema.domain.entities.Seat;

import java.util.Collections;
import java.util.Set;

public class SeatsAlreadyBookedException extends AbstractDomainException {
    private final Set<Seat> seats;

    public SeatsAlreadyBookedException(Set<Seat> seats) {
        super("Seats already booked: " + seats);
        this.seats = seats;
    }

    public Set<Seat> seats() {
        return Collections.unmodifiableSet(seats);
    }
}