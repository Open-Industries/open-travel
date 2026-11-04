package org.openindustries.travel.offers;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class OfferRuleRepository {
    private final ConcurrentHashMap<UUID, OfferRule> store = new ConcurrentHashMap<>();

    public OfferRule save(OfferRule rule) {
        store.put(rule.id, rule);
        return rule;
    }

    public Optional<OfferRule> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    public List<OfferRule> findAll() {
        return List.copyOf(store.values());
    }

    /** Active rules for a scope, highest priority (lowest number) first. */
    public List<OfferRule> activeFor(String cabinClass) {
        return store.values().stream()
                .filter(r -> r.cabinClass.equals(cabinClass) && "ACTIVE".equals(r.status))
                .sorted(Comparator.comparingInt(r -> r.priority))
                .toList();
    }
}
