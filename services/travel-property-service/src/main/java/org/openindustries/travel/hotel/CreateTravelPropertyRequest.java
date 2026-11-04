package org.openindustries.travel.hotel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateTravelPropertyRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "HOTEL|RESORT|APARTMENT|HOSTEL") String propertyType,
        @Positive int roomsCount) {
}
