package org.openindustries.travel.fare;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/** Outbound port; default in-memory adapter, production adapter is JPA on this service's own database. */
@ApplicationScoped
public class FareRepository {
    private final ConcurrentHashMap<UUID, FareHold> accounts = new ConcurrentHashMap<>();
    private final CopyOnWriteArrayList<FareCharge> records = new CopyOnWriteArrayList<>();

    public FareHold saveAccount(FareHold account) {
        accounts.put(account.id, account);
        return account;
    }

    public Optional<FareHold> findAccount(UUID id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public FareCharge saveRecord(FareCharge record) {
        records.add(record);
        return record;
    }

    public List<FareCharge> recordsOf(UUID accountId) {
        return records.stream().filter(r -> r.accountId.equals(accountId)).toList();
    }
}
