package com.github.manerajona.cinema.ports.input.rest.requests;

import java.util.Set;
import java.util.UUID;

public record BookSeatsRequest(
        UUID customerId,
        Set<SeatRequest> requestedSeats) {
}