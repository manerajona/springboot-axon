package com.github.manerajona.cinema.rest;

import com.github.manerajona.cinema.domain.commands.BookSeatsCommand;
import com.github.manerajona.cinema.domain.commands.CreateMovieSessionCommand;
import com.github.manerajona.cinema.domain.errors.SeatsAlreadyBookedException;
import com.github.manerajona.cinema.domain.errors.SeatsDoNotExistException;
import com.github.manerajona.cinema.domain.projections.BookingDetails;
import com.github.manerajona.cinema.domain.queries.FindBookingQuery;
import com.github.manerajona.cinema.domain.vo.BookingRef;
import com.github.manerajona.cinema.domain.vo.MovieSessionId;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.command.AggregateNotFoundException;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/movie-sessions")
public class MovieSessionController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public MovieSessionController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping
    public ResponseEntity<Void> createSession(@RequestBody CreateMovieSessionCommand command) {
        var movieSessionId = new MovieSessionId(UUID.randomUUID());
        commandGateway.send(command.withMovieSessionId(movieSessionId));
        return ResponseEntity.created(URI.create("/movie-sessions/" + movieSessionId.guid())).build();
    }

    @PostMapping("/{guid}/bookings")
    public CompletableFuture<BookingRef> bookSeats(@PathVariable UUID guid, @RequestBody BookSeatsCommand command) {
        var movieSessionId = new MovieSessionId(guid);
        return commandGateway.send(command.withMovieSessionId(movieSessionId));
    }

    @GetMapping("/{guid}/bookings/{ref}")
    public CompletableFuture<BookingDetails> bookSeats(@PathVariable UUID guid, @PathVariable String ref) {
        var query = new FindBookingQuery(
                new MovieSessionId(guid),
                new BookingRef(ref)
        );
        return queryGateway.query(query, ResponseTypes.instanceOf(BookingDetails.class));
    }

    @ExceptionHandler(value = AggregateNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleAggregateNotFoundException(AggregateNotFoundException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(value = SeatsDoNotExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleSeatsDoNotExistException(SeatsDoNotExistException exception) {
        return exception.getMessage();
    }

    @ExceptionHandler(value = SeatsAlreadyBookedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleSeatsAlreadyBookedException(SeatsAlreadyBookedException exception) {
        return exception.getMessage();
    }
}