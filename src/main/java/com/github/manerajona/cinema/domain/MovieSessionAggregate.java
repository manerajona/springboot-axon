package com.github.manerajona.cinema.domain;

import com.github.manerajona.cinema.domain.commands.BookSeatsCommand;
import com.github.manerajona.cinema.domain.commands.CreateMovieSessionCommand;
import com.github.manerajona.cinema.domain.entities.*;
import com.github.manerajona.cinema.domain.errors.SeatsAlreadyBookedException;
import com.github.manerajona.cinema.domain.errors.SeatsDoNotExistException;
import com.github.manerajona.cinema.domain.events.MovieSessionCreatedEvent;
import com.github.manerajona.cinema.domain.events.SeatsBookedEvent;
import com.github.manerajona.cinema.domain.vo.BookingRef;
import com.github.manerajona.cinema.domain.vo.MovieSessionId;
import com.github.manerajona.cinema.domain.vo.SeatingPlan;
import com.github.manerajona.cinema.domain.vo.Showtime;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.*;
import java.util.stream.Collectors;

@Aggregate
public class MovieSessionAggregate {

    @AggregateIdentifier
    private MovieSessionId id;
    private Showtime showTime;
    private Movie movie;
    private Screen screen;
    private SeatingPlan seatingPlan;
    private Map<BookingRef, Booking> bookings;
    private int aggregateVersion;

    // No‑arg constructor required for event sourcing rehydration
    protected MovieSessionAggregate() {
    }

    @CommandHandler
    public MovieSessionAggregate(CreateMovieSessionCommand command) {
        AggregateLifecycle.apply(new MovieSessionCreatedEvent(
                command.movieSessionId(),
                new Movie(command.movieTitle()),
                new Screen(command.screenNumber()),
                new Showtime(command.showtimeDate(), command.showtimeTime()),
                new SeatingPlan(command.seatsMaxCol(), command.seatsMaxRow())
        ));
    }

    @EventSourcingHandler
    public void on(MovieSessionCreatedEvent event) {
        this.id = event.movieSessionId();
        this.movie = event.movie();
        this.screen = event.screen();
        this.showTime = event.showTime();
        this.seatingPlan = event.seatingPlan();
        this.bookings = new HashMap<>();
        this.aggregateVersion = 1;
    }

    @CommandHandler
    public BookingRef handle(BookSeatsCommand command) {
        // Validate that all requested seats exist
        Set<Seat> nonExistingSeats = command.requestedSeats().stream()
                .filter(seat -> seat.col() > seatingPlan.maxCol() || seat.row() > seatingPlan.maxRow())
                .collect(Collectors.toSet());

        if (!nonExistingSeats.isEmpty()) {
            throw new SeatsDoNotExistException(nonExistingSeats);
        }

        // Validate that the requested seats are not already booked
        Set<Seat> alreadyBooked = bookings.values().stream()
                .flatMap(booking -> booking.seats().stream())
                .filter(seat -> command.requestedSeats().contains(seat))
                .collect(Collectors.toSet());

        if (!alreadyBooked.isEmpty()) {
            throw new SeatsAlreadyBookedException(alreadyBooked);
        }

        // Generate booking reference
        final String bookCode = Long.toHexString(System.currentTimeMillis()).toUpperCase(Locale.ROOT);
        BookingRef bookingRef = new BookingRef(bookCode);

        // Apply the event to record the booking
        AggregateLifecycle.apply(new SeatsBookedEvent(
                command.movieSessionId(),
                bookingRef,
                new Customer(command.customerEmail()),
                command.requestedSeats()
        ));

        return bookingRef;
    }

    @EventSourcingHandler
    public void on(SeatsBookedEvent event) {
        this.bookings.put(event.bookingRef(), new Booking(event.bookingRef(), event.customer(), event.seats()));
        this.aggregateVersion++;
    }

    public Screen getScreen() {
        return screen;
    }

    public Movie getMovie() {
        return movie;
    }

    public Showtime getShowTime() {
        return showTime;
    }

    public Map<BookingRef, Booking> getBookings() {
        return Collections.unmodifiableMap(bookings);
    }
}
