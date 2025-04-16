package com.github.manerajona.cinema.domain.errors;

import com.github.manerajona.cinema.domain.entities.Seat;

import java.util.Set;

public class SeatsAlreadyBookedException extends AbstractDomainException {

    public SeatsAlreadyBookedException(Set<Seat> seats) {
        super("Seats already booked: " + seats);
    }
}