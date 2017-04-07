package beans;

import java.util.ArrayList;
import beans.GameGenre;

public class Game implements Comparable<Game> {
	private int id;
	private String title;
	private String console;
	private double price;
	private String releaseDate;
	private int stock;
	private ArrayList<GameGenre> genres; // Could also use strings instead since a game type is only a name for now
	
	public Game(int id, String title, String console, double price, String releaseDate, int stock, ArrayList<GameGenre> genres) {
		super();
		this.id = id;
		this.title = title;
		this.console = console;
		this.price = price;
		this.releaseDate = releaseDate;
		this.stock = stock;
		this.genres = genres;
	}
	
	// Building new Game before being inserted in database
	public Game(String title, String console, double price, String releaseDate, int stock,  ArrayList<GameGenre> genres) {
		super();
		this.id = -1;
		this.title = title;
		this.console = console;
		this.price = price;
		this.releaseDate = releaseDate;
		this.stock = stock;
		this.genres = genres;
	}

	public int getId() {
		return this.id;
	}
	public Game setId(int id) {
		this.id = id;
		return this;
	}
	
	public String getTitle() {
		return this.title;
	}
	public Game setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public String getConsole() {
		return this.console;
	}
	public Game setConsole(String console) {
		this.console = console;
		return this;
	}
	
	public double getPrice() {
		return this.price;
	}
	public Game setPrice(double price) {
		this.price = price;
		return this;
	}
	
	public String getReleaseDate() {
		return this.releaseDate;
	}
	public Game setReleaseDate(String releaseDate){
		this.releaseDate = releaseDate;
		return this;
	}
	
	public int getStock() {
		return this.stock;
	}
	public Game setStock(int stock) {
		this.stock = stock;
		return this;
	}
	
	public ArrayList<GameGenre> getGenres(){
		return this.genres;
	}
	public Game setGenres(ArrayList<GameGenre> genres){
		this.genres = genres;
		return this;
	}

	@Override
	public int compareTo(Game g) {
		return this.id - g.id;
	}	

	@Override
	public String toString(){
		return "ID: " + this.id + "; Title: " + this.title + "; Console: " + this.console + "; Price: " + this.price + "; Release Date: " + this.releaseDate + "; Stock: " + this.stock + "; Genres: " + this.genres.toString() + ";";
	}	
}
