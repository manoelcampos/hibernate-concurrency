package com.manoelcampos.server.rest;

import com.manoelcampos.server.model.Cliente;

import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

import static jakarta.ws.rs.core.Response.Status;

@Path("/cliente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {
    @GET
    public List<Cliente> all() {
        return Cliente.listAll();
    }

    @GET
    @Path("{id}")
    public Cliente findById(@PathParam("id") long id) {
        return Cliente.findById(id);
    }

    @Transactional
    @POST
    public long insert(Cliente cliente) {
        Cliente.persist(cliente);
        return cliente.id;
    }

    @PUT
    @Transactional
    public void update(Cliente cliente) {
        try{
            if(Cliente.update(cliente))
                return;
        }catch(OptimisticLockException e){
            Response response = Response.status(Status.CONFLICT)
                                        .entity("The record was changed by another user. Try reloading the page.")
                                        .type(MediaType.TEXT_PLAIN)
                                        .build();
            throw new WebApplicationException(e, response);
        }catch(Exception e){
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }

        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }

}
