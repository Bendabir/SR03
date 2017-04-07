package beans;

import java.util.ArrayList;
import beans.GameGenre;

public class Game implements Comparable<Game> {
	private Integer id;
	private String title;
	private String console;
	private Double price;
	private String releaseDate;
	private Integer stock;
	private ArrayList<GameGenre> genres; // Could also use strings instead since a game type is only a name for now
	
	public Game(Integer id, String title, String console, Double price, String releaseDate, Integer stock, ArrayList<GameGenre> genres) {
		super();
		this.id = id;
		this.title = title;
		this.console = console;
		this.price = price;
		this.releaseDate = releaseDate;
		this.stock = stock;
		this.genres = genres;
	}
	
	public Game(int id, String title, String console, double price, String releaseDate, int stock, ArrayList<GameGenre> genres) {
		super();
		this.id = new Integer(id);
		this.title = title;
		this.console = console;
		this.price = new Double(price);
		this.releaseDate = releaseDate;
		this.stock = new Integer(stock);
		this.genres = genres;
	}	
	
	// Building new Game before being inserted in database
	public Game(String title, String console, Double price, String releaseDate, Integer stock,  ArrayList<GameGenre> genres) {
		super();
		this.id = null;
		this.title = title;
		this.console = console;
		this.price = price;
		this.releaseDate = releaseDate;
		this.stock = stock;
		this.genres = genres;
	}
	
	public Game(String title, String console, double price, String releaseDate, int stock,  ArrayList<GameGenre> genres) {
		super();
		this.id = null;
		this.title = title;
		this.console = console;
		this.price = new Double(price);
		this.releaseDate = releaseDate;
		this.stock = new Integer(stock);
		this.genres = genres;
	}	
	
	// Simplified version of a Game
	public Game(String title, String console){
		super();
		this.id = null;
		this.title = title;
		this.console = console;
		this.price = null;
		this.releaseDate = null;
		this.stock = null;
		this.genres = null;
	}

	public Integer getId() {
		return this.id;
	}
	public Game setId(Integer id){
		this.id = id;
		return this;
	}
	public Game setId(int id) {
		this.id = new Integer(id);
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
	
	public Double getPrice() {
		return this.price;
	}
	public Game setPrice(Double price){
		this.price = price;
		return this;
	}
	public Game setPrice(double price) {
		this.price = new Double(price);
		return this;
	}
	
	public String getReleaseDate() {
		return this.releaseDate;
	}
	public Game setReleaseDate(String releaseDate){
		this.releaseDate = releaseDate;
		return this;
	}
	
	public Integer getStock() {
		return this.stock;
	}
	public Game setStock(Integer stock){
		this.stock = stock;
		return this;
	}
	public Game setStock(int stock) {
		this.stock = new Integer(stock);
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
		return this.id.compareTo(g.id);
	}	

	@Override
	public String toString(){
		return "ID: " + this.id.toString() + "; Title: " + this.title + "; Console: " + this.console + "; Price: " + this.price.toString() + "; Release Date: " + this.releaseDate + "; Stock: " + this.stock.toString() + "; Genres: " + this.genres.toString() + ";";
	}	
}
