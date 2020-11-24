package Model;

import java.util.ArrayList;
import java.util.HashMap;

import Utils.Directions;
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
//		Location targetLocation = targetTile.getLocation();
//		if(this.isMoveLegal(targetLocation)) {
//			if(Board.getInstance().canPieceMove(this, targetLocation)) {
//				//			TODO does moving contains eating?
//				this.setLocation(targetLocation);
//			}
//		}
	}
	
	/**
	 * Method used to move a piece by selected direction
	 * @param targetTile
	 * @param direction
	 */
	public void move(Tile targetTile, Directions direction) {
		Location targetLocation = targetTile.getLocation();
		if(this.isMoveLegalByDirection(targetLocation, direction)) {
			if(Board.getInstance().canPieceMove(this, targetLocation, direction)) {
				Piece toEat = getEdiblePieceByDirections(targetTile.getLocation(), direction);
				if(toEat != null)
				{
					Board.getInstance().getTilesMap().get(toEat.getLocation().getRow()).get(toEat.getLocation().getColumn() - 'A').setPiece(null);
					Board.getInstance().getPieces().remove(toEat);
				}
				Board.getInstance().getTilesMap().get(this.getLocation().getRow()).get(this.getLocation().getColumn() - 'A').setPiece(null);
				targetTile.setPiece(this);
				this.setLocation(targetLocation);
			}
		}
	}

	@Override
	public boolean isMoveLegal(Location targetLocation) { // NOT DONE
		Location currentLocation = getLocation();
		if(currentLocation.getRow() == targetLocation.getRow() ||
				currentLocation.getColumn() == targetLocation.getColumn())
			return false;
		if(currentLocation.getRow() - targetLocation.getRow() != currentLocation.getColumn() - targetLocation.getColumn()) {
			isMoveLegalByDirection(targetLocation, Directions.UP_LEFT);
			isMoveLegalByDirection(targetLocation, Directions.UP_RIGHT);
			isMoveLegalByDirection(targetLocation, Directions.DOWN_LEFT);
			isMoveLegalByDirection(targetLocation, Directions.DOWN_RIGHT);
		}
		
		return true;
		
	}
	/**
	 * Checks whether the move is legal by direction
	 * @param targetLocation
	 * @param direction
	 * @return true if it is legal, otherwise false
	 */
	public boolean isMoveLegalByDirection(Location targetLocation, Directions direction) {
		if(getLocation().getRow() == targetLocation.getRow() || getLocation().getColumn() == targetLocation.getColumn()) return false;
		switch(direction) {
			case UP_LEFT:{
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();

				do {
					if(targetLocation.getRow() == i && targetLocation.getColumn() == c) return true;
					i++;
					c--;
					if(i > Board.getInstance().getBoardSize()) i = 1;
					if(c < Board.getInstance().getColumnLowerbond()) c = Board.getInstance().getColumnUpperbond();
				}
				while(i != getLocation().getRow() && c != getLocation().getColumn());

				break;
			}
			case UP_RIGHT:{
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();
				do {
					if(targetLocation.getRow() == i && targetLocation.getColumn() == c) return true;
					i++;
					c++;
					if(i > Board.getInstance().getBoardSize()) i = 1;
					if(c > Board.getInstance().getColumnUpperbond()) c = Board.getInstance().getColumnLowerbond();
				}
				while(i != getLocation().getRow() && c != getLocation().getColumn());
				
				break;
			}
			case DOWN_LEFT:{//
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();

				do {
					if(targetLocation.getRow() == i && targetLocation.getColumn() == c) return true;
					i--;
					c--;
					if(i < 1) i = Board.getInstance().getBoardSize();
					if(c < Board.getInstance().getColumnLowerbond()) c = Board.getInstance().getColumnUpperbond();
				}
				while(i != getLocation().getRow() && c != getLocation().getColumn());
				
				break;
			}
			case DOWN_RIGHT:{//
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();

				do {
					if(targetLocation.getRow() == i && targetLocation.getColumn() == c) return true;
					i--;
					c++;
					if(i < 1) i = Board.getInstance().getBoardSize();
					if(c > Board.getInstance().getColumnUpperbond()) c = Board.getInstance().getColumnLowerbond();
				}
				while(i != getLocation().getRow() && c != getLocation().getColumn());
				
				break;
			}
			default: return false;
		}
		
		return false;
	}
	
	
	@Override
	public ArrayList<Piece> getEdiblePieces() {
		ArrayList<Piece> pieces = new ArrayList<>();
		Piece temp = null;
		temp  = getEdiblePieceByDirections(Directions.UP_LEFT);
		if(temp != null)
			pieces.add(temp);
		temp  = getEdiblePieceByDirections(Directions.UP_RIGHT);
		if(temp != null)
			pieces.add(temp);
		temp  = getEdiblePieceByDirections(Directions.DOWN_LEFT);
		if(temp != null)
			pieces.add(temp);
		temp  = getEdiblePieceByDirections(Directions.DOWN_RIGHT);
		if(temp != null)
			pieces.add(temp);
		
		return pieces;
	}
	
	/**
	 * 
	 * @param direction
	 * @return Object of Edible Piece, otherwise null
	 */
	public Piece getEdiblePieceByDirections(Directions direction){
		Board board = Board.getInstance();
		HashMap<Integer, ArrayList<Tile>> boardMap = board.getTilesMap();
		Piece suspectedToAdd = null;
		
		switch(direction) {
			case UP_LEFT:{
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();
	
				do {
					if(boardMap.get(i).get('H' - c).getPiece() != null) {
						if(suspectedToAdd != null) break;
						else {
							suspectedToAdd = boardMap.get(i).get('H' - c).getPiece();
						}
					}else {
						if(suspectedToAdd != null) {
							return suspectedToAdd = boardMap.get(i).get('H' - c).getPiece();
						}
					}
					i++;
					c--;
					if(i > Board.getInstance().getBoardSize()) i = 1;
					if(c < Board.getInstance().getColumnLowerbond()) c = Board.getInstance().getColumnUpperbond();
				}
				while(i != getLocation().getRow() && c != getLocation().getColumn());
	
				break;
			}
			case UP_RIGHT:{
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();
	
				do {
					if(boardMap.get(i).get('H' - c).getPiece() != null) {
						if(suspectedToAdd != null) break;
						else {
							suspectedToAdd = boardMap.get(i).get('H' - c).getPiece();
						}
					}else {
						if(suspectedToAdd != null) {
							return suspectedToAdd = boardMap.get(i).get('H' - c).getPiece();

						}
					}
					i++;
					c++;
					if(i > Board.getInstance().getBoardSize()) i = 1;
					if(c > Board.getInstance().getColumnUpperbond()) c = Board.getInstance().getColumnLowerbond();
				}
				while(i != getLocation().getRow() && c != getLocation().getColumn());

				break;
			}
			case DOWN_LEFT:{//
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();
	
				do {
					if(boardMap.get(i).get('H' - c).getPiece() != null) {
						if(suspectedToAdd != null) break;
						else {
							suspectedToAdd = boardMap.get(i).get('H' - c).getPiece();
						}
					}else {
						if(suspectedToAdd != null) {
							return suspectedToAdd = boardMap.get(i).get('H' - c).getPiece();
						}
					}
					i--;
					c--;
					if(i < 1) i = Board.getInstance().getBoardSize();
					if(c < Board.getInstance().getColumnLowerbond()) c = Board.getInstance().getColumnUpperbond();
				}
				while(i != getLocation().getRow() && c != getLocation().getColumn());

				break;
			}
			case DOWN_RIGHT:{//
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();
	
				do {
					if(boardMap.get(i).get('H' - c).getPiece() != null) {
						if(suspectedToAdd != null) break;
						else {
							suspectedToAdd = boardMap.get(i).get('H' - c).getPiece();
						}
					}else {
						if(suspectedToAdd != null) {
							return suspectedToAdd = boardMap.get(i).get('H' - c).getPiece();
						}
					}
					i--;
					c++;
					if(i < 1) i = Board.getInstance().getBoardSize();
					if(c > Board.getInstance().getColumnUpperbond()) c = Board.getInstance().getColumnLowerbond();
				}
				while(i != getLocation().getRow() && c != getLocation().getColumn());

				break;
			}
			default: return null;
		}
		
		return null;
	}
	
	public Piece getEdiblePieceByDirections(Location targetLocation, Directions direction) {
		Board board = Board.getInstance();
		HashMap<Integer, ArrayList<Tile>> boardMap = board.getTilesMap();
		Piece suspectedToBlock = null;
		
		switch(direction) {
			case UP_LEFT:{
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();
				int counter = 1;
				do {
					if(boardMap.get(i).get(c - 'A').getPiece() != null) {
						if((boardMap.get(i).get(c - 'A').getPiece().getColor() != this.getColor()) && !boardMap.get(i).get(c - 'A').getPiece().equals(this)) {
							if(suspectedToBlock != null) return null;
							else {
								suspectedToBlock = boardMap.get(i).get(c - 'A').getPiece();
							}
						}
					}else {
						if(suspectedToBlock != null) {
							return suspectedToBlock;
						}
					}
					i++;
					c--;
					counter++;
					if(i > Board.getInstance().getBoardSize()) i = 1;
					if(c < Board.getInstance().getColumnLowerbond()) c = Board.getInstance().getColumnUpperbond();
				}
				while((i != targetLocation.getRow() && c != targetLocation.getColumn() ) &&  counter <= 8);
	
				if(i == targetLocation.getRow() && c == targetLocation.getColumn() ) {
					if(boardMap.get(i).get(c - 'A').getPiece() != null) {
						if((boardMap.get(i).get(c - 'A').getPiece().getColor() != this.getColor()) && !boardMap.get(i).get(c - 'A').getPiece().equals(this)) {
							if(suspectedToBlock != null) return null;
							else {
								suspectedToBlock = boardMap.get(i).get(c - 'A').getPiece();
							}
						}
					}else {
						if(suspectedToBlock != null) {
							return suspectedToBlock;
						}
					}
				}
				
				break;
			}
			case UP_RIGHT:{
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();
				int counter = 1;
	
				do {
					if(boardMap.get(i).get(c - 'A').getPiece() != null) {
						if((boardMap.get(i).get(c - 'A').getPiece().getColor() != this.getColor()) && !boardMap.get(i).get(c - 'A').getPiece().equals(this)) {
							if(suspectedToBlock != null) return null;
							else {
								suspectedToBlock = boardMap.get(i).get(c - 'A').getPiece();
							}
						}
					}else {
						if(suspectedToBlock != null) {
							return suspectedToBlock;
						}
					}
					i++;
					c++;
					counter++;
	
					if(i > Board.getInstance().getBoardSize()) i = 1;
					if(c > Board.getInstance().getColumnUpperbond()) c = Board.getInstance().getColumnLowerbond();
				}
				while((i != targetLocation.getRow() && c != targetLocation.getColumn() ) &&  counter <= 8);
	
				if(i == targetLocation.getRow() && c == targetLocation.getColumn() ) {
					if(boardMap.get(i).get(c - 'A').getPiece() != null) {
						if((boardMap.get(i).get(c - 'A').getPiece().getColor() != this.getColor()) && !boardMap.get(i).get(c - 'A').getPiece().equals(this)) {
							if(suspectedToBlock != null) return null;
							else {
								suspectedToBlock = boardMap.get(i).get(c - 'A').getPiece();
							}
						}
					}else {
						if(suspectedToBlock != null) {
							return suspectedToBlock;
						}
					}
				}
				break;
			}
			case DOWN_LEFT:{//
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();
				int counter = 1;
	
				do {
					if(boardMap.get(i).get(c - 'A').getPiece() != null) {
						if((boardMap.get(i).get(c - 'A').getPiece().getColor() != this.getColor()) && !boardMap.get(i).get(c - 'A').getPiece().equals(this)) {
							if(suspectedToBlock != null) return null;
							else {
								suspectedToBlock = boardMap.get(i).get(c - 'A').getPiece();
							}
						}
					}else {
						if(suspectedToBlock != null) {
							return suspectedToBlock;
						}
					}
					i--;
					c--;
					counter++;
	
					if(i < 1) i = Board.getInstance().getBoardSize();
					if(c < Board.getInstance().getColumnLowerbond()) c = Board.getInstance().getColumnUpperbond();
				}
				while((i != targetLocation.getRow() && c != targetLocation.getColumn() ) &&  counter <= 8);
	
				if(i == targetLocation.getRow() && c == targetLocation.getColumn() ) {
					if(boardMap.get(i).get(c - 'A').getPiece() != null) {
						if((boardMap.get(i).get(c - 'A').getPiece().getColor() != this.getColor()) && !boardMap.get(i).get(c - 'A').getPiece().equals(this)) {
							if(suspectedToBlock != null) return null;
							else {
								suspectedToBlock = boardMap.get(i).get(c - 'A').getPiece();
							}
						}
					}else {
						if(suspectedToBlock != null) {
							return suspectedToBlock;
						}
					}
				}
				break;
			}
			case DOWN_RIGHT:{//
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();
				int counter = 1;
	
				do {
					if(boardMap.get(i).get(c - 'A').getPiece() != null) {
						if((boardMap.get(i).get(c - 'A').getPiece().getColor() != this.getColor()) && !boardMap.get(i).get(c - 'A').getPiece().equals(this)) {
							if(suspectedToBlock != null) return null;
							else {
								suspectedToBlock = boardMap.get(i).get(c - 'A').getPiece();
							}
						}
					}else {
						if(suspectedToBlock != null) {
							return suspectedToBlock;
						}
					}
					i--;
					c++;
					counter++;
	
					if(i < 1) i = Board.getInstance().getBoardSize();
					if(c > Board.getInstance().getColumnUpperbond()) c = Board.getInstance().getColumnLowerbond();
				}
				while((i != targetLocation.getRow() && c != targetLocation.getColumn() ) &&  counter <= 8);
	
				if(i == targetLocation.getRow() && c == targetLocation.getColumn() ) {
					if(boardMap.get(i).get(c - 'A').getPiece() != null) {
						if((boardMap.get(i).get(c - 'A').getPiece().getColor() != this.getColor()) && !boardMap.get(i).get(c - 'A').getPiece().equals(this)) {
							if(suspectedToBlock != null) return null;
							else {
								suspectedToBlock = boardMap.get(i).get(c - 'A').getPiece();
							}
						}
					}else {
						if(suspectedToBlock != null) {
							return suspectedToBlock;
						}
					}
				}
				
				break;
			}
			default: return null;
		}
		return null;
	}
	
	public boolean isPieceBlockedByDirection(Location targetLocation, Directions direction) {
		Board board = Board.getInstance();
		HashMap<Integer, ArrayList<Tile>> boardMap = board.getTilesMap();
		Piece suspectedToBlock = null;
		if(boardMap.get(targetLocation.getRow()).get(targetLocation.getColumn() - 'A').getPiece() != null) return true;
		switch(direction) {
			case UP_LEFT:{
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();
				int counter = 1;
				do {
					if(boardMap.get(i).get(c - 'A').getPiece() != null) {
						if((boardMap.get(i).get(c - 'A').getPiece().getColor() == this.getColor()) && !boardMap.get(i).get(c - 'A').getPiece().equals(this)) return true;
						if(suspectedToBlock != null) return true;
						else {
							suspectedToBlock = boardMap.get(i).get(c - 'A').getPiece();
						}
					}else {
						if(suspectedToBlock != null) {
							suspectedToBlock = null;
						}
					}
					i++;
					c--;
					counter++;
					if(i > Board.getInstance().getBoardSize()) i = 1;
					if(c < Board.getInstance().getColumnLowerbond()) c = Board.getInstance().getColumnUpperbond();
				}
				while((i != targetLocation.getRow() && c != targetLocation.getColumn() ) &&  counter <= 8);
	
				break;
			}
			case UP_RIGHT:{
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();
				int counter = 1;

				do {
					if(boardMap.get(i).get(c - 'A').getPiece() != null) {
						if((boardMap.get(i).get(c - 'A').getPiece().getColor() == this.getColor()) && !boardMap.get(i).get(c - 'A').getPiece().equals(this)) return true;
						if(suspectedToBlock != null) return true;
						else {
							suspectedToBlock = boardMap.get(i).get(c - 'A').getPiece();
						}
					}else {
						if(suspectedToBlock != null) {
							suspectedToBlock = null;
						}
					}
					i++;
					c++;
					counter++;

					if(i > Board.getInstance().getBoardSize()) i = 1;
					if(c > Board.getInstance().getColumnUpperbond()) c = Board.getInstance().getColumnLowerbond();
				}
				while((i != targetLocation.getRow() && c != targetLocation.getColumn() ) &&  counter <= 8);

				break;
			}
			case DOWN_LEFT:{//
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();
				int counter = 1;

				do {
					if(boardMap.get(i).get(c - 'A').getPiece() != null) {
						if((boardMap.get(i).get(c - 'A').getPiece().getColor() == this.getColor()) && !boardMap.get(i).get(c - 'A').getPiece().equals(this)) return true;
						if(suspectedToBlock != null) return true;
						else {
							suspectedToBlock = boardMap.get(i).get(c - 'A').getPiece();
						}
					}else {
						if(suspectedToBlock != null) {
							suspectedToBlock = null;						}
					}
					i--;
					c--;
					counter++;

					if(i < 1) i = Board.getInstance().getBoardSize();
					if(c < Board.getInstance().getColumnLowerbond()) c = Board.getInstance().getColumnUpperbond();
				}
				while((i != targetLocation.getRow() && c != targetLocation.getColumn() ) &&  counter <= 8);

				break;
			}
			case DOWN_RIGHT:{//
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();
				int counter = 1;

				do {
					if(boardMap.get(i).get(c - 'A').getPiece() != null) {
						if((boardMap.get(i).get(c - 'A').getPiece().getColor() == this.getColor()) && !boardMap.get(i).get(c - 'A').getPiece().equals(this)) return true;
						if(suspectedToBlock != null) return true;
						else {
							suspectedToBlock = boardMap.get(i).get(c - 'A').getPiece();
						}
					}else {
						if(suspectedToBlock != null) {
							suspectedToBlock = null;
						}
					}
					i--;
					c++;
					counter++;

					if(i < 1) i = Board.getInstance().getBoardSize();
					if(c > Board.getInstance().getColumnUpperbond()) c = Board.getInstance().getColumnLowerbond();
				}
				while((i != targetLocation.getRow() && c != targetLocation.getColumn() ) &&  counter <= 8);

				break;
			}
			default: return false;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "Q-"+this.getColor();
	}

}
