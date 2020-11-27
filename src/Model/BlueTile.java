package Model;

import java.util.ArrayList;

import Utils.PrimaryColor;
import Utils.SeconderyTileColor;

public class BlueTile extends Tile {

	/**
	 * Constructor
	 * @param location
	 * @param color1
	 * @param color2
	 * @param piece
	 */
	public BlueTile(Location location, PrimaryColor color1, SeconderyTileColor color2, Piece piece) {
		super(location, color1, color2, piece);
		// TODO Auto-generated constructor stub
	}
	
	//methods
	
	/**
	 * retrievs a soldier back to the board after siting on blue tile
	 * @param K - proposed position
	 * @param enemy - enemy's color to identify pieces
	 */
	public void retrieveSoldier(Location K, PrimaryColor enemy) {
		
		PrimaryColor t;
		
		if(enemy.equals(PrimaryColor.BLACK)) {
			t = PrimaryColor.WHITE;
		}
		else {
			t = PrimaryColor.BLACK;
		}
		
		if(!isReturnPosLegal(K,enemy)) {
			System.err.println("Error retriving soldier : invalid position");
			return;
		}
		else {
			
			int found = 0;
			for(Location c : Board.getInstance().getEmptyLocations()) {
				if(c.equals(K)) {
					found = 1;
				}
			}
			
			if(found == 0) {
				System.err.println("Error retriving soldier : illegal tile");
				return;
			}
			
			System.out.println("Retrieved Soldier to pos : " + K);
			Board.getInstance().addPiece(new Soldier(Board.getInstance().getPieces().size()+1,t,K));
			
			
			
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
	

}
