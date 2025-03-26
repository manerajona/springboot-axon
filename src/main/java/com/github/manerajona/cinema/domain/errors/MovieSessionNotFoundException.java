package com.github.manerajona.cinema.domain.errors;

public class MovieSessionNotFoundException extends AbstractDomainException {
    public MovieSessionNotFoundException() {
        super("Movie session not found.");
    }
}