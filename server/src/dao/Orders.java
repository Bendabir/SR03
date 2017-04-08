package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import bdd.DatabaseConnection;
import beans.Game;
import beans.Order;
import beans.OrderLine;

public class Orders{
	public static ArrayList<Order> get(String user){
		ArrayList<Order> lo = new ArrayList<Order>();
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();

			// Requête
			String sql = "SELECT * FROM orders WHERE user = ?;";
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
				
				lo.add(new Order(res.getInt("num"), res.getString("order_date"), res.getString("user"), lines));
				break;
			}
			
			res.close();
			DatabaseConnection.getInstance().closeCnx();					
		}
		catch(SQLException e){
			e.printStackTrace();		
		}
		
		return lo;				
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

	public static Boolean add(Order order){
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			// Requête
			String sql = "INSERT INTO orders (order_date, user) VALUES (CURDATE(), ?);";
			
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, order.getUser());
			
			//Execution et traitement de la réponse
			ps.executeUpdate();
			
			// Need a good way to retrieve added order (num)
			sql = "SELECT MAX(num) AS order_num FROM orders;";
			
			ps = cnx.prepareStatement(sql);
			ResultSet res = ps.executeQuery();
			res.next();
			
			// Updating lines
			sql = "INSERT INTO orders_lines (order_num, game, unit_price, quantity) VALUES (?, ?, 0, ?);";
			
			for(Iterator<OrderLine> i = order.getLines().iterator(); i.hasNext(); ){
				OrderLine line = i.next();
				
				ps = cnx.prepareStatement(sql);
				ps.setInt(1, res.getInt("order_num"));
				ps.setInt(2, line.getGame().getId());
				ps.setInt(3, line.getQuantity());
				
				ps.executeUpdate();
				
				// Save price
				String savePriceSql = "UPDATE orders_lines SET unit_price = (SELECT price FROM games WHERE id = ?) WHERE order_num = ? AND game = ?;";
				PreparedStatement savePricePs = cnx.prepareStatement(savePriceSql);
				savePricePs.setInt(1, line.getGame().getId());				
				savePricePs.setInt(2, res.getInt("order_num"));
				savePricePs.setInt(3, line.getGame().getId());
				
				savePricePs.executeUpdate();
			}
			
			DatabaseConnection.getInstance().closeCnx();			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	// Orders cannot be updated
	
	// Orders cannot be deleted
	
	public static Integer count(){
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