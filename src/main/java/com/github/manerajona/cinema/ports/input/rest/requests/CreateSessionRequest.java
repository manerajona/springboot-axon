package com.github.manerajona.cinema.ports.input.rest.requests;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public record CreateSessionRequest(
        LocalDate date,
        LocalTime time,
        UUID movieId,
        UUID screenId,
        Integer col,
        Integer row) {
}