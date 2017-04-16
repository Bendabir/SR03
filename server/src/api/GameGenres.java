package api;

import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

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
    		return Response.status(Status.NOT_FOUND).entity("{\"error\" : \"This game genre doesn't exist.\"}").build();
    	}
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response post(String gameGenre){
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
    public Response put(String genreData, @PathParam("genre") String genreName){
    	GameGenre gg = new GameGenre(genreName);
    	GameGenre gg2 = dao.GameGenres.update(gg);
		
		return Response.ok(this.gson.toJson(gg2)).build();
    }
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("/{genre}")      
    public Response delete(@PathParam("genre") String genreName){
    	return Response.ok(dao.GameGenres.delete(genreName).toString()).build();
    }
}