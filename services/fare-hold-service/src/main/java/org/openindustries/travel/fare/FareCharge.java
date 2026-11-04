package org.openindustries.travel.fare;

import java.time.Instant;
import java.util.UUID;

/** One guarded consumption record — the auditable evidence of a draw on a FareHold. */
public class FareCharge {
    public UUID id;
    public UUID accountId;
    public String chargeType;
    public long amountMinor;
    public long remainingAfter;
    public Instant recordedAt;

    public static FareCharge of(UUID accountId, String chargeType, long amountMinor, long remainingAfter) {
        FareCharge r = new FareCharge();
        r.id = UUID.randomUUID();
        r.accountId = accountId;
        r.chargeType = chargeType;
        r.amountMinor = amountMinor;
        r.remainingAfter = remainingAfter;
        r.recordedAt = Instant.now();
        return r;
    }
}
