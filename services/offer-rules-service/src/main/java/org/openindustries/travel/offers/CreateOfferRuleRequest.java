package org.openindustries.travel.offers;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateOfferRuleRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "ECONOMY|PREMIUM|BUSINESS|FIRST") String cabinClass,
        @Positive long maxDiscountMinor,
        @Positive int maxStopovers,
        @Min(1) @Max(100) int priority) {
}
