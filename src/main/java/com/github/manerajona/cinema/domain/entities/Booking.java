package com.github.manerajona.cinema.domain.entities;

import com.github.manerajona.cinema.domain.vo.BookingRef;

import java.util.Collections;
import java.util.Set;

public record Booking(BookingRef bookingRef, Customer customer, Set<Seat> seats) {

    public Set<Seat> seats() {
        return Collections.unmodifiableSet(seats);
    }
}