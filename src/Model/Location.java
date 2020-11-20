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

	//TODO check if set is in board boundaries!!!
	/**
	 * 
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
