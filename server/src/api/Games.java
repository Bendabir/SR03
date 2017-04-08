package api;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.Game;

@Path("/games")
public class Games extends Application {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{game: [0-9]+}")
    public Response get(@PathParam("game") String gameName) {
		// Getting param
    	return Response.ok(gameName).build();
		
//		ArrayList<Game> lg = dao.Games.all();
//		
//        // Gson gson = new Gson();
//        Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Human readable
//        
//        // Setting headers
//        response.setHeader("Content-Type", "application/json");
//		
//        // Printing response
//        response.getWriter().print(gson.toJson(lg));
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(){
    	return Response.ok("All games").build();
    }
}

///**
// * Servlet implementation class GestionUsers
// */
////@WebServlet("/api/games")
//@Path("/{game}")
//public class Games extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//
//	/**
//	 * @see HttpServlet#HttpServlet()
//	 */
//	public Games() {
//		super();
//		// TODO Auto-generated constructor stub
//	}
//	
//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
//	 *      response)
//	 */
//	@GET
//	protected void doGet(HttpServletRequest request, HttpServletResponse response, @PathParam("game") String gameName) throws ServletException, IOException {
//		// Getting param
//		System.out.println(gameName);
//		
//		ArrayList<Game> lg = dao.Games.all();
//		
//        // Gson gson = new Gson();
//        Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Human readable
//        
//        // Setting headers
//        response.setHeader("Content-Type", "application/json");
//		
//        // Printing response
//        response.getWriter().print(gson.toJson(lg));			
//	}
//	
//	/**
//	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
//	 *      response)
//	 */
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// If Content-Type doesn't match
//		if(request.getContentType().compareTo("application/json") == 0){
//			// Getting data from client
//			Gson gson = new Gson();			
//			Game g = gson.fromJson(request.getReader(), Game.class);
//			
//			// No check on data integrity
//			
//			// Add game
//			response.getWriter().print(gson.toJson(dao.Games.add(g)));			
//		}
//	}
//}
