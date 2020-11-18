/**
 * 
 */
package Model;

import Utils.MainColor;
import Utils.SeconderyTileColor;

/**
 * @author aseel
 *
 */
public class Tile {
	
	private Location location;
	private MainColor color1;
	private SeconderyTileColor color2=null;
	private Piece piece;
	
	/**
	 * @param location
	 * @param color1
	 * @param piece
	 */
	public Tile(Location location, MainColor color1, Piece piece) {
		super();
		this.location = location;
		this.color1 = color1;		
		this.piece = piece;
	}
	
	/**
	 * @param location
	 * @param color1
	 * @param color2
	 * @param piece
	 */
	public Tile(Location location, MainColor color1, SeconderyTileColor color2, Piece piece) {
		super();
		this.location = location;
		this.color1 = color1;
		this.color2 = color2;
		this.piece = piece;
	}
	
	
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
	public MainColor getColor1() {
		return color1;
	}
	/**
	 * @param color1 the color1 to set
	 */
	public void setColor1(MainColor color1) {
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
	
	
	public String getColorName() {
		if(this.color2 != null) {
			return  this.color2.name();
		}
		return this.color1.name();
		
	}

}
