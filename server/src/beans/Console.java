package beans;

// Date constructor for String is deprecated
// import java.util.Date; 

public class Console implements Comparable<Console> {
	private String name;
	private String launchedDate;
	
	public Console(String name, String launched) {
		this.name = name;
		this.launchedDate = launched;
	}

	public Console(String name) {
		this.name = name;
		this.launchedDate = "0000-00-00";
	}
	
	// SETTERS
	public Console setName(String uname){
		this.name = uname;
		return this;
	}

	public Console setLaunchedDate(String date){
		this.launchedDate = date;
		return this;
	}

	// GETTERS

	public String getName(){
		return this.name;
	}

	public String getLaunchedDate(){
		return this.launchedDate;
	}
	
	@Override
	public int compareTo(Console c){
		return this.name.compareTo(c.name);
	}
	
	@Override
	public String toString(){
		return "Name: " + this.name + "; Launched Date: " + this.launchedDate + ";";
	}
}
