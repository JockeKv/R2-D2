package se.liu.ida.tdp024.account.rest.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import se.liu.ida.tdp024.account.data.api.exception.EntityInputParameterException;
import se.liu.ida.tdp024.account.data.api.exception.EntityNotFoundException;
import se.liu.ida.tdp024.account.data.api.exception.EntityServiceConfigurationException;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;


@Path("/account")
public class AccountService {

        private final AccountLogicFacade accountLogicFacde =
            new AccountLogicFacadeImpl(new AccountEntityFacadeDB());
    
        
	@GET
	@Path("create")
	public Response create(@QueryParam("accounttype") String accounttype,
                               @QueryParam("name") String name,
                               @QueryParam("bank") String bank) {
            
                try {
                    String result = accountLogicFacde.createAccount(accounttype, name, bank);
                }
                catch(EntityInputParameterException e) {
                    return Response.status(Response.Status.BAD_REQUEST).entity("FAILED").build();
                }
                catch(EntityServiceConfigurationException e) {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("FAILED").build();
                }
 
                return Response.status(Response.Status.OK).entity("OK").build();
 
	}
        
        @GET
        @Path("find/name")
        public Response find(@QueryParam("name") String name) {
            String result;
            try {
                result = accountLogicFacde.listAccounts(name);
            }
            catch(EntityServiceConfigurationException e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("FAILED").build();
            }
            return Response.status(Response.Status.OK).entity(result).build();
        }
        
        @GET
        @Path("debit")
        public Response debit(@QueryParam("id") int id,
                             @QueryParam("amount") int amount) {
            String result;
            try {
                result = accountLogicFacde.debitAccount(id, amount);
            }
            catch(Exception e) {
                if(e instanceof EntityInputParameterException) {
                    return Response.status(Response.Status.BAD_REQUEST).entity("FAILED").build();
                } else if (e instanceof EntityNotFoundException) {
                    return Response.status(Response.Status.NOT_FOUND).entity("FAILED").build();
                } else {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("FAILED").build();
                }
            }
            return Response.status(Response.Status.OK).entity(result).build();
        }
        
        @GET
        @Path("credit")
        public Response credit(@QueryParam("id") int id,
                             @QueryParam("amount") int amount) {
            String result;
            try {
                result = accountLogicFacde.creditAccount(id, amount);
            }
            catch(Exception e) {
                if(e instanceof EntityInputParameterException) {
                    return Response.status(Response.Status.BAD_REQUEST).entity("FAILED").build();
                } else if (e instanceof EntityNotFoundException) {
                    return Response.status(Response.Status.NOT_FOUND).entity("FAILED").build();
                } else {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("FAILED").build();
                }
            }
            return Response.status(Response.Status.OK).entity(result).build();
        }
 
        @GET
        @Path("transactions")
        public Response transactions(@QueryParam("id") int id) {
            String result;
            try {
                result = accountLogicFacde.listTransactions(id);
            }
            catch(Exception e) {
                if (e instanceof EntityNotFoundException) {
                    return Response.status(Response.Status.NOT_FOUND).entity("FAILED").build();
                } else {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("FAILED").build();
                }
            }
            return Response.status(200).entity(result).build();
        }
}
