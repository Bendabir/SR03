package api;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import beans.User;
import utils.SessionChecker;

@Path("/api/users")
public class Users extends Application {
	private Gson gson; // Gson builder
	
	public Users(){
		this.gson = new GsonBuilder().setPrettyPrinting().create(); // Human readable
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	@Path("/me")
	public Response getMe(@Context HttpServletRequest baseRequest){
		// Check if user is connected
		Response error = SessionChecker.checkSession(baseRequest);
		
		// If not, returning error
		if(error != null){
			return error;
		}
		
		HttpSession session = baseRequest.getSession(false);
		User u = dao.Users.get(session.getAttribute("username").toString());
		
		// Building JSON object with user informations
		return Response.ok(this.gson.toJson(u)).build();
	}
	
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response get(@Context HttpServletRequest baseRequest){
    	// Need to be admin to get all users
    	Response error = SessionChecker.checkAdminRight(baseRequest);
    	
    	// If error, then return it
    	if(error != null){
    		return error;
    	}
    	
		ArrayList<User> lu = dao.Users.all();

    	return Response.ok(this.gson.toJson(lu)).build();
    }	
	
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("/{user}")
    public Response get(@Context HttpServletRequest baseRequest, @PathParam("user") String username){
    	// Need to be admin to get one users
    	Response error = SessionChecker.checkAdminRight(baseRequest);
    	
    	// If error, then return it
    	if(error != null){
    		return error;
    	}
    	
    	User u = dao.Users.get(username);
    	
    	// If user not found
    	if(u == null){
    		JsonObject jsonError = new JsonObject();
    		jsonError.addProperty("error", "This user doesn't exist.");
    		
    		return Response.status(Status.NOT_FOUND).entity(this.gson.toJson(jsonError)).build();
    	}
    	
    	// Else, return user
    	return Response.ok(this.gson.toJson(u)).build();   		
    }
    
// Following methods are disabled (cannot add a new user from the API, cannot update or delete one as well). 
    
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
//    public Response post(@Context HttpServletRequest baseRequest, String user){
//    	// Need to be admin to get one users
//    	Response error = SessionChecker.checkAdminRight(baseRequest);
//    	
//    	// If error, then return it
//    	if(error != null){
//    		return error;
//    	}
//    	
//		// Getting data from client
//    	User u = this.gson.fromJson(user, User.class);
//		
//		return Response.status(Status.CREATED).entity(dao.Users.add(u).toString()).build();
//    }
    
//    @PUT
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
//    @Path("/{user}")    
//    public Response put(@Context HttpServletRequest baseRequest, String userData, @PathParam("user") String username){
//    	// Need to be admin to get one users
//    	Response error = SessionChecker.checkAdminRight(baseRequest);
//    	
//    	// If error, then return it
//    	if(error != null){
//    		return error;
//    	}
//    	
//    	User u = this.gson.fromJson(userData, User.class);
//    	u.setUsername(username);
//    	
//    	return Response.ok(dao.Users.update(u).toString()).build();
//    }
    
//    @DELETE
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
//    @Path("/{user}")      
//    public Response delete(@Context HttpServletRequest baseRequest, @PathParam("user") String username){
//    	// Need to be admin to get one users
//    	Response error = SessionChecker.checkAdminRight(baseRequest);
//    	
//    	// If error, then return it
//    	if(error != null){
//    		return error;
//    	}
//    	
//    	return Response.ok(dao.Users.delete(username).toString()).build();
//    }
}