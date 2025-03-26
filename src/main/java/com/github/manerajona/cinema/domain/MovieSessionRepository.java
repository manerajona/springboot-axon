package com.github.manerajona.cinema.domain;

import com.github.manerajona.cinema.domain.errors.MovieSessionNotFoundException;
import com.github.manerajona.cinema.domain.vo.MovieSessionId;

public interface MovieSessionRepository {
    MovieSessionAggregate find(MovieSessionId movieSessionId) throws MovieSessionNotFoundException;

    void save(MovieSessionAggregate movieSession);
}