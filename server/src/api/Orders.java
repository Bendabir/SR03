package api;

import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import beans.Order;
import utils.SessionChecker;

@Path("/api/orders")
public class Orders extends Application {
	private Gson gson; // Gson builder
	
	public Orders(){
		this.gson = new GsonBuilder().setPrettyPrinting().create(); // Human readable
	}
	
	// GET method for orders from all users
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("/all")
    public Response getAll(@Context HttpServletRequest baseRequest){
    	Response error = SessionChecker.checkAdminRight(baseRequest);
    	
    	// If error, then return it
    	if(error != null){
    		return error;
    	}
    	
		ArrayList<Order> lo = dao.Orders.all();
	
		return Response.ok(this.gson.toJson(lo)).build();
    }	
    
	// GET method for one order
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("/all/{num: [0-9]+}")
    public Response getAll(@Context HttpServletRequest baseRequest, @PathParam("num") String orderNum){
    	Response error = SessionChecker.checkAdminRight(baseRequest);
    	
    	// If error, then return it
    	if(error != null){
    		return error;
    	}
    	
    	Order o = dao.Orders.get(Integer.parseInt(orderNum));
    	
    	// If not found, return 404
    	if(o == null){
    		JsonObject jsonError = new JsonObject();
    		jsonError.addProperty("error", "This order doesn't exist.");
    		
    		return Response.status(Status.NOT_FOUND).entity(this.gson.toJson(jsonError)).build();    		
    	}	
    	
    	// Else return data
    	return Response.ok(this.gson.toJson(o)).build();
    }	    
	
	// GET method for orders from the connected user
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response get(@Context HttpServletRequest baseRequest){
    	Response error = SessionChecker.checkSession(baseRequest);
    	
    	// If error, then return it
    	if(error != null){
    		return error;
    	}
		
    	// Session found, returning orders from the user
    	HttpSession session = baseRequest.getSession(false);
    	
		ArrayList<Order> lo = dao.Orders.get(session.getAttribute("username").toString());
		
		// Removing username from object
		for(Iterator<Order> i = lo.iterator(); i.hasNext(); ){
			Order order = i.next();
			order.setUser(null);
		}
	
		return Response.ok(this.gson.toJson(lo)).build();
    }	
	
    // Get a specific order of the connected user
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("/{num: [0-9]+}")
    public Response get(@Context HttpServletRequest baseRequest, @PathParam("num") String orderNum){
    	Response error = SessionChecker.checkSession(baseRequest);
    	
    	// If error, then return it
    	if(error != null){
    		return error;
    	}
    	
    	HttpSession session = baseRequest.getSession(false);

    	Order o = dao.Orders.get(session.getAttribute("username").toString(), Integer.parseInt(orderNum));
    	
    	// If not found, return 404
    	if(o == null){
    		JsonObject jsonError = new JsonObject();
    		jsonError.addProperty("error", "You didn't process this order.");
    		
    		return Response.status(Status.NOT_FOUND).entity(this.gson.toJson(jsonError)).build();    		
    	}	
    	
    	o.setUser(null); // Removing username
    	
    	// Else return data
    	return Response.ok(this.gson.toJson(o)).build();   		
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("/{user}")
    public Response getOne(@Context HttpServletRequest baseRequest, @PathParam("user") String user){
    	Response error = SessionChecker.checkAdminRight(baseRequest);
    	
    	// If error, then return it
    	if(error != null){
    		return error;
    	}
    	
    	ArrayList<Order> lo = dao.Orders.get(user);
    	
		// Removing username from object
		for(Iterator<Order> i = lo.iterator(); i.hasNext(); ){
			Order order = i.next();
			order.setUser(null);
		}
    	
    	return Response.ok(this.gson.toJson(lo)).build();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("/{user}/{num: [0-9]+}")
    public Response getOne(@Context HttpServletRequest baseRequest, @PathParam("user") String user, @PathParam("num") String orderNum){
    	Response error = SessionChecker.checkAdminRight(baseRequest);
    	
    	// If error, then return it
    	if(error != null){
    		return error;
    	}
    	
    	Order o = dao.Orders.get(user, Integer.parseInt(orderNum));
    	
    	// If not found
    	if(o == null){
    		JsonObject jsonError = new JsonObject();
    		jsonError.addProperty("error", "This order for this user doesn't exist.");
    		
    		return Response.status(Status.NOT_FOUND).entity(this.gson.toJson(jsonError)).build();
    	}
    	
    	o.setUser(null);
    	
		return Response.ok(this.gson.toJson(o)).build();   		
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response post(@Context HttpServletRequest baseRequest, String order){
    	Response error = SessionChecker.checkSession(baseRequest);
    	
    	// If error, then return it
    	if(error != null){
    		return error;
    	}
    	
    	HttpSession session = baseRequest.getSession(false);

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