package org.openindustries.travel.booking;

import java.time.Instant;
import java.util.UUID;

/**
 * Booking aggregate — Booking registry; the PNR is masked at creation and never kept in clear.
 *
 * Privacy by design (ISO/IEC 27002 A.5.34): secret identifiers are validated at
 * creation and NEVER persisted or returned in clear — only a masked form is kept.
 * All identifiers in this reference implementation are synthetic.
 */
public class Booking {
    public UUID id;
        public String bookingType;
        public String pnrCodeMasked;
        public String channelCode;
    public String status;
    public Instant createdAt;

    public static Booking create(String bookingType, String pnrCode, String channelCode) {
        Booking e = new Booking();
        e.id = UUID.randomUUID();
        e.bookingType = bookingType;
        e.pnrCodeMasked = mask(pnrCode);
        e.channelCode = channelCode;
        e.status = "HELD";
        e.createdAt = Instant.now();
        return e;
    }

    static String mask(String value) {
        if (value == null || value.length() < 4) {
            return "****";
        }
        return "*".repeat(value.length() - 4) + value.substring(value.length() - 4);
    }
}
