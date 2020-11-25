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
	//!! call for eating if eating is possible by this move !!
	//update in Turn lastPieceMoved
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
	
	//!! call for eating if eating is possible by this move!!
	//update in Turn lastPieceMoved
	public void move(Tile targetTile, Directions direction) { // TODO NOT DONE
		Location targetLocation = targetTile.getLocation();
		if(this.isMoveLegal(targetLocation)) {
			if(Board.getInstance().canPieceMove(this, targetLocation, direction)) {
				//			TODO does moving contains eating?
				this.setLocation(targetLocation);
			}
		}
	}

	@Override
	public boolean isMoveLegal(Location targetLocation) { //TODO NOT DONE
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
					c++;
					if(i > Board.getInstance().getBoardSize()) i = 1;
					if(c > Board.getInstance().getColumnLowerbond()) c = Board.getInstance().getColumnUpperbond();
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
					i++;
					c++;
					if(i == 1) i = Board.getInstance().getBoardSize();
					if(c > Board.getInstance().getColumnLowerbond()) c = Board.getInstance().getColumnUpperbond();
				}
				while(i != getLocation().getRow() && c != getLocation().getColumn());
				
				break;
			}
			case DOWN_RIGHT:{//
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();

				do {
					if(targetLocation.getRow() == i && targetLocation.getColumn() == c) return true;
					i++;
					c++;
					if(i == 1) i = Board.getInstance().getBoardSize();
					if(c > Board.getInstance().getColumnUpperbond()) c = Board.getInstance().getColumnLowerbond();
				}
				while(i != getLocation().getRow() && c != getLocation().getColumn());
				
				break;
			}
			default: return false;
		}
		
		return true;
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
					c++;
					if(i > Board.getInstance().getBoardSize()) i = 1;
					if(c > Board.getInstance().getColumnLowerbond()) c = Board.getInstance().getColumnUpperbond();
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
					i++;
					c++;
					if(i == 1) i = Board.getInstance().getBoardSize();
					if(c > Board.getInstance().getColumnLowerbond()) c = Board.getInstance().getColumnUpperbond();
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
					i++;
					c++;
					if(i == 1) i = Board.getInstance().getBoardSize();
					if(c > Board.getInstance().getColumnUpperbond()) c = Board.getInstance().getColumnLowerbond();
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
	 * @param direction
	 * @return Piece edible piece
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
					if(boardMap.get(i).get(board.getColumnUpperbond() - c).getPiece() != null) {
						if(suspectedToBlock != null) return null;
						else {
							suspectedToBlock = boardMap.get(i).get(board.getColumnUpperbond() - c).getPiece();
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
					if(c > Board.getInstance().getColumnLowerbond()) c = Board.getInstance().getColumnUpperbond();
				}
				while((i != targetLocation.getRow() && c != targetLocation.getColumn() ) &&  counter <= board.getBoardSize());
	
				break;
			}
			case UP_RIGHT:{
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();
				int counter = 1;
	
				do {
					if(boardMap.get(i).get(board.getColumnUpperbond() - c).getPiece() != null) {
						if(suspectedToBlock != null) return null;
						else {
							suspectedToBlock = boardMap.get(i).get(board.getColumnUpperbond() - c).getPiece();
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
				while((i != targetLocation.getRow() && c != targetLocation.getColumn() ) &&  counter <= board.getBoardSize());
	
				break;
			}
			case DOWN_LEFT:{//
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();
				int counter = 1;
	
				do {
					if(boardMap.get(i).get(board.getColumnUpperbond() - c).getPiece() != null) {
						if(suspectedToBlock != null) return null;
						else {
							suspectedToBlock = boardMap.get(i).get(board.getColumnUpperbond() - c).getPiece();
						}
					}else {
						if(suspectedToBlock != null) {
							return suspectedToBlock;
						}
					}
					i++;
					c++;
					counter++;
	
					if(i == 1) i = Board.getInstance().getBoardSize();
					if(c > Board.getInstance().getColumnLowerbond()) c = Board.getInstance().getColumnUpperbond();
				}
				while((i != targetLocation.getRow() && c != targetLocation.getColumn() ) &&  counter <= board.getBoardSize());
	
				break;
			}
			case DOWN_RIGHT:{//
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();
				int counter = 1;
	
				do {
					if(boardMap.get(i).get(board.getColumnUpperbond() - c).getPiece() != null) {
						if(suspectedToBlock != null) return null;
						else {
							suspectedToBlock = boardMap.get(i).get(board.getColumnUpperbond() - c).getPiece();
						}
					}else {
						if(suspectedToBlock != null) {
							return suspectedToBlock;
						}
					}
					i++;
					c++;
					counter++;
	
					if(i == 1) i = Board.getInstance().getBoardSize();
					if(c > Board.getInstance().getColumnUpperbond()) c = Board.getInstance().getColumnLowerbond();
				}
				while((i != targetLocation.getRow() && c != targetLocation.getColumn() ) &&  counter <= board.getBoardSize());
	
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
		
		switch(direction) {
			case UP_LEFT:{
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();
				int counter = 1;
				do {
					if(boardMap.get(i).get(board.getColumnUpperbond() - c).getPiece() != null) {
						if(suspectedToBlock != null) return true;
						else {
							suspectedToBlock = boardMap.get(i).get(board.getColumnUpperbond() - c).getPiece();
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
					if(c > Board.getInstance().getColumnLowerbond()) c = Board.getInstance().getColumnUpperbond();
				}
				while((i != targetLocation.getRow() && c != targetLocation.getColumn() ) &&  counter <= board.getBoardSize());
	
				break;
			}
			case UP_RIGHT:{
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();
				int counter = 1;

				do {
					if(boardMap.get(i).get(board.getColumnUpperbond() - c).getPiece() != null) {
						if(suspectedToBlock != null) return true;
						else {
							suspectedToBlock = boardMap.get(i).get(board.getColumnUpperbond() - c).getPiece();
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
				while((i != targetLocation.getRow() && c != targetLocation.getColumn() ) &&  counter <= board.getBoardSize());

				break;
			}
			case DOWN_LEFT:{//
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();
				int counter = 1;

				do {
					if(boardMap.get(i).get(board.getColumnUpperbond() - c).getPiece() != null) {
						if(suspectedToBlock != null) return true;
						else {
							suspectedToBlock = boardMap.get(i).get(board.getColumnUpperbond() - c).getPiece();
						}
					}else {
						if(suspectedToBlock != null) {
							suspectedToBlock = null;						}
					}
					i++;
					c++;
					counter++;

					if(i == 1) i = Board.getInstance().getBoardSize();
					if(c > Board.getInstance().getColumnLowerbond()) c = Board.getInstance().getColumnUpperbond();
				}
				while((i != targetLocation.getRow() && c != targetLocation.getColumn() ) &&  counter <= board.getBoardSize());

				break;
			}
			case DOWN_RIGHT:{//
				
				int i = getLocation().getRow();
				int c = getLocation().getColumn();
				int counter = 1;

				do {
					if(boardMap.get(i).get(board.getColumnUpperbond() - c).getPiece() != null) {
						if(suspectedToBlock != null) return true;
						else {
							suspectedToBlock = boardMap.get(i).get(board.getColumnUpperbond() - c).getPiece();
						}
					}else {
						if(suspectedToBlock != null) {
							suspectedToBlock = null;
						}
					}
					i++;
					c++;
					counter++;

					if(i == 1) i = Board.getInstance().getBoardSize();
					if(c > Board.getInstance().getColumnUpperbond()) c = Board.getInstance().getColumnLowerbond();
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
		return "Q-"+this.getColor();
	}

}
