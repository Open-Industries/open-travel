package org.openindustries.travel.fare;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateFareChargeRequest(
        @NotBlank @Pattern(regexp = "FARE|TAXES|FEES|ANCILLARY") String chargeType,
        @Positive long amountMinor) {
}
