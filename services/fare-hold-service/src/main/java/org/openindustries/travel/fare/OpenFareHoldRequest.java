package org.openindustries.travel.fare;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record OpenFareHoldRequest(
        @NotNull java.util.UUID bookingId,
        @PositiveOrZero long heldAmountMinor) {
}
