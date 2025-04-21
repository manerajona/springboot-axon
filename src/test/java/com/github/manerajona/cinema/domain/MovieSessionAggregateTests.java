package com.github.manerajona.cinema.domain;

import com.github.manerajona.cinema.domain.commands.BookSeatsCommand;
import com.github.manerajona.cinema.domain.commands.CreateMovieSessionCommand;
import com.github.manerajona.cinema.domain.entities.Movie;
import com.github.manerajona.cinema.domain.entities.Screen;
import com.github.manerajona.cinema.domain.entities.Seat;
import com.github.manerajona.cinema.domain.errors.SeatsAlreadyBookedException;
import com.github.manerajona.cinema.domain.errors.SeatsDoNotExistException;
import com.github.manerajona.cinema.domain.events.MovieSessionCreatedEvent;
import com.github.manerajona.cinema.domain.events.SeatsBookedEvent;
import com.github.manerajona.cinema.domain.vo.SeatingPlan;
import com.github.manerajona.cinema.domain.vo.Showtime;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.axonframework.test.matchers.Matchers.exactSequenceOf;
import static org.axonframework.test.matchers.Matchers.messageWithPayload;
import static org.hamcrest.CoreMatchers.instanceOf;

class MovieSessionAggregateTests {

    FixtureConfiguration<MovieSessionAggregate> fixture;

    @BeforeEach
    public void setUp() {
        fixture = new AggregateTestFixture<>(MovieSessionAggregate.class);
    }

    @Test
    void handleCreateMovieSessionCommand_Success() {
        var command = Instancio.create(CreateMovieSessionCommand.class);

        fixture.givenNoPriorActivity()
                .when(command)
                .expectEvents(new MovieSessionCreatedEvent(
                        command.movieSessionId(),
                        new Movie(command.movieTitle()),
                        new Screen(command.screenNumber()),
                        new Showtime(command.showtimeDate(), command.showtimeTime()),
                        new SeatingPlan(command.seatsMaxCol(), command.seatsMaxRow())
                ));
    }

    @Test
    void handleBookSeatsCommand_Success() {
        var movieSessionCreatedEvent = Instancio.create(MovieSessionCreatedEvent.class);
        var command = new BookSeatsCommand(
                movieSessionCreatedEvent.movieSessionId(),
                Instancio.create(String.class),
                Set.of(new Seat(movieSessionCreatedEvent.seatingPlan().maxCol(), movieSessionCreatedEvent.seatingPlan().maxRow()))
        );

        fixture.given(movieSessionCreatedEvent)
                .when(command)
                .expectEventsMatching(exactSequenceOf(
                        messageWithPayload(instanceOf(SeatsBookedEvent.class))
                ));
    }

    @Test
    void handleBookSeatsCommand_SeatsDoNotExistException() {
        var movieSessionCreatedEvent = Instancio.create(MovieSessionCreatedEvent.class);
        var command = Instancio.create(BookSeatsCommand.class)
                .withMovieSessionId(movieSessionCreatedEvent.movieSessionId());

        fixture.given(movieSessionCreatedEvent)
                .when(command)
                .expectException(SeatsDoNotExistException.class);
    }

    @Test
    void handleBookSeatsCommand_SeatsAlreadyBookedException() {
        var movieSessionCreatedEvent = Instancio.create(MovieSessionCreatedEvent.class);
        var command = new BookSeatsCommand(
                movieSessionCreatedEvent.movieSessionId(),
                Instancio.create(String.class),
                Set.of(new Seat(movieSessionCreatedEvent.seatingPlan().maxCol(), movieSessionCreatedEvent.seatingPlan().maxRow()))
        );

        fixture.given(movieSessionCreatedEvent)
                .andGivenCommands(command)
                .when(command)
                .expectException(SeatsAlreadyBookedException.class);
    }

}