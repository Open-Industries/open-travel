package org.openindustries.travel.booking;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class BookingService {

    @Inject
    BookingRepository repository;

    public Booking create(CreateBookingRequest request) {
        return repository.save(Booking.create(request.bookingType(), request.pnrCode(), request.channelCode()));
    }

    public Booking get(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new DomainException(404, "Booking not found: " + id));
    }

    public List<Booking> list() {
        return repository.findAll();
    }

    public Booking confirm(UUID id) {
        Booking entity = get(id);
        if ("CANCELLED".equals(entity.status)) {
            throw new DomainException(409, "A cancelled booking is terminal");
        }
        if ("CONFIRMED".equals(entity.status)) {
            throw new DomainException(409, "Booking already CONFIRMED");
        }
        entity.status = "CONFIRMED";
        return repository.save(entity);
    }

    public Booking cancel(UUID id) {
        Booking entity = get(id);
        if ("CANCELLED".equals(entity.status)) {
            throw new DomainException(409, "A cancelled booking is terminal");
        }
        if ("CANCELLED".equals(entity.status)) {
            throw new DomainException(409, "Booking already CANCELLED");
        }
        entity.status = "CANCELLED";
        return repository.save(entity);
    }
}
