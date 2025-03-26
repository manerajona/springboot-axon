package com.github.manerajona.cinema.domain.errors;

import com.github.manerajona.cinema.domain.entities.Seat;

import java.util.Collections;
import java.util.Set;

public class SeatsDoNotExistException extends AbstractDomainException {
    private final Set<Seat> seats;

    public SeatsDoNotExistException(Set<Seat> seats) {
        super("Seats do not exist: " + seats);
        this.seats = seats;
    }

    public Set<Seat> seats() {
        return Collections.unmodifiableSet(seats);
    }
}