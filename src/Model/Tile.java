/**
 * 
 */
package Model;

import Utils.PrimaryColor;
import Utils.SeconderyTileColor;

/**
 * @author aseel
 *
 */
public class Tile implements Comparable<Tile>{
	
	private Location location;
	private PrimaryColor color1;
	private SeconderyTileColor color2=null;
	private Piece piece = null;
	
	
	
	/**
	 * Constructor for regular tile without piece
	 * 
	 * @param location
	 * @param color1
	 */
	//

	public Tile(Location location, PrimaryColor color1) {
		super();
		this.location = location;
		this.color1 = color1;		
	}
	
	/**
	 * Constructor for Special tile without piece
	 * 
	 * @param location
	 * @param color1
	 * @param color2
	 */
	public Tile(Location location, PrimaryColor color1, SeconderyTileColor color2) {
		super();
		this.location = location;
		this.color1 = color1;
		this.color2 = color2;
		
	}
	
	/**
	 * Constructor for regular tile with piece
	 * 
	 * @param location
	 * @param color1
	 * @param piece
	 */

	public Tile(Location location, PrimaryColor color1, Piece piece) {
		super();
		this.location = location;
		this.color1 = color1;		
		this.piece = piece;
	}
	
	/**
	 * Constructor for Special tile with piece
	 * 
	 * @param location
	 * @param color1
	 * @param color2
	 * @param piece
	 */
	public Tile(Location location, PrimaryColor color1, SeconderyTileColor color2, Piece piece) {
		super();
		this.location = location;
		this.color1 = color1;
		this.color2 = color2;
		this.piece = piece;
	}
	
	
	
	@Override
	public int compareTo(Tile t) {
		// TODO Auto-generated method stub
		int result;
		if (this.location.getRow()==t.location.getRow()) {
			result = this.location.getColumn()-t.location.getColumn();
		}else {
			result = this.location.getRow()-t.location.getRow();
		}
			
		return result;
	}
	
	//setters & getters
	
	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}
	
	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	
	/**
	 * @return the color1
	 */
	public PrimaryColor getColor1() {
		return color1;
	}
	
	/**
	 * @param color1 the color1 to set
	 */
	public void setColor1(PrimaryColor color1) {
		this.color1 = color1;
	}
	
	/**
	 * @return the color2
	 */
	public SeconderyTileColor getColor2() {
		return color2;
	}
	
	/**
	 * @param color2 the color2 to set
	 */
	public void setColor2(SeconderyTileColor color2) {
		this.color2 = color2;
	}
	
	/**
	 * @return the piece
	 */
	public Piece getPiece() {
		return piece;
	}
	
	/**
	 * @param piece the piece to set
	 */
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	
	//methods
	
	/**
	 * 
	 * @return dominant tile color- secondary color if exists else the primary color of the tile
	 */
	public String getColorName() {
		if(this.color2 != null) {
			return  this.color2.name();
		}
		return this.color1.name();
		
	}

	

}
