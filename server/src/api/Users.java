package api;

import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import beans.User;

@Path("/api/users")
public class Users extends Application {
	private Gson gson; // Gson builder
	
	public Users(){
		this.gson = new GsonBuilder().setPrettyPrinting().create(); // Human readable
	}
	
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response get(){
		ArrayList<User> lu = dao.Users.all();

    	return Response.ok(this.gson.toJson(lu)).build();
    }	
	
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("/{user}")
    public Response get(@PathParam("user") String username){
    	User u = dao.Users.get(username);
    	
    	if(u != null){
        	return Response.ok(this.gson.toJson(u)).build();   		
    	}
    	else {
    		JsonObject jsonError = new JsonObject();
    		jsonError.addProperty("error", "This user doesn't exist.");
    		
    		return Response.status(Status.NOT_FOUND).entity(this.gson.toJson(jsonError)).build();
    	}    	
    }
    
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
//    public Response post(String user){
//		// Getting data from client
//    	User u = this.gson.fromJson(user, User.class);
//		
//		return Response.status(Status.CREATED).entity(dao.Users.add(u).toString()).build();
//    }
    
//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
//    @Path("/{user}")    
//    public Response put(String userData, @PathParam("user") String username){
//    	User u = this.gson.fromJson(userData, User.class);
//    	u.setUsername(username);
//    	
//    	return Response.ok(dao.Users.update(u).toString()).build();
//    }
    
//    @DELETE
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
//    @Path("/{user}")      
//    public Response delete(@PathParam("user") String username){
//    	return Response.ok(dao.Users.delete(username).toString()).build();
//    }
}