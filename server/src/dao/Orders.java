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
	public static Order get(int orderNum){
		Order o = null;
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			String sql = "SELECT * FROM orders WHERE num = ? ORDER BY num DESC;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setInt(1, orderNum);
			
			ResultSet res = ps.executeQuery();
			
			// What about res.first() ?
			while(res.next()){
				// Get lines
				String sqlLines = "SELECT ol.unit_price, ol.quantity, g.title AS game, g.console, g.description, g.release_date, g.publisher, g.cover FROM orders_lines ol INNER JOIN games g ON ol.game = g.id WHERE ol.order_num = ?;";
				PreparedStatement psLines = cnx.prepareStatement(sqlLines);
				psLines.setInt(1, res.getInt("num"));
				
				ArrayList<OrderLine> lines = new ArrayList<OrderLine>();
				
				ResultSet resLines = psLines.executeQuery();
				
				while(resLines.next()){
					Game g = new Game();
					g.setTitle(resLines.getString("game"))
					 .setConsole(resLines.getString("console"))
					 .setDescription(resLines.getString("description"))
					 .setReleaseDate(resLines.getString("release_date"))
					 .setCover(resLines.getString("cover"))
					 .setPublisher(resLines.getString("publisher"));
					
					lines.add(new OrderLine(g, resLines.getDouble("unit_price"), resLines.getInt("quantity")));
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
	
	public static Order get(String user, int orderNum){
		Order o = null;
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			String sql = "SELECT * FROM orders WHERE user = ? AND num = ? ORDER BY num DESC;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, user);
			ps.setInt(2, orderNum);
			
			ResultSet res = ps.executeQuery();
			
			// What about res.first() ?
			while(res.next()){
				// Get lines
				String sqlLines = "SELECT ol.unit_price, ol.quantity, g.title AS game, g.console, g.description, g.publisher, g.release_date, g.cover FROM orders_lines ol INNER JOIN games g ON ol.game = g.id WHERE ol.order_num = ?;";
				PreparedStatement psLines = cnx.prepareStatement(sqlLines);
				psLines.setInt(1, res.getInt("num"));
				
				ArrayList<OrderLine> lines = new ArrayList<OrderLine>();
				
				ResultSet resLines = psLines.executeQuery();
				
				while(resLines.next()){
					Game g = new Game();
					g.setTitle(resLines.getString("game"))
					 .setConsole(resLines.getString("console"))
					 .setDescription(resLines.getString("description"))
					 .setReleaseDate(resLines.getString("release_date"))
					 .setCover(resLines.getString("cover"))
					 .setPublisher(resLines.getString("publisher"));
					
					lines.add(new OrderLine(g, resLines.getDouble("unit_price"), resLines.getInt("quantity")));
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
	
	public static ArrayList<Order> get(String user){
		ArrayList<Order> lo = new ArrayList<Order>();
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();

			// Jointure � la place, pour �viter les sous-requ�tes d�gueux ?
			
			// Requ�te
			String sql = "SELECT * FROM orders WHERE user = ? ORDER BY num DESC;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, user);
			
			//Execution et traitement de la r�ponse
			ResultSet res = ps.executeQuery();
			
			while(res.next()){
				// Get lines
				String sqlLines = "SELECT ol.unit_price, ol.quantity, g.title AS game, g.console, g.description, g.publisher, g.cover, g.release_date FROM orders_lines ol INNER JOIN games g ON ol.game = g.id WHERE ol.order_num = ?;";
				PreparedStatement psLines = cnx.prepareStatement(sqlLines);
				psLines.setInt(1, res.getInt("num")); // Sometimes a 500 error is thrown here...
				
				ArrayList<OrderLine> lines = new ArrayList<OrderLine>();
				
				ResultSet resLines = psLines.executeQuery();
				
				while(resLines.next()){
					Game g = new Game();
					g.setTitle(resLines.getString("game"))
					 .setConsole(resLines.getString("console"))
					 .setDescription(resLines.getString("description"))
					 .setReleaseDate(resLines.getString("release_date"))
					 .setCover(resLines.getString("cover"))
					 .setPublisher(resLines.getString("publisher"));
					
					lines.add(new OrderLine(g, resLines.getDouble("unit_price"), resLines.getInt("quantity")));
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

	public static ArrayList<Order> all(){
		ArrayList<Order> lo = new ArrayList<Order>();
		Connection cnx = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			// Requ�te
			String sql = "SELECT * FROM orders ORDER BY num DESC;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			
			//Execution et traitement de la r�ponse
			ResultSet res = ps.executeQuery();
			
			while(res.next()){
				// Get lines
				String sqlLines = "SELECT ol.unit_price, ol.quantity, g.title AS game, g.console, g.description, g.publisher, g.cover, g.release_date FROM orders_lines ol INNER JOIN games g ON ol.game = g.id WHERE ol.order_num = ?;";
				PreparedStatement psLines = cnx.prepareStatement(sqlLines);
				psLines.setInt(1, res.getInt("num"));
				
				ArrayList<OrderLine> lines = new ArrayList<OrderLine>();
				
				ResultSet resLines = psLines.executeQuery();
				
				while(resLines.next()){
					Game g = new Game();
					g.setTitle(resLines.getString("game"))
					 .setConsole(resLines.getString("console"))
					 .setDescription(resLines.getString("description"))
					 .setReleaseDate(resLines.getString("release_date"))
					 .setCover(resLines.getString("cover"))
					 .setPublisher(resLines.getString("publisher"));
					
					lines.add(new OrderLine(g, resLines.getDouble("unit_price"), resLines.getInt("quantity")));
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

	public static Order add(Order order){
		Connection cnx = null;
		Order o = null;
		
		try {
			cnx = DatabaseConnection.getInstance().getCnx();
			
			// Requ�te
			String sql = "INSERT INTO orders (order_date, user) VALUES (CURDATE(), ?);";
			
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, order.getUser());
			
			//Execution et traitement de la r�ponse
			ps.executeUpdate();
			
			// Need a good way to retrieve added order (num)
			sql = "SELECT MAX(num) AS order_num FROM orders;";
			
			ps = cnx.prepareStatement(sql);
			ResultSet res = ps.executeQuery();
			res.next();
			
			Integer orderID = res.getInt("order_num");
			
			// Updating lines
			sql = "INSERT INTO orders_lines (order_num, game, unit_price, quantity) VALUES (?, ?, 0, ?);";
			
			for(Iterator<OrderLine> i = order.getLines().iterator(); i.hasNext(); ){
				OrderLine line = i.next();
				
				ps = cnx.prepareStatement(sql);
				ps.setInt(1, orderID);
				ps.setInt(2, line.getGame().getId());
				ps.setInt(3, line.getQuantity());
				
				ps.executeUpdate();
				
				// Save price
				String savePriceSql = "UPDATE orders_lines SET unit_price = (SELECT price FROM games WHERE id = ?) WHERE order_num = ? AND game = ?;";
				PreparedStatement savePricePs = cnx.prepareStatement(savePriceSql);
				savePricePs.setInt(1, line.getGame().getId());				
				savePricePs.setInt(2, orderID);
				savePricePs.setInt(3, line.getGame().getId());
				
				savePricePs.executeUpdate();
				
				// Update stock
				String stockUpdateSql = "UPDATE games SET stock = stock - ? WHERE id = ?;";
				PreparedStatement stockUpdatePs = cnx.prepareStatement(stockUpdateSql);
				stockUpdatePs.setInt(1, line.getQuantity());
				stockUpdatePs.setInt(2, line.getGame().getId());
				
				stockUpdatePs.executeUpdate();
			}
			
			DatabaseConnection.getInstance().closeCnx();
			
			// For response
			o = new Order();
			o.setNum(orderID);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return o;
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
			
			res.first();
			
			counter = res.getInt("counter");
			
			res.close();
			ps.close();
			DatabaseConnection.getInstance().closeCnx();
		}catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
		return counter;
	}	
}