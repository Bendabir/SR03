package api;

import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.User;

@Path("/users")
public class Users extends Application {
	private Gson gson; // Gson builder
	
	public Users(){
		this.gson = new GsonBuilder().setPrettyPrinting().create(); // Human readable
	}
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(){
		ArrayList<User> lu = dao.Users.all();

    	return Response.ok(this.gson.toJson(lu)).build();
    }	
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{user}")
    public Response get(@PathParam("user") String username){
    	User u = dao.Users.get(username);
    	
    	return Response.ok(this.gson.toJson(u)).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(String user){
		// Getting data from client
    	User u = this.gson.fromJson(user, User.class);
    	u.hash();
		
		return Response.ok(dao.Users.add(u).toString()).build();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{user}")    
    public Response put(String userData, @PathParam("user") String username){
    	User u = this.gson.fromJson(userData, User.class);
    	u.setUsername(username);
    	u.hash();
    	
    	return Response.ok(dao.Users.update(u).toString()).build();
    }
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{user}")      
    public Response delete(@PathParam("user") String username){
    	return Response.ok(dao.Users.delete(username).toString()).build();
    }
}