package api;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import beans.Game;
import utils.SessionChecker;

@Path("/api/games")
public class Games extends Application {
	private Gson gson; // Gson builder
	
	public Games(){
		this.gson = new GsonBuilder().setPrettyPrinting().create(); // Human readable
	}
	
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response get(){
		ArrayList<Game> lg = dao.Games.all();

    	return Response.ok(this.gson.toJson(lg)).build();
    }	
	
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("/{game: [0-9]+}")
    public Response get(@PathParam("game") String gameID){
    	Game g = dao.Games.get(Integer.parseInt(gameID));
    	
    	if(g == null){
    		JsonObject jsonError = new JsonObject();
    		jsonError.addProperty("error", "This game doesn't exist.");
    		
    		return Response.status(Status.NOT_FOUND).entity(this.gson.toJson(jsonError)).build();
    	}
    	
		return Response.ok(this.gson.toJson(g)).build();   		
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response post(@Context HttpServletRequest baseRequest, String game){
    	Response error = SessionChecker.checkAdminRight(baseRequest);
    	
    	// If error, then return it
    	if(error != null){
    		return error;
    	}
    	
		// Getting data from client
		Game g = this.gson.fromJson(game, Game.class);
		Game g2 = dao.Games.add(g);
		
		// We could also return a 201 (created) HTTP code
		return Response.status(Status.CREATED).entity(this.gson.toJson(g2)).build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("/{game: [0-9]+}")    
    public Response put(@Context HttpServletRequest baseRequest, String gameData, @PathParam("game") String gameID){
    	Response error = SessionChecker.checkAdminRight(baseRequest);
    	
    	// If error, then return it
    	if(error != null){
    		return error;
    	}
    	
    	Game g = this.gson.fromJson(gameData, Game.class);
    	g.setId(Integer.parseInt(gameID));
    	Game g2 = dao.Games.update(g);
    	
    	return Response.ok(this.gson.toJson(g2)).build();
    }
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("/{game: [0-9]+}")      
    public Response delete(@Context HttpServletRequest baseRequest, @PathParam("game") String gameID){
    	Response error = SessionChecker.checkAdminRight(baseRequest);
    	
    	// If error, then return it
    	if(error != null){
    		return error;
    	}
    	
    	return Response.ok(dao.Games.delete(Integer.parseInt(gameID)).toString()).build();
    }
}

