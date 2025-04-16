package com.github.manerajona.cinema.config;

import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value = "com.github.manerajona.cinema.eventstore.type", havingValue = "inmemory")
class AxonInMemoryConfig {

    @Bean
    EventStorageEngine eventStorageEngine() {
        return new InMemoryEventStorageEngine();
    }

    @Bean
    EmbeddedEventStore eventStore(
            EventStorageEngine storageEngine,
            org.axonframework.config.Configuration axonConfiguration,
            @Value("${com.github.manerajona.cinema.eventstore.name}") String eventStoreName) {
        return EmbeddedEventStore.builder()
                .storageEngine(storageEngine)
                .messageMonitor(axonConfiguration.messageMonitor(EventStore.class, eventStoreName))
                .build();
    }
}