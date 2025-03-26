package com.github.manerajona.cinema.domain.errors;

public class SeatsCannotBeEmptyException extends AbstractDomainException {
    public SeatsCannotBeEmptyException() {
        super("Seats cannot be empty.");
    }
}