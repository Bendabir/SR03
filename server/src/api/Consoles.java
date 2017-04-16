package api;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import beans.Console;
import utils.SessionChecker;

// PERHAPS WE CAN FIND A BETTER ARCHITECTURE WITH INHERITANCE AND CALLBACKS TO HAVE SOMETHING CLEANER AND MORE FLEXIBLE

@Path("/api/consoles")
public class Consoles extends Application {
	private Gson gson; // Gson builder
	
	public Consoles(){
		this.gson = new GsonBuilder().setPrettyPrinting().create(); // Human readable
	}
	
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response get(){
		ArrayList<Console> lc = dao.Consoles.all();

    	return Response.ok(this.gson.toJson(lc)).build();
    }	
	
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("/{console}")
    public Response get(@PathParam("console") String consoleName){
    	Console c = dao.Consoles.get(consoleName);
    	
    	if(c != null){
        	return Response.ok(this.gson.toJson(c)).build();   		
    	}
    	else {
    		JsonObject jsonError = new JsonObject();
    		jsonError.addProperty("error", "This console doesn't exist.");
    		
    		return Response.status(Status.NOT_FOUND).entity(this.gson.toJson(jsonError)).build();
    	}
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response post(@Context HttpServletRequest baseRequest, String console){
    	Response error = SessionChecker.checkAdminRight(baseRequest);
    	
    	// If error, then return it
    	if(error != null){
    		return error;
    	}
    		
		// Else process
		// Getting data from client
		Console c = this.gson.fromJson(console, Console.class);
		Console c2 = dao.Consoles.add(c);
		
		return Response.status(Status.CREATED).entity(this.gson.toJson(c2)).build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("/{console}")    
    public Response put(@Context HttpServletRequest baseRequest, String consoleData, @PathParam("console") String consoleName){
    	Response error = SessionChecker.checkAdminRight(baseRequest);
    	
    	// If error, then return it
    	if(error != null){
    		return error;
    	}
    		
		// Else process
		// Getting data from client
    	Console c = this.gson.fromJson(consoleData, Console.class);   	
//      c.setName(consoleName); // USEFUL ?
    	Console c2 = dao.Consoles.update(c);
    	
    	return Response.ok(this.gson.toJson(c2)).build();
    }
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("/{console}")      
    public Response delete(@Context HttpServletRequest baseRequest, @PathParam("console") String consoleName){
    	Response error = SessionChecker.checkAdminRight(baseRequest);
    	
    	// If error, then return it
    	if(error != null){
    		return error;
    	}
    		
		// Else process
    	return Response.ok(dao.Consoles.delete(consoleName).toString()).build();
    }
}