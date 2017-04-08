package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import bdd.DatabaseConnection;
import beans.Game;
import beans.GameGenre;

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
			ArrayList<GameGenre> genres = new ArrayList<GameGenre>();
			
			while(resGenres.next()){
				genres.add(new GameGenre(resGenres.getString("genre")));
			}
			
			// Requête
			String sql = "SELECT * FROM games WHERE id = ?;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setInt(1, id);
			
			//Execution et traitement de la réponse
			ResultSet res = ps.executeQuery();
			
			while(res.next()){
				g = new Game(res.getInt("id"), res.getString("title"), res.getString("console"), res.getFloat("price"), res.getString("release_date"), res.getInt("stock"), genres);
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
				
				ArrayList<GameGenre> genres = new ArrayList<GameGenre>();
				
				while(resGenres.next()){
					genres.add(new GameGenre(resGenres.getString("genre")));
				}
				
				lg.add(new Game(res.getInt("id"), res.getString("title"), res.getString("console"), res.getFloat("price"), res.getString("release_date"), res.getInt("stock"), genres));
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
	public static boolean add(Game game){
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			// Requête
			String sql = "INSERT INTO games (title, console, price, release_date, stock) VALUES (?, ?, ?, ?, ?);";
			
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, game.getTitle());
			ps.setString(2, game.getConsole());
			ps.setDouble(3, game.getPrice().doubleValue());
			ps.setString(4, game.getReleaseDate());
			ps.setInt(5, game.getStock().intValue());
			
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
			
			// Add genres
			sql = "INSERT INTO assoc_game_genres_games (genre, game) VALUES (?, ?);";			
			
			for(Iterator<GameGenre> i = game.getGenres().iterator(); i.hasNext(); ) {
			    GameGenre genre= i.next();
			    
				ps = cnx.prepareStatement(sql);
				
				ps.setString(1, genre.getName());
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
	public static boolean update(Game game){
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			// Requête
			String sql = "UPDATE games SET title = ?, console = ?, price = ?, release_date = ?, stock = ? WHERE id = ?";
			
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, game.getTitle());
			ps.setString(2, game.getConsole());
			ps.setDouble(3, game.getPrice().doubleValue());
			ps.setString(4, game.getReleaseDate());
			ps.setInt(5, game.getStock().intValue());
			ps.setInt(6, game.getId().intValue());
			
			//Execution et traitement de la réponse
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
			
			// Requête
			String sql = "DELETE FROM games WHERE id = ?;";
			PreparedStatement ps = cnx.prepareStatement(sql);
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
