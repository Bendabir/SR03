package beans;

import java.util.ArrayList;

public class Game implements Comparable<Game> {
	private Integer id;
	private String title;
	private String console;
	private Double price;
	private String releaseDate;
	private Integer stock;
	private ArrayList<String> genres; // Could also use strings instead since a game type is only a name for now
	
	private String publisher;
	private String description;
	private String cover;
	
	public Game(){
		this.id = null;
		this.title = null;
		this.console = null;
		this.price = null;
		this.releaseDate = null;
		this.stock = null;
		this.genres = null;
		
		this.publisher = null;
		this.description = null;
		this.cover = null;
	}	
	
	// MAYBE WE DON'T NEED ALL THESE CONSTRUCTORS BECAUSE BEANS ARE LOADED DIRECTLY FROM JSON
	
	public Game(Integer id, String title, String console, Double price, String releaseDate, Integer stock, ArrayList<String> genres) {
		this.id = id;
		this.title = title;
		this.console = console;
		this.price = price;
		this.releaseDate = releaseDate;
		this.stock = stock;
		this.genres = genres;
		
		this.publisher = null;
		this.description = null;
		this.cover = null;
	}

	// GETTERS/SETTERS
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
	
	public ArrayList<String> getGenres(){
		return this.genres;
	}
	public Game setGenres(ArrayList<String> genres){
		this.genres = genres;
		return this;
	}
	
	public String getPublisher(){
		return this.publisher;
	}
	public Game setPublisher(String p){
		this.publisher = p;
		return this;
	}
	
	public String getDescription(){
		return this.description;
	}
	public Game setDescription(String d){
		this.description = d;
		return this;
	}
	
	public String getCover(){
		return this.cover;
	}
	public Game setCover(String c){
		this.cover = c;
		return this;
	}

	@Override
	public int compareTo(Game g) {
		return this.id.compareTo(g.id);
	}	

	@Override
	public String toString(){
		return "ID: " + this.id.toString() + "; Title: " + this.title + "; Console: " + this.console + "; Publisher: " + this.publisher + "; Price: " + this.price.toString() + "; Release Date: " + this.releaseDate + "; Stock: " + this.stock.toString() + "; Genres: " + this.genres.toString() + ";";
	}	
}
