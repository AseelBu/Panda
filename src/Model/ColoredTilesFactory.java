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
	public Tile createColoredTile(Tile tileToColor, SeconderyTileColor color2) {
		if (tileToColor==null) {
			System.out.println("didn't get the tile to add color to- tileToColor==null");
			return null;
		}
		Location location = tileToColor.getLocation();
		PrimaryColor color1 = tileToColor.getColor1();
		Piece pieceOnTile = tileToColor.getPiece();
		

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
		}else if (SeconderyTileColor.GREEN.equals(color2)) {
			return new Tile(location, color1, color2, pieceOnTile);
		}else if (SeconderyTileColor.ORANGE.equals(color2)) {
			return new Tile(location, color1, color2, pieceOnTile);
		}else if (SeconderyTileColor.YELLOW_ORANGE.equals(color2)) {
			//TODO yellow_orange tile factory
			return new Tile(location, color1, color2, pieceOnTile);
		}
		else if (color2==null) {
			//TODO ---
		}
		return null;

	}

}
