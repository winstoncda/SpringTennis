package com.dyma.tennis;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record Rank(
    @Positive(message = "Position must be a positive integer") int position, 
    @PositiveOrZero(message = "Points must be a positive integer or zero") int points) {

}
