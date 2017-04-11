package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bdd.DatabaseConnection;

import beans.User;

public class Users {
	
	public static User get(String username){
		User u = null;
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			String sql = "SELECT * FROM users WHERE username = ? AND active = 1;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, username);

			ResultSet res = ps.executeQuery();

			while(res.next()){
				u = new User();
				u.setUsername(res.getString("username"));
				u.setFirstName(res.getString("firstname"));
				u.setLastName(res.getString("lastname"));
				u.setStatus(res.getString("status"));
				break;
			}	
			
			res.close();
			DatabaseConnection.getInstance().closeCnx();			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return u;
	}

	public static ArrayList<User> all(){
		ArrayList<User> lu = new ArrayList<User>();
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			String sql = "SELECT * FROM users WHERE active = 1;";
			PreparedStatement ps = cnx.prepareStatement(sql);

			ResultSet res = ps.executeQuery();

			while(res.next()){
				User u = new User();
				u.setUsername(res.getString("username"));
				u.setFirstName(res.getString("firstname"));
				u.setLastName(res.getString("lastname"));
				u.setStatus(res.getString("status"));
				
				lu.add(u);
			}		

			res.close();
			DatabaseConnection.getInstance().closeCnx();
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		
		return lu;		
	}

	public static Boolean add(User user){
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			// First check if user exist and is disabled
			String sql = "SELECT * FROM users WHERE username = ? AND active = 0;";
			
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			
			ResultSet res = ps.executeQuery();
			
			if(res.first()){
				res.close();
				ps.close();
				
				// Reactivating
				sql = "UPDATE users SET active = 1 WHERE username = ?;";
				
				ps = cnx.prepareStatement(sql);
				ps.setString(1, user.getUsername());
			}
			else {
				res.close();
				ps.close();				
				
				// Inserting
				sql = "INSERT INTO users (username, firstname, lastname, status) VALUES(?, ?, ?, ?);";
				
				ps = cnx.prepareStatement(sql);
				ps.setString(1, user.getUsername());
				ps.setString(2, user.getFirstName());
				ps.setString(3, user.getLastName());
				ps.setString(4, user.getStatus());
			}
			
			//Execution et traitement de la réponse
			ps.executeUpdate();			

			DatabaseConnection.getInstance().closeCnx();			
		} catch (SQLException e) {
			e.printStackTrace();
			
			return false;
		}

		return true; // What about returning ID instead ?	
	}

	public static Boolean update(User user){
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			// Requête
			String sql = "UPDATE users SET firstname = ?, lastname = ?, status = ? WHERE username = ? AND active = 1;";
			
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, user.getFirstName());
			ps.setString(2, user.getLastName());
			ps.setString(3, user.getStatus());
			ps.setString(4, user.getUsername());			
			
			//Execution et traitement de la réponse
			ps.executeUpdate();
			
			DatabaseConnection.getInstance().closeCnx();			
		} catch (SQLException e) {
			e.printStackTrace();
			
			return false;
		}

		return true;		
	}

	public static Boolean delete(String username){
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();	
			
			// Requête
			String sql = "UPDATE users SET active = 0 WHERE username = ?;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, username);

			//Execution et traitement de la réponse
			ps.executeUpdate();
			
			DatabaseConnection.getInstance().closeCnx();
		}
		catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}	
	
	public static Integer count(){
		int counter = 0;
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
		
			String sql = "SELECT COUNT(*) AS counter FROM users WHERE active = 1;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ResultSet res = ps.executeQuery();
			
			while(res.next()){
				counter = res.getInt("counter");
				break;
			}	
		}catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
		return counter;
	}
}