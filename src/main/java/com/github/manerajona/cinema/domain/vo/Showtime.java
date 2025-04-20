package com.github.manerajona.cinema.domain.vo;

import java.time.LocalDate;
import java.time.LocalTime;

public record Showtime(LocalDate date, LocalTime time) {
}