
package Model;

import java.util.ArrayList;

import Utils.Directions;
import Utils.PrimaryColor;

public abstract class  Piece {
	private int id;
	private PrimaryColor color;
	private Location location;
	private int eatingCntr=0;
	
	
	/**
	 * constructor 
	 * 
	 * @param color
	 * @param location
	 */
	public Piece(int id,PrimaryColor color, Location location) {
		super();
		this.id = id;
		this.color = color;
		this.location = location;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Piece other = (Piece) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public int getEatingCntr() {
		return eatingCntr;
	}


	public void setEatingCntr(int eatingCntr) {
		this.eatingCntr = eatingCntr;
	}

	

	public abstract void move(Tile targetTile);
	public abstract boolean isMoveLegal(Location targetLocation);
	public abstract ArrayList<Piece> getEdiblePieces();
	
	
	
	
	

	@Override
	public String toString() {
		return "Piece [color=" + color + ", location=" + location + "]";
	}

	
	

}
