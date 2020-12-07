/**
 * 
 */
package Model;

import java.util.HashMap;

import Exceptions.LocationException;
import Utils.Directions;
import Utils.PrimaryColor;

/**
 * @author aseel
 *
 */
public class Location {
	private int row;
	private char column;

	

	/**
	 * location Constructor
	 * @param row
	 * @param column
	 * @throws Exception 
	 */
	public Location(int row, char column) throws LocationException {
		super();
		this.setRow(row);
		this.setColumn(column);

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
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
		Location other = (Location) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}


	//getters &setters
	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	
	/**
	 * sets row with checking if set is in board boundaries
	 * @param row the row to set
	 * @throws Exception - row out of board boundaries
	 */
	public void setRow(int row) throws LocationException {
		Board board = Board.getInstance();
		if (row>=1 && row<=board.getBoardSize()) {
			this.row = row;
		}else {
			throw new LocationException("row value should be between 1 and "+board.getBoardSize());
		}
	}

	/**
	 * @return the column
	 */
	public char getColumn() {
		return column;
	}

	/**
	 * @param column the column to set
	 * @throws Exception- column out of board boundaries
	 */
	public void setColumn(char column) throws LocationException {
		Board board = Board.getInstance();
		if (column>=board.getColumnLowerBound() && column<=board.getColumnUpperBound()) {
			this.column = column;
		}else {
			throw new LocationException("column value should be a capital letter between "+board.getColumnLowerBound()+" and "+board.getColumnUpperBound());
		}
	}

	/**
	 * updates location values to new location that exists diagonally from current location within board limits.
	 * @param direction UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT
	 * @param steps- number of steps diagonally
	 * @return new location if it's within board boundaries, else returns null
	 * @throws Exception 
	 */
	public Location addToLocationDiagonally(Directions dir,int steps) throws LocationException {
		Board board = Board.getInstance();
		int currRow= this.getRow();
		char currCol = this.getColumn();
		Location updatedLocation=null;

		switch (dir) {
		case UP_LEFT: {
			if(currRow+steps<=board.getBoardSize() && currCol-steps>=board.getColumnLowerBound()) {
				updatedLocation = new Location(currRow+steps, (char)(currCol-steps));
			}
			break;

		}
		case UP_RIGHT:{
			if(currRow+steps<=board.getBoardSize() && currCol+steps<=board.getColumnUpperBound()) {
				updatedLocation = new Location(currRow+steps, (char)(currCol+steps));
			}
			break;
		}
		case DOWN_LEFT:{
			if(currRow-steps>=1 && currCol-steps>=board.getColumnLowerBound()) {
				updatedLocation = new Location(currRow-steps, (char)(currCol-steps));
			}
			break;
		}
		case DOWN_RIGHT:{
			if(currRow-steps>=1 && currCol+steps<=board.getColumnUpperBound()) {
				updatedLocation = new Location(currRow-steps, (char)(currCol+steps));
			}
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + dir);
		}
		return updatedLocation;
	}
	
	public Location rotateLocation(Directions direction) throws Exception {
		Board board = Board.getInstance();
		
		if((this.getColumn() != board.getColumnLowerBound())
				&&
			(this.getColumn() != board.getColumnUpperBound())
				&&
			(this.getRow() != 1)
				&&
			(this.getRow() != board.getBoardSize())){
			
				return null;
			}
		
		switch(direction) {
			case UP_LEFT:{
				int i = this.getRow();
				int c = this.getColumn();
	
				i++;
				c--;
				if(i > board.getBoardSize()) i = 1;
				if(c < board.getColumnLowerBound()) c = board.getColumnUpperBound();
	
				return new Location(i,(char) c);
			}
			case UP_RIGHT:{
				
				int i = this.getRow();
				int c = this.getColumn();
					
				i++;
				c++;
				if(i > board.getBoardSize()) i = 1;
				if(c > board.getColumnUpperBound()) c = board.getColumnLowerBound();

				
				return new Location(i,(char) c);
			}
			case DOWN_LEFT:{
				
				int i = this.getRow();
				int c = this.getColumn();
	
				i--;
				c--;
				if(i < 1) i = board.getBoardSize();
				if(c < board.getColumnLowerBound()) c = board.getColumnUpperBound();

				
				return new Location(i,(char) c);
			}
			case DOWN_RIGHT:{//
				
				int i = this.getRow();
				int c = this.getColumn();
	
				i--;
				c++;
				if(i < 1) i = board.getBoardSize();
				if(c > board.getColumnUpperBound()) c = board.getColumnLowerBound();

				
				return new Location(i,(char) c);
			}
			default: return null;
		}
		
	}

