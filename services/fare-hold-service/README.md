# Fare Hold Service

Bounded context: **Fare Holds** — Guarded fare holds per booking and the charges against them.

Alignment: Booking money discipline (hold → charge). Core invariant, enforced in code and tests: **the guarded
balance/capacity never goes negative** — over-drawing operations are rejected with `422`
and nothing is recorded. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8082` (`HTTP_PORT`) |
| Base path | `/api/v1/fare-holds` |
| Owns data | FareHold accounts, FareCharge records |
| Publishes | `fare.consumed`, `fare.insufficient` |

Run locally: `mvn -pl services/fare-hold-service quarkus:dev`.
