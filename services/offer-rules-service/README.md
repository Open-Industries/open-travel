# Offer Rules Service

Bounded context: **Offer Rules** — Decision rules binding cabin classes to discount and stopover envelopes.

Alignment: IATA NDC offer policy as auditable rules. Decisions are `ALLOWED` / `DEGRADED` / `DENIED`, always
with the matched rule and a human-readable reason for audit. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8083` (`HTTP_PORT`) |
| Base path | `/api/v1/offer-rules` |
| Owns data | OfferRule rules |
| Publishes | `offers.activated` |

Run locally: `mvn -pl services/offer-rules-service quarkus:dev`.
