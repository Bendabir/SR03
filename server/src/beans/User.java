package beans;

public class User implements Comparable<User> {
	private String username;
	private String firstName;
	private String lastName;
	private String status;
	
	public User(){
		this.username = null;		
		this.firstName = null;
		this.lastName = null;
		this.status = null;			
	}
	
	public User(String username) {
		this.username = username;
		this.firstName = null;
		this.lastName = null;
		this.status = null;	
	}
	
	public User(String username, String firstName, String lastName, String status) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.status = status;		
	}

	// SETTERS
	public User setUsername(String uname){
		this.username = uname;
		return this;
	}

	public User setFirstName(String firstName){
		this.firstName = firstName;
		return this;
	}

	public User setLastName(String lastName){
		this.lastName = lastName;
		return this;
	}

	public User setStatus(String st){
		this.status = st;
		return this;
	}

	// GETTERS

	public String getUsername(){
		return this.username;
	}

	public String getFirstName(){
		return this.firstName;
	}

	public String getLastName(){
		return this.lastName;
	}

	public String getName(){
		return this.firstName + " " + this.lastName;
	}

	public String getStatus(){
		return this.status;
	}

	@Override
	public int compareTo(User user){
		return this.username.compareTo(user.username);
	}
	
	@Override
	public String toString(){
		return "Username: " + this.username + "; Firstname: " + this.firstName + "; Lastname: " + this.lastName + "; Status: " + this.status + ";";
	}

}
