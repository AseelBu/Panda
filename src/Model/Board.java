/**
 * 
 */
package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import Utils.PrimaryColor;

/**
 * @author aseel
 *
 */
public class Board {

	private final int BOARD_SIZE = 8;

	//TODO change to hashmap
	private ArrayList<Piece> pieces;
	private HashMap<Integer, ArrayList<Tile>> tiles; //key:row number,vale: array list of tiles ordered by column

	//Singleton Class

	private static Board instance = new Board();


	private Board(){

		pieces = new ArrayList<Piece>();
		tiles = new HashMap<Integer, ArrayList<Tile>>(BOARD_SIZE);
		System.out.println("Board created..");
	}

	//Get the only object available
	public static Board getInstance(){
		return instance;
	}


	// getters &setters 

	public HashMap<Integer, ArrayList<Tile>> getTilesMap() {
		return tiles;
	}

	public ArrayList<Piece> getPieces() {
		return pieces;
	}

	public int getBoardSize(){
		return BOARD_SIZE;
	}

	//Methods

	/**
	 * returns all tiles in the board
	 * @return ArrayList<Tile> all tiles in board
	 */
	public ArrayList<Tile> getAllBoardTiles() {
		// TODO Auto-generated method stub
		ArrayList<ArrayList<Tile>> tilesMapValues=new ArrayList<ArrayList<Tile>>(getTilesMap().values()) ;
		ArrayList<Tile> allTiles =new ArrayList<Tile>() ;
		for(ArrayList<Tile> tileList : tilesMapValues) {
			Collections.copy(allTiles,tileList);
		}
		return allTiles;
	}

	/**
	 * Given row returns all the tiles in specified row
	 * 
	 * @param row -wanted tiles' row number
	 * @return ArrayList<Tile> of tiles in given row
	 */
	public ArrayList<Tile> getTilesinRow(int row) {
		
		return getTilesMap().get(row);
	}

	/**
	 * Given column returns all the tiles in specified column
	 * 
	 * @param Col -wanted tiles' column letter
	 * @return ArrayList<Tile> of tiles in given column
	 */
	public ArrayList<Tile> getTilesinCol(char Col) {
		
		ArrayList<Tile> tilesInCol=new ArrayList<Tile>();
		for (int row : this.tiles.keySet()) {
			ArrayList<Tile> tilesInRow= this.tiles.get(row);
			Tile tile=tilesInRow.get(Col);
			tilesInCol.add(tile);
		}
		return tilesInCol;
	}

	/**
	 * Given location returns the tile in specified location
	 * 
	 * @param location object
	 * @return Tile-tile in the requested location
	 * @throws Exception-row [number] in board doesn't have tiles
	 */
	public Tile getTileInLocation(Location location) throws Exception {
		
		if (location == null) return null;
		ArrayList<Tile> tilesInRow = getTilesinRow(location.getRow());
		if(tilesInRow != null) {
			return tilesInRow.get(location.getColumn());
		}else {
			throw new Exception("Error: Row "+location.getRow()+" has no tiles");
		}

	}
	/**
	 * Adds tile to tiles map 
	 * 
	 * @param tile
	 * @return true if added successfully,otherwise false
	 */
	public Boolean addTile(Tile tile) {

		boolean isSuccess = false;
		int tileRow = tile.getLocation().getRow();
		int tileCol = tile.getLocation().getColumn();

		ArrayList<Tile> boardRow= null;

		if(tiles.containsKey(tileRow)) {
			boardRow=tiles.get(tileRow);
			if(boardRow == null) {
				boardRow = new ArrayList<Tile>();
			}
		}
		else {
			boardRow= new ArrayList<Tile>();
		}
		boardRow.add(tileCol, tile);
		this.tiles.put(tileRow, boardRow);
		isSuccess = true;

		return isSuccess;

	}

	/**
	 * @param piece
	 * @return true if added successfully,otherwise false
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

	//TODO
	public Location getRandomFreeLocation() {
		Location loc = null;
		return loc;
	}

	//TODO
	public ArrayList<Tile> getLegalMoves(PrimaryColor color) {
		ArrayList<Tile> possibleTiles= new ArrayList<Tile>();
		return possibleTiles;

	}

	//TODO
	public void upgradeSoldier() {

	}

	//TODO
	public void eat() {
		// TODO Auto-generated method stub

	}

	//TODO
	public boolean isAllPiecesEaten() {
		// TODO Auto-generated method stub
		return false;
	}

	//TODO
	public boolean isPlayerStuck() {
		// TODO Auto-generated method stub
		return false;

	}

	public void printBoard() {
		ArrayList<String> board = new ArrayList<String>();
		//TODO sort Tiles
		ArrayList<Tile> boardTiles=getAllBoardTiles();
		for (Tile tile : boardTiles) {

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
