package com.github.manerajona.cinema.domain.errors;

import com.github.manerajona.cinema.domain.entities.Seat;

import java.util.Set;

public class SeatsDoNotExistException extends AbstractDomainException {

    public SeatsDoNotExistException(Set<Seat> seats) {
        super("Seats do not exist: " + seats);
    }
}