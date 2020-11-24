package Model;

import java.util.ArrayList;

import Utils.PrimaryColor;

public class Queen extends Piece{

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param color
	 * @param location
	 */
	public Queen(int id,PrimaryColor color, Location location) {
		super(id,color, location);
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
	public ArrayList<Piece> getEdiblePieces() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public String toString() {
		return "Q-"+this.getColor();
	}

}
