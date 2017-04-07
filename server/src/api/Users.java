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

import beans.User;

/**
 * Servlet implementation class GestionUsers
 */
@WebServlet("/api/users")
public class Users extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<User> lu = dao.Users.all();
		
        // Gson gson = new Gson();
        Gson gson = new GsonBuilder().setPrettyPrinting().create(); // Human readable
        
        // Setting headers
        response.setHeader("Content-Type", "application/json");
		
        // Printing response
        response.getWriter().print(gson.toJson(lu));			
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
			User u = gson.fromJson(request.getReader(), User.class);
			u.hash();
			
			// No check on data integrity
			
			// Add game
			response.getWriter().print(gson.toJson(dao.Users.add(u)));			
		}
	}
}
