# Domain Standards — Travel

Alignment mapping for this core. *Aligned to* throughout: airline/host system-of-record
functions, GDS connectivity and payment processing remain with certified providers.

## Concept mapping

| Platform concept | Domain standard | Mapping |
|---|---|---|
| Offers & orders | IATA NDC / ONE Order | Booking and offer-rule shapes follow offer/order thinking |
| Booking record | ONE Order / PADIS posture | Masked PNR; statuses as order states |
| Traveler documents | ICAO MRTD | Verification out of scope; no document data stored |
| Properties | OpenTravel | Property catalogue field posture |
| Payments | PCI DSS | Certified PSP boundary only |

## Scope boundaries

No ticketing issuance, no GDS connectivity, no payment processing: this is the
booking/hold/rules platform sellers integrate with certified travel systems.
