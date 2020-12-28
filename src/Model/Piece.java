
package Model;

import java.util.ArrayList;
import Exceptions.IllegalMoveException;
import Exceptions.LocationException;
import Utils.Directions;
import Utils.PrimaryColor;


/**
 * 
 * @author aseel
 * 
 * This class describes an abstract game play piece
 */
public abstract class  Piece {
	private int id;
	private PrimaryColor color;
	private Location location;
	private int eatingCntr=0;//number of eaten pieces eaten in current turn by this piece


	/**
	 * Piece constructor , to be used only by it's concrete subclasses
	 * 
	 * @param color
	 * @param location
	 */
	protected Piece(int id,PrimaryColor color, Location location) {
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

	// Getters and Setters
	/**
	 *
	 * @return int the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @return PrimaryColor the piece color
	 */
	public PrimaryColor getColor() {
		return color;
	}

	/**
	 * 
	 * @return Location the location of the piece on board
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * set the location of the piece
	 * @param location the location to set to piece
	 * @throws LocationException thrown in case location is a white tile
	 */
	public void setLocation(Location location) throws LocationException {
		Board board=Board.getInstance();
		Tile tile = board.getTileInLocation(location);
		if(tile.getColor1()==PrimaryColor.BLACK) {
			this.location = location;
		}else {
			throw new LocationException("you can't locate piece on white tile ");
		}
	}

	/**
	 * 
	 * @return int the eatingCntr value
	 */
	public int getEatingCntr() {
		return eatingCntr;
	}

	/**
	 * 
	 * @param eatingCntr the eating counter for the piece to set
	 */
	public void setEatingCntr(int eatingCntr) {
		this.eatingCntr = eatingCntr;
	}


	/**
	 * increments eatingCntr field by amount
	 * @param amount wanted to increment by
	 * @return new EatingCntr value
	 */
	public int incEatingCntr(int amount) {
		int updatedCntr=this.eatingCntr+amount;
		this.eatingCntr=updatedCntr;
		return updatedCntr;
	}

	/**
	 * resets eatingCntr field for piece
	 */
	public void resetEatingCntr() {
		this.eatingCntr = 0;
	}

	//Abstract methods
	/**
	 * gets all must edible pieces around this piece
	 * @return list of pieces that are edible for specific piece, null if piece can't eat
	 */
	public abstract ArrayList<Piece> getMustEdiblePieces();
	
	/**
	 * moves location from current location to targetLocation. if the move includes eating ,it will make the eating
	 * @param targetTile targetTile to move to
	 * @param direction direction of movement
	 * @return true if move was done,false if something prevented the move to finish
	 * @throws IllegalMoveException 
	 * @throws LocationException 
	 */
	public abstract boolean move(Tile targetTile,Directions direction) throws IllegalMoveException, LocationException;

	/**
	 * checks if moving to target location is legal by specific piece moving rules (without taking into consideration board status)
	 * 
	 * @param targetLocation the targetLocation for the move
	 * @return true if move legal by piece moving rules,false otherwise
	 * @throws IllegalMoveException 
	 */
	public abstract boolean isMoveLegal(Location targetLocation) throws IllegalMoveException;

	/**
	 * gets all edible pieces around this piece
	 * @return list of pieces that are edible for specific piece, null if piece can't eat
	 */
	public abstract ArrayList<Piece> getEdiblePieces();

	/**
	 * gets the piece that is going be eaten by this piece if it moves to direction
	 * 
	 * @param direction the direction of eating
	 * @return Piece the piece that is going to be eaten by moving in direction, null if no piece was found
	 */
	public abstract Piece getEdiblePieceByDirection( Directions direction);

	/**
	 * Gets the piece that is going be eaten by this piece if it moves to targetLocation in direction
	 * @param targetLocation the target location to move to while eating
	 * @param direction the direction of eating
	 * @return Piece edible piece
	 */
	public abstract Piece getEdiblePieceByDirection(Location targetLocation, Directions direction) ;

	/**
	 * checks if this piece can eat targetPiece legally
	 * @param targetPiece the piece to check for eating
	 * @return true if this piece can eat targetPiece, false otherwise
	 */
	public abstract boolean canEatPiece(Piece targetPiece);

	/**
	 * gets all possible tiles for this piece to move to
	 * @return ArrayList of possible tiles for this piece to move to
	 */
	public abstract ArrayList<Tile> getPossibleMoves(PrimaryColor playerColor);

}
