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
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@Context HttpServletRequest baseRequest){
    	HttpSession session = baseRequest.getSession(false);

    	// If no session, go to login
    	if(session == null){
    		try {
    			// What about giving a forbidden response instead of a redirection ?
        		return Response.temporaryRedirect(new URI("./login")).build();	
    		}
    		catch(URISyntaxException e){
    			e.printStackTrace();

        		JsonObject jsonError = new JsonObject();
        		jsonError.addProperty("message", "Oops, something went wrong because we don't know how to code. Sorry pal !");
    			
    			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(this.gson.toJson(jsonError)).build();
    		}
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
    		try {
        		return Response.temporaryRedirect(new URI("./login")).build();	
    		}
    		catch(URISyntaxException e){
    			e.printStackTrace();

        		JsonObject jsonError = new JsonObject();
        		jsonError.addProperty("message", "Oops, something went wrong because we don't know how to code. Sorry pal !");
    			
    			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(this.gson.toJson(jsonError)).build();
    		}
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
    public Response post(String order){
		// Getting data from client
    	// Need to add a check on session, then modifying username to connected user
    	
    	Order o = this.gson.fromJson(order, Order.class);
    	Order o2 = dao.Orders.add(o);
		
		return Response.status(Status.CREATED).entity(this.gson.toJson(o2)).build();
    }
}