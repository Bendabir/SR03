package api;

import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.GameGenre;

@Path("/api/gameGenres")
public class GameGenres extends Application {
	private Gson gson; // Gson builder
	
	public GameGenres(){
		this.gson = new GsonBuilder().setPrettyPrinting().create(); // Human readable
	}
	
    @GET
    @Produces("application/json; charset=UTF-8")
    public Response get(){
		ArrayList<GameGenre> lgg = dao.GameGenres.all();

    	return Response.ok(this.gson.toJson(lgg)).build();
    }	
	
    @GET
    @Produces("application/json; charset=UTF-8")
    @Path("/{genre}")
    public Response get(@PathParam("genre") String genreName){
    	GameGenre gg = dao.GameGenres.get(genreName);
    	
    	if(gg != null){
        	return Response.ok(this.gson.toJson(gg)).build();   		
    	}
    	else {
    		return Response.status(404).entity("{\"error\" : \"This game genre doesn't exist.\"}").build();
    	}
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json; charset=UTF-8")
    public Response post(String gameGenre){
		// Getting data from client
    	GameGenre gg = this.gson.fromJson(gameGenre, GameGenre.class);
		
		return Response.ok(dao.GameGenres.add(gg).toString()).build();
    }
    
    // Maybe not useful
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json; charset=UTF-8")
    @Path("/{genre}")    
    public Response put(String genreData, @PathParam("genre") String genreName){
    	GameGenre gg = new GameGenre(genreName);
    	
    	return Response.ok(dao.GameGenres.update(gg).toString()).build();
    }
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/json; charset=UTF-8")
    @Path("/{genre}")      
    public Response delete(@PathParam("genre") String genreName){
    	return Response.ok(dao.GameGenres.delete(genreName).toString()).build();
    }
}