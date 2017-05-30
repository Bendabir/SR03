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
			
			// Getting order information
			String sql = "SELECT * FROM orders o INNER JOIN orders_lines l ON o.num = l.order_num INNER JOIN games g ON g.id = l.game WHERE o.num = ? ORDER BY o.num DESC;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setInt(1, orderNum);
			
			ResultSet res = ps.executeQuery();
			
			// First order line
			if(res.next()){
				ArrayList<OrderLine> lines = new ArrayList<OrderLine>();
				o = new Order();
				o.setNum(orderNum)
				 .setUser(res.getString("user"))
				 .setLines(null)
				 .setDate(res.getString("order_date"));
				
				do {
					Game g = new Game();
					g.setId(res.getInt("game"))
					 .setTitle(res.getString("title"))
					 .setConsole(res.getString("console"))
					 .setDescription(res.getString("description"))
					 .setReleaseDate(res.getString("release_date"))
					 .setCover(res.getString("cover"))
					 .setPublisher(res.getString("publisher"));				
					
					lines.add(new OrderLine(g, res.getDouble("unit_price"), res.getInt("quantity")));					
				} while(res.next());
				
				o.setLines(lines);
			}
			
			res.close();
			ps.close();
			
//			DatabaseConnection.getInstance().closeCnx();			
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
			
			// Getting order information
			String sql = "SELECT * FROM orders o INNER JOIN orders_lines l ON o.num = l.order_num INNER JOIN games g ON g.id = l.game WHERE o.num = ? AND o.user = ? ORDER BY o.num DESC;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setInt(1, orderNum);
			ps.setString(2, user);
			
			ResultSet res = ps.executeQuery();
			
			// First order line
			if(res.next()){
				ArrayList<OrderLine> lines = new ArrayList<OrderLine>();
				o = new Order();
				o.setNum(orderNum)
				 .setUser(user)
				 .setLines(null)
				 .setDate(res.getString("order_date"));
				
				do {
					Game g = new Game();
					g.setId(res.getInt("game"))
					 .setTitle(res.getString("title"))
					 .setConsole(res.getString("console"))
					 .setDescription(res.getString("description"))
					 .setReleaseDate(res.getString("release_date"))
					 .setCover(res.getString("cover"))
					 .setPublisher(res.getString("publisher"));				
					
					lines.add(new OrderLine(g, res.getDouble("unit_price"), res.getInt("quantity")));					
				} while(res.next());
				
				o.setLines(lines);
			}
			
			res.close();
			ps.close();			
			
//			DatabaseConnection.getInstance().closeCnx();			
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

			// Getting order information
			String sql = "SELECT * FROM orders o INNER JOIN orders_lines l ON o.num = l.order_num INNER JOIN games g ON g.id = l.game WHERE o.user = ? ORDER BY o.num DESC;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, user);
			
			ResultSet res = ps.executeQuery();
			
			ArrayList<OrderLine> lines = null;
			Order o = null;
			int currentOrder = -1;
			
			while(res.next()){
				// When switching order
				if(currentOrder != res.getInt("num")){
					// Saving previous lines
					if(currentOrder != -1){
						o.setLines(lines);
						lo.add(o);
					}
					
					// Going to next game
					currentOrder = res.getInt("num");
					
					lines = new ArrayList<OrderLine>();
					o = new Order();
					
					o.setNum(currentOrder)
					 .setUser(user)
					 .setLines(null)
					 .setDate(res.getString("order_date"));					
				}

				// Filling lines
				Game g = new Game();
				g.setId(res.getInt("game"))
				 .setTitle(res.getString("title"))
				 .setConsole(res.getString("console"))
				 .setDescription(res.getString("description"))
				 .setReleaseDate(res.getString("release_date"))
				 .setCover(res.getString("cover"))
				 .setPublisher(res.getString("publisher"));				
				
				lines.add(new OrderLine(g, res.getDouble("unit_price"), res.getInt("quantity")));				
			}
			
			// Adding last game
			if(o != null){
				o.setLines(lines);
				lo.add(o);					
			}
			
			res.close();
			ps.close();	
