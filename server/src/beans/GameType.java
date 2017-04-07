package beans;

public class GameType implements Comparable<GameType> {
	private String name;
	
	public GameType(String name) {
		this.name = name;
	}
	
	// SETTERS
	public GameType setName(String uname){
		this.name = uname;
		return this;
	}

	// GETTERS
	public String getName(){
		return this.name;
	}
	
	@Override
	public int compareTo(GameType c){
		return this.name.compareTo(c.name);
	}
	
	@Override
	public String toString(){
		return "Name: " + this.name + ";";
	}
}
