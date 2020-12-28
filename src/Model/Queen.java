package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import Exceptions.IllegalMoveException;
import Exceptions.LocationException;
import Utils.Directions;
import Utils.PrimaryColor;

/**
 * 
 * @author aseel
 * This class is a concrete subclass that extends Piece class.
 * The class describes attributes and actions of Queen game piece. 
 */
public class Queen extends Piece{

	/**
	 * Queen Constructor
	 * 
	 * @param id
	 * @param color
	 * @param location
	 */
	public Queen(int id,PrimaryColor color, Location location) {
		super(id,color, location);
	}

	
	@Override
	public boolean move(Tile targetTile, Directions direction) throws IllegalMoveException, LocationException {
		Board board = Board.getInstance();	
		Location targetLocation = targetTile.getLocation();
		if(this.isMoveLegalByDirection(targetLocation, direction)) {
			if(board.canPieceMove(this, targetLocation, direction)) {
				Piece toEat = getEdiblePieceByDirection(targetTile.getLocation(), direction);
				if(toEat != null)
				{
					board.eat(this, toEat);
				}
				board.removePiece(this);
				this.setLocation(targetLocation);
				board.addPiece(this);
				Game.getInstance().getTurn().setLastPieceMoved(this);
				return true;
			}
		}
		throw new IllegalMoveException("Illegal Move!");

	}

