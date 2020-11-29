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

	
	/**
	 * Method used to move a piece by selected direction
	 * @param targetTile
	 * @param direction
	 * @return 
	 */
	public boolean move(Tile targetTile, Directions direction) {
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
				try {
					this.setLocation(targetLocation);
					board.addPiece(this);
					Game.getInstance().getTurn().setLastPieceMoved(this);
					return true;
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
				
			}
		}
		System.out.println("Illegal Move!");
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
			case DOWN_LEFT:{//
				
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
	public HashMap<Integer,HashMap<Character,Tile>> getAllAvailableMovesByDirection(Directions direction) throws Exception{
		HashMap<Integer,HashMap<Character,Tile>> tiles = new HashMap<>();
		for(int i = 1 ; i <= 8 ; i++) {
			for(char c = 'A'; c <='H' ; c++) {
				HashMap<Character,Tile> temp = new HashMap<>();
				temp.put(c, null);
				tiles.put(i, temp);
			}
		}
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
							if(board.getTileInLocation(location).getPiece().getColor().equals(this.getColor())) return tiles;
						if(board.getTileInLocation(location).getPiece() != null) {
							if((board.getTileInLocation(location).getPiece().getColor() == this.getColor()) && !board.getTileInLocation(location).getPiece().equals(this)) return tiles;
							if(suspectedToBlock != null) return tiles;
							else {
								suspectedToBlock = board.getTileInLocation(location).getPiece();

							}
						}else {
							if(suspectedToBlock != null) {
								HashMap<Character,Tile> temp = tiles.get(suspectedToBlock.getLocation().getRow());
								temp.put(suspectedToBlock.getLocation().getColumn(), board.getTileInLocation(suspectedToBlock.getLocation()));
								suspectedToBlock = null;
							}else {
								tiles.get(location.getRow()).put(location.getColumn(), board.getTileInLocation(getLocation()));

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
							if(board.getTileInLocation(location).getPiece().getColor().equals(this.getColor())) return tiles;
						if(board.getTileInLocation(location).getPiece() != null) {
							if((board.getTileInLocation(location).getPiece().getColor() == this.getColor()) && !board.getTileInLocation(location).getPiece().equals(this)) return tiles;
							if(suspectedToBlock != null) return tiles;
							else {
								suspectedToBlock = board.getTileInLocation(location).getPiece();

							}
						}else {
							if(suspectedToBlock != null) {
								HashMap<Character,Tile> temp = tiles.get(suspectedToBlock.getLocation().getRow());
								temp.put(suspectedToBlock.getLocation().getColumn(), board.getTileInLocation(suspectedToBlock.getLocation()));
								suspectedToBlock = null;
							}else {
								tiles.get(location.getRow()).put(location.getColumn(), board.getTileInLocation(getLocation()));

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
							if(board.getTileInLocation(location).getPiece().getColor().equals(this.getColor())) return tiles;
						if(board.getTileInLocation(location).getPiece() != null) {
							if((board.getTileInLocation(location).getPiece().getColor() == this.getColor()) && !board.getTileInLocation(location).getPiece().equals(this)) return tiles;
							if(suspectedToBlock != null) return tiles;
							else {
								suspectedToBlock = board.getTileInLocation(location).getPiece();

							}
						}else {
							if(suspectedToBlock != null) {
								HashMap<Character,Tile> temp = tiles.get(suspectedToBlock.getLocation().getRow());
								temp.put(suspectedToBlock.getLocation().getColumn(), board.getTileInLocation(suspectedToBlock.getLocation()));
								suspectedToBlock = null;
							}else {
								tiles.get(location.getRow()).put(location.getColumn(), board.getTileInLocation(getLocation()));

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
							if(board.getTileInLocation(location).getPiece().getColor().equals(this.getColor())) return tiles;
						if(board.getTileInLocation(location).getPiece() != null) {
							if((board.getTileInLocation(location).getPiece().getColor() == this.getColor()) && !board.getTileInLocation(location).getPiece().equals(this)) return tiles;
							if(suspectedToBlock != null) return tiles;
							else {
								suspectedToBlock = board.getTileInLocation(location).getPiece();

							}
						}else {
							if(suspectedToBlock != null) {
								HashMap<Character,Tile> temp = tiles.get(suspectedToBlock.getLocation().getRow());
								temp.put(suspectedToBlock.getLocation().getColumn(), board.getTileInLocation(suspectedToBlock.getLocation()));
								suspectedToBlock = null;
							}else {
								tiles.get(location.getRow()).put(location.getColumn(), board.getTileInLocation(getLocation()));

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
			default: return tiles;
		}
		
		return tiles;
	}
	
	/**
	 *  
	 * @param targetLocation 
	 * @param direction
	 * @return Pieces count in a specific direction (excluding this object)
	 * @throws Exception 
	 */
	public Integer getPiecesCountByDirection(Location targetLocation, Directions direction) throws Exception {
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
		Location UL =pieceLoc.addToLocationDiagonally(Directions.UP_LEFT, 1);
		Location UR =pieceLoc.addToLocationDiagonally(Directions.UP_RIGHT, 1);
		Location DL =pieceLoc.addToLocationDiagonally(Directions.DOWN_LEFT, 1);
		Location DR =pieceLoc.addToLocationDiagonally(Directions.DOWN_RIGHT, 1);

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
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return ediblePieces;
	}

}
