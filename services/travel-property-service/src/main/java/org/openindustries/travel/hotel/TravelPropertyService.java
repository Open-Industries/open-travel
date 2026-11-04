package org.openindustries.travel.hotel;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class TravelPropertyService {

    @Inject
    TravelPropertyRepository repository;

    public TravelProperty create(CreateTravelPropertyRequest request) {
        return repository.save(TravelProperty.create(request.name(), request.propertyType(), request.roomsCount()));
    }

    public TravelProperty get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "TravelProperty not found: " + id));
    }

    public List<TravelProperty> list() {
        return repository.findAll();
    }

    public TravelProperty suspend(UUID id) {
        TravelProperty entity = get(id);
        if (!"ACTIVE".equals(entity.status)) {
            throw new DomainException(409, "Only a ACTIVE travelproperty can transition via suspend");
        }
        entity.status = "SUSPENDED";
        return repository.save(entity);
    }
}
