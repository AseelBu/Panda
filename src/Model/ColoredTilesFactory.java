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
public class ColoredTilesFactory {

	/**
	 * gets instance of tile based on it's secondary color and tileToColor attributes
	 * @param tileToColor
	 * @param color2
	 * @return Tile instance with chosen secondary color
	 */
	public Tile getColoredTile(Tile tileToColor, SeconderyTileColor color2) {
		if (tileToColor==null) {
			System.out.println("didn't get the tile to add color to- tileToColor==null");
			return null;
		}

		Location location = tileToColor.getLocation();
		PrimaryColor color1 = tileToColor.getColor1();
		Piece pieceOnTile = tileToColor.getPiece();
		if(color2==null) {
			return new Tile(location, color1, color2, pieceOnTile);
		}
		
		switch(color2){
		case YELLOW:
			YellowTile yTile = new YellowTile(location, color1, color2, pieceOnTile);
//			yTile.drawQuestion();
			return yTile;
		case YELLOW_ORANGE:
			tileToColor.setColor2(color2);
			return tileToColor;

		case BLUE:
			BlueTile bTile = new BlueTile(location, color1, color2, pieceOnTile);
			return bTile;

		case RED:
		case GREEN:
		case ORANGE:
			return new Tile(location, color1, color2, pieceOnTile);

		default:{
			
			return null;
		}
		
		}

	}

}
