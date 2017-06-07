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
			
			// Get information
			String sql = "SELECT * FROM games g INNER JOIN assoc_game_genres_games a ON g.id = a.game WHERE g.id = ? AND g.deleted = 0;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet res = ps.executeQuery();
			
			if(res.next()){				
				ArrayList<String> genres = new ArrayList<String>();
				g = new Game(res.getInt("id"), res.getString("title"), res.getString("console"), res.getDouble("price"), res.getString("release_date"), res.getInt("stock"), null);
				g.setPublisher(res.getString("publisher")).setDescription(res.getString("description")).setCover(res.getString("cover"));
				genres.add(res.getString("genre"));
				
				// Going through results
				while(res.next()){
					genres.add(res.getString("genre"));
				}
				
				g.setGenres(genres);				
			}
			
			res.close();
//			DatabaseConnection.getInstance().closeCnx();					
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
			
			// Getting games with genres
			String sql = "SELECT * FROM games g LEFT JOIN assoc_game_genres_games a ON g.id = a.game WHERE g.deleted = 0 ORDER BY g.id;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ResultSet res = ps.executeQuery();
			
			// Filling objects
			ArrayList<String> genres = null;
			Game g = null;
			int currentGame = -1;			
			
			while(res.next()){
				// When switching game
				if(currentGame != res.getInt("id")){
					// Saving previous game genres
					if(currentGame != -1){
						g.setGenres(genres);
						lg.add(g);
					}
					
					// Going to next game
					currentGame = res.getInt("id");
					
					genres = new ArrayList<String>();
					g = new Game();
					
					g.setId(currentGame)
					 .setTitle(res.getString("title"))
					 .setConsole(res.getString("console"))
					 .setPrice(res.getDouble("price"))
					 .setReleaseDate(res.getString("release_date"))
					 .setStock(res.getInt("stock"))
					 .setGenres(null)
					 .setPublisher(res.getString("publisher"))
					 .setDescription(res.getString("description"))
					 .setCover(res.getString("cover"));
				}

				// Filling genres
				if(res.getString("genre") != null){
					genres.add(res.getString("genre"));
				}
			}
			
			// Adding last game
			if(g != null){
				g.setGenres(genres);
				lg.add(g);
			}
			
			res.close();
//			DatabaseConnection.getInstance().closeCnx();			
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
			if(game.getPublisher() != null){
				String publisherSql = "SELECT * FROM publishers WHERE LOWER(name) = LOWER(?);";
				PreparedStatement publisherPs = cnx.prepareStatement(publisherSql);
				publisherPs.setString(1, game.getPublisher());
				ResultSet publisherRes = publisherPs.executeQuery();
				
				// If we have a result, we update the publisher with the good name (could be a bit different)
				if(publisherRes.next()){
					game.setPublisher(publisherRes.getString("name"));
					
					publisherRes.close();
					publisherPs.close();
				}
				// Otherwise, let's add it in the db
				else {
					publisherRes.close();
					publisherPs.close();
					
					String addPublisherSql = "INSERT INTO publishers (name) VALUES (?);";
					PreparedStatement addPublisherPs = cnx.prepareStatement(addPublisherSql);
					addPublisherPs.setString(1, game.getPublisher());
					addPublisherPs.executeQuery();
					
					addPublisherPs.close();
				}			
			}
			
			// Requête
			String sql = "INSERT INTO games (title, console,odl"
					+ " price, publisher, release_date, description, stock, cover) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
			
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, game.getTitle());
			ps.setString(2, game.getConsole());
			ps.setDouble(3, game.getPrice().doubleValue());
			ps.setString(4, game.getPublisher());
			ps.setString(5, game.getReleaseDate());
			ps.setString(6, game.getDescription());
			ps.setInt(7, game.getStock().intValue());
			ps.setString(8, game.getCover());
			
			//Execution et traitement de la réponse
			ps.executeUpdate();
			ps.close();
			
			// Getting ID
			sql = "SELECT id FROM games WHERE title = ? AND console = ? AND release_date = ?;";
			ps = cnx.prepareStatement(sql);
			ps.setString(1, game.getTitle());
			ps.setString(2, game.getConsole());
			ps.setString(3, game.getReleaseDate());
			
			ResultSet res = ps.executeQuery();
			res.next();
			
			Integer gameID = res.getInt("id");
			
			res.close();
			ps.close();
			
			// Add genres
			sql = "INSERT INTO assoc_game_genres_games (genre, game) VALUES (?, ?);";			
			
			for(Iterator<String> i = game.getGenres().iterator(); i.hasNext(); ){
			    String genre= i.next();
			    
				ps = cnx.prepareStatement(sql);
				
				ps.setString(1, genre);
				ps.setInt(2, gameID);
				
				ps.executeUpdate();
				ps.close();
			}
			
