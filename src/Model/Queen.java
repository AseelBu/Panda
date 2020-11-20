package Model;

import java.util.ArrayList;

import Utils.PrimaryColor;

public class Queen extends Piece{

	/**
	 * Constructor
	 * 
	 * @param color
	 * @param location
	 */
	public Queen(PrimaryColor color, Location location) {
		super(color, location);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void move(Tile targetTile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isMoveLegal(Location targetLocation) {
		
		// TODO Auto-generated method stub
		return false;
		
	}

	@Override
	public String toString() {
		return "Q-"+this.getColor();
	}
	
	

}
