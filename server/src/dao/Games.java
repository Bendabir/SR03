package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import bdd.DatabaseConnection;
import beans.Game;

public class Games {
	public static Game get(int id){
		Game g = null;
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();

			// Get game types
			String sqlGenres = "SELECT genre FROM assoc_game_genres_games WHERE game = ?;";
			PreparedStatement psGenres = cnx.prepareStatement(sqlGenres);
			psGenres.setInt(1, id);
			
			ResultSet resGenres = psGenres.executeQuery();
			ArrayList<String> genres = new ArrayList<String>();
			
			while(resGenres.next()){
				genres.add(resGenres.getString("genre"));
			}
			
			// Requ�te
			String sql = "SELECT * FROM games WHERE id = ?;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setInt(1, id);
			
			//Execution et traitement de la r�ponse
			ResultSet res = ps.executeQuery();
			
			while(res.next()){
				g = new Game(res.getInt("id"), res.getString("title"), res.getString("console"), res.getDouble("price"), res.getString("release_date"), res.getInt("stock"), genres);
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
				// New request for types
				String sqlGenres = "SELECT genre FROM assoc_game_genres_games WHERE game = ?;";
				PreparedStatement psGenres = cnx.prepareStatement(sqlGenres);
				psGenres.setInt(1, res.getInt("id"));
				ResultSet resGenres = psGenres.executeQuery();
				
				ArrayList<String> genres = new ArrayList<String>();
				
				while(resGenres.next()){
					genres.add(resGenres.getString("genre"));
				}
				
				lg.add(new Game(res.getInt("id"), res.getString("title"), res.getString("console"), res.getDouble("price"), res.getString("release_date"), res.getInt("stock"), genres));
			}
			
			res.close();
			DatabaseConnection.getInstance().closeCnx();			
		}
		catch(SQLException e){
			e.printStackTrace();			
		}			

		return lg;
	}

	// Need to add game types handling
	public static Boolean add(Game game){
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			// Requ�te
			String sql = "INSERT INTO games (title, console, price, release_date, stock) VALUES (?, ?, ?, ?, ?);";
			
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, game.getTitle());
			ps.setString(2, game.getConsole());
			ps.setDouble(3, game.getPrice().doubleValue());
			ps.setString(4, game.getReleaseDate());
			ps.setInt(5, game.getStock().intValue());
			
			//Execution et traitement de la r�ponse
			ps.executeUpdate();
			
			// Getting ID
			sql = "SELECT id FROM games WHERE title = ? AND console = ? AND release_date = ?;";
			ps = cnx.prepareStatement(sql);
			ps.setString(1, game.getTitle());
			ps.setString(2, game.getConsole());
			ps.setString(3, game.getReleaseDate());
			
			ResultSet res = ps.executeQuery();
			res.next();
			
			// Add genres
			sql = "INSERT INTO assoc_game_genres_games (genre, game) VALUES (?, ?);";			
			
			for(Iterator<String> i = game.getGenres().iterator(); i.hasNext(); ){
			    String genre= i.next();
			    
				ps = cnx.prepareStatement(sql);
				
				ps.setString(1, genre);
				ps.setInt(2, res.getInt("id"));
				
				ps.executeUpdate();			    
			}
			
			DatabaseConnection.getInstance().closeCnx();			
		} catch (SQLException e) {
			e.printStackTrace();
			
			return false;
		}

		return true;
	}

	// Need to add game types handling
	public static Boolean update(Game game){
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			// Requ�te
			String sql = "UPDATE games SET title = ?, console = ?, price = ?, release_date = ?, stock = ? WHERE id = ?";
			
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, game.getTitle());
			ps.setString(2, game.getConsole());
			ps.setDouble(3, game.getPrice().doubleValue());
			ps.setString(4, game.getReleaseDate());
			ps.setInt(5, game.getStock().intValue());
			ps.setInt(6, game.getId().intValue());
			
			//Execution et traitement de la r�ponse
			ps.executeUpdate();
			
			// Delete genres
			sql = "DELETE FROM assoc_game_genres_games WHERE game = ?;";
			
			ps = cnx.prepareStatement(sql);
			ps.setInt(1, game.getId());
			ps.executeUpdate();				
			
			// Add new genres
			sql = "INSERT INTO assoc_game_genres_games (genre, game) VALUES (?, ?);";			
			
			for(Iterator<String> i = game.getGenres().iterator(); i.hasNext(); ) {
			    String genre= i.next();
			    
				ps = cnx.prepareStatement(sql);
				
				ps.setString(1, genre);
				ps.setInt(2, game.getId());
				
				ps.executeUpdate();			    
			}			
			
			DatabaseConnection.getInstance().closeCnx();			
		} catch (SQLException e) {
			e.printStackTrace();
			
			return false;
		}

		return true;
	}	
	
	public static Boolean delete(int id){
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();	
			
			// First delete genres assoc
			String sql = "DELETE FROM assoc_game_genres_games WHERE game = ?;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setInt(1, id);

			ps.executeUpdate();			
			
			// Requ�te
			sql = "DELETE FROM games WHERE id = ?;";
			ps = cnx.prepareStatement(sql);
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
	
	public static Integer count(){
	
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
