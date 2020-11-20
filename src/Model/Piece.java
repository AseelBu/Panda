
package Model;

import java.util.ArrayList;

import Utils.PrimaryColor;

public abstract class  Piece {
	private PrimaryColor color;
	private Location location;
	
	
	/**
	 * constructor 
	 * 
	 * @param color
	 * @param location
	 */
	public Piece(PrimaryColor color, Location location) {
		super();
		this.color = color;
		this.location = location;
	}
	
	public PrimaryColor getColor() {
		return color;
	}
	public void setColor(PrimaryColor color) {
		this.color = color;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	
	public abstract void move(Tile targetTile);
	public abstract boolean isMoveLegal(Location targetLocation);
	
	@Override
	public String toString() {
		return "Piece [color=" + color + ", location=" + location + "]";
	}
	

}
