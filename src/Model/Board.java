/**
 * 
 */
package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import Utils.Directions;
import Utils.PrimaryColor;
import Utils.SeconderyTileColor;

/**
 * @author aseel
 *
 */
public class Board {

	private final int BOARD_SIZE = 8;


	private ArrayList<Piece> pieces;
	private HashMap<Integer, ArrayList<Tile>> tiles; //key:row number,value: array list of tiles ordered by column

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

	public char getColumnUpperbond(){
		return (char)('A'+(this.getBoardSize())-1);
	}
	public char getColumnLowerbond(){
		return 'A';
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
			allTiles.addAll(tileList);

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
	public boolean addTile(Tile tile) {

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
	 * Replaces tile in tiles map 
	 * 
	 * @param tile
	 * @return true if replaced successfully,otherwise false
	 */
	private boolean replaceTile(Tile tile) {

		boolean isSuccess = false;
		int tileRow = tile.getLocation().getRow();
		int tileCol = tile.getLocation().getColumn();

		ArrayList<Tile> boardRow= tiles.get(tileRow);

		boardRow.set(tileCol, tile);
		this.tiles.put(tileRow, boardRow);
		isSuccess = true;

		return isSuccess;

	}

	/**
	 * adds piece to board
	 * 
	 * @param piece
	 * @return true if added successfully,otherwise false
	 */
	public boolean addPiece(Piece piece) {
		if(piece != null) {
			return this.pieces.add(piece) && addPieceToBoardTile(piece);
		}
		return false;
	}

	//helping method that adds piece to tile in board
	//assumes tile already exist in board
	private boolean addPieceToBoardTile(Piece piece) {

		if(piece == null) return false; 
		Location pieceLoc = piece.getLocation();

		Tile tile=null;
		try {
			tile = getTileInLocation(pieceLoc);
			tile.setPiece(piece);
			this.replaceTile(tile);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	//helping method that removes piece from tile in board
	private boolean removePieceFromBoardTile(Piece piece) {

		if(piece == null) return false; 
		Location pieceLoc = piece.getLocation();

		Tile tile=null;
		try {
			tile = getTileInLocation(pieceLoc);
			tile.setPiece(null);
			this.replaceTile(tile);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	//TODO 
	/**
	 * checks if the piece can move depending on all pieces locations on board
	 * @param piece
	 * @param targetLocation
	 * @return true if it's legal to move the piece ,otherwise false
	 */
	public Boolean canPieceMove(Piece piece, Location targetLocation) {
		// is tile black location
		Tile targetTile=null;
		try {
			targetTile = getTileInLocation(targetLocation);
			if (targetTile.getColor1()!=PrimaryColor.BLACK) {
				System.out.println("can't move to this "+targetLocation+", you can move only on black tiles! ");
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		// does tile contain another piece
		if(targetTile.getPiece() != null) {
			System.out.println("can't move to this "+targetLocation+", it contains another Piece!");
			return false;
		}
		if(piece instanceof Soldier) {

			//TODO if its moving in 2 make sure:
			//there is a piece we are eating by moving like that
			//NOT DONE
			piece.getLocation().relativeLocationTo(targetLocation);
			piece.getEdiblePieces();

			// TODO if it's moving backwards it's because it's eating for second time




		}
		else if (piece instanceof Queen) {
			//TODO handle queen
		}

		return true;

	}

	//	//TODO
	//	private void isEdiableBy(Piece currentPiece, Piece targetPiece) {
	//		// TODO Auto-generated method stub
	//		Location currLocation = currentPiece.getLocation();
	//		Location targetLocation = targetPiece.getLocation();
	//		if(currLocation.addToLocationDiagonally(Directions.UP_LEFT, 1))
	//
	//	}


	/**
	 * gets all the color pieces on the board
	 * @param PrimaryColor color-black/white
	 * @return ArrayList<Piece> of color pieces on the board
	 */
	public ArrayList<Piece> getColorPieces(PrimaryColor color){
		ArrayList<Piece> colorPieces = new ArrayList<Piece>();

		for(Piece p : this.pieces) {
			if(p.getColor()== color) {
				colorPieces.add(p);
			}
		}
		return colorPieces;

	}

	/**
	 * gets all black tiles on board the don't have a piece on them 
	 * 
	 * @return ArrayList<Tile> of empty tiles
	 */
	public ArrayList<Tile> getEmptyTiles(){
		ArrayList<Tile> emptyTiles = new ArrayList<Tile>();
		ArrayList<Tile> boardTiles = getAllBoardTiles();
		for (Tile tile : boardTiles) {
			if(tile.getPiece()== null && tile.getColor1()!= PrimaryColor.WHITE) {
				emptyTiles.add(tile);
			}
		}
		return emptyTiles;
	}


	/**
	 * gets all black locations on board the don't have a piece on them
	 * 
	 * @return ArrayList<Location> of empty locations
	 */
	public ArrayList<Location> getEmptyLocations(){

		ArrayList<Tile> emptyTiles = getEmptyTiles();
		ArrayList<Location> emptyLocations = new ArrayList<Location>();

		for (Tile t : emptyTiles) {

			emptyLocations.add(t.getLocation());

		}
		return emptyLocations;
	}


	/**
	 * gets a piece free random black location on board
	 * @return Location- random black location
	 */
	public Location getRandomFreeLocation() {

		Random random = new Random();
		ArrayList<Location> emptyLocations = getEmptyLocations();
		Location randomLocation = emptyLocations.get( random.nextInt(emptyLocations.size()));
		return randomLocation;
	}


	/**
	 * gets a piece free random black tile on board
	 * @return Tile- random board tile
	 */
	public Tile getRandomFreeTile() {

		Random random = new Random();
		ArrayList<Tile> emptyTiles = getEmptyTiles();
		Tile randomTile  = emptyTiles.get( random.nextInt(emptyTiles.size()));
		return randomTile;
	}


	/**
	 * upgrade soldier to queen
	 * @param Soldier-soldier we want to upgrade
	 */
	public void upgradeSoldier(Soldier soldier) {

		Queen newQueen = new Queen(soldier.getId(), soldier.getColor(), soldier.getLocation());

		//update in pieces list

		//replace soldier with queen on board
		this.pieces.set(this.pieces.indexOf(soldier), newQueen);


		//update in tiles map
		addPieceToBoardTile(soldier);

		System.out.println("soldier updated to queen successfully");

	}

	//TODO
	/**
	 * gets all possible moves for certain color (player)
	 * 
	 * @param PrimaryColor color-BLACK/WHITE
	 * @return ArrayList<Tile> of legal moves for the color
	 */
	public ArrayList<Tile> getLegalMoves(PrimaryColor color) {


		ArrayList<Tile> possibleTiles= new ArrayList<Tile>();

		ArrayList<Tile> emptyTiles = getEmptyTiles();
		ArrayList<Piece> colorPieces = getColorPieces(color);



		return possibleTiles;

	}

	//TODO
	/**
	 * gets all the pieces that can be eaten by specific player
	 * @param player color PrimaryColor BLACK,WHITE
	 * @return ArrayList<Piece> that are edible for color player
	 */
	public ArrayList<Piece> getAllEdiblePiecesForColor(PrimaryColor playerColor){
		ArrayList<Piece> ediblePieces= new ArrayList<Piece>();
		ArrayList<Piece> colorPieces=getColorPieces(playerColor);

		for (Piece p : pieces) {
			ediblePieces.addAll(p.getEdiblePieces());
		}

		return ediblePieces;
	}


	/**
	 * removes piece from board pieces and from board tiles
	 * @param piece burning piece
	 * @return true if removed successfully from board, false otherwise
	 */
	public boolean burn(Piece piece) {
		if(piece == null) return false;

		boolean result =false;
		//remove from list of pieces
		result= this.pieces.remove(piece);

		//remove from board tile
		Location pieceLocation = piece.getLocation();
		result=result && removePieceFromBoardTile(piece);

		if (result) System.out.println(piece+" is burnt !!");
		else System.out.println("Error: wasn't able to burn piece "+ piece);

		return result;

	}

	//TODO
	//points
	public void eat() {
		// TODO Auto-generated method stub
	}


	/**
	 *  checks if there is more eating left for color player
	 *  
	 * @param playerColor PrimaryColor BLACK,WHITE
	 * @return true if there is no more eating left for color player,otherwise false
	 */
	public boolean isAllPiecesEaten(PrimaryColor playerColor) {
		// TODO Auto-generated method stub
		ArrayList<Piece> ediblePieces= getAllEdiblePiecesForColor(playerColor);
		if(ediblePieces.isEmpty()) {
			return true;
		}
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
