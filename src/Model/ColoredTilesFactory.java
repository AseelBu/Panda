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
	 * 
	 */
	public ColoredTilesFactory() {
		// TODO Auto-generated constructor stub
	}

	//TODO
	public Tile createColoredTile(Tile tileToCopy, SeconderyTileColor color2) {
		Location location = tileToCopy.getLocation();
		PrimaryColor color1 = tileToCopy.getColor1();
		Piece pieceOnTile = tileToCopy.getPiece();
		

		if (SeconderyTileColor.YELLOW.equals(color2)) {
			YellowTile yTile = new YellowTile(location, color1, color2, pieceOnTile);
			yTile.drawQuestion();
			return yTile;
		}
		else if (SeconderyTileColor.BLUE.equals(color2)) {
			BlueTile bTile = new BlueTile(location, color1, color2, pieceOnTile);
			return bTile;

		}
		else if (SeconderyTileColor.RED.equals(color2)) {
			return new Tile(location, color1, color2, pieceOnTile);
		}
		else if (color2==null) {

		}
		return null;

	}

}
