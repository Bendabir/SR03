package api;

import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.GameGenre;

@Path("/gameGenres")
public class GameGenres extends Application {
	private Gson gson; // Gson builder
	
	public GameGenres(){
		this.gson = new GsonBuilder().setPrettyPrinting().create(); // Human readable
	}
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(){
		ArrayList<GameGenre> lgg = dao.GameGenres.all();

    	return Response.ok(this.gson.toJson(lgg)).build();
    }	
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{genre}")
    public Response get(@PathParam("genre") String genreName){
    	GameGenre gg = dao.GameGenres.get(genreName);
    	
    	return Response.ok(this.gson.toJson(gg)).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(String gameGenre){
		// Getting data from client
    	GameGenre gg = this.gson.fromJson(gameGenre, GameGenre.class);
		
		return Response.ok(dao.GameGenres.add(gg).toString()).build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{genre}")    
    public Response put(String genreData, @PathParam("genre") String genreName){
    	return Response.ok("PUT").build();
    }
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{genre}")      
    public Response delete(@PathParam("genre") String genreName){
    	return Response.ok("DELETE").build();
    }
}