package beans;

public class GameGenre implements Comparable<GameGenre> {
	private String name;
	
	public GameGenre(){
		this.name = null;
	}
	
	public GameGenre(String name) {
		this.name = name;
	}
	
	// SETTERS
	public GameGenre setName(String uname){
		this.name = uname;
		return this;
	}

	// GETTERS
	public String getName(){
		return this.name;
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
