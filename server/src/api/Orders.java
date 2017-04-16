package api;

import java.util.ArrayList;

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
	
	// Disabling GET method for all orders
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response get(){
//		ArrayList<Order> lo = dao.Orders.all();
//
//    	return Response.ok(this.gson.toJson(lo)).build();
//    }	
	
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("/{user}")
    public Response get(@PathParam("user") String user){
    	ArrayList<Order> lo = dao.Orders.get(user);
    	
    	return Response.ok(this.gson.toJson(lo)).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("/{user}/{num: [0-9]+}")
    public Response get(@PathParam("user") String user, @PathParam("num") String orderNum){
    	Order o = dao.Orders.get(user, Integer.parseInt(orderNum));
    	
    	if(o != null){
        	return Response.ok(this.gson.toJson(o)).build();   		
    	}
    	else {
    		JsonObject jsonError = new JsonObject();
    		jsonError.addProperty("error", "This order for this user doesn't exist.");
    		
    		return Response.status(Status.NOT_FOUND).entity(this.gson.toJson(jsonError)).build();
    	}
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response post(String order){
		// Getting data from client
    	Order o = this.gson.fromJson(order, Order.class);
    	Order o2 = dao.Orders.add(o);
		
		return Response.status(Status.CREATED).entity(this.gson.toJson(o2)).build();
    }
}