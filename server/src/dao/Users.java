package dao;

import beans.*;

public class Users {
	
	public User get(String username) throws SQLException{
		Connection cnx = DatabaseConnection.getInstance().getCnx();

		String sql = "SELECT * FROM users WHERE `username`=?;";
		PreparedStatement ps = cnx.prepareStatement(sql);
		ps.setString(1, username);

		ResultSet res = ps.executeQuery();

		while(res.next()){
			User user = new User(res.getString('username'));
			user.setFirstName(res.getString('first_name'));
			user.setLastName(res.getString('last_name'));
			user.setBirthDate(new Date(res.getString('brith_date')));
			user.setStatus(res.getString('status'));
			return user;
		}
	}

	public ArrayList<User> all() throws SQLException{
		ArrayList<User> users = new ArrayList<User>();
		String sql = "SELECT * FROM users";
		PreparedStatement ps = cnx.prepareStatement(sql);

		ResultSet res = ps.executeQuery();

		while(res.next()){
			User user = new User(res.getString('username'));
			user.setFirstName(res.getString('first_name'));
			user.setLastName(res.getString('last_name'));
			user.setBirthDate(new Date(res.getString('brith_date')));
			user.setStatus(res.getString('status'));
			users.add(user);
		}

		return users;
	}

	public boolean add(User user){
		String sql = "INSERT INTO users (username, password, firstname, lastname, birth_date, status) VALUES(?, ?, ?, ?, ?, ?);";
		PreparedStatement ps = cnx.prepareStatement(sql);
		ps.setString(1, user.getUsername());
		ps.setString(2, user.getPassword());
		ps.setString(3, user.getFirstName());
		ps.setString(4, user.getLastName());
		ps.setString(5, user.getBirthDate().toString());
		ps.setString(6, user.getStatus());

		try{
			ResultSet res = ps.executeQuery();
		} catch(SQLException e){
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean update(User user){
		String sql = "UPDATE users SET (username=?, password=?, firstname=?, lastname=?, birth_date=?, status=?);";
		PreparedStatement ps = cnx.prepareStatement(sql);
		ps.setString(1, user.getUsername());
		ps.setString(2, user.getPassword());
		ps.setString(3, user.getFirstName());
		ps.setString(4, user.getLastName());
		ps.setString(5, user.getBirthDate().toString());
		ps.setString(6, user.getStatus());

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
		
			String sql = "SELECT COUNT(*) FROM users;";
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