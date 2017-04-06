package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bdd.DatabaseConnection;
import beans.Console;

public class Consoles{
	public static Console get(String name){
		Console c = null;
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();

			// Requête
			String sql = "SELECT * FROM consoles WHERE name = ?;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, name);
			
			//Execution et traitement de la réponse
			ResultSet res = ps.executeQuery();
			
			while(res.next()){
				c = new Console(res.getString("name"), res.getString("launched_date"));
				break;
			}
			
			res.close();
			DatabaseConnection.getInstance().closeCnx();					
		}
		catch(SQLException e){
			e.printStackTrace();		
		}
		
		return c;				
	}

	public static ArrayList<Console> all(){
		ArrayList<Console> lc = new ArrayList<Console>();
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			// Requête
			String sql = "SELECT * FROM consoles;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			
			//Execution et traitement de la réponse
			ResultSet res = ps.executeQuery();
			
			while(res.next()){
				lc.add(new Console(res.getString("name"), res.getString("launched_date")));
			}
			
			res.close();
			DatabaseConnection.getInstance().closeCnx();			
		}
		catch(SQLException e){
			e.printStackTrace();			
		}			

		return lc;
	}	
	
	public static boolean add(Console console){
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			// Requête
			String sql = "INSERT INTO consoles (name, launched_date) VALUES (?, ?);";
			
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, console.getName());
			ps.setString(2, console.getLaunchedDate());
			
			//Execution et traitement de la réponse
			ps.executeUpdate();
			
			DatabaseConnection.getInstance().closeCnx();			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public static boolean update(Console console){
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			// Requête
			String sql = "UPDATE consoles SET name = ?, launched_date = ? WHERE name = ?";
			
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, console.getName());
			ps.setString(2, console.getLaunchedDate());
			ps.setString(3, console.getName());			
			
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
		
			String sql = "SELECT COUNT(*) FROM consoles;";
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