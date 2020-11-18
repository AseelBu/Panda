
package Model;

import java.util.ArrayList;

import Utils.MainColor;

public abstract class Piece {
	private MainColor color;
	private ArrayList<Location> location;
	
	//Constructor

	public Piece(MainColor color, ArrayList<Location> location) {
		super();
		this.color = color;
		this.location = location;
	}
	
	public MainColor getColor() {
		return color;
	}
	public void setColor(MainColor color) {
		this.color = color;
	}
	public ArrayList<Location> getLocation() {
		return location;
	}
	public void setLocation(ArrayList<Location> location) {
		this.location = location;
	}
	
	@Override
	public String toString() {
		return "Piece [color=" + color + ", location=" + location + "]";
	}
	

}