	@Override
	public boolean isMoveLegal(Location targetLocation) throws IllegalMoveException { 
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
	 * @throws IllegalMoveException 
	 */
	public boolean isMoveLegalByDirection(Location targetLocation, Directions direction) throws IllegalMoveException {
		if(direction == null) throw new IllegalMoveException("Illegal Direction");
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
				if(this.getLocation().getRow() != i && this.getLocation().getColumn() != c) {
					if(boardMap.get(i).get(c - 'A').getPiece() != null)
						if(boardMap.get(i).get(c - 'A').getPiece().getColor().equals(this.getColor())) break;
					if(boardMap.get(i).get(c - 'A').getPiece() != null) {
						if(suspectedToAdd != null) break;
						else {
							if(!boardMap.get(i).get(c - 'A').getPiece().getColor().equals(this.getColor()))
								suspectedToAdd = boardMap.get(i).get(c - 'A').getPiece();
						}
					}else {
						if(suspectedToAdd != null) {
							System.out.println(suspectedToAdd.getLocation());
							return suspectedToAdd;
						}
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
				if(this.getLocation().getRow() != i && this.getLocation().getColumn() != c) {
					if(boardMap.get(i).get(c - 'A').getPiece() != null)
						if(boardMap.get(i).get(c - 'A').getPiece().getColor().equals(this.getColor())) break;
					if(boardMap.get(i).get(c - 'A').getPiece() != null) {
						if(suspectedToAdd != null) break;
						else {
							if(!boardMap.get(i).get(c - 'A').getPiece().getColor().equals(this.getColor()))
								suspectedToAdd = boardMap.get(i).get(c - 'A').getPiece();
						}
					}else {
						if(suspectedToAdd != null) {
							return suspectedToAdd;

						}
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
		case DOWN_LEFT:{

			int i = getLocation().getRow();
			int c = getLocation().getColumn();

			do {
				if(this.getLocation().getRow() != i && this.getLocation().getColumn() != c) {
					if(boardMap.get(i).get(c - 'A').getPiece() != null)
						if(boardMap.get(i).get(c - 'A').getPiece().getColor().equals(this.getColor())) break;
					if(boardMap.get(i).get(c - 'A').getPiece() != null) {
						if(suspectedToAdd != null) break;
						else {
							if(!boardMap.get(i).get(c - 'A').getPiece().getColor().equals(this.getColor()))
								suspectedToAdd = boardMap.get(i).get(c - 'A').getPiece();
						}
					}else {
						if(suspectedToAdd != null) {
							return suspectedToAdd;
						}
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
		case DOWN_RIGHT:{

			int i = getLocation().getRow();
			int c = getLocation().getColumn();

			do {
				if(this.getLocation().getRow() != i && this.getLocation().getColumn() != c) {
					if(boardMap.get(i).get(c - 'A').getPiece() != null)
						if(boardMap.get(i).get(c - 'A').getPiece().getColor().equals(this.getColor())) break;
					if(boardMap.get(i).get(c - 'A').getPiece() != null) {
						if(suspectedToAdd != null) break;
						else {
							if(!boardMap.get(i).get(c - 'A').getPiece().getColor().equals(this.getColor()))
								suspectedToAdd = boardMap.get(i).get(c - 'A').getPiece();
						}
					}else {
						if(suspectedToAdd != null) {
							return suspectedToAdd;
						}
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

	@Override 
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

	/**
	 * Checks if piece is blocked in a specific direction
	 * @param targetLocation
	 * @param direction
	 * @return true if piece blocked, otherwise false
	 */
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
						if(!boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece().getColor().equals(this.getColor()))
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
					if((boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece().getColor() == this.getColor()) 
							&& !boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece().equals(this)) return true;
					if(suspectedToBlock != null) return true;
					else {
						if(!boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece().getColor().equals(this.getColor()))
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
						if(!boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece().getColor().equals(this.getColor()))
							suspectedToBlock = boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece();
					}
				}else {
					if(suspectedToBlock != null) {
						suspectedToBlock = null;
					}
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
						if(!boardMap.get(i).get(c - board.getColumnLowerBound()).getPiece().getColor().equals(this.getColor()))
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

	/**
	 * 
	 * @param direction
	 * @return HashMap contains tiles, main key is the row (Integer), internal map key is column (Character)
	 * @throws Exception if there is any missing tile in board
	 */
	public ArrayList<Tile> getAllAvailableMovesByDirection(Directions direction) throws Exception{
		HashSet<Tile> tiles = new HashSet<>();

		Board board =Board.getInstance();
		Piece suspectedToBlock = null;
		switch(direction) {
		case UP_LEFT:{
			int i = getLocation().getRow();
			int c = getLocation().getColumn();

			do {
				Location location = new Location(i, (char)c);
				if(!this.getLocation().equals(location)) {
					if(board.getTileInLocation(location).getPiece() != null)
						if(board.getTileInLocation(location).getPiece().getColor().equals(this.getColor())) return new ArrayList<Tile>(tiles);
					if(board.getTileInLocation(location).getPiece() != null) {
						if((board.getTileInLocation(location).getPiece().getColor() == this.getColor()) && !board.getTileInLocation(location).getPiece().equals(this)) return new ArrayList<Tile>(tiles);
						if(suspectedToBlock != null) return new ArrayList<Tile>(tiles);
						else {
							suspectedToBlock = board.getTileInLocation(location).getPiece();

						}
					}else {
						if(suspectedToBlock != null) {
							//								HashMap<Character,Tile> temp = tiles.get(suspectedToBlock.getLocation().getRow());
							//								temp.put(suspectedToBlock.getLocation().getColumn(), board.getTileInLocation(suspectedToBlock.getLocation()));
							tiles.add(board.getTileInLocation(location));

							suspectedToBlock = null;
						}else {
							tiles.add(board.getTileInLocation(location));

						}
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
				Location location = new Location(i, (char)c);
				if(!this.getLocation().equals(location)) {
					if(board.getTileInLocation(location).getPiece() != null)
						if(board.getTileInLocation(location).getPiece().getColor().equals(this.getColor())) return new ArrayList<Tile>(tiles);
					if(board.getTileInLocation(location).getPiece() != null) {
						if((board.getTileInLocation(location).getPiece().getColor() == this.getColor()) && !board.getTileInLocation(location).getPiece().equals(this)) return new ArrayList<Tile>(tiles);
						if(suspectedToBlock != null) return new ArrayList<Tile>(tiles);
						else {
							suspectedToBlock = board.getTileInLocation(location).getPiece();

						}
					}else {
						if(suspectedToBlock != null) {
							//								HashMap<Character,Tile> temp = tiles.get(suspectedToBlock.getLocation().getRow());
							//								temp.put(suspectedToBlock.getLocation().getColumn(), board.getTileInLocation(suspectedToBlock.getLocation()));
							tiles.add(board.getTileInLocation(location));

							suspectedToBlock = null;
						}else {
							tiles.add(board.getTileInLocation(location));

						}
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
		case DOWN_LEFT:{

			int i = getLocation().getRow();
			int c = getLocation().getColumn();

			do {
				Location location = new Location(i, (char)c);
				if(!this.getLocation().equals(location)) {
					if(board.getTileInLocation(location).getPiece() != null)
						if(board.getTileInLocation(location).getPiece().getColor().equals(this.getColor())) return new ArrayList<Tile>(tiles);
					if(board.getTileInLocation(location).getPiece() != null) {
						if((board.getTileInLocation(location).getPiece().getColor() == this.getColor()) && !board.getTileInLocation(location).getPiece().equals(this)) return new ArrayList<Tile>(tiles);
						if(suspectedToBlock != null) return new ArrayList<Tile>(tiles);
						else {
							suspectedToBlock = board.getTileInLocation(location).getPiece();

						}
					}else {
						if(suspectedToBlock != null) {
							//								HashMap<Character,Tile> temp = tiles.get(suspectedToBlock.getLocation().getRow());
							//								temp.put(suspectedToBlock.getLocation().getColumn(), board.getTileInLocation(suspectedToBlock.getLocation()));
							tiles.add(board.getTileInLocation(location));

							suspectedToBlock = null;
						}else {
							tiles.add(board.getTileInLocation(location));

						}
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
		case DOWN_RIGHT:{

			int i = getLocation().getRow();
			int c = getLocation().getColumn();

			do {
				Location location = new Location(i, (char)c);
				if(!this.getLocation().equals(location)) {
					if(board.getTileInLocation(location).getPiece() != null)
						if(board.getTileInLocation(location).getPiece().getColor().equals(this.getColor())) return new ArrayList<Tile>(tiles);
					if(board.getTileInLocation(location).getPiece() != null) {
						if((board.getTileInLocation(location).getPiece().getColor() == this.getColor()) && !board.getTileInLocation(location).getPiece().equals(this)) return new ArrayList<Tile>(tiles);
						if(suspectedToBlock != null) return new ArrayList<Tile>(tiles);
						else {
							suspectedToBlock = board.getTileInLocation(location).getPiece();

						}
					}else {
						if(suspectedToBlock != null) {
							//								HashMap<Character,Tile> temp = tiles.get(suspectedToBlock.getLocation().getRow());
							//								temp.put(suspectedToBlock.getLocation().getColumn(), board.getTileInLocation(suspectedToBlock.getLocation()));
							tiles.add(board.getTileInLocation(location));

							suspectedToBlock = null;
						}else {
							tiles.add(board.getTileInLocation(location));

						}
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
		default: return new ArrayList<Tile>(tiles);
		}

		return new ArrayList<Tile>(tiles);
	}

	/**
	 * counts the number of pieces between this queen location and targetLocation in the direction
	 * @param targetLocation of planned move
	 * @param direction the direction of the planned move
	 * @return Pieces count in a specific direction (excluding this object)
	 * @throws LocationException 
	 */
	public Integer getPiecesCountByDirection(Location targetLocation, Directions direction) throws LocationException {
		Board board = Board.getInstance();
		Integer count = 0;
		switch(direction) {
		case UP_LEFT:{

			int i = getLocation().getRow();
			int c = getLocation().getColumn();

			do {
				if(this.getLocation().getRow() != i && this.getLocation().getColumn() != c) {
					if(board.getTileInLocation(new Location(i, (char) c)).getPiece() != null)
						count++;
				}
				i++;
				c--;
				if(i > board.getBoardSize()) i = 1;
				if(c < board.getColumnLowerBound()) c = board.getColumnUpperBound();
			}
			while((i != getLocation().getRow() && c != getLocation().getColumn()) 
					&&
					(i != targetLocation.getRow() && c != targetLocation.getColumn()) );

			break;
		}
		case UP_RIGHT:{

			int i = getLocation().getRow();
			int c = getLocation().getColumn();

			do {
				if(this.getLocation().getRow() != i && this.getLocation().getColumn() != c) {
					if(board.getTileInLocation(new Location(i, (char) c)).getPiece() != null)
						count++;
				}
				i++;
				c++;
				if(i > board.getBoardSize()) i = 1;
				if(c > board.getColumnUpperBound()) c = board.getColumnLowerBound();
			}
			while((i != getLocation().getRow() && c != getLocation().getColumn()) 
					&&
					(i != targetLocation.getRow() && c != targetLocation.getColumn()) );
			break;
		}
		case DOWN_LEFT:{

			int i = getLocation().getRow();
			int c = getLocation().getColumn();

			do {
				if(this.getLocation().getRow() != i && this.getLocation().getColumn() != c) {
					if(board.getTileInLocation(new Location(i, (char) c)).getPiece() != null)
						count++;
				}
				i--;
				c--;
				if(i < 1) i = board.getBoardSize();
				if(c < board.getColumnLowerBound()) c = board.getColumnUpperBound();
			}
			while((i != getLocation().getRow() && c != getLocation().getColumn()) 
					&&
					(i != targetLocation.getRow() && c != targetLocation.getColumn()) );				
			break;
		}
		case DOWN_RIGHT:{

			int i = getLocation().getRow();
			int c = getLocation().getColumn();

			do {
				if(this.getLocation().getRow() != i && this.getLocation().getColumn() != c) {
					if(board.getTileInLocation(new Location(i, (char) c)).getPiece() != null)
						count++;
				}
				i--;
				c++;
				if(i < 1) i = board.getBoardSize();
				if(c > board.getColumnUpperBound()) c = board.getColumnLowerBound();
			}
			while((i != getLocation().getRow() && c != getLocation().getColumn()) 
					&&
					(i != targetLocation.getRow() && c != targetLocation.getColumn()) );
			break;
		}
		default: return count;
		}

		return count;
	}

	@Override
	public String toString() {
		return "Queen-"+this.getColor()+" in "+getLocation();
	}

	@Override
	public boolean canEatPiece(Piece targetPiece) {
		ArrayList<Piece> pieces = getEdiblePieces();
		return pieces.contains(targetPiece);
	}


	@Override
	public ArrayList<Piece> getMustEdiblePieces() {
		ArrayList<Piece> ediblePieces = new ArrayList<Piece>();
		Board board = Board.getInstance();
		Player currPlayer = Game.getInstance().getTurn().getCurrentPlayer();
		Location pieceLoc = this.getLocation();
		Location UL = null;
		Location UR = null;
		Location DL = null;
		Location DR = null;

		try {
			UL = pieceLoc.addToLocationDiagonally(Directions.UP_LEFT, 1);
			UR =pieceLoc.addToLocationDiagonally(Directions.UP_RIGHT, 1);
			DL =pieceLoc.addToLocationDiagonally(Directions.DOWN_LEFT, 1);
			DR =pieceLoc.addToLocationDiagonally(Directions.DOWN_RIGHT, 1);
		} catch (LocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			if(UL!=null) {
				Piece p =board.getTileInLocation(UL).getPiece();
				if(p!=null) {
					if(!p.getColor().equals(currPlayer.getColor())) {
						Location jmpLoc = UL.addToLocationDiagonally(Directions.UP_LEFT, 1);
						if(jmpLoc == null) {
							jmpLoc = UL.rotateLocation(Directions.UP_LEFT);
							if(jmpLoc == null) System.out.println("Failed to locate a specific location!");
						}
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
						if(jmpLoc == null) {
							jmpLoc = UR.rotateLocation(Directions.UP_RIGHT);
							if(jmpLoc == null) System.out.println("Failed to locate a specific location!");
						}
						if(jmpLoc!=null && board.getTileInLocation(jmpLoc).isEmpty()) {
							ediblePieces.add(p);
						}
					}
				}
			}


			if(DL!=null) {
				Piece p =board.getTileInLocation(DL).getPiece();
				if(p!=null) {
					if(!p.getColor().equals(currPlayer.getColor())) {
						Location jmpLoc = DL.addToLocationDiagonally(Directions.DOWN_LEFT, 1);
						if(jmpLoc == null) {
							jmpLoc = DL.rotateLocation(Directions.DOWN_LEFT);
							if(jmpLoc == null) System.out.println("Failed to locate a specific location!");
						}
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
						if(jmpLoc == null) {
							jmpLoc = DR.rotateLocation(Directions.DOWN_RIGHT);
							if(jmpLoc == null) System.out.println("Failed to locate a specific location!");
						}
						if(jmpLoc!=null && board.getTileInLocation(jmpLoc).isEmpty()) {
							ediblePieces.add(p);
						}
					}
				}
			}

		}
		catch (LocationException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return ediblePieces;
	}


	@Override
	public ArrayList<Tile> getPossibleMoves(PrimaryColor playerColor) {
		HashSet<Tile> hs = new HashSet<Tile>();
		try {
			hs.addAll((this).getAllAvailableMovesByDirection(Directions.UP_LEFT));
			hs.addAll((this).getAllAvailableMovesByDirection(Directions.UP_RIGHT));
			hs.addAll((this).getAllAvailableMovesByDirection(Directions.DOWN_LEFT));
			hs.addAll((this).getAllAvailableMovesByDirection(Directions.DOWN_RIGHT));
			hs.remove(null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return new ArrayList<Tile>(hs);
	}




}
