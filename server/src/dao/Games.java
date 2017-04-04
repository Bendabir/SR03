package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bdd.DatabaseConnection;
import beans.Game;
import beans.User;
import beans.Utilisateur;

public class Games {
	public Game get(int id) throws SQLException {
		Game g = null;
		Connection cnx = null;
		
		cnx = DatabaseConnection.getInstance().getCnx();

		// Requête
		String sql = "SELECT * FROM games WHERE id = ?;";
		PreparedStatement ps = cnx.prepareStatement(sql);
		ps.setInt(1, id);
		
		//Execution et traitement de la réponse
		ResultSet res = ps.executeQuery();
		
		while(res.next()){
			g = new Game(res.getInt("id"), res.getString("title"), res.getString("console"), res.getFloat("price"), res.getString("releaseDate"), res.getInt("stock"));
			break;
		}
		
		res.close();
		DatabaseConnection.getInstance().closeCnx();			

		return g;
	}

	public ArrayList<Game> all() throws SQLException {

	}

	public boolean add(Game game){
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			// Requête
			String sql = "INSERT INTO games (title, console, price, release_date, stock) VALUES (?, ?, ?, ?, ?);";
			
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, game.getTitle());
			ps.setString(2, game.getConsole());
			ps.setFloat(3, game.getPrice());
			ps.setString(4, game.getReleaseDate());
			ps.setInt(5, game.getStock());
			
			//Execution et traitement de la réponse
			ps.executeUpdate();
			
			DatabaseConnection.getInstance().closeCnx();			
		} catch (SQLException e) {
			e.printStackTrace();
			
			return false;
		}

		return true;
	}

	public boolean update(Game game){
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			// Requête
			String sql = "UPDATE games SET title = ?, console = ?, price = ?, release_date = ?, stock = ? WHERE id = ?";
			
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, game.getTitle());
			ps.setString(2, game.getConsole());
			ps.setFloat(3, game.getPrice());
			ps.setString(4, game.getReleaseDate());
			ps.setInt(5, game.getStock());
			ps.setInt(6, game.getId());
			
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
		
			String sql = "SELECT COUNT(*) FROM games;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ResultSet res = ps.executeQuery();
			
			while(res.next()){
				counter = res.getInt("COUNT(*)");
				break;
			}	
		}catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
		return counter;
	}
}
