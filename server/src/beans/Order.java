package beans;

import beans.OrderLine;

import java.util.ArrayList;

public class Order implements Comparable<Order> {
	private Integer num;
	private String date;
	private String user; // Perhaps not necessary
	private ArrayList<OrderLine> lines;
	
	public Order(Integer num, String date, String user, ArrayList<OrderLine> lines){
		this.num = num;
		this.date = date;
		this.user = user;
		this.lines = lines;
	}
	
	public Order(int num, String date, String user, ArrayList<OrderLine> lines){
		this.num = new Integer(num);
		this.date = date;
		this.user = user;
		this.lines = lines;
	}	
	
	public Order(String user, ArrayList<OrderLine> lines){
		this.num = null;
		this.date = null;
		this.user = user;
		this.lines = lines;
	}
	
	// SETTERS
	public Order setNum(int num){
		this.num = new Integer(num);
		return this;
	}
	
	public Order setNum(Integer num){
		this.num = num;
		return this;
	}	
	
	public Order setDate(String date){
		this.date = date;
		return this;
	}
	
	public Order setUser(String user){
		this.user = user;
		return this;
	}
	
	public Order setLines(ArrayList<OrderLine> lines){
		this.lines = lines;
		return this;
	}	
	

	// GETTERS
	public Integer getNum(){
		return this.num;
	}
	
	public String getDate(){
		return this.date;
	}
	
	public String getUser(){
		return this.user;
	}
	
	public ArrayList<OrderLine> getLines(){
		return this.lines;
	}
	
	
	@Override
	public int compareTo(Order o){
		return this.num - o.num;
	}
	
	@Override
	public String toString(){
		return "Num: " + this.num.toString() + "; Date: " + this.date + "; User: " + this.user + "; Lines: " + this.lines.toString() + ";";
	}
}
