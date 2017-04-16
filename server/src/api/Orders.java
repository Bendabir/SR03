package api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import beans.Order;

@Path("/api/orders")
public class Orders extends Application {
	private Gson gson; // Gson builder
	
	public Orders(){
		this.gson = new GsonBuilder().setPrettyPrinting().create(); // Human readable
	}
	
	// GET method for orders from the connected user
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response get(@Context HttpServletRequest baseRequest){
    	HttpSession session = baseRequest.getSession(false);

    	// If no session, go to login
    	if(session == null){
    		JsonObject jsonError = new JsonObject();
    		jsonError.addProperty("message", "You cannot proccess this operation if you are not logged in.");
			
			return Response.status(Status.UNAUTHORIZED).entity(this.gson.toJson(jsonError)).build();
    	}
    	else {
    		// Session found, returning orders from the user
    		ArrayList<Order> lo = dao.Orders.get(session.getAttribute("username").toString());

        	return Response.ok(this.gson.toJson(lo)).build();
    	}
    }	
	
    // Get a specific order of the connected user
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("/{num: [0-9]+}")
    public Response get(@Context HttpServletRequest baseRequest, @PathParam("num") String orderNum){
    	HttpSession session = baseRequest.getSession(false);

    	// If no session, go to login
    	if(session == null){
    		JsonObject jsonError = new JsonObject();
    		jsonError.addProperty("message", "You cannot proccess this operation if you are not logged in.");
			
			return Response.status(Status.UNAUTHORIZED).entity(this.gson.toJson(jsonError)).build();
    	}
    	else {
        	Order o = dao.Orders.get(session.getAttribute("username").toString(), Integer.parseInt(orderNum));
        	
        	if(o != null){
            	return Response.ok(this.gson.toJson(o)).build();   		
        	}
        	else {
        		JsonObject jsonError = new JsonObject();
        		jsonError.addProperty("error", "You didn't process this order.");
        		
        		return Response.status(Status.NOT_FOUND).entity(this.gson.toJson(jsonError)).build();
        	}
    	}
    }
    
//    @GET
//    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
//    @Path("/{user}")
//    public Response get(@PathParam("user") String user){
//    	ArrayList<Order> lo = dao.Orders.get(user);
//    	
//    	return Response.ok(this.gson.toJson(lo)).build();
//    }
//    
//    @GET
//    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
//    @Path("/{user}/{num: [0-9]+}")
//    public Response get(@PathParam("user") String user, @PathParam("num") String orderNum){
//    	Order o = dao.Orders.get(user, Integer.parseInt(orderNum));
//    	
//    	if(o != null){
//        	return Response.ok(this.gson.toJson(o)).build();   		
//    	}
//    	else {
//    		JsonObject jsonError = new JsonObject();
//    		jsonError.addProperty("error", "This order for this user doesn't exist.");
//    		
//    		return Response.status(Status.NOT_FOUND).entity(this.gson.toJson(jsonError)).build();
//    	}
//    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response post(@Context HttpServletRequest baseRequest, String order){
    	HttpSession session = baseRequest.getSession(false);

    	// If no session, go to login
    	if(session == null){
    		JsonObject jsonError = new JsonObject();
    		jsonError.addProperty("message", "You cannot proccess this operation if you are not logged in.");
			
			return Response.status(Status.UNAUTHORIZED).entity(this.gson.toJson(jsonError)).build();
    	}
    	else {
        	// Getting data from client
        	Order o = this.gson.fromJson(order, Order.class);
        	o.setUser(session.getAttribute("username").toString()); // Order for logged user !
        	
        	// If no lines, return error
        	if(o.getLines().isEmpty()){
        		JsonObject jsonError = new JsonObject();
        		jsonError.addProperty("message", "You cannot add an order without any purchase.");
    			
    			return Response.status(Status.BAD_REQUEST).entity(this.gson.toJson(jsonError)).build();
        	}
        	
        	// If ok, then add
        	Order o2 = dao.Orders.add(o);
    		
    		return Response.status(Status.CREATED).entity(this.gson.toJson(o2)).build();
    	}
    }
}