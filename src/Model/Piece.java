
package Model;

import java.util.ArrayList;


import Exceptions.IllegalMoveException;
import Exceptions.LocationException;
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
	protected Piece(int id,PrimaryColor color, Location location) {
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

	/**
	 * Getters and Setters
	 * @return
	 */
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
	public void setLocation(Location location) throws LocationException {
		Board board=Board.getInstance();
		Tile tile = board.getTileInLocation(location);
		if(tile.getColor1()==PrimaryColor.BLACK) {
		this.location = location;
		}else {
			throw new LocationException("you can't locate piece on white tile ");
		}
	}
	public int getEatingCntr() {
		return eatingCntr;
	}
	public void setEatingCntr(int eatingCntr) {
		this.eatingCntr = eatingCntr;
	}

	
	/**
	 * gets all must edible pieces around this piece
	 * @param piece that we want to check
	 * @return list of pieces that are edible for specific piece, null if piece can't eat
	 */
	public abstract ArrayList<Piece> getMustEdiblePieces();
	
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

	/**
	 * moves location from current location to targetLocation. if the move includes eating ,it will make the eating
	 * @param targetTile to move to
	 * @param direction of movement
	 * @return true if move was done,false if something prevented the move to finish
	 * @throws IllegalMoveException 
	 * @throws LocationException 
	 */
	public abstract boolean move(Tile targetTile,Directions direction) throws IllegalMoveException, LocationException;
	
	/**
	 * checks if moving to target location is legal by specific piece moving rules (without taking into consideration board status)
	 * 
	 * @param targetLocation
	 * @return boolean- true if move legal by piece moving rules,false otherwise
	 * @throws IllegalMoveException 
	 */
	public abstract boolean isMoveLegal(Location targetLocation) throws IllegalMoveException;
	
	/**
	 * gets all edible pieces around this piece
	 * @param piece that we want to check
	 * @return list of pieces that are edible for specific piece, null if piece can't eat
	 */
	public abstract ArrayList<Piece> getEdiblePieces();
	
	/**
	 * gets the piece that is going be eaten by soldier if it moves to direction
	 * 
	 * @param direction
	 * @return Piece that is going to be eaten by moving in direction, null if no piece was found
	 */
	public abstract Piece getEdiblePieceByDirection( Directions direction);
	
	/**
	 * 
	 * @param targetLocation
	 * @param direction
	 * @return Piece edible piece
	 */
	public abstract Piece getEdiblePieceByDirection(Location targetLocation, Directions direction) ;

	/**
	 * checks if this piece can eat targetPiece legally
	 * @param targetPiece
	 * @return true if this piece can eat targetPiece, false otherwise
	 */
	public abstract boolean canEatPiece(Piece targetPiece);

	/**
	 * gets all possible tiles for this piece to move to
	 * @return ArrayList<Tile> of possible tiles for this piece to move to
	 */
	public abstract ArrayList<Tile> getPossibleMoves(PrimaryColor playerColor);
	



	@Override
	public String toString() {
		return "Piece [color=" + color + ", location=" + location + "]";
	}




}
