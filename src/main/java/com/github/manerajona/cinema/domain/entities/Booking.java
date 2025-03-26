package com.github.manerajona.cinema.domain.entities;

import com.github.manerajona.cinema.domain.errors.MaxSeatsAllowedPerBookingReachedException;
import com.github.manerajona.cinema.domain.errors.SeatsCannotBeEmptyException;
import com.github.manerajona.cinema.domain.vo.BookingRef;
import com.github.manerajona.cinema.domain.vo.CustomerId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

import java.util.Collections;
import java.util.Set;

@Value
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Booking {
    BookingRef bookingRef;
    CustomerId customerId;
    Set<Seat> seats;

    public static Booking create(BookingRef bookingRef, CustomerId customerId, Set<Seat> seats, int maxSeatsAllowedPerBooking) {
        if (seats.size() > maxSeatsAllowedPerBooking) {
            throw new MaxSeatsAllowedPerBookingReachedException();
        }
        if (seats.isEmpty()) {
            throw new SeatsCannotBeEmptyException();
        }
        return new Booking(bookingRef, customerId, seats);
    }

    public static Booking reconstitute(BookingRef bookingRef, CustomerId customerId, Set<Seat> seats) {
        return new Booking(bookingRef, customerId, seats);
    }

    public Set<Seat> seats() {
        return Collections.unmodifiableSet(seats);
    }
}