	/**
	 * works only on current black tiles
	 * @param targetLocation
	 * @return HashMap<Directions,Integer> 
	 */
	public HashMap<Directions,Integer>relativeLocationTo(Location targetLocation) {
		HashMap<Directions,Integer> result= new HashMap<Directions,Integer>();

		Directions direction = getRelativeDirection(targetLocation);
		Integer steps = getRelativeNumberOfSteps(targetLocation);

		result.put(direction, steps);
		return result;
	}

	/**
	 * without going through walls??
	 * works for current black tiles only
	 * 
	 * @param targetLocation -black tile location
	 * @return int Absolute number of steps on black tiles between locations, if target location is white return -1
	 */
	public int getRelativeNumberOfSteps(Location targetLocation) {
		int steps = -1;
		int curRow =this.row;
		char curCol = this.column;
		int targetRow = targetLocation.getRow();
		char targetCol = targetLocation.getColumn();

		int rowCmp = targetRow-curRow;
		int colCmp = targetCol-curCol;

		Directions dir = getRelativeDirection(targetLocation);
		switch (dir) {
		case DOWN_STRAIGHT: 
		case UP_STRAIGHT:{
			if(rowCmp%2==0) {
				steps = Math.abs(rowCmp)-(Math.abs(rowCmp)/2);
			}
			break;
		}
		case LEFT_STRAIGHT:
		case RIGHT_STRAIGHT:{
			if(colCmp%2==0) {
				steps = Math.abs(colCmp)-(Math.abs(colCmp)/2);
			}
			break;
		}
		//diagonal location
		default:
			steps = Math.abs(rowCmp);
		}
		return steps;
	};


	/**
	 * gets the direction from this Location to targetLocation
	 * @param targetLocation
	 * @return Directions Enum values, null if direction is not a defined Directions value
	 */
	public Directions getRelativeDirection(Location targetLocation) {
		Directions direction = null ;

		int curRow =this.row;
		char curCol = this.column;
		int targetRow = targetLocation.getRow();
		char targetCol = targetLocation.getColumn();

		int rowCmp = targetRow-curRow;
		int colCmp = targetCol-curCol;
		//up
		if(rowCmp>0) {
			//right
			if(colCmp>0) {
				direction=Directions.UP_RIGHT;
			}
			//left
			else if(colCmp<0) {
				direction = Directions.UP_LEFT;
			}
			//forward
			else if(colCmp==0) {
				direction = Directions.UP_STRAIGHT;
			}

		}//down
		else if(rowCmp<0) {
			//right
			if(colCmp>0) {
				direction=Directions.DOWN_RIGHT;
			}
			//left
			else if(colCmp<0) {
				direction = Directions.DOWN_LEFT;
			}
			//forward
			else if(colCmp==0) {
				direction = Directions.DOWN_STRAIGHT;
			}

		}//same line
		else if(rowCmp==0) {
			//right
			if(colCmp>0) {
				direction=Directions.RIGHT_STRAIGHT;
			}
			//left
			else if(colCmp<0) {
				direction = Directions.LEFT_STRAIGHT;
			}
			//forward
			else if(colCmp==0) {
				direction = Directions.SAME_PLACE;
			}
		}
		return direction;

	};

	/**
	 * Checks if location is on end of board for each player to check for queen upgrade situation
	 * @return
	 */
	public boolean isEndOfBoard(){
		Board board = Board.getInstance();
		Player currPlayer = Game.getInstance().getTurn().getCurrentPlayer();
		if((currPlayer.getColor()==PrimaryColor.WHITE && this.row == board.getBoardSize()) || (currPlayer.getColor()==PrimaryColor.BLACK && this.row == 1)) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "Location :("+column+", "+row+")";
	}





}
