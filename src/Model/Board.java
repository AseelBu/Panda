/**
 * 
 */
package Model;

import java.util.ArrayList;

import Utils.MainColor;


/**
 * @author aseel
 *
 */
public class Board {

	private ArrayList<Tile> tiles;
	private ArrayList<Piece> pieces;

	/**
	 *Board constructor 
	 */
	public Board() {
		super();
		tiles = new ArrayList<Tile>();
		pieces = new ArrayList<Piece>();
		System.out.println("Board created..");
	}


	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	public ArrayList<Piece> getPieces() {
		return pieces;
	}

	/**
	 * @param tile
	 */
	public Boolean addTile(Tile tile) {
		if(tile != null) {
			return this.tiles.add(tile);
		}
		return false;

	}

	/**
	 * @param piece
	 */
	public Boolean addPiece(Piece piece) {
		if(piece != null) {
			return this.pieces.add(piece);
		}
		return false;
	}

	//TODO
	/**
	 * @param piece
	 * @param targetLocation
	 * @return true if it's legal to move the piece ,otherwise false
	 */
	public Boolean canPieceMove(Piece piece, Location targetLocation) {
		return null;

	}

	//TODO
	/**
	 * 
	 * @param piece that we want to check
	 * 
	 * @return list of pieces that are edible, null if piece can't eat
	 */
	public ArrayList<Piece> canPieceEat(Piece piece) {
		return null;

	}

	//TODO
	/**
	 * 
	 * @return list of black pieces on the board
	 */
	public ArrayList<Piece> getBlackPieces(){
		return pieces;

	}

	//TODO
	/**
	 * 
	 * @return list of white pieces on the board
	 */
	public ArrayList<Piece> getWhitePieces(){
		return pieces;

	}
	
	public Location getRandomFreeLocation() {
		Location loc = null;
		return loc;
	}
	
	public ArrayList<Tile> getLegalMoves(MainColor color) {
		ArrayList<Tile> possibleTiles= new ArrayList<Tile>();
		return possibleTiles;
		
	}
	


	public void printBoard() {
		ArrayList<String> board = new ArrayList<String>();
		//TODO sort Tiles
		for (Tile tile : this.tiles) {

			//[black/white/colorful tile-piece details]
			String tileDetails= "["+tile.getColorName()+" tile-"+tile.getPiece()+"]";
			board.add(tileDetails);
			System.out.println(tileDetails);
		}

		if(board.isEmpty()) {
			System.out.println("board has no tiles yet"); 
		}


	}





}
