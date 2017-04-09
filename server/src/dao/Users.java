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
				u = new User(res.getString("username"), res.getString("firstname"), res.getString("lastname"), res.getString("birth_date"), res.getString("status"));
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
				lu.add(new User(res.getString("username"), res.getString("firstname"), res.getString("lastname"), res.getString("birth_date"), res.getString("status")));
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
			
			// Requête
			String sql = "INSERT INTO users (username, password, firstname, lastname, birth_date, status) VALUES(?, ?, ?, ?, ?, ?);";
			
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getFirstName());
			ps.setString(4, user.getLastName());
			ps.setString(5, user.getBirthDate());
			ps.setString(6, user.getStatus());
			
			//Execution et traitement de la réponse
			ps.executeUpdate();
			
			DatabaseConnection.getInstance().closeCnx();			
		} catch (SQLException e) {
			e.printStackTrace();
			
			return false;
		}

		return true;		
	}

	public static Boolean update(User user){
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			// Requête
			String sql = "UPDATE users SET username = ?, password = ?, firstname = ?, lastname = ?, birth_date = ?, status = ? WHERE username = ? AND active = 1;";
			
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getFirstName());
			ps.setString(4, user.getLastName());
			ps.setString(5, user.getBirthDate());
			ps.setString(6, user.getStatus());
			ps.setString(7, user.getUsername());			
			
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