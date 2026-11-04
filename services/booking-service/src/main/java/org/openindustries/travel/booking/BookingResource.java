package org.openindustries.travel.booking;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@Path("/api/v1/bookings")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Bookings", description = "Booking registry; the PNR is masked at creation and never kept in clear.")
public class BookingResource {

    @Inject
    BookingService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a booking")
    public Response create(@Valid CreateBookingRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.create(request)).build();
    }

    @GET
    @Operation(summary = "List all")
    public List<Booking> list() {
        return service.list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one by id")
    public Booking get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/confirm")
    @Operation(summary = "Confirm this booking (lifecycle transition, no body)")
    public Booking confirm(@PathParam("id") UUID id) {
        return service.confirm(id);
    }

    @POST
    @Path("/{id}/cancel")
    @Operation(summary = "Cancel this booking (lifecycle transition, no body)")
    public Booking cancel(@PathParam("id") UUID id) {
        return service.cancel(id);
    }
}
