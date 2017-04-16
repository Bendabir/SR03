package api;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import beans.GameGenre;

import utils.SessionChecker;

@Path("/api/gameGenres")
public class GameGenres extends Application {
	private Gson gson; // Gson builder
	
	public GameGenres(){
		this.gson = new GsonBuilder().setPrettyPrinting().create(); // Human readable
	}
	
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response get(){
		ArrayList<GameGenre> lgg = dao.GameGenres.all();

    	return Response.ok(this.gson.toJson(lgg)).build();
    }	
	
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("/{genre}")
    public Response get(@PathParam("genre") String genreName){
    	GameGenre gg = dao.GameGenres.get(genreName);
    	
    	if(gg != null){
        	return Response.ok(this.gson.toJson(gg)).build();   		
    	}
    	else {
    		JsonObject jsonError = new JsonObject();
    		jsonError.addProperty("error", "This game genre doesn't exist.");
    		
    		return Response.status(Status.NOT_FOUND).entity(this.gson.toJson(jsonError)).build();
    	}
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response post(@Context HttpServletRequest baseRequest, String gameGenre){
    	Response error = SessionChecker.checkAdminRight(baseRequest);
    	
    	// If error, then return it
    	if(error != null){
    		return error;
    	}
    
    	// Getting data from client
    	GameGenre gg = this.gson.fromJson(gameGenre, GameGenre.class);
    	GameGenre gg2 = dao.GameGenres.add(gg);
		
		return Response.status(Status.CREATED).entity(this.gson.toJson(gg2)).build();
    }
    
    // Maybe not useful
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("/{genre}")    
    public Response put(@Context HttpServletRequest baseRequest, String genreData, @PathParam("genre") String genreName){
    	Response error = SessionChecker.checkAdminRight(baseRequest);
    	
    	// If error, then return it
    	if(error != null){
    		return error;
    	}
    		
		// Else process
    	// Getting data from client
    	GameGenre gg = new GameGenre(genreName);
    	GameGenre gg2 = dao.GameGenres.update(gg);
		
		return Response.ok(this.gson.toJson(gg2)).build();	    	
    }
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("/{genre}")      
    public Response delete(@Context HttpServletRequest baseRequest, @PathParam("genre") String genreName){
    	Response error = SessionChecker.checkAdminRight(baseRequest);
    	
    	// If error, then return it
    	if(error != null){
    		return error;
    	}
    		
		// Else process
    	return Response.ok(dao.GameGenres.delete(genreName).toString()).build();
    }
}