package api;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import beans.GameType;

/**
 * Servlet implementation class GestionUsers
 */
@WebServlet("/api/gameTypes")
public class GameTypes extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GameTypes() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<GameType> lt = dao.GameTypes.all();
		
        // Gson gson = new Gson();
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Human readable
        
        // Setting headers
        response.setHeader("Content-Type", "application/json");
		
        // Printing response
        response.getWriter().print(gson.toJson(lt));			
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// If Content-Type doesn't match
		if(request.getContentType().compareTo("application/json") == 0){
			// Getting data from client
			Gson gson = new Gson();			
			GameType t = gson.fromJson(request.getReader(), GameType.class);
			
			// No check on data integrity
			
			// Add console
			response.getWriter().print(gson.toJson(dao.GameTypes.add(t)));			
		}
	}
}
