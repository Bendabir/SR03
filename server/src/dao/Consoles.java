package dao;

import beans.*;

public class Consoles{
	public Console get(String name) throws SQLException{
		Connection cnx = DatabaseConnection.getInstance().getCnx();

		String sql = "SELECT * FROM consoles WHERE `name`=?;";
		PreparedStatement ps = cnx.prepareStatement(sql);
		ps.setString(1, name);

		ResultSet res = ps.executeQuery();

		while(res.next()){
			Console console = new Conosle(res.getString('name'));
			console.setLaunchedDate(new Date(res.getString('launched_date')));
			return console;
		}
	}

	public ArrayList<Console> all() throws SQLException{
		ArrayList<Console> consoles = new ArrayList<Console>();
		String sql = "SELECT * FROM consoles";
		PreparedStatement ps = cnx.prepareStatement(sql);

		ResultSet res = ps.executeQuery();

		while(res.next()){
			Console console = new Conosle(res.getString('name'));
			console.setLaunchedDate(new Date(res.getString('launched_date')));
			return console;
		}

		return users;
	}

	public boolean add(Console console){
		String sql = "INSERT INTO consoles (name, launched_date) VALUES(?, ?);";
		PreparedStatement ps = cnx.prepareStatement(sql);
		ps.setString(1, console.getName());
		ps.setString(2, console.getLaunchedDate().toString());

		try{
			ResultSet res = ps.executeQuery();
		} catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean update(Console user){
		String sql = "UPDATE consoles SET (name=?, launched_date=?);";
		PreparedStatement ps = cnx.prepareStatement(sql);
		ps.setString(1, console.getName());
		ps.setString(2, console.getLaunchedDate().toString());

		try{
			ResultSet res = ps.executeQuery();
		} catch(SQLException e){
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