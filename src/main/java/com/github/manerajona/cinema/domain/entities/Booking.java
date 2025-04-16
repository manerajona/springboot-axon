package com.github.manerajona.cinema.domain.entities;

import com.github.manerajona.cinema.domain.errors.SeatsCannotBeEmptyException;
import com.github.manerajona.cinema.domain.vo.BookingRef;
import com.github.manerajona.cinema.domain.vo.CustomerId;

import java.util.Collections;
import java.util.Set;

public class Booking {
    private final BookingRef bookingRef;
    private final CustomerId customerId;
    private final Set<Seat> seats;

    private Booking(BookingRef bookingRef, CustomerId customerId, Set<Seat> seats) {
        this.bookingRef = bookingRef;
        this.customerId = customerId;
        this.seats = seats;
    }

    public Set<Seat> seats() {
        return Collections.unmodifiableSet(seats);
    }

    public static Booking create(BookingRef bookingRef, CustomerId customerId, Set<Seat> seats) {
        if (seats.isEmpty()) {
            throw new SeatsCannotBeEmptyException();
        }
        return new Booking(bookingRef, customerId, seats);
    }
}