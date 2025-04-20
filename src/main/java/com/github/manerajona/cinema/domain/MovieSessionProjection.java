package com.github.manerajona.cinema.domain;

import com.github.manerajona.cinema.domain.entities.Booking;
import com.github.manerajona.cinema.domain.entities.Movie;
import com.github.manerajona.cinema.domain.entities.Screen;
import com.github.manerajona.cinema.domain.projections.BookingDetails;
import com.github.manerajona.cinema.domain.queries.FindBookingQuery;
import com.github.manerajona.cinema.domain.vo.Showtime;
import org.axonframework.modelling.command.Repository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class MovieSessionProjection {

    private final Repository<MovieSessionAggregate> repository;

    public MovieSessionProjection(Repository<MovieSessionAggregate> repository) {
        this.repository = repository;
    }

    @QueryHandler
    public BookingDetails handle(FindBookingQuery query) {
        MovieSessionAggregate aggregate = repository.load(query.movieSessionId().toString()).invoke(Function.identity());

        Movie movie = aggregate.getMovie();
        Screen screen = aggregate.getScreen();
        Showtime showtime = aggregate.getShowTime();
        Booking booking = aggregate.getBookings().get(query.bookingRef());
        return new BookingDetails(movie.title(), screen.number(), showtime.date(), showtime.time(), booking.seats());
    }
}
