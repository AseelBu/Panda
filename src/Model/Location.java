/**
 * 
 */
package Model;

import java.util.HashMap;

import Exceptions.OutOfBoardBoundariesException;
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
	 */
	public Location(int row, char column) {
		super();
		try {
			this.setRow(row);
			this.setColumn(column);
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
	public void setRow(int row) throws Exception {
		Board board = Board.getInstance();
		if (row>=1 && row<=board.getBoardSize()) {
			this.row = row;
		}else {
			throw new Exception("row value should be between 1 and "+board.getBoardSize());
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
	public void setColumn(char column) throws Exception {
		Board board = Board.getInstance();
		if (column>=board.getColumnLowerBound() && column<=board.getColumnUpperBound()) {
			this.column = column;
		}else {
			throw new Exception("column value should be a capital letter between "+board.getColumnLowerBound()+" and "+board.getColumnUpperBound());
		}
	}

	/**
	 * updates location values to new location that exists diagonally from current location within board limits.
	 * @param direction UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT
	 * @param steps- number of steps diagonally
	 * @return new location if it's within board boundaries, else returns null

	 */
	public Location addToLocationDiagonally(Directions dir,int steps) {
		Board board = Board.getInstance();
		int currRow= this.getRow();
		char currCol = this.getColumn();
		Location updatedLocation=null;

		switch (dir) {
		case UP_LEFT: {
			if(currRow+steps<=board.getBoardSize() && currCol-steps>=board.getColumnLowerBound()) {
				updatedLocation = new Location(currRow+steps, (char)(currCol-steps));
			}
			//			else {
			//				throw new OutOfBoardBoundariesException("your move is out of board boundries");
			//			}
			break;

		}
		case UP_RIGHT:{
			if(currRow+steps<=board.getBoardSize() && currCol+steps<=board.getColumnUpperBound()) {
				updatedLocation = new Location(currRow+steps, (char)(currCol+steps));
			}
			//				else {
			//					throw new OutOfBoardBoundariesException("your move is out of board boundries");
			//				}
			break;
		}
		case DOWN_LEFT:{
			if(currRow-steps>=1 && currCol-steps>=board.getColumnLowerBound()) {
				updatedLocation = new Location(currRow-steps, (char)(currCol-steps));
			}
			//				else {
			//					throw new OutOfBoardBoundariesException("your move is out of board boundries");
			//				}
			break;
		}
		case DOWN_RIGHT:{
			if(currRow-steps>=1 && currCol+steps<=board.getColumnUpperBound()) {
				updatedLocation = new Location(currRow-steps, (char)(currCol+steps));
			}
			//				else {
			//					throw new OutOfBoardBoundariesException("your move is out of board boundries");
			//				}
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + dir);
		}

		if(updatedLocation == null) System.out.println("new location is out of board boundries");
		return updatedLocation;
	}

	//TODO
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

	//TODO 
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

//	/**
//	 * TODO
//	 * @param loc
//	 * @return
//	 */
//	public boolean isLocationInBoardBoundries(int row, char col) {
//		Board board = Board.getInstance();
//		boolean inBoundries =false;
//		int row = loc.getRow();
//		Character col =loc.getColumn();
//
//		if(row<1 || row>board.getBoardSize()) {
//			System.out.println(in Loct);
//		}
//		else {
//			inBoundries=true;
//		}
//		if (col.compareTo(board.getColumnLowerbond())<0 || col.compareTo(board.getColumnUpperbond())>0) {
//			System.out.println();
//		}else {
//			inBoundries =true;
//		}
//		return inBoundries;
//	}

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
		return "Location :("+row+", "+column+")";
	}





}
