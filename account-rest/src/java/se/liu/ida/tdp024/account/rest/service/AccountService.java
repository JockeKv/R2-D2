package se.liu.ida.tdp024.account.rest.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;


@Path("/account")
public class AccountService {

        private final AccountLogicFacade accountLogicFacde =
            new AccountLogicFacadeImpl(new AccountEntityFacadeDB());
    
        private static final AccountJsonSerializer jsonSerializer = new AccountJsonSerializerImpl();
    
	@GET
	@Path("create")
	public Response create(@QueryParam("accounttype") String accounttype,
                               @QueryParam("name") String name,
                               @QueryParam("bank") String bank) {
            

                String result = accountLogicFacde.createAccount(accounttype, name, bank);
 
                return Response.status(200).entity(result).build();
 
	}
        
        @GET
        @Path("find/name")
        public Response find(@QueryParam("name") String name) {
            String result = accountLogicFacde.listAccounts(name);
            return Response.status(200).entity(result).build();
        }
        
        @GET
        @Path("debit")
        public Response debit(@QueryParam("id") int id,
                             @QueryParam("amount") int amount) {
            String result = accountLogicFacde.debitAccount(id, amount);
            return Response.status(200).entity(result).build();
        }
        
        @GET
        @Path("credit")
        public Response credit(@QueryParam("id") int id,
                             @QueryParam("amount") int amount) {
            String result = accountLogicFacde.creditAccount(id, amount);
            return Response.status(200).entity(result).build();
        }
 
        @GET
        @Path("transactions")
        public Response transactions(@QueryParam("id") int id) {
            String result = accountLogicFacde.listTransactions(id);
            return Response.status(200).entity(result).build();
        }
}
