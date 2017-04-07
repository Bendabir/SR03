package beans;

public class User implements Comparable<User> {
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String birthDate;
	private String status;
	
	public User(String username, String password) {
		this.username = username;
		this.password = utils.MD5.hash(username + password);		
		this.firstName = null;
		this.lastName = null;
		this.birthDate = null;
		this.status = null;	
	}

	public User(String username) {
		this.username = username;
		this.password  = null;
		this.firstName = null;
		this.lastName = null;
		this.birthDate = null;
		this.status = null;		
	}
	
	public User(String username, String firstName, String lastName, String birthDate, String status) {
		this.username = username;
		this.password  = null;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.status = status;		
	}
	
	public User(String username, String password, String firstName, String lastName, String birthDate, String status) {
		this.username = username;
		this.password = utils.MD5.hash(username + password);	
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.status = status;	
	}		

	// SETTERS
	public User setUsername(String uname){
		this.username = uname;
		return this;
	}

	public User setPassword(String password){
		// Hash the password concatenated with the username
		// Then, 2 identical passwords will give different hashes
		this.password = utils.MD5.hash(this.username + password);		
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

	public User setBirthDate(String date){
		this.birthDate = date;
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

	public String getBirthDate(){
		return this.birthDate;
	}

	public String getStatus(){
		return this.status;
	}

	// returns hashed password anyways...
	public String getPassword(){
		return this.password;
	}
	
	@Override
	public int compareTo(User user){
		return this.username.compareTo(user.username);
	}
	
	@Override
	public String toString(){
		return "Username: " + this.username + "; Password: " + this.password + "; Firstname: " + this.firstName + "; Lastname: " + this.lastName + "; Birth Date:" + this.birthDate + "; Status: " + this.status + ";";
	}
	
	public void hash(){
		this.password = utils.MD5.hash(this.username + this.password);
	}
}
