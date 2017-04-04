package beans;
import java.io.Serializable;

public class Game implements Serializable, Comparable<Game> {
	private int id;
	private String title;
	private String console;
	private float price;
	private String releaseDate;
	private int stock;
	
	public Game() {}
	
	public Game(int id, String title, String console, float price, String releaseDate, int stock) {
		super();
		this.id = id;
		this.title = title;
		this.console = console;
		this.price = price;
		this.releaseDate = releaseDate;
		this.stock = stock;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getConsole() {
		return console;
	}
	public void setConsole(String console) {
		this.console = console;
	}
	
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate){
		this.releaseDate = releaseDate;
	}
	
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}

	@Override
	public int compareTo(Game g) {
		return this.id - g.id;
	}	

}
