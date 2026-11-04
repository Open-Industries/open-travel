# Open Travel — Reference Travel Core Platform

A **standard, generic travel and hospitality distribution core platform** that any organization in this vertical
can deploy and adapt: built as **microservices and serverless functions** on
**Java 21 + Quarkus**, aligned with **The Twelve-Factor App**, documented under the ISO
architecture and quality standards, and aligned with the domain standards of the
vertical (see the traceability matrix). Everything is *aligned to* the cited standards —
**no certification, conformance or regulatory-compliance claim is made**. All data is
**synthetic**.

> Reference implementation: small, complete and runnable. The rules that matter in this
> domain are enforced in code and covered by tests — start with the invariant:
> charges against a booking's held amount never exceed that hold — a charge that would overrun it is rejected (422) and records nothing; a booking's money is always exactly accounted.

## Standards alignment

| Standard / framework | What it governs here | Where |
|---|---|---|
| ISO/IEC/IEEE **42010** | Architecture description | `docs/architecture/architecture-description.md` |
| ISO/IEC **25010** (SQuaRE) | Product quality model and scenarios | `docs/architecture/quality-attributes.md` |
| ISO/IEC/IEEE **15288 & 12207** | Life-cycle processes | `docs/lifecycle/software-lifecycle.md` |
| ISO/IEC **27001 & 27002** | Information security | `docs/security/` |
| **The Twelve-Factor App** | Cloud-native methodology | `docs/architecture/twelve-factor.md` |
| IATA NDC | New Distribution Capability (offers/orders) | `docs/domain/domain-standards.md` mapping |
| IATA ONE Order | Single order record concept | Booking/order posture |
| IATA PADIS | Passenger service message standards | Boundary messaging posture |
| ICAO | Travel documents (MRTD) | Boundary: document verification stays with certified systems; no document data stored |
| OpenTravel | Travel industry XML/API schemas | Property/booking field posture |
| PCI DSS | Cardholder data protection | Boundary: payments via certified PSPs only |

Full traceability: `docs/compliance/standards-traceability.md`.

## The platform at a glance

### Microservices (Quarkus, `services/`)

| Service | Port | Alignment | Responsibility |
|---|---|---|---|
| `booking-service` | 8081 | IATA ONE Order posture; privacy by design | Booking registry; the PNR is masked at creation and never kept in clear. |
| `fare-hold-service` | 8082 | Booking money discipline (hold → charge) | Guarded fare holds per booking and the charges against them. |
| `offer-rules-service` | 8083 | IATA NDC offer policy as auditable rules | Decision rules binding cabin classes to discount and stopover envelopes. |
| `travel-property-service` | 8084 | OpenTravel property posture | Travel property catalogue (hotels, resorts, apartments, hostels). |
| `notification-service` | 8085 | Platform support (shared pattern) | Channel-agnostic dispatch of domain notifications from events. |

### Serverless functions (Quarkus Funqy + Knative, `functions/`)

| Function | Trigger | Responsibility |
|---|---|---|
| `disruption-risk-scorer` | Event / HTTP | Scores disruption signals (weather, congestion, industrial action) with an auditable rationale. |
| `journey-sla-evaluator` | Event / HTTP | Compares delay minutes and connection success against booking targets; one finding per breach. |

## Design principles

1. **One bounded context per service**, identical hexagonal layout everywhere.
2. **The domain invariant is code, not prose** — charges against a booking's held amount never exceed that hold — a charge that would overrun it is rejected (422) and records nothing; a booking's money is always exactly accounted.
3. **Privacy by design** — Booking references (PNR) are validated at creation and never stored or returned in clear — only a masked form is kept. Traveler personal and document data is never stored by this core; all data is synthetic. Card data stays with certified PSPs.
4. **Database per service, API-first (OpenAPI), events as CloudEvents.**
5. **Adapt by configuration** — rules, thresholds, tariffs/rates and providers are data
   and configuration, not code forks (see *Adaptation guide*).

## Quick start

Prerequisites: Java 21, Maven 3.9+, optionally Docker/Podman and Knative/OpenShift.

```bash
mvn verify                                    # build and test everything
mvn -pl services/booking-service quarkus:dev
docker compose up -d                          # local PostgreSQL, Kafka, Keycloak
```

## Deployment

- **Kubernetes / OpenShift**: `kubectl apply -k deploy/kubernetes/`.
- **Serverless**: Knative Services in `deploy/knative/` (scale to zero).
- **Local**: `docker-compose.yml` for dependencies; services run with `quarkus:dev`.

## Adaptation guide

| You want to… | Change only… |
|---|---|
| Adjust domain rules and thresholds | Rule data in the `offer-rules-service` context and function configuration |
| Change the guarded capacity semantics | The `fare-hold-service` context (invariant stays: never negative) |
| Plug your IdP / providers | OIDC at the gateway; provider adapters at the boundary |
| Replace in-memory persistence | The repository port's JPA adapter on the service's own database (`DB_KIND`, `DB_URL`) |

## Documentation map

- Architecture (42010): `docs/architecture/` · Decisions: `docs/adr/`
- Domain standards: `docs/domain/domain-standards.md`
- Quality (25010), Security (27001/27002), Life cycle (15288/12207), API, Data, Operations: under `docs/`

## License

Apache-2.0 — see `LICENSE`.
