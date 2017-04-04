package beans;
import java.io.Serializable;

public class Console implements Serializable, Comparable<User> {
	private int id;
	private String name;
	private Date launched_date;

	public Console(String name, Date launched) {
		this.name = name;
		this.launched_date = launched;
	}

	public Console(String name) {
		this.name = name;
	}

	// SETTERS
	public Console setName(String uname){
		this.name = uname;
		return this;
	}

	public Date setLaunchedDate(Date date){
		// TODO: Hash password before
		this.launched_date = date;
		return this;
	}

	// GETTERS
	public int getId(){
		return this.id;
	}

	public String getName(){
		return this.name;
	}

	public Date getLaunchedDate(){
		return this.launched_date;
	}
}
