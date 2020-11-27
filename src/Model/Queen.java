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

//	@Override
//	//!! call for eating if eating is possible by this move !!
//	//update in Turn lastPieceMoved
//	public void move(Tile targetTile) {
////		Location targetLocation = targetTile.getLocation();
////		if(this.isMoveLegal(targetLocation)) {
////			if(Board.getInstance().canPieceMove(this, targetLocation)) {
////				//			TODO does moving contains eating?
////				this.setLocation(targetLocation);
////			}
////		}
//	}
	
	/**
	 * Method used to move a piece by selected direction
	 * @param targetTile
	 * @param direction
	 * @return 
	 */
	
	
	//update in Turn lastPieceMoved
	public boolean move(Tile targetTile, Directions direction) {
		Board board = Board.getInstance();	
		Turn turn = Game.getInstance().getTurn();
		Location targetLocation = targetTile.getLocation();
		if(this.isMoveLegalByDirection(targetLocation, direction)) {
			if(board.canPieceMove(this, targetLocation, direction)) {
				Piece toEat = getEdiblePieceByDirection(targetTile.getLocation(), direction);
				if(toEat != null)
				{
//					board.eat(this, toEat);
					board.getTilesMap().get(toEat.getLocation().getRow()).get(toEat.getLocation().getColumn() - board.getColumnLowerBound()).setPiece(null);
					board.getPieces().remove(toEat);
				}
				//board.removePiece(this);
				board.getTilesMap().get(this.getLocation().getRow()).get(this.getLocation().getColumn() - board.getColumnLowerBound()).setPiece(null);
				targetTile.setPiece(this);
				try {
					this.setLocation(targetLocation);
					turn.setLastPieceMoved(this);
					return true;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
				
			}
		}
		return false;
	}

	@Override
	public boolean isMoveLegal(Location targetLocation) { 
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
		Board board =Board.getInstance();
		if(getLocation().getRow() == targetLocation.getRow() || getLocation().getColumn() == targetLocation.getColumn()) return false;
		switch(direction) {
			case UP_LEFT:{
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();

				do {
					if(targetLocation.getRow() == i && targetLocation.getColumn() == c) return true;
					i++;
					c--;
					if(i > board.getBoardSize()) i = 1;
					if(c < board.getColumnLowerBound()) c = board.getColumnUpperBound();
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
					if(i > board.getBoardSize()) i = 1;
					if(c > board.getColumnUpperBound()) c = board.getColumnLowerBound();
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
					if(i < 1) i = board.getBoardSize();
					if(c < board.getColumnLowerBound()) c = board.getColumnUpperBound();
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
					if(i < 1) i = board.getBoardSize();
					if(c > board.getColumnUpperBound()) c = board.getColumnLowerBound();
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
		temp  = getEdiblePieceByDirection(Directions.UP_LEFT);
		if(temp != null)
			pieces.add(temp);
		temp  = getEdiblePieceByDirection(Directions.UP_RIGHT);
		if(temp != null)
			pieces.add(temp);
		temp  = getEdiblePieceByDirection(Directions.DOWN_LEFT);
		if(temp != null)
			pieces.add(temp);
		temp  = getEdiblePieceByDirection(Directions.DOWN_RIGHT);
		if(temp != null)
			pieces.add(temp);
		
		return pieces;
	}
	
	//TODO check if 'H' needs to be fixed also
	/**
	 * 
	 * @param direction
	 * @return Object of Edible Piece, otherwise null
	 */
	@Override
	public Piece getEdiblePieceByDirection(Directions direction){
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
					if(i > board.getBoardSize()) i = 1;
					if(c < board.getColumnLowerBound()) c = board.getColumnUpperBound();
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
					if(i > board.getBoardSize()) i = 1;
					if(c > board.getColumnUpperBound()) c = board.getColumnLowerBound();
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
					if(i < 1) i = board.getBoardSize();
					if(c < board.getColumnLowerBound()) c = board.getColumnUpperBound();
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
					if(i < 1) i = board.getBoardSize();
					if(c > board.getColumnUpperBound()) c = board.getColumnLowerBound();
				}
				while(i != getLocation().getRow() && c != getLocation().getColumn());

				break;
			}
			default: return null;
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param targetLocation
	 * @param direction of eating/moving
	 * @return Piece -edible piece
	 */
	
	public Piece getEdiblePieceByDirection(Location targetLocation, Directions direction) {
		Board board = Board.getInstance();
		HashMap<Integer, ArrayList<Tile>> boardMap = board.getTilesMap();
		Piece suspectedToBlock = null;
		
		switch(direction) {
			case UP_LEFT:{
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();
				int counter = 1;
				do {

					if(boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece() != null) {
						if((boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece().getColor() != this.getColor()) && !boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece().equals(this)) {
							if(suspectedToBlock != null) return null;
							else {
								suspectedToBlock = boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece();
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
					if(i > board.getBoardSize()) i = 1;
					if(c < board.getColumnLowerBound()) c = board.getColumnUpperBound();
				}
				while((i != targetLocation.getRow() && c != targetLocation.getColumn() ) &&  counter <= board.getBoardSize());
	
				if(i == targetLocation.getRow() && c == targetLocation.getColumn() ) {
					if(boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece() != null) {
						if((boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece().getColor() != this.getColor()) && !boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece().equals(this)) {
							if(suspectedToBlock != null) return null;
							else {
								suspectedToBlock = boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece();
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

					if(boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece() != null) {
						if((boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece().getColor() != this.getColor()) && !boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece().equals(this)) {
							if(suspectedToBlock != null) return null;
							else {
								suspectedToBlock = boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece();
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
	
					if(i > board.getBoardSize()) i = 1;
					if(c > board.getColumnUpperBound()) c = board.getColumnLowerBound();
				}
				while((i != targetLocation.getRow() && c != targetLocation.getColumn() ) &&  counter <= board.getBoardSize());
	
				if(i == targetLocation.getRow() && c == targetLocation.getColumn() ) {
					if(boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece() != null) {
						if((boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece().getColor() != this.getColor()) && !boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece().equals(this)) {
							if(suspectedToBlock != null) return null;
							else {
								suspectedToBlock = boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece();
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

					if(boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece() != null) {
						if((boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece().getColor() != this.getColor()) && !boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece().equals(this)) {
							if(suspectedToBlock != null) return null;
							else {
								suspectedToBlock = boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece();
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
	
					if(i < 1) i = board.getBoardSize();
					if(c < board.getColumnLowerBound()) c = board.getColumnUpperBound();
				}
				while((i != targetLocation.getRow() && c != targetLocation.getColumn() ) &&  counter <= board.getBoardSize());
	
				if(i == targetLocation.getRow() && c == targetLocation.getColumn() ) {
					if(boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece() != null) {
						if((boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece().getColor() != this.getColor()) && !boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece().equals(this)) {
							if(suspectedToBlock != null) return null;
							else {
								suspectedToBlock = boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece();
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

					if(boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece() != null) {
						if((boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece().getColor() != this.getColor()) && !boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece().equals(this)) {
							if(suspectedToBlock != null) return null;
							else {
								suspectedToBlock = boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece();
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
	
					if(i < 1) i = board.getBoardSize();
					if(c > board.getColumnUpperBound()) c = board.getColumnLowerBound();
				}
				while((i != targetLocation.getRow() && c != targetLocation.getColumn() ) &&  counter <= board.getBoardSize());
	
				if(i == targetLocation.getRow() && c == targetLocation.getColumn() ) {
					if(boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece() != null) {
						if((boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece().getColor() != this.getColor()) && !boardMap.get(i).get(c -board.getColumnLowerBound()).getPiece().equals(this)) {
							if(suspectedToBlock != null) return null;
							else {
								suspectedToBlock = boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece();
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
		if(boardMap.get(targetLocation.getRow()).get(targetLocation.getColumn() - board.getColumnLowerBound()).getPiece() != null) return true;
		switch(direction) {
			case UP_LEFT:{
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();
				int counter = 1;
				do {

					if(boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece() != null) {
						if((boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece().getColor() == this.getColor()) && !boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece().equals(this)) return true;
						if(suspectedToBlock != null) return true;
						else {
							suspectedToBlock = boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece();

						}
					}else {
						if(suspectedToBlock != null) {
							suspectedToBlock = null;
						}
					}
					i++;
					c--;
					counter++;
					if(i > board.getBoardSize()) i = 1;
					if(c < board.getColumnLowerBound()) c = board.getColumnUpperBound();
				}
				while((i != targetLocation.getRow() && c != targetLocation.getColumn() ) &&  counter <= board.getBoardSize());
	
				break;
			}
			case UP_RIGHT:{
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();
				int counter = 1;

				do {

					if(boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece() != null) {
						if((boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece().getColor() == this.getColor()) && !boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece().equals(this)) return true;
						if(suspectedToBlock != null) return true;
						else {
							suspectedToBlock = boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece();

						}
					}else {
						if(suspectedToBlock != null) {
							suspectedToBlock = null;
						}
					}
					i++;
					c++;
					counter++;

					if(i > board.getBoardSize()) i = 1;
					if(c > board.getColumnUpperBound()) c = board.getColumnLowerBound();
				}
				while((i != targetLocation.getRow() && c != targetLocation.getColumn() ) &&  counter <= board.getBoardSize());

				break;
			}
			case DOWN_LEFT:{//
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();
				int counter = 1;

				do {

					if(boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece() != null) {
						if((boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece().getColor() == this.getColor()) && !boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece().equals(this)) return true;
						if(suspectedToBlock != null) return true;
						else {
							suspectedToBlock = boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece();

						}
					}else {
						if(suspectedToBlock != null) {
							suspectedToBlock = null;						}
					}
					i--;
					c--;
					counter++;

					if(i < 1) i = board.getBoardSize();
					if(c < board.getColumnLowerBound()) c = board.getColumnUpperBound();
				}
				while((i != targetLocation.getRow() && c != targetLocation.getColumn() ) &&  counter <= board.getBoardSize());

				break;
			}
			case DOWN_RIGHT:{//
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();
				int counter = 1;

				do {

					if(boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece() != null) {
						if((boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece().getColor() == this.getColor()) && !boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece().equals(this)) return true;
						if(suspectedToBlock != null) return true;
						else {
							suspectedToBlock = boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece();

						}
					}else {
						if(suspectedToBlock != null) {
							suspectedToBlock = null;
						}
					}
					i--;
					c++;
					counter++;

					if(i < 1) i = board.getBoardSize();
					if(c > board.getColumnUpperBound()) c = board.getColumnLowerBound();
				}
				while((i != targetLocation.getRow() && c != targetLocation.getColumn() ) &&  counter <= board.getBoardSize());

				break;
			}
			default: return false;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "Queen-"+this.getColor();
	}

	@Override
	public boolean canEatPiece(Piece targetPiece) {
		// TODO Auto-generated method stub
		return false;
	}

}
