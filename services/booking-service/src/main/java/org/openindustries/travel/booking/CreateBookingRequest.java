package org.openindustries.travel.booking;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateBookingRequest(
        @NotBlank @Pattern(regexp = "FLIGHT|HOTEL|PACKAGE|CRUISE") String bookingType,
        @NotBlank String pnrCode,
        @NotBlank String channelCode) {
}