//			DatabaseConnection.getInstance().closeCnx();
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

			// Getting order information
			String sql = "SELECT * FROM orders o INNER JOIN orders_lines l ON o.num = l.order_num INNER JOIN games g ON g.id = l.game ORDER BY o.num DESC;";
			PreparedStatement ps = cnx.prepareStatement(sql);
			
			ResultSet res = ps.executeQuery();
			
			ArrayList<OrderLine> lines = null;
			Order o = null;
			int currentOrder = -1;
			
			while(res.next()){
				// When switching order
				if(currentOrder != res.getInt("num")){
					// Saving previous lines
					if(currentOrder != -1){
						o.setLines(lines);
						lo.add(o);
					}
					
					// Going to next game
					currentOrder = res.getInt("num");
					
					lines = new ArrayList<OrderLine>();
					o = new Order();
					
					o.setNum(currentOrder)
					 .setUser(res.getString("user"))
					 .setLines(null)
					 .setDate(res.getString("order_date"));					
				}

				// Filling lines
				Game g = new Game();
				g.setId(res.getInt("game"))
				 .setTitle(res.getString("title"))
				 .setConsole(res.getString("console"))
				 .setDescription(res.getString("description"))
				 .setReleaseDate(res.getString("release_date"))
				 .setCover(res.getString("cover"))
				 .setPublisher(res.getString("publisher"));				
				
				lines.add(new OrderLine(g, res.getDouble("unit_price"), res.getInt("quantity")));				
			}
			
			// Adding last game
			if(o != null){
				o.setLines(lines);
				lo.add(o);						
			}
			
			res.close();
			ps.close();
			
//			DatabaseConnection.getInstance().closeCnx();			
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
			
			// Need to check if a game already exists and has been deleted
			
			// Requête
			String sql = "INSERT INTO orders (order_date, user) VALUES (CURDATE(), ?);";
			
			PreparedStatement ps = cnx.prepareStatement(sql);
			ps.setString(1, order.getUser());
			
			//Execution et traitement de la réponse
			ps.executeUpdate();
			ps.close();
			
			// Need a good way to retrieve added order (num)
			sql = "SELECT MAX(num) AS order_num FROM orders;";
			
			ps = cnx.prepareStatement(sql);
			ResultSet res = ps.executeQuery();
			res.next();
			
			Integer orderID = res.getInt("order_num");
			res.close();
			ps.close();
			
			// Updating lines
			sql = "INSERT INTO orders_lines (order_num, game, unit_price, quantity) VALUES (?, ?, 0, ?);";
			
			for(Iterator<OrderLine> i = order.getLines().iterator(); i.hasNext(); ){
				OrderLine line = i.next();
				
				ps = cnx.prepareStatement(sql);
				ps.setInt(1, orderID);
				ps.setInt(2, line.getGame().getId());
				ps.setInt(3, line.getQuantity());
				
				ps.executeUpdate();
				ps.close();
				
				// Save price
				String savePriceSql = "UPDATE orders_lines SET unit_price = (SELECT price FROM games WHERE id = ?) WHERE order_num = ? AND game = ?;";
				PreparedStatement savePricePs = cnx.prepareStatement(savePriceSql);
				savePricePs.setInt(1, line.getGame().getId());				
				savePricePs.setInt(2, orderID);
				savePricePs.setInt(3, line.getGame().getId());
				
				savePricePs.executeUpdate();
				savePricePs.close();
				
				// Update stock
				String stockUpdateSql = "UPDATE games SET stock = stock - ? WHERE id = ?;";
				PreparedStatement stockUpdatePs = cnx.prepareStatement(stockUpdateSql);
				stockUpdatePs.setInt(1, line.getQuantity());
				stockUpdatePs.setInt(2, line.getGame().getId());
				
				stockUpdatePs.executeUpdate();
				stockUpdatePs.close();
			}
			
//			DatabaseConnection.getInstance().closeCnx();
			
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
//			DatabaseConnection.getInstance().closeCnx();
		}catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		
		return counter;
	}	
}