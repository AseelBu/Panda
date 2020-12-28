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
	 * @return the column
	 */
	public char getColumn() {
		return column;
	}

	/**
	 * sets row with checking if set is in board boundaries
	 * @param row the row to set
	 * @throws LocationException - row out of board boundaries
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
	 * @param column the column to set
	 * @throws LocationException- column out of board boundaries
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
	 * @param steps The number of steps to add diagonally
	 * @return The new location if it's within board boundaries, else returns null
	 * @throws LocationException  -Location is out of board boundaries
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

	/**
	 * By a given location on the one of the sides of the board, returns the location of a tile that linked diagonally to the current location
	 * @param direction
	 * @return Location 
	 * @throws LocationException 
	 */
	public Location rotateLocation(Directions direction) throws LocationException {
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
		case DOWN_RIGHT:{

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
	 * calculates the relative location between this location and target location
	 *
	 * @param targetLocation to calculate direction and steps from
	 * @return HashMap<Directions,Integer> key is relative direction and value is number of relative absolute number of steps ,
	 * between this location and target location
	 */
	public HashMap<Directions,Integer>relativeLocationTo(Location targetLocation) {
		HashMap<Directions,Integer> result= new HashMap<Directions,Integer>();

		Directions direction = getRelativeDirection(targetLocation);
		Integer steps = getRelativeNumberOfSteps(targetLocation);

		result.put(direction, steps);
		return result;
	}

	/** 
	 * gets the number of steps between this location and targetLocation
	 * 
	 * @param targetLocation to calculate number of steps from
	 * @return int absolute number of steps skipping on black tiles between locations, if target location is white return -1
	 */
	public int getRelativeNumberOfSteps(Location targetLocation) {
		int steps = -1;
		int curRow =this.row;
		int targetRow = targetLocation.getRow();
		int rowCmp = targetRow-curRow;
		Directions dir = getRelativeDirection(targetLocation);
		if(dir!=null) {
			steps = Math.abs(rowCmp);
		}
		return steps;
	};


	/**
	 * calculates the direction from this Location to target Location
	 * @param targetLocation to calculate relative location from
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
			
		}
		
		return direction;
	};

	/**
	 * Checks if this location is on the end of board based on current player color
	 * @return true if this location is located on last row in the board and current player is white or 
	 * 			if this location is in the first row in the board and current player is black
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
