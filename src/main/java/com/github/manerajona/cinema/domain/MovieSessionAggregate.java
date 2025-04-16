package com.github.manerajona.cinema.domain;

import com.github.manerajona.cinema.domain.commands.BookSeatsCommand;
import com.github.manerajona.cinema.domain.commands.CreateMovieSessionCommand;
import com.github.manerajona.cinema.domain.entities.Booking;
import com.github.manerajona.cinema.domain.entities.Seat;
import com.github.manerajona.cinema.domain.errors.SeatsAlreadyBookedException;
import com.github.manerajona.cinema.domain.errors.SeatsDoNotExistException;
import com.github.manerajona.cinema.domain.events.MovieSessionCreatedEvent;
import com.github.manerajona.cinema.domain.events.SeatsBookedEvent;
import com.github.manerajona.cinema.domain.vo.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Aggregate
public class MovieSessionAggregate {

    @AggregateIdentifier
    private MovieSessionId id;
    private Showtime showTime;
    private MovieId movieId;
    private ScreenId screenId;
    private SeatingPlan seatingPlan;
    private Set<Booking> bookings;
    private long aggregateVersion;

    // No‑arg constructor required for event sourcing rehydration
    protected MovieSessionAggregate() {
    }

    @CommandHandler
    public MovieSessionAggregate(CreateMovieSessionCommand command) {
        AggregateLifecycle.apply(new MovieSessionCreatedEvent(
                command.movieSessionId(),
                command.showTime(),
                command.movieId(),
                command.screenId(),
                command.seatingPlan()
        ));
    }

    @EventSourcingHandler
    public void on(MovieSessionCreatedEvent event) {
        this.id = event.id();
        this.showTime = event.showTime();
        this.movieId = event.movieId();
        this.screenId = event.screenId();
        this.seatingPlan = event.seatingPlan();
        this.bookings = new HashSet<>();
        this.aggregateVersion = 1;
    }

    @CommandHandler
    public BookingRef handle(BookSeatsCommand command) {
        // Validate that all requested seats exist
        Set<Seat> nonExistingSeats = command.requestedSeats().stream()
                .filter(seat -> seat.row() > seatingPlan.maxRow() || seat.col() > seatingPlan.maxCol())
                .collect(Collectors.toSet());

        if (!nonExistingSeats.isEmpty()) {
            throw new SeatsDoNotExistException(nonExistingSeats);
        }

        // Validate that the requested seats are not already booked
        Set<Seat> alreadyBooked = bookings.stream()
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
        AggregateLifecycle.apply(new SeatsBookedEvent(LocalDate.now(), this.id, bookingRef, command.customerId(), command.requestedSeats()));

        return bookingRef;
    }

    @EventSourcingHandler
    public void on(SeatsBookedEvent event) {
        this.bookings.add(new Booking(event.bookingRef(), event.customerId(), event.seats()));
        this.aggregateVersion++;
    }
}
