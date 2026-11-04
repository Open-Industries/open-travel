# Booking Service

Bounded context: **Bookings** — Booking registry; the PNR is masked at creation and never kept in clear.

Alignment: IATA ONE Order posture; privacy by design. Lifecycle transitions take no body; secret identifiers,
where the domain has them, are masked at creation and never stored or returned in clear.
All data is synthetic.

| Item | Value |
|---|---|
| Port | `8081` (`HTTP_PORT`) |
| Base path | `/api/v1/bookings` |
| Owns data | Booking records |
| Publishes | `booking.created`, lifecycle events (see `contracts/asyncapi.yaml`) |

Run locally: `mvn -pl services/booking-service quarkus:dev`.
