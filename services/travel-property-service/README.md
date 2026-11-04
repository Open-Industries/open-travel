# Travel Property Service

Bounded context: **Travel Properties** — Travel property catalogue (hotels, resorts, apartments, hostels).

Alignment: OpenTravel property posture. Lifecycle transitions take no body. All data is synthetic.

| Item | Value |
|---|---|
| Port | `8084` (`HTTP_PORT`) |
| Base path | `/api/v1/travel-properties` |
| Owns data | TravelProperty records |

Run locally: `mvn -pl services/travel-property-service quarkus:dev`.
