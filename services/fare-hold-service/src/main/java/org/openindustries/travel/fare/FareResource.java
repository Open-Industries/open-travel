package org.openindustries.travel.fare;

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

@Path("/api/v1/fare-holds")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Fare Holds", description = "Guarded fare holds per booking and the charges against them.")
public class FareResource {

    @Inject
    FareService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Open a farehold")
    public Response open(@Valid OpenFareHoldRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.open(request)).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one by id")
    public FareHold get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/charges")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Draw from the account (rejects if it would overdraw, 422)")
    public FareCharge consume(@PathParam("id") UUID id, @Valid CreateFareChargeRequest request) {
        return service.consume(id, request);
    }

    @GET
    @Path("/{id}/charges")
    @Operation(summary = "List consumption records of an account")
    public List<FareCharge> records(@PathParam("id") UUID id) {
        return service.recordsOf(id);
    }
}
