package Model;

import java.util.ArrayList;

import Utils.PrimaryColor;

public class Soldier extends Piece{

	/**
	 * Constructor
	 * 
	 * @param color
	 * @param location
	 */
	public Soldier(PrimaryColor color, Location location) {
		super(color, location);
		// TODO Auto-generated constructor stub
	}



	@Override
	public void move(Tile targetTile) {
		// TODO Auto-generated method stub
		Location targetLocation = targetTile.getLocation();
		if(this.isMoveLegal(targetLocation)) {
			if(Board.getInstance().canPieceMove(this, targetLocation)) {
				this.setLocation(targetLocation);
			}
		}

	}


	@Override
	public boolean isMoveLegal(Location targetLocation) {
		// TODO Auto-generated method stub
		boolean legalRow = false;
		int targetRow = targetLocation.getRow();
		char targetCol= targetLocation.getColumn();

		Location currentPos = this.getLocation();
		int curRow = currentPos.getRow();
		char curCol= currentPos.getColumn();

		//		check row
		if(targetRow==curRow+1 || targetRow==curRow-1 || targetRow==curRow+2 ||targetRow==curRow-2 ) {

			legalRow=true;
		}

		if(legalRow && (targetCol == (char)(curCol+1) || targetCol == (char)(curCol-1)|| targetCol==(char)(curCol+2) ||targetCol==(char)(curCol-2))) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "S-"+this.getColor();
	}


}
