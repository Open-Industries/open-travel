package org.openindustries.travel.hotel;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class TravelPropertyRepository {
    private final ConcurrentHashMap<UUID, TravelProperty> store = new ConcurrentHashMap<>();

    public TravelProperty save(TravelProperty entity) {
        store.put(entity.id, entity);
        return entity;
    }

    public Optional<TravelProperty> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<TravelProperty> findAll() {
        return List.copyOf(store.values());
    }
}
