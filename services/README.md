# Services

One independently deployable Quarkus microservice per bounded context. Every service
follows the same hexagonal layout, exposes `/openapi`, `/q/health` and `/q/metrics`,
returns RFC 9457 problem details, and is configured only through environment variables
(Twelve-Factor). Lifecycle transitions take no body; `@Consumes` appears only on
endpoints that read one.

**Implementation order** (see `../docs/implementation-roadmap.md`):
Bookings → Fare Holds → Offer Rules → Open APIs.

| Service | Port | Alignment |
|---|---|---|
| `booking-service` | 8081 | IATA ONE Order posture; privacy by design |
| `fare-hold-service` | 8082 | Booking money discipline (hold → charge) |
| `offer-rules-service` | 8083 | IATA NDC offer policy as auditable rules |
| `travel-property-service` | 8084 | OpenTravel property posture |
| `notification-service` | 8085 | Platform support (shared pattern) |

Run the pilot service:

```bash
mvn -pl services/booking-service quarkus:dev
# OpenAPI UI: http://localhost:8081/docs — health: http://localhost:8081/q/health
```
