package com.github.manerajona.cinema.ports.output;

import com.github.manerajona.cinema.domain.errors.AbstractDomainException;
import com.github.manerajona.cinema.domain.events.DomainEvent;

public interface EventPublisher {
    void publish(DomainEvent event);

    void publishError(AbstractDomainException error);
}
