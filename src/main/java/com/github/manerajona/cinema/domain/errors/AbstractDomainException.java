package com.github.manerajona.cinema.domain.errors;

public abstract class AbstractDomainException extends RuntimeException {
    protected AbstractDomainException(String message) {
        super(message);
    }
}