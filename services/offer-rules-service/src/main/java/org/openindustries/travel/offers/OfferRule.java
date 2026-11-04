package org.openindustries.travel.offers;

import java.util.UUID;

/**
 * OfferRule aggregate — a decision rule of this core: for one cabinClass, it guarantees a
 * capacity (maxDiscountMinor) and a threshold (maxStopovers), with a precedence priority.
 * Only ACTIVE rules decide; every decision names its rule and its reason (audit).
 */
public class OfferRule {
    public UUID id;
    public String name;
    public String cabinClass;
    public long maxDiscountMinor;
    public int maxStopovers;
    public int priority;
    public String status;

    public static OfferRule create(String name, String cabinClass, long maxDiscountMinor, int maxStopovers, int priority) {
        OfferRule r = new OfferRule();
        r.id = UUID.randomUUID();
        r.name = name;
        r.cabinClass = cabinClass;
        r.maxDiscountMinor = maxDiscountMinor;
        r.maxStopovers = maxStopovers;
        r.priority = priority;
        r.status = "DRAFT";
        return r;
    }
}
