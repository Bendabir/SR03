package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bdd.DatabaseConnection;
import beans.GameGenre;

public class GameGenres {
	public static GameGenre get(String name){
		GameGenre t = null;
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();

			// Requête
			String sql = "SELECT * FROM game_genres WHERE name = ?;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, name);
			
			//Execution et traitement de la réponse
			ResultSet res = ps.executeQuery();
			
			res.first();

			t = new GameGenre(res.getString("name"));
			t.setDescription(res.getString("description"));
			
			res.close();
			ps.close();
			DatabaseConnection.getInstance().closeCnx();					
		}
		catch(SQLException e){
			e.printStackTrace();		
		}
		
		return t;				
	}

	public static ArrayList<GameGenre> all(){
		ArrayList<GameGenre> lt = new ArrayList<GameGenre>();
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			// Requête
			String sql = "SELECT * FROM game_genres;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			
			//Execution et traitement de la réponse
			ResultSet res = ps.executeQuery();
			
			while(res.next()){
				GameGenre t = new GameGenre(res.getString("name"));
				t.setDescription(res.getString("description"));
				
				lt.add(t);
			}
			
			res.close();
			ps.close();
			DatabaseConnection.getInstance().closeCnx();			
		}
		catch(SQLException e){
			e.printStackTrace();			
		}			

		return lt;
	}	
	
	public static GameGenre add(GameGenre type){
		Connection cnx = null;
		GameGenre gg = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			// Requête
			String sql = "INSERT INTO game_genres (name, description) VALUES (?, ?);";
			
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, type.getName());
			ps.setString(2, type.getDescription());
			
			//Execution et traitement de la réponse
			ps.executeUpdate();
			ps.close();
			DatabaseConnection.getInstance().closeCnx();
			
			gg = new GameGenre(); // Allow us to modify GameGenre in the future without modifying this part
			gg.setName(type.getName());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return gg;
	}

	public static GameGenre update(GameGenre type){
		Connection cnx = null;
		GameGenre gg = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			// Requête
			String sql = "UPDATE game_genres SET name = ?, description = ? WHERE name = ?";
			
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, type.getName());
			ps.setString(2, type.getDescription());
			ps.setString(3, type.getName());			
			
			//Execution et traitement de la réponse
			ps.executeUpdate();
			ps.close();
			DatabaseConnection.getInstance().closeCnx();
			
			gg = new GameGenre(); // Allow us to modify GameGenre in the future without modifying this part
			gg.setName(type.getName());			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return gg;
	}	
	
	public static Boolean delete(String name){
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();	
			
			// Requête
			String sql = "DELETE FROM game_genres WHERE name = ?;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, name);

			//Execution et traitement de la réponse
			ps.executeUpdate();
			ps.close();
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
		
			String sql = "SELECT COUNT(*) AS counter FROM game_genres;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ResultSet res = ps.executeQuery();
			
			res.first();
			
			counter = res.getInt("counter");
			
			res.close();
			ps.close();
			DatabaseConnection.getInstance().closeCnx();
		}catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
		return counter;
	}	
}