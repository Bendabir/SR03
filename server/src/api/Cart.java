package api;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import beans.Game;
import beans.OrderLine;
import utils.SessionChecker;

@Path("/api/cart")
public class Cart extends Application {
	private Gson gson; // Gson builder
	
	public Cart(){
		this.gson = new GsonBuilder().setPrettyPrinting().create(); // Human readable
	}
	
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response get(@Context HttpServletRequest baseRequest){
    	Response error = SessionChecker.checkSession(baseRequest);
    	
    	// If error, then return it
    	if(error != null){
    		return error;
    	}
    	
    	HttpSession session = baseRequest.getSession(false);
    	ArrayList<OrderLine> cart = (ArrayList<OrderLine>) session.getAttribute("cart");
    	
    	return Response.ok(this.gson.toJson(cart)).build();
    }
    
    // Using array index as ID for products in cart
    @GET
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("/{product: [0-9]+}")
    public Response get(@Context HttpServletRequest baseRequest, @PathParam("product") String product){
    	Response error = SessionChecker.checkSession(baseRequest);
    	
    	// If error, then return it
    	if(error != null){
    		return error;
    	}
    	
    	int index = Integer.parseInt(product);
    	HttpSession session = baseRequest.getSession(false);
    	ArrayList<OrderLine> cart = (ArrayList<OrderLine>) session.getAttribute("cart");
    	
    	// If index doesn't exist
    	if(index >= cart.size() || index < 0){
    		JsonObject jsonError = new JsonObject();
    		jsonError.addProperty("error", "This product doesn't exist in cart.");
    		
    		return Response.status(Status.NOT_FOUND).entity(this.gson.toJson(jsonError)).build();
    	}
    	
    	return Response.ok(this.gson.toJson(cart.get(index))).build();  		
    }
    
    
    // NEED TO ADD A CHECK ON PRODUCT ALREADY IN CART
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response post(@Context HttpServletRequest baseRequest, String product){
    	Response error = SessionChecker.checkSession(baseRequest);
    	
    	// If error, then return it
    	if(error != null){
    		return error;
    	}
    	
    	// Getting data from client
    	JsonObject json = this.gson.fromJson(product, JsonObject.class);
    	
		OrderLine p = new OrderLine();
		p.setQuantity(json.get("quantity").getAsInt());
		
		// Add a check on quantity value
		if(p.getQuantity() == null || p.getQuantity() <= 0){
    		JsonObject jsonError = new JsonObject();
    		jsonError.addProperty("error", "Cannot add a product to cart with a null quantity.");
    		
    		return Response.status(Status.BAD_REQUEST).entity(this.gson.toJson(jsonError)).build();
		}
		
		Game tempG = dao.Games.get(json.get("game").getAsInt());
		
		// If game is null, bad
		if(tempG == null){
    		JsonObject jsonError = new JsonObject();
    		jsonError.addProperty("error", "A valid product is required.");
    		
    		return Response.status(Status.BAD_REQUEST).entity(this.gson.toJson(jsonError)).build();
		}
		
		// Building game
		Game g = new Game();
		g.setConsole(tempG.getConsole());
		g.setTitle(tempG.getTitle());
		
		p.setGame(g);
		
    	HttpSession session = baseRequest.getSession(false);		
    	ArrayList<OrderLine> cart = (ArrayList<OrderLine>) session.getAttribute("cart");
    	cart.add(p); // Add product to cart
    	
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("product", cart.size() - 1);
    	
    	return Response.ok(this.gson.toJson(jsonResponse)).build(); 
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("/{product: [0-9]+}")    
    public Response put(@Context HttpServletRequest baseRequest, String productData, @PathParam("product") String product){
    	Response error = SessionChecker.checkSession(baseRequest);
    	
    	// If error, then return it
    	if(error != null){
    		return error;
    	}
    	
    	// Getting data from client
    	// Can only modify quantity
    	int index = Integer.parseInt(product);
    	HttpSession session = baseRequest.getSession(false);
    	ArrayList<OrderLine> cart = (ArrayList<OrderLine>) session.getAttribute("cart");
    	JsonObject json = this.gson.fromJson(productData, JsonObject.class);;
    	
    	// If index doesn't exist
    	if(index >= cart.size() || index < 0){
    		JsonObject jsonError = new JsonObject();
    		jsonError.addProperty("error", "This product doesn't exist in cart.");
    		
    		return Response.status(Status.NOT_FOUND).entity(this.gson.toJson(jsonError)).build();
    	}
    	
    	// Updating quantity
    	int quantity = json.get("quantity").getAsInt();
    	
		// Add a check on quantity value
		if(quantity <= 0){
    		JsonObject jsonError = new JsonObject();
    		jsonError.addProperty("error", "Cannot set a null quantity.");
    		
    		return Response.status(Status.BAD_REQUEST).entity(this.gson.toJson(jsonError)).build();
		}
		
		cart.get(index).setQuantity(quantity);
    	
		JsonObject jsonResponse = new JsonObject();
		jsonResponse.addProperty("product", index);
    	
    	return Response.ok(this.gson.toJson(jsonResponse)).build(); 
    }
    
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    @Path("/{product: [0-9]+}")      
    public Response delete(@Context HttpServletRequest baseRequest, @PathParam("product") String product){
    	Response error = SessionChecker.checkSession(baseRequest);
    	
    	// If error, then return it
    	if(error != null){
    		return error;
    	}
    	
    	int index = Integer.parseInt(product);
    	HttpSession session = baseRequest.getSession(false);		
    	ArrayList<OrderLine> cart = (ArrayList<OrderLine>) session.getAttribute("cart");
    	
    	// If index doesn't exist
    	if(index >= cart.size() || index < 0){
    		return Response.ok("false").build();
    	}
    	
    	cart.remove(index); // Removing item
    	
    	return Response.ok("true").build(); 
    }    
}

