package Model;

import java.util.ArrayList;

import Utils.PrimaryColor;

public class BlueTile extends Tile{

	protected static abstract class Init <T extends Init<T>> extends Tile.Init<T>{
		public BlueTile build() {
			return new BlueTile(this);
		}
	}

	public static class Builder extends Init<Builder>{
		public Builder(Location location,PrimaryColor color1) {
			super.location = location;
			super.color1 = color1;	
		}
		@Override
		protected Builder self() {
			return this;
		}
	}

	/**
	 * Constructor
	 * @param init
	 */
	protected BlueTile(Init<?> init) {
		super(init);

	}



	//methods

	/**
	 * retrievs a soldier back to the board after siting on blue tile
	 * @param location - proposed position
	 * @param enemyColor - enemy's color to identify pieces
	 */
	public void retrieveSoldier(Location location, PrimaryColor enemyColor) {

		PrimaryColor t;

		if(enemyColor.equals(PrimaryColor.BLACK)) {
			t = PrimaryColor.WHITE;
		}
		else {
			t = PrimaryColor.BLACK;
		}

		if(!isReturnPosLegal(location,enemyColor)) {
			System.out.println("Error retriving soldier : invalid position");
			return;
		}
		else {

			int found = 0;
			for(Location c : Board.getInstance().getEmptyLocations()) {
				if(c.equals(location)) {
					found = 1;
				}
			}

			if(found == 0) {
				System.out.println("Error retriving soldier : illegal tile");
				return;
			}

			System.out.println("Retrieved Soldier to pos : " + location);
			Board.getInstance().addPiece(new Soldier(Board.getInstance().getPieces().size()+1,t,location));



		}

	}

	/**
	 * checks if proposed position is away at least 2 tiles from any other enemy's tile
	 * @param K - proposed location
	 * @param enemy - the color of the enemy to identify pieces
	 * @return can be placed or not
	 */
	public boolean isReturnPosLegal(Location K , PrimaryColor enemy) {

		int row = K.getRow();
		char col = K.getColumn();

		ArrayList<Piece> enemy_positions = new ArrayList<Piece>();

		for(Piece p : Game.getInstance().getBoard().getPieces()) {

			if(p.getColor().equals(enemy)) {
				enemy_positions.add(p);
			}


		}

		for(Piece p : enemy_positions) {

			if((row > p.getLocation().getRow() && row <= p.getLocation().getRow() + 2) && 
					((col > p.getLocation().getColumn() && col <= p.getLocation().getColumn() + 2 ) || (col < p.getLocation().getColumn() && col >=p.getLocation().getColumn() - 2 ))) {

				return false;
			}
			else if((row < p.getLocation().getRow() && row >= p.getLocation().getRow() - 2) && 
					((col > p.getLocation().getColumn() && col <= p.getLocation().getColumn() + 2 ) || (col < p.getLocation().getColumn() && col >=p.getLocation().getColumn() - 2 ))) {

				return false;
			}


		}

		return true;

	}



	@Override
	public String toString() {
		return "BlueTile: "+ super.toString();
	}


}
