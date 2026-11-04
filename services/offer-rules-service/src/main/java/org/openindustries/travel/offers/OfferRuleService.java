package org.openindustries.travel.offers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class OfferRuleService {

    @Inject
    OfferRuleRepository repository;

    public OfferRule create(CreateOfferRuleRequest request) {
        return repository.save(OfferRule.create(request.name(), request.cabinClass(),
                request.maxDiscountMinor(), request.maxStopovers(), request.priority()));
    }

    public OfferRule get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "OfferRule not found: " + id));
    }

    public List<OfferRule> list() {
        return repository.findAll();
    }

    public OfferRule activate(UUID id) {
        OfferRule rule = get(id);
        rule.status = "ACTIVE";
        return repository.save(rule);
    }

    public Evaluation evaluate(EvaluateRequest request) {
        var candidates = repository.activeFor(request.cabinClass());
        if (candidates.isEmpty()) {
            return new Evaluation("DENIED", null,
                    "No active rule for " + request.cabinClass());
        }
        for (OfferRule rule : candidates) {
            if (rule.maxStopovers <= request.requiredMaxStopovers()
                    && rule.maxDiscountMinor >= request.requiredMaxDiscountMinor()) {
                return new Evaluation("ALLOWED", rule.id,
                        "Rule '" + rule.name + "' covers the request");
            }
        }
        return new Evaluation("DEGRADED", candidates.get(0).id,
                "Active rules exist for " + request.cabinClass() + " but none meets the requirement");
    }
}
