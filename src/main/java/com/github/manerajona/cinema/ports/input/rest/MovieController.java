package com.github.manerajona.cinema.ports.input.rest;

import com.github.manerajona.cinema.domain.commands.CreateMovieSessionCommand;
import com.github.manerajona.cinema.domain.vo.*;
import com.github.manerajona.cinema.ports.input.rest.requests.CreateSessionRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/movie-sessions")
public class MovieController {

    private final CommandGateway commandGateway;

    public MovieController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public ResponseEntity<Void> createSession(@RequestBody CreateSessionRequest request) {
        final UUID movieSessionId = UUID.randomUUID();
        commandGateway.sendAndWait(createSessionRequestCreateSessionCommand(movieSessionId, request));
        return ResponseEntity.created(URI.create("/movie-sessions/" + movieSessionId)).build();
    }

    private static CreateMovieSessionCommand createSessionRequestCreateSessionCommand(UUID movieSessionId, CreateSessionRequest request) {
        return new CreateMovieSessionCommand(
                new MovieSessionId(movieSessionId),
                new Showtime(request.date(), request.time()),
                new MovieId(request.movieId()),
                new ScreenId(request.screenId()),
                new SeatingPlan(request.row(), request.col())
        );
    }
}