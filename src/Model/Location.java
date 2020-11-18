/**
 * 
 */
package Model;

/**
 * @author aseel
 *
 */
public class Location {
	private int row;
	private char column;
	
	/**
	 * @param row
	 * @param column
	 */
	//Constructor

	public Location(int row, char column) {
		super();
		this.row = row;
		this.column = column;
		
		
	}

	/**
	 * @return the row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @param row the row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * @return the column
	 */
	public char getColumn() {
		return column;
	}

	/**
	 * @param column the column to set
	 */
	public void setColumn(char column) {
		this.column = column;
	}

	@Override
	public String toString() {
		return "Location :("+row+", "+column+")";
	}
	
	
	
	

}
