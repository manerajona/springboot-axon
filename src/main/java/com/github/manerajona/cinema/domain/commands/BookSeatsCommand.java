package com.github.manerajona.cinema.domain.commands;

import com.github.manerajona.cinema.domain.entities.Seat;
import com.github.manerajona.cinema.domain.vo.CustomerId;
import com.github.manerajona.cinema.domain.vo.MovieSessionId;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.Set;

public record BookSeatsCommand(
        @TargetAggregateIdentifier MovieSessionId movieSessionId,
        CustomerId customerId,
        Set<Seat> requestedSeats) {
}