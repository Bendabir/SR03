package api;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

@Provider
public class RequestsExceptionHandler implements ExceptionMapper<WebApplicationException> {
	private Gson gson;
	
	public RequestsExceptionHandler(){
		this.gson = new GsonBuilder().setPrettyPrinting().create(); // Human readable
	}	
	
    @Override
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response toResponse(WebApplicationException exception){
    	// Bad request by default if an error occurred
    	// Returning application/json put could be text instead
    	JsonObject jsonError = new JsonObject();
    	jsonError.addProperty("error", exception.getMessage());
    	
        return Response.status(Status.BAD_REQUEST).entity(this.gson.toJson(jsonError)).header("Content-Type", "application/json").build();
    }
}