package api;

import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Order;

@Path("/orders")
public class Orders extends Application {
	private Gson gson; // Gson builder
	
	public Orders(){
		this.gson = new GsonBuilder().setPrettyPrinting().create(); // Human readable
	}
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(){
		ArrayList<Order> lo = dao.Orders.all();

    	return Response.ok(this.gson.toJson(lo)).build();
    }	
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{user}")
    public Response get(@PathParam("user") String user){
    	ArrayList<Order> lo = dao.Orders.get(user);
    	
    	return Response.ok(this.gson.toJson(lo)).build();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(String order){
		// Getting data from client
    	Order o = this.gson.fromJson(order, Order.class);
		
		return Response.ok(dao.Orders.add(o).toString()).build();
    }
}