//			DatabaseConnection.getInstance().closeCnx();
			
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
			if(game.getPublisher() != null){
				String publisherSql = "SELECT * FROM publishers WHERE LOWER(name) = LOWER(?);";
				PreparedStatement publisherPs = cnx.prepareStatement(publisherSql);
				publisherPs.setString(1, game.getPublisher());
				ResultSet publisherRes = publisherPs.executeQuery();
				
				// If we have a result, we update the publisher with the good name (could be a bit different)
				if(publisherRes.next()){
					game.setPublisher(publisherRes.getString("name"));
					
					publisherRes.close();
					publisherPs.close();
				}
				// Otherwise, let's add it in the db
				else {
					publisherRes.close();
					publisherPs.close();
					
					String addPublisherSql = "INSERT INTO publishers (name) VALUES (?);";
					PreparedStatement addPublisherPs = cnx.prepareStatement(addPublisherSql);
					addPublisherPs.setString(1, game.getPublisher());
					addPublisherPs.executeQuery();
					
					addPublisherPs.close();
				}				
			}
			
			// Requête
			String sql = "UPDATE games SET title = ?, console = ?, price = ?, release_date = ?, stock = ?, publisher = ?, description = ?, cover = ? WHERE id = ?";
			
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, game.getTitle());
			ps.setString(2, game.getConsole());
			ps.setDouble(3, game.getPrice().doubleValue());
			ps.setString(4, game.getReleaseDate());
			ps.setInt(5, game.getStock().intValue());
			ps.setString(6, game.getPublisher());
			ps.setString(7, game.getDescription());
			ps.setString(8, game.getCover());
			ps.setInt(9, game.getId().intValue());
			
			//Execution et traitement de la réponse
			ps.executeUpdate();
			
			ps.close();
			
			// Delete genres
			sql = "DELETE FROM assoc_game_genres_games WHERE game = ?;";
			
			ps = cnx.prepareStatement(sql);
			ps.setInt(1, game.getId());
			ps.executeUpdate();
			
			ps.close();
			
			// Add new genres
			sql = "INSERT INTO assoc_game_genres_games (genre, game) VALUES (?, ?);";			
			
			for(Iterator<String> i = game.getGenres().iterator(); i.hasNext(); ) {
			    String genre= i.next();
			    
				ps = cnx.prepareStatement(sql);
				
				ps.setString(1, genre);
				ps.setInt(2, game.getId());
				
				ps.executeUpdate();
				ps.close();
			}			
			
//			DatabaseConnection.getInstance().closeCnx();	
			
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

//			ps.executeUpdate();
			ps.close();
			
			// Requête
//			sql = "DELETE FROM games WHERE id = ?;";
			sql = "UPDATE games SET deleted = 1 WHERE id = ?;";
			ps = cnx.prepareStatement(sql);
			ps.setInt(1, id);

			//Execution et traitement de la réponse
			ps.executeUpdate();
			ps.close();
			
//			DatabaseConnection.getInstance().closeCnx();
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
		
			String sql = "SELECT COUNT(*) AS counter FROM games WHERE deleted = 0;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ResultSet res = ps.executeQuery();
			
			res.first();
			
			counter = res.getInt("counter");
			
			res.close();
			ps.close();
//			DatabaseConnection.getInstance().closeCnx();
		}catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
		return counter;
	}
}
