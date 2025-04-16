package com.github.manerajona.cinema.rest;

import com.github.manerajona.cinema.domain.commands.BookSeatsCommand;
import com.github.manerajona.cinema.domain.entities.Seat;
import com.github.manerajona.cinema.domain.vo.BookingRef;
import com.github.manerajona.cinema.domain.vo.CustomerId;
import com.github.manerajona.cinema.domain.vo.MovieSessionId;
import com.github.manerajona.cinema.rest.requests.BookSeatsRequest;
import com.github.manerajona.cinema.rest.requests.SeatRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movie-sessions/{id}/bookings")
public class BookingController {

    private final CommandGateway commandGateway;

    public BookingController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public CompletableFuture<BookingRef> bookSeats(@PathVariable String id, @RequestBody BookSeatsRequest request) {
        final UUID movieSessionId = UUID.fromString(id);
        return commandGateway.send(bookSeatsRequestToBookSeatsCommand(movieSessionId, request));
    }

    private static BookSeatsCommand bookSeatsRequestToBookSeatsCommand(UUID movieSessionId, BookSeatsRequest request) {
        return new BookSeatsCommand(
                new MovieSessionId(movieSessionId),
                new CustomerId(request.customerId()),
                toSeats(request.requestedSeats())
        );
    }

    private static Set<Seat> toSeats(Set<SeatRequest> seats) {
        return seats.stream()
                .map(seat -> new Seat(seat.col(), seat.row()))
                .collect(Collectors.toSet());
    }
}