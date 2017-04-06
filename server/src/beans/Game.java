package beans;

public class Game implements Comparable<Game> {
	private int id;
	private String title;
	private String console;
	private double price;
	private String releaseDate;
	private int stock;
	
	public Game() {}
	
	public Game(int id, String title, String console, double price, String releaseDate, int stock) {
		super();
		this.id = id;
		this.title = title;
		this.console = console;
		this.price = price;
		this.releaseDate = releaseDate;
		this.stock = stock;
	}
	
	// Building new Game before being inserted in database
	public Game(String title, String console, double price, String releaseDate, int stock) {
		super();
		this.id = -1;
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
	
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
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

	@Override
	public String toString(){
		return "ID: " + id + "; Title: " + title + "; Console: " + console + "; Price: " + price + "; Release Date: " + releaseDate + "; Stock: " + stock + ";";
	}	
}
