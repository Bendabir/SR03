package api;

import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Console;

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
    		return Response.status(Status.NOT_FOUND).entity("{\"error\" : \"This console doesn't exist.\"}").build();
    	}
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response post(String console){
		// Getting data from client
		Console c = this.gson.fromJson(console, Console.class);
		Console c2 = dao.Consoles.add(c);
		
		return Response.status(Status.CREATED).entity(this.gson.toJson(c2)).build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("/{console}")    
    public Response put(String consoleData, @PathParam("console") String consoleName){
    	Console c = this.gson.fromJson(consoleData, Console.class);   	
    	c.setName(consoleName); // USEFUL ?
    	Console c2 = dao.Consoles.update(c);
    	
    	return Response.ok(this.gson.toJson(c2)).build();
    }
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("/{console}")      
    public Response delete(@PathParam("console") String consoleName){
    	return Response.ok(dao.Consoles.delete(consoleName).toString()).build();
    }
}