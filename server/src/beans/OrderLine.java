package beans;

import beans.Game;

public class OrderLine implements Comparable<OrderLine> {
	// private int orderNum;
	private Game game;
	private Double unitPrice;
	private Integer quantity;
	
	public OrderLine(){
		this.game = null;
		this.unitPrice = null;
		this.quantity = null;
	}
	
	public OrderLine(Game game, double price, int quantity){
		this.game = game;
		this.unitPrice = new Double(price);
		this.quantity = new Integer(quantity);		
	}
	
	public OrderLine(Game game, Double price, Integer quantity){
		this.game = game;
		this.unitPrice = price;
		this.quantity = quantity;		
	}	
	
	public OrderLine(Game game, double price){
		this.game = game;		
		this.unitPrice = new Double(price);
		this.quantity = new Integer(1);
	}
	
	public OrderLine(Game game, Double price){
		this.game = game;		
		this.unitPrice = price;
		this.quantity = new Integer(1);
	}	
	
	public OrderLine(double price, int quantity){
		this.game = null;
		this.unitPrice = price;
		this.quantity = quantity;
	}
	
	public OrderLine(Double price, Integer quantity){
		this.game = null;
		this.unitPrice = price;
		this.quantity = quantity;
	}	
	
	public OrderLine(int quantity){
		this.game = null;
		this.unitPrice = null;
		this.quantity = new Integer(quantity);
	}
	
	public OrderLine(Integer quantity){
		this.game = null;
		this.unitPrice = null;
		this.quantity = quantity;
	}
	
	public OrderLine(Game game, int quantity){
		this.game = game;		
		this.unitPrice = null;
		this.quantity = new Integer(quantity);
	}
	
	public OrderLine(Game game, Integer quantity){
		this.game = game;		
		this.unitPrice = null;
		this.quantity = quantity;
	}		
	
	// SETTERS
	 public OrderLine setGame(Game game){
	 	this.game = game;
	 	return this;
	 }
	
	public OrderLine setUnitPrice(double price){
		this.unitPrice = new Double(price);
		return this;
	}
	
	public OrderLine setUnitPrice(Double price){
		this.unitPrice = price;
		return this;
	}	
	
	public OrderLine setQuantity(int quantity){
		this.quantity = new Integer(quantity);
		return this;
	}	
	
	public OrderLine setQuantity(Integer quantity){
		this.quantity = quantity;
		return this;
	}		

	// GETTERS
	// public int getOrderNum(){
	// 	return this.orderNum;
	// }
	
	 public Game getGame(){
		return this.game;
	 }
	
	public Double getPrice(){
		return this.unitPrice;
	}
	
	public Integer getQuantity(){
		return this.quantity;
	}	
	
//	@Override
//	public int compareTo(OrderLine ol){
//		if(this.orderNum == ol.orderNum && this.game == ol.game){
//			return 0;
//		}
//
//		return -1;
//	}
	
	@Override
	public int compareTo(OrderLine ol){
		return -1;
	}
	
//	@Override
//	public String toString(){
//		return "Order Num: " + this.orderNum + "; Game: " + this.game + "; Unit Price: " + this.unitPrice + "; Quantity: " + this.quantity + ";";
//	}
	
	@Override
	public String toString(){
		return "Game: [" + this.game.toString() + "]; Unit Price: " + this.unitPrice.toString() + "; Quantity: " + this.quantity.toString() + ";";
	}	
}