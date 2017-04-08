package api;

import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Game;

@Path("/games")
public class Games extends Application {
	private Gson gson; // Gson builder
	
	public Games(){
		this.gson = new GsonBuilder().setPrettyPrinting().create(); // Human readable
	}
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(){
		ArrayList<Game> lg = dao.Games.all();

    	return Response.ok(this.gson.toJson(lg)).build();
    }	
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{game: [0-9]+}")
    public Response get(@PathParam("game") String gameID){
    	Game g = dao.Games.get(Integer.parseInt(gameID));
    	
    	return Response.ok(this.gson.toJson(g)).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(String game){
		// Getting data from client
		Game g = this.gson.fromJson(game, Game.class);
		
		return Response.ok(dao.Games.add(g).toString()).build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{game: [0-9]+}")    
    public Response put(String gameData, @PathParam("game") String gameID){
    	return Response.ok("PUT").build();
    }
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{game: [0-9]+}")      
    public Response delete(@PathParam("game") String gameID){
    	return Response.ok("DELETE").build();
    }
}

