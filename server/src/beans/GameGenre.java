package beans;

public class GameGenre implements Comparable<GameGenre> {
	private String name;
	private String description;
	
	public GameGenre(){
		this.name = null;
		this.description = null;
	}
	
	public GameGenre(String name) {
		this.name = name;
		this.description = null;
	}
	
	// SETTERS
	public GameGenre setName(String uname){
		this.name = uname;
		return this;
	}
	
	public GameGenre setDescription(String d){
		this.description = d;
		return this;
	}

	// GETTERS
	public String getName(){
		return this.name;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	@Override
	public int compareTo(GameGenre c){
		return this.name.compareTo(c.name);
	}
	
	@Override
	public String toString(){
		return "Name: " + this.name + ";";
	}
}
