package com.manoelcampos.server.rest;

import com.manoelcampos.server.model.Cliente;
import io.quarkus.hibernate.orm.panache.Panache;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.OptimisticLockException;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status;

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

    @Transactional
    @PUT
    public void update(Cliente cliente) {
        try {
            int useVersion=2;
            if (useVersion==1){

                /**
                 * this is weird, why no CLiente.merge(cliente) ? Missing in the API
                 */

                Panache.getEntityManager().merge(cliente);
            }else if (useVersion==2){
                final Cliente byId = Cliente.findById(cliente.id);
                byId.cpf=cliente.cpf;
                byId.endereco=cliente.endereco;
                byId.nome=cliente.nome;
                byId.sexo=cliente.sexo;
                byId.telefone=cliente.telefone;
                // setting version has no effect - its value will be ignored on the sql update
                byId.versao=0;
                // leave out copying the @Version field
            }

        } catch (OptimisticLockException e) {
            Response response = Response.status(Status.CONFLICT)
                    .entity("The record was changed by another user. Try reloading the page.")
                    .type(MediaType.TEXT_PLAIN)
                    .build();
            throw new WebApplicationException(e, response);
        } catch (Exception e) {
            throw new WebApplicationException(e, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

}
