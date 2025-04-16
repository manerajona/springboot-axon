package com.github.manerajona.cinema.domain.entities;

import com.github.manerajona.cinema.domain.vo.BookingRef;
import com.github.manerajona.cinema.domain.vo.CustomerId;

import java.util.Collections;
import java.util.Set;

public record Booking(BookingRef bookingRef, CustomerId customerId, Set<Seat> seats) {

    public Set<Seat> seats() {
        return Collections.unmodifiableSet(seats);
    }
}