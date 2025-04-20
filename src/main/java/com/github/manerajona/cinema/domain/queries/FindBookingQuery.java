package com.github.manerajona.cinema.domain.queries;

import com.github.manerajona.cinema.domain.vo.BookingRef;
import com.github.manerajona.cinema.domain.vo.MovieSessionId;

public record FindBookingQuery(MovieSessionId movieSessionId, BookingRef bookingRef) {
}
