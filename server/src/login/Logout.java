package login;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

@Path("/logout")
public class Logout extends Application {
	private Gson gson; // Gson builder
	private Properties config;
	
	public Logout(){
		this.gson = new GsonBuilder().setPrettyPrinting().create(); // Human readable		
		this.config = new Properties();
		
		try {
			config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("conf.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response get(@Context Request request, @Context HttpServletRequest baseRequest){
    	HttpSession session = baseRequest.getSession(false);
    	
    	if(session == null){
    		JsonObject jsonError = new JsonObject();
    		jsonError.addProperty("message", "You cannot log out if you are not logged in.");
			
			return Response.status(Status.UNAUTHORIZED).entity(gson.toJson(jsonError)).build();
    	}
    	
    	// Destroying session
    	session.invalidate();
    	
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("message", "You are logged out.");
    	
    	return Response.ok(this.gson.toJson(jsonResponse)).build();
    }	
}