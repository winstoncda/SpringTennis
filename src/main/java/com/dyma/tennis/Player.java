package com.dyma.tennis;

import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record Player(
    @NotBlank(message = "First name is required") String firstName, 
    @NotBlank(message = "Last name is required") String lastName, 
    @NotNull(message = "Date of birth is required") @PastOrPresent(message = "Date of birth must be in the past or present") LocalDate dateOfBirth, @Valid Rank rank) {

}
