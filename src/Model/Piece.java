
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
	
	public void setLocation(Location location) throws Exception {
		Board board=Board.getInstance();
		Tile tile = board.getTileInLocation(location);
		if(tile.getColor1()==PrimaryColor.BLACK) {
		this.location = location;
		}else {
			throw new Exception("you can't locate piece on white tile ");
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
	public ArrayList<Piece> getMustEdiblePieces() {

		ArrayList<Piece> ediblePieces = new ArrayList<Piece>();
		Board board = Board.getInstance();
		Player currPlayer = Game.getInstance().getTurn().getCurrentPlayer();
		Location pieceLoc = this.getLocation();
		Location UL =pieceLoc.addToLocationDiagonally(Directions.UP_LEFT, 1);
		Location UR =pieceLoc.addToLocationDiagonally(Directions.UP_RIGHT, 1);
		Location DL =pieceLoc.addToLocationDiagonally(Directions.DOWN_LEFT, 1);
		Location DR =pieceLoc.addToLocationDiagonally(Directions.DOWN_RIGHT, 1);

		try {
			if((getEatingCntr()>=1 && currPlayer.getColor().equals(PrimaryColor.BLACK))
					|| currPlayer.getColor().equals(PrimaryColor.WHITE)) {
				if(UL!=null) {
					Piece p =board.getTileInLocation(UL).getPiece();
					if(p!=null) {
						if(!p.getColor().equals(currPlayer.getColor())) {
							Location jmpLoc = UL.addToLocationDiagonally(Directions.UP_LEFT, 1);
							if(jmpLoc!=null && board.getTileInLocation(jmpLoc).isEmpty()) {
								ediblePieces.add(p);
							}
						}
					}
				}
				if(UR!=null) {
					Piece p =board.getTileInLocation(UR).getPiece();
					if(p!=null) {
						if(!p.getColor().equals(currPlayer.getColor())) {
							Location jmpLoc = UR.addToLocationDiagonally(Directions.UP_RIGHT, 1);
							if(jmpLoc!=null && board.getTileInLocation(jmpLoc).isEmpty()) {
								ediblePieces.add(p);
							}
						}
					}
				}
			}
			if((getEatingCntr()>=1 && currPlayer.getColor().equals(PrimaryColor.WHITE))
					|| currPlayer.getColor().equals(PrimaryColor.BLACK)) {
				if(DL!=null) {
					Piece p =board.getTileInLocation(DL).getPiece();
					if(p!=null) {
						if(!p.getColor().equals(currPlayer.getColor())) {
							Location jmpLoc = DL.addToLocationDiagonally(Directions.DOWN_LEFT, 1);
							if(jmpLoc!=null && board.getTileInLocation(jmpLoc).isEmpty()) {
								ediblePieces.add(p);
							}
						}
					}
				}
				if(DR!=null) {
					Piece p =board.getTileInLocation(DR).getPiece();
					if(p!=null) {
						if(!p.getColor().equals(currPlayer.getColor())) {
							Location jmpLoc = DR.addToLocationDiagonally(Directions.DOWN_RIGHT, 1);
							if(jmpLoc!=null && board.getTileInLocation(jmpLoc).isEmpty()) {
								ediblePieces.add(p);
							}
						}
					}
				}
			}
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return ediblePieces;

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

	/**
	 * moves location from current location to targetLocation. if the move includes eating ,it will make the eating
	 * @param targetTile to move to
	 * @param direction of movement
	 * @return true if move was done,false if something prevented the move to finish
	 */
	public abstract boolean move(Tile targetTile,Directions direction);
	
	/**
	 * checks if moving to target location is legal by specific piece moving rules (without taking into consideration board status)
	 * 
	 * @param targetLocation
	 * @return boolean- true if move legal by piece moving rules,false otherwise
	 */
	public abstract boolean isMoveLegal(Location targetLocation);
	
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






	@Override
	public String toString() {
		return "Piece [color=" + color + ", location=" + location + "]";
	}




}
