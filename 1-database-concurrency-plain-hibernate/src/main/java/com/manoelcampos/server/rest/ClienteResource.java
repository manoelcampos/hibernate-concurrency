package com.manoelcampos.server.rest;

import com.manoelcampos.server.dao.DAO;
import com.manoelcampos.server.model.Cliente;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

import static jakarta.ws.rs.core.Response.Status;

@Path("/cliente")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {

    @Inject 
    DAO<Cliente> dao;

    @GET
    public List<Cliente> all() {
        return dao.all();
    }
        
    @GET
    @Path("{id}")
    public Cliente findById(@PathParam("id") long id) {
        return dao.findById(id);
    }

    @POST
    @Transactional
    public long insert(Cliente cliente) {
        return dao.save(cliente);
    }
    
    @PUT
    @Transactional
    public void update(Cliente cliente) {
        try {
            dao.save(cliente);
        }catch(OptimisticLockException e){
            Response response = Response.status(Status.CONFLICT)
                                        .entity("The record was changed by another user. Try reloading the page.")
                                        .type(MediaType.TEXT_PLAIN)
                                        .build();
            e.printStackTrace();
            throw new WebApplicationException(e, response);
        }catch(Exception e){
            e.printStackTrace();
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

}
