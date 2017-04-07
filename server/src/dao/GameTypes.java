package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bdd.DatabaseConnection;
import beans.GameType;

public class GameTypes {
	public static GameType get(String name){
		GameType t = null;
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();

			// Requête
			String sql = "SELECT * FROM game_types WHERE name = ?;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, name);
			
			//Execution et traitement de la réponse
			ResultSet res = ps.executeQuery();
			
			while(res.next()){
				t = new GameType(res.getString("name"));
				break;
			}
			
			res.close();
			DatabaseConnection.getInstance().closeCnx();					
		}
		catch(SQLException e){
			e.printStackTrace();		
		}
		
		return t;				
	}

	public static ArrayList<GameType> all(){
		ArrayList<GameType> lt = new ArrayList<GameType>();
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			// Requête
			String sql = "SELECT * FROM game_types;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			
			//Execution et traitement de la réponse
			ResultSet res = ps.executeQuery();
			
			while(res.next()){
				lt.add(new GameType(res.getString("name")));
			}
			
			res.close();
			DatabaseConnection.getInstance().closeCnx();			
		}
		catch(SQLException e){
			e.printStackTrace();			
		}			

		return lt;
	}	
	
	public static boolean add(GameType type){
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			// Requête
			String sql = "INSERT INTO game_types (name) VALUES (?);";
			
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, type.getName());
			
			//Execution et traitement de la réponse
			ps.executeUpdate();
			
			DatabaseConnection.getInstance().closeCnx();			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public static boolean update(GameType type){
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			// Requête
			String sql = "UPDATE game_types SET name = ? WHERE name = ?";
			
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, type.getName());
			ps.setString(2, type.getName());			
			
			//Execution et traitement de la réponse
			ps.executeUpdate();
			
			DatabaseConnection.getInstance().closeCnx();			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}	
	
	public static int count(){
		int counter = 0;
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
		
			String sql = "SELECT COUNT(*) AS counter FROM game_types;";
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