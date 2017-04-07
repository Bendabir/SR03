package beans;

public class OrderLine implements Comparable<OrderLine> {
	// private int orderNum;
	private String game;
	private double unitPrice;
	private int quantity;
	
	public OrderLine(String game, double price, int quantity){
		this.game = game;
		this.unitPrice = price;
		this.quantity = quantity;		
	}
	
	public OrderLine(String game, double price){
		this.game = game;		
		this.unitPrice = price;
		this.quantity = 1;
	}
	
	// public OrderLine(int num, int game, double price, int quantity){
	//	this.orderNum = num;
	//	this.game = game;
	//	this.unitPrice = price;
	//	this.quantity = quantity;		
	// }
	
	// public OrderLine(int num, int game, double price){
	//	this.orderNum = num;
	//	this.game = game;
	//	this.unitPrice = price;
	//	this.quantity = 1;
	// }
	
	// SETTERS
	// public OrderLine setOrderNum(int num){
	//	this.orderNum = num;
	//	return this;
	// }

	 public OrderLine setGame(String game){
	 	this.game = game;
	 	return this;
	 }
	
	public OrderLine setUnitPrice(double price){
		this.unitPrice = price;
		return this;
	}
	
	public OrderLine setQuantity(int quantity){
		this.quantity = quantity;
		return this;
	}	

	// GETTERS
	// public int getOrderNum(){
	// 	return this.orderNum;
	// }
	
	 public String getGame(){
		return this.game;
	 }
	
	public double getPrice(){
		return this.unitPrice;
	}
	
	public int getQuantity(){
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
		return "Game: " + this.game + "; Unit Price: " + this.unitPrice + "; Quantity: " + this.quantity + ";";
	}	
}