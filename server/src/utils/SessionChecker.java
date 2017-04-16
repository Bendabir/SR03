package utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

// Little helper that check some parameters in session
public class SessionChecker{
	private static Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Gson builder
	
	private SessionChecker(){}
	
	// Check if logged in user is admin (and if user is logged in)
	public static Response checkAdminRight(HttpServletRequest request){
    	// User needs to be logged in and admin in order to modify this
    	HttpSession session = request.getSession(false);

    	// If no session, respond 401
    	if(session == null){
    		JsonObject jsonError = new JsonObject();
    		jsonError.addProperty("message", "You cannot proccess this operation if you are not logged in.");
			
			return Response.status(Status.UNAUTHORIZED).entity(gson.toJson(jsonError)).build();
    	}
    	
		// Checking rights
		String status = session.getAttribute("status").toString();
		
		// If not admin, respond 401
		if(!status.equals("admin")){
    		JsonObject jsonError = new JsonObject();
    		jsonError.addProperty("message", "You cannot proccess this operation if you are not admin.");
			
			return Response.status(Status.UNAUTHORIZED).entity(gson.toJson(jsonError)).build();
		}
		
		// Else return null saying that we can process 
		return null;
	}
	
	// Check if user is logged in
	public static Response checkSession(HttpServletRequest request){
    	// User needs to be logged in to process
    	HttpSession session = request.getSession(false);

    	// If no session, respond 401
    	if(session == null){
    		JsonObject jsonError = new JsonObject();
    		jsonError.addProperty("message", "You cannot proccess this operation if you are not logged in.");
			
			return Response.status(Status.UNAUTHORIZED).entity(gson.toJson(jsonError)).build();
    	}
		
		// Else return null saying that we can process (no error)
		return null;
	}
}

