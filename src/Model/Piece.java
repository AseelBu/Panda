
package Model;

import java.util.ArrayList;

import Utils.SeconderyTileColor;

public abstract class Piece {
	private SeconderyTileColor color;
	private ArrayList<Location> location;
	
	//Constructor

	public Piece(SeconderyTileColor color, ArrayList<Location> location) {
		super();
		this.color = color;
		this.location = location;
	}
	
	public SeconderyTileColor getColor() {
		return color;
	}
	public void setColor(SeconderyTileColor color) {
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
