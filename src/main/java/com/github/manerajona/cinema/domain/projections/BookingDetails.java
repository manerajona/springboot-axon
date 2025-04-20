package com.github.manerajona.cinema.domain.projections;

import com.github.manerajona.cinema.domain.entities.Seat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public record BookingDetails(
        String movieTitle,
        Short screenNumber,
        LocalDate showtimeDate,
        LocalTime showtimeTime,
        Set<Seat> seats) {
}
