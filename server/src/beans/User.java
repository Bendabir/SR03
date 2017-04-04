package beans;
import java.io.Serializable;

public class User implements Serializable, Comparable<User> {
	private int id;
	private String username;
	private String password;
	private String first_name;
	private String last_name;
	private Date brith_date;
	private String status;
	
	public User(String username, String password) {
		this.username = username;
		// TODO: hash password before
		this.password  = password;
	}

	// SETTERS
	public User setUsername(String uname){
		this.username = uname;
		return this;
	}

	public User setPassword(String password){
		// TODO: Hash password before
		this.password = password;
		return this;
	}

	public User setFirstName(String first_name){
		this.first_name = first_name;
		return this;
	}

	public User setLastName(String last_name){
		this.last_name = last_name;
		return this;
	}

	public User setBirthDate(Date date){
		this.brith_date = date;
		return this;
	}

	public User setStatus(String st){
		this.status = st;
		return this;
	}

	// GETTERS
	public int getId(){
		return this.id;
	}

	public String getUsername(){
		return this.username;
	}

	public String getFirstName(){
		return this.first_name;
	}

	public String getLastName(){
		return this.last_name;
	}

	public String getName(){
		return this.first_name + " " + this.last_name;
	}

	public Date getBirthDate(){
		return this.brith_date;
	}

	public String getStatus(){
		return this.status;
	}
}
