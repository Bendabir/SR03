package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bdd.DatabaseConnection;
import beans.Game;

public class Games {
	public static Game get(int id){
		Game g = null;
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();

			// Requ�te
			String sql = "SELECT * FROM games WHERE id = ?;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setInt(1, id);
			
			//Execution et traitement de la r�ponse
			ResultSet res = ps.executeQuery();
			
			while(res.next()){
				g = new Game(res.getInt("id"), res.getString("title"), res.getString("console"), res.getFloat("price"), res.getString("release_date"), res.getInt("stock"));
				break;
			}
			
			res.close();
			DatabaseConnection.getInstance().closeCnx();					
		}
		catch(SQLException e){
			e.printStackTrace();		
		}
		
		return g;			
	}

	public static ArrayList<Game> all(){
		ArrayList<Game> lg = new ArrayList<Game>();
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			// Requ�te
			String sql = "SELECT * FROM games;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			
			//Execution et traitement de la r�ponse
			ResultSet res = ps.executeQuery();
			
			while(res.next()){
				lg.add(new Game(res.getInt("id"), res.getString("title"), res.getString("console"), res.getFloat("price"), res.getString("release_date"), res.getInt("stock")));
			}
			
			res.close();
			DatabaseConnection.getInstance().closeCnx();			
		}
		catch(SQLException e){
			e.printStackTrace();			
		}			

		return lg;
	}

	public static boolean add(Game game){
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			// Requ�te
			String sql = "INSERT INTO games (title, console, price, release_date, stock) VALUES (?, ?, ?, ?, ?);";
			
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, game.getTitle());
			ps.setString(2, game.getConsole());
			ps.setDouble(3, game.getPrice());
			ps.setString(4, game.getReleaseDate());
			ps.setInt(5, game.getStock());
			
			//Execution et traitement de la r�ponse
			ps.executeUpdate();
			
			DatabaseConnection.getInstance().closeCnx();			
		} catch (SQLException e) {
			e.printStackTrace();
			
			return false;
		}

		return true;
	}

	public static boolean update(Game game){
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			// Requ�te
			String sql = "UPDATE games SET title = ?, console = ?, price = ?, release_date = ?, stock = ? WHERE id = ?";
			
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, game.getTitle());
			ps.setString(2, game.getConsole());
			ps.setDouble(3, game.getPrice());
			ps.setString(4, game.getReleaseDate());
			ps.setInt(5, game.getStock());
			ps.setInt(6, game.getId());
			
			//Execution et traitement de la r�ponse
			ps.executeUpdate();
			
			DatabaseConnection.getInstance().closeCnx();			
		} catch (SQLException e) {
			e.printStackTrace();
			
			return false;
		}

		return true;
	}	
	
	public static boolean delete(int id){
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();	
			
			// Requ�te
			String sql = "DELETE FROM games WHERE id = ?;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setInt(1, id);

			//Execution et traitement de la r�ponse
			ps.executeUpdate();
			
			DatabaseConnection.getInstance().closeCnx();
		}
		catch(SQLException e){
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
		
			String sql = "SELECT COUNT(*) AS counter FROM games;";
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
