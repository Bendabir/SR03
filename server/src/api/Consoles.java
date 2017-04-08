package api;

import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Console;

@Path("/consoles")
public class Consoles extends Application {
	private Gson gson; // Gson builder
	
	public Consoles(){
		this.gson = new GsonBuilder().setPrettyPrinting().create(); // Human readable
	}
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(){
		ArrayList<Console> lc = dao.Consoles.all();

    	return Response.ok(this.gson.toJson(lc)).build();
    }	
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{console: [a-bA-B0-9]+}")
    public Response get(@PathParam("console") String consoleName){
    	Console c = dao.Consoles.get(consoleName);
    	
    	return Response.ok(this.gson.toJson(c)).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(String console){
		// Getting data from client
		Console c = this.gson.fromJson(console, Console.class);
		
		return Response.ok(dao.Consoles.add(c).toString()).build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{console: [a-bA-B0-9]+}")    
    public Response put(String consoleData, @PathParam("console") String consoleName){
    	return Response.ok("PUT").build();
    }
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{console: [a-bA-B0-9]+}")      
    public Response delete(@PathParam("console") String consoleName){
    	return Response.ok("DELETE").build();
    }
}