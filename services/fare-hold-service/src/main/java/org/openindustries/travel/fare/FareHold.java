package org.openindustries.travel.fare;

import java.util.UUID;

/**
 * FareHold aggregate — the guarded resource account of this core: a balance/capacity that
 * consumption operations draw from. Core invariant (ADR-0004): it NEVER goes negative;
 * an operation that would overdraw it is rejected and records nothing.
 */
public class FareHold {
    public UUID id;
        public java.util.UUID bookingId;
    public long heldAmountMinor;
    public String status;

    public static FareHold open(java.util.UUID bookingId, long heldAmountMinor) {
        FareHold a = new FareHold();
        a.id = UUID.randomUUID();
        a.bookingId = bookingId;
        a.heldAmountMinor = heldAmountMinor;
        a.status = "ACTIVE";
        return a;
    }
}
