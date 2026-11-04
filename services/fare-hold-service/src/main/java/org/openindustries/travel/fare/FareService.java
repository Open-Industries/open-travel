package org.openindustries.travel.fare;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class FareService {

    @Inject
    FareRepository repository;

    public FareHold open(OpenFareHoldRequest request) {
        return repository.saveAccount(FareHold.open(request.bookingId(), request.heldAmountMinor()));
    }

    public FareHold get(UUID id) {
        return repository.findAccount(id)
                .orElseThrow(() -> new DomainException(404, "FareHold not found: " + id));
    }

    /**
     * Draws from the account. Invariant: the balance/capacity never goes negative —
     * an over-drawing operation is rejected (422) and nothing is recorded.
     */
    public FareCharge consume(UUID accountId, CreateFareChargeRequest request) {
        FareHold account = get(accountId);
        if (!"ACTIVE".equals(account.status)) {
            throw new DomainException(409, "FareHold is not ACTIVE");
        }
        if (request.amountMinor() > account.heldAmountMinor) {
            throw new DomainException(422, "Insufficient capacity: requested " + request.amountMinor()
                    + " exceeds available " + account.heldAmountMinor);
        }
        account.heldAmountMinor -= request.amountMinor();
        repository.saveAccount(account);
        return repository.saveRecord(FareCharge.of(accountId, request.chargeType(), request.amountMinor(), account.heldAmountMinor));
    }

    public List<FareCharge> recordsOf(UUID accountId) {
        get(accountId);
        return repository.recordsOf(accountId);
    }
}
