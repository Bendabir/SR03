package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import bdd.DatabaseConnection;
import beans.Game;
import beans.Order;
import beans.OrderLine;

public class Orders{
	public static Order get(String user){
		Order o = null;
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();

			// Requête
			String sql = "SELECT * FROM oders WHERE user = ?;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, user);
			
			//Execution et traitement de la réponse
			ResultSet res = ps.executeQuery();
			
			while(res.next()){
				// Get lines
				String sqlLines = "SELECT ol.unit_price, ol.quantity, g.title AS game, g.console FROM orders_lines ol INNER JOIN games g ON ol.game = g.id WHERE ol.order_num = ?;";
				PreparedStatement psLines = cnx.prepareStatement(sqlLines);
				psLines.setInt(1, res.getInt("num"));
				
				ArrayList<OrderLine> lines = new ArrayList<OrderLine>();
				
				ResultSet resLines = psLines.executeQuery();
				
				while(resLines.next()){
					lines.add(new OrderLine(new Game(resLines.getString("game"), resLines.getString("console")), resLines.getDouble("unit_price"), resLines.getInt("quantity")));
				}
				
				o = new Order(res.getInt("num"), res.getString("order_date"), res.getString("user"), lines);
				break;
			}
			
			res.close();
			DatabaseConnection.getInstance().closeCnx();					
		}
		catch(SQLException e){
			e.printStackTrace();		
		}
		
		return o;				
	}

	public static ArrayList<Order> all(){
		ArrayList<Order> lo = new ArrayList<Order>();
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			// Requête
			String sql = "SELECT * FROM orders;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			
			//Execution et traitement de la réponse
			ResultSet res = ps.executeQuery();
			
			while(res.next()){
				// Get lines
				String sqlLines = "SELECT ol.unit_price, ol.quantity, g.title AS game, g.console FROM orders_lines ol INNER JOIN games g ON ol.game = g.id WHERE ol.order_num = ?;";
				PreparedStatement psLines = cnx.prepareStatement(sqlLines);
				psLines.setInt(1, res.getInt("num"));
				
				ArrayList<OrderLine> lines = new ArrayList<OrderLine>();
				
				ResultSet resLines = psLines.executeQuery();
				
				while(resLines.next()){
					lines.add(new OrderLine(new Game(resLines.getString("game"), resLines.getString("console")), resLines.getDouble("unit_price"), resLines.getInt("quantity")));
				}
				
				lo.add(new Order(res.getInt("num"), res.getString("order_date"), res.getString("user"), lines));
			}
			
			res.close();
			DatabaseConnection.getInstance().closeCnx();			
		}
		catch(SQLException e){
			e.printStackTrace();			
		}			

		return lo;
	}	

	public static boolean add(Order order){
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			// Requête
			String sql = "INSERT INTO orders (date, user) VALUES (?, ?);";
			
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, order.getDate());
			ps.setString(2, order.getUser());
			
			//Execution et traitement de la réponse
			ps.executeUpdate();
			
			// Need a good way to retrieve added order (num)
			
			// Updating lines
			
			DatabaseConnection.getInstance().closeCnx();			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	// Orders cannot be updated
//	public static boolean update(Console console){
//		Connection cnx = null;
//		
//		try {
//			cnx = DatabaseConnection.getInstance().getCnx();
//			
//			// Requête
//			String sql = "UPDATE consoles SET name = ?, launched_date = ? WHERE name = ?";
//			
//			PreparedStatement ps = cnx.prepareStatement(sql);
//			ps.setString(1, console.getName());
//			ps.setString(2, console.getLaunchedDate());
//			ps.setString(3, console.getName());			
//			
//			//Execution et traitement de la réponse
//			ps.executeUpdate();
//			
//			DatabaseConnection.getInstance().closeCnx();			
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return false;
//		}
//
//		return true;
//	}	
	
	// Orders cannot be deleted
//	public static boolean delete(int name){
//		Connection cnx = null;
//		
//		try {
//			cnx = DatabaseConnection.getInstance().getCnx();	
//			
//			// Requête
//			String sql = "DELETE FROM consoles WHERE name = ?;";
//			PreparedStatement ps = cnx.prepareStatement(sql);
//			ps.setString(1, name);
//
//			//Execution et traitement de la réponse
//			ps.executeUpdate();
//			
//			DatabaseConnection.getInstance().closeCnx();
//		}
//		catch(SQLException e){
//			e.printStackTrace();
//			return false;
//		}
//		
//		return true;
//	}	
	
	public static int count(){
		int counter = 0;
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
		
			String sql = "SELECT COUNT(*) AS counter FROM orders;";
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