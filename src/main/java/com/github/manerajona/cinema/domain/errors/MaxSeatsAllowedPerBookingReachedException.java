package com.github.manerajona.cinema.domain.errors;

public class MaxSeatsAllowedPerBookingReachedException extends AbstractDomainException {
    public MaxSeatsAllowedPerBookingReachedException() {
        super("Max seats allowed per booking reached.");
    }
}