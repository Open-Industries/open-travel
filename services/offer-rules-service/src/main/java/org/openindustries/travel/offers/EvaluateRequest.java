package org.openindustries.travel.offers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record EvaluateRequest(
        @NotBlank @Pattern(regexp = "ECONOMY|PREMIUM|BUSINESS|FIRST") String cabinClass,
        @Positive int requiredMaxStopovers,
        @Positive long requiredMaxDiscountMinor) {
}
