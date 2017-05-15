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
			
			// Requête
			String sql = "SELECT * FROM games WHERE id = ?;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setInt(1, id);
			
			//Execution et traitement de la réponse
			ResultSet res = ps.executeQuery();
			
			while(res.next()){
				g = new Game(res.getInt("id"), res.getString("title"), res.getString("console"), res.getDouble("price"), res.getString("release_date"), res.getInt("stock"), genres);
				g.setPublisher(res.getString("publisher")).setDescription(res.getString("description"));
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
			
			// Requête
			String sql = "SELECT * FROM games;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			
			//Execution et traitement de la réponse
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
				
				Game g = new Game(res.getInt("id"), res.getString("title"), res.getString("console"), res.getDouble("price"), res.getString("release_date"), res.getInt("stock"), genres);
				g.setPublisher(res.getString("publisher")).setDescription(res.getString("description"));
				
				lg.add(g);
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
	public static Game add(Game game){
		Connection cnx = null;
		Game g = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			// First inserting the publisher if not already in database
			// Could use a regex to improve the test
			String publisherSql = "SELECT * FROM publisher WHERE LOWER(name) = LOWER(?);";
			PreparedStatement publisherPs = cnx.prepareStatement(publisherSql);
			publisherPs.setString(1, game.getPublisher());
			ResultSet publisherRes = publisherPs.executeQuery();
			
			// If we have a result, we update the publisher with the good name (could be a bit different)
			if(publisherRes.next()){
				game.setPublisher(publisherRes.getString("name"));
			}
			// Otherwise, let's add it in the db
			else {
				String addPublisherSql = "INSERT INTO publisher (name) VALUES (?);";
				PreparedStatement addPublisherPs = cnx.prepareStatement(addPublisherSql);
				addPublisherPs.setString(1, game.getPublisher());
				addPublisherPs.executeQuery();
			}
			
			// Requête
			String sql = "INSERT INTO games (title, console, price, publisher, release_date, description, stock) VALUES (?, ?, ?, ?, ?, ?, ?);";
			
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, game.getTitle());
			ps.setString(2, game.getConsole());
			ps.setDouble(3, game.getPrice().doubleValue());
			ps.setString(4, game.getPublisher());
			ps.setString(5, game.getReleaseDate());
			ps.setString(6, game.getDescription());
			ps.setInt(7, game.getStock().intValue());
			
			//Execution et traitement de la réponse
			ps.executeUpdate();
			
			// Getting ID
			sql = "SELECT id FROM games WHERE title = ? AND console = ? AND release_date = ?;";
			ps = cnx.prepareStatement(sql);
			ps.setString(1, game.getTitle());
			ps.setString(2, game.getConsole());
			ps.setString(3, game.getReleaseDate());
			
			ResultSet res = ps.executeQuery();
			res.next();
			
			Integer gameID = res.getInt("id");
			
			// Add genres
			sql = "INSERT INTO assoc_game_genres_games (genre, game) VALUES (?, ?);";			
			
			for(Iterator<String> i = game.getGenres().iterator(); i.hasNext(); ){
			    String genre= i.next();
			    
				ps = cnx.prepareStatement(sql);
				
				ps.setString(1, genre);
				ps.setInt(2, gameID);
				
				ps.executeUpdate();			    
			}
			
			DatabaseConnection.getInstance().closeCnx();
			
			g = new Game();
			g.setId(gameID);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return g;
	}

	// Need to add game types handling
	public static Game update(Game game){
		Connection cnx = null;
		Game g = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			// First inserting the publisher if not already in database
			// Could use a regex to improve the test
			String publisherSql = "SELECT * FROM publisher WHERE LOWER(name) = LOWER(?);";
			PreparedStatement publisherPs = cnx.prepareStatement(publisherSql);
			publisherPs.setString(1, game.getPublisher());
			ResultSet publisherRes = publisherPs.executeQuery();
			
			// If we have a result, we update the publisher with the good name (could be a bit different)
			if(publisherRes.next()){
				game.setPublisher(publisherRes.getString("name"));
			}
			// Otherwise, let's add it in the db
			else {
				String addPublisherSql = "INSERT INTO publisher (name) VALUES (?);";
				PreparedStatement addPublisherPs = cnx.prepareStatement(addPublisherSql);
				addPublisherPs.setString(1, game.getPublisher());
				addPublisherPs.executeQuery();
			}			
			
			// Requête
			String sql = "UPDATE games SET title = ?, console = ?, price = ?, release_date = ?, stock = ?, publisher = ?, description = ? WHERE id = ?";
			
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, game.getTitle());
			ps.setString(2, game.getConsole());
			ps.setDouble(3, game.getPrice().doubleValue());
			ps.setString(4, game.getReleaseDate());
			ps.setInt(5, game.getStock().intValue());
			ps.setString(6, game.getPublisher());
			ps.setString(7, game.getDescription());
			ps.setInt(8, game.getId().intValue());
			
			//Execution et traitement de la réponse
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
			
			g = new Game();
			g.setId(game.getId());			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return g;
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
			
			// Requête
			sql = "DELETE FROM games WHERE id = ?;";
			ps = cnx.prepareStatement(sql);
			ps.setInt(1, id);

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
