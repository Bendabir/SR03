package beans;

public class Console implements Comparable<Console> {
	private String name;
	private String launchedDate;

	public Console() {
		this.name = null;
		this.launchedDate = null;
	}	
	
	public Console(String name, String launched) {
		this.name = name;
		this.launchedDate = launched;
	}

	public Console(String name) {
		this.name = name;
		this.launchedDate = null;
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
