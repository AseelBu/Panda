/**
 * 
 */
package Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;

import Exceptions.IllegalMoveException;
import Exceptions.LocationException;
import Utils.Directions;
import Utils.PrimaryColor;
import Utils.SeconderyTileColor;

/**
 * @author aseel
 *
 */
public class Board {

	private final int BOARD_SIZE = 8;
	private final int YELLOW_TILES_AMOUNT = 3;


	private ArrayList<Piece> pieces;
	private HashMap<Integer, ArrayList<Tile>> tiles; //key:row number,value: array list of tiles ordered by column
	private ArrayList<Tile> coloredTilesList;
	private ArrayList<Tile> orangeTiles;
	//Singleton Class

	private static Board instance;

	/**
	 * Board class constructor
	 */
	private Board(){
		pieces = new ArrayList<Piece>();
		tiles = new HashMap<Integer, ArrayList<Tile>>(BOARD_SIZE);
		coloredTilesList = new ArrayList<Tile>();
		orangeTiles=new ArrayList<Tile>();
		System.out.println("Board created..");
	}

	//Get the only object available
	public static Board getInstance(){
		if (instance == null) 
		{ 
			instance = new Board(); 
		} 
		return instance; 
	}
	/**
	 * destructor for this class
	 */
	public static void destruct() {
		instance = null;
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

	public char getColumnUpperBound(){
		return (char)('A'+(this.getBoardSize())-1);
	}
	public char getColumnLowerBound(){
		return 'A';
	}
	//Methods

	/**
	 * Add standard tiles to the game.
	 */
	public void initBasicBoardTiles() {
		try {
			for(int i = 1 ; i <= BOARD_SIZE ; i+=2) {
				for(char c = getColumnLowerBound() ; c <= getColumnUpperBound() ; c+=2) {
					addTile(new Tile(new Location(i, c), PrimaryColor.BLACK));
					addTile(new Tile(new Location(i, (char) ( c + 1)), PrimaryColor.WHITE));
				}
			}
			for(int i = 2 ; i <= BOARD_SIZE ; i+=2) {
				for(char c = getColumnLowerBound() ; c <= getColumnUpperBound() ; c+=2) {

					addTile(new Tile(new Location(i, c), PrimaryColor.WHITE));
					addTile(new Tile(new Location(i, (char) ( c + 1)), PrimaryColor.BLACK));
				}
			}
		}catch(LocationException e) {
			e.printStackTrace();
		}
	}



	/**
	 * returns all tiles in the board
	 * @return ArrayList of Tile all tiles in board
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

	//helping method to get all black tiles on the board
	private ArrayList<Tile> getBlackBoardTiles() {
		ArrayList<Tile> blackTiles = new ArrayList<Tile>();

		for(Tile t :getAllBoardTiles()) {
			if(t.getColor1()== PrimaryColor.BLACK) {
				blackTiles.add(t);
			}
		}
		return blackTiles;

	}

	/**
	 * @return the coloredTilesList
	 */
	public ArrayList<Tile> getColoredTilesList() {
		return coloredTilesList;
	}

	public ArrayList<Tile> getOrangeTiles() {
		return orangeTiles;
	}


	//helping method to get random black tile on the board
	private Tile getRandomBlackTile() {
		Random random = new Random();
		ArrayList<Tile> blackTiles = getBlackBoardTiles();
		Tile randomTile  = blackTiles.get( random.nextInt(blackTiles.size()));
		return randomTile;
	}


	/**
	 * Given row returns all the tiles in specified row
	 * 
	 * @param row -wanted tiles' row number
	 * @return ArrayList of Tile of tiles in given row
	 */
	public ArrayList<Tile> getTilesinRow(int row) {

		return getTilesMap().get(row);
	}

	/**
	 * Given column returns all the tiles in specified column
	 * 
	 * @param Col -wanted tiles' column letter
	 * @return ArrayList of Tile of tiles in given column
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
	 * @throws Exception - row number in board doesn't have tiles
	 */
	public Tile getTileInLocation(Location location) throws LocationException {

		if (location == null) return null;
		ArrayList<Tile> tilesInRow = getTilesinRow(location.getRow());
		if(tilesInRow != null) {
			return tilesInRow.get(location.getColumn() - 'A');
		}else {
			throw new LocationException("Error: Row " + location.getRow() + " has no tiles");
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
				boardRow = new ArrayList<Tile>(BOARD_SIZE);
				for (int i = 0; i < BOARD_SIZE; i++) {
					boardRow.add(0,null);
				}
			}
		}
		else {
			boardRow= new ArrayList<Tile>(BOARD_SIZE);
			for (int i = 0; i < BOARD_SIZE; i++) {
				boardRow.add(0,null);
			}
		}
		boardRow.set(tileCol - 'A', tile);
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
	public boolean replaceTileInSameTileLocation(Tile newTileInstance) {
		if(newTileInstance== null) return false;
		boolean isSuccess = false;
		int tileRow = newTileInstance.getLocation().getRow();
		int tileCol = newTileInstance.getLocation().getColumn();

		ArrayList<Tile> boardRow= tiles.get(tileRow);

		boardRow.set(tileCol - getColumnLowerBound(), newTileInstance);
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
	
	/**
	 * adds given pieces list to game board
	 * @param pieces to add
	 * @return true if input was'nt null or empty,false otherwise
	 */
	public boolean addPiecesToBoard(ArrayList<Piece> pieces) {
		if(pieces==null || pieces.isEmpty())return false;
		for(Piece p:pieces) {
			addPiece(p);
		}
		return true;
	}

	/**
	 * remove piece from board
	 * 
	 * @param piece
	 * @return true if added successfully,otherwise false
	 */
	public boolean removePiece(Piece piece) {
		boolean result =false;
		//remove from list of pieces
		result= this.pieces.remove(piece);

		//remove from board tile

		result = result && removePieceFromBoardTile(piece);
		return result;
	}

	public boolean replacePiece(Piece pieceToReplace,Piece pieceToReplaceWith) {
		if(removePiece(pieceToReplace)){
			return addPiece(pieceToReplaceWith);
		}
		return false;
	}


	/**
	 * adds piece to tile in board
	 * NOTE - assumes tile already exist in board
	 * 
	 * @param piece
	 * @return true if added successfully, false otherwise
	 */
	public boolean addPieceToBoardTile(Piece piece) {

		if(piece == null) return false; 
		Location pieceLoc = piece.getLocation();

		Tile tile=null;
		try {
			tile = getTileInLocation(pieceLoc);
			tile.setPiece(piece);
			this.replaceTileInSameTileLocation(tile);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}


	/**
	 * add color to board tile
	 * @param tile
	 * @param color
	 * @return added Tile
	 */
	public Tile addSeconderyColorToBoardTile(Tile tilee,SeconderyTileColor color) {
		Location tLoc =tilee.getLocation();

		Tile tile=null;
		try {
			tile = getTileInLocation(tLoc);
			tile.setColor2(color);
			this.replaceTileInSameTileLocation(tile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return tile;
	}




	//helping method that removes piece from tile in board
	private boolean removePieceFromBoardTile(Piece piece) {

		if(piece == null) return false; 
		Location pieceLoc = piece.getLocation();

		Tile tile=null;
		try {
			tile = getTileInLocation(pieceLoc);
			tile.setPiece(null);
			this.replaceTileInSameTileLocation(tile);
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}


	/**
	 * checks if the piece can move to targetLocation depending on all pieces locations on board
	 * @param piece
	 * @param targetLocation
	 * @return true if it's legal to move the piece ,otherwise false
	 * @throws LocationException 
	 * @throws IllegalMoveException 
	 */
	public boolean canPieceMove(Piece piece, Location targetLocation, Directions direction) throws LocationException, IllegalMoveException {
		// is tile black location
		Tile targetTile=null;

		targetTile = getTileInLocation(targetLocation);
		if (targetTile.getColor1()!=PrimaryColor.BLACK) {
			throw new IllegalMoveException("piece can't move to this " + targetLocation + ", you can move only on black tiles! ");
		}

		// does tile contain another piece
		if(targetTile.getPiece() != null) {
			throw new IllegalMoveException("piece can't move to this " + targetLocation + ", it contains another Piece!");
		}
		if(piece instanceof Soldier) {
			int steps = piece.getLocation().getRelativeNumberOfSteps(targetLocation);
			Piece ediblePiece = ((Soldier) piece).getEdiblePieceByDirection( direction);
			// if its moving in 2s make sure there is a piece we are eating by moving like that
			if(((piece.getColor()==PrimaryColor.WHITE) && (direction == Directions.UP_LEFT || direction == Directions.UP_RIGHT)) 
					|| ((piece.getColor()==PrimaryColor.BLACK) && (direction == Directions.DOWN_LEFT|| direction == Directions.DOWN_RIGHT))){
				if(steps==2) {
					//if no piece to eat
					if(ediblePiece == null) {
						throw new IllegalMoveException("soldier can't move 2 steps if you are not eating another piece fom rival player");
					}
				}
				else if (steps!=1) {
					throw new IllegalMoveException("soldier can only move 2 steps if you are eating ,and 1 step if you are not");
				}

			}// if it's moving backwards it's because it's eating for second time
			else if(((piece.getColor()==PrimaryColor.BLACK) && (direction == Directions.UP_LEFT || direction == Directions.UP_RIGHT)) 
					|| ((piece.getColor()==PrimaryColor.WHITE) && (direction == Directions.DOWN_LEFT|| direction == Directions.DOWN_RIGHT))  ) {
				if(steps !=2) {
					throw new IllegalMoveException("soldier can't move backwards unless you are moving 2 steps while eating in sequence");
				}//if 2 steps
				else {
					int eatingCntr =piece.getEatingCntr();
					if (eatingCntr<1) {
						throw new IllegalMoveException("soldier can't move backwards unless you are eating for the second time");
					}//if in  eating sequence
					else {
						//if there is nothing to eat while moving in 2
						if(ediblePiece == null) {
							throw new IllegalMoveException("soldier can't move 2 steps if you are not eating another piece from rival player");
						}
					}
				}
			}
			//not moving diagonally
			else {
				return false;
			}
		}
		else if (piece instanceof Queen) {

			if(!((Queen) piece).isMoveLegalByDirection(targetLocation, direction)) {
				throw new IllegalMoveException("Illegal Move!");
			}
			if(((Queen) piece).isPieceBlockedByDirection(targetLocation, direction)) {
				throw new IllegalMoveException("Queen is blocked!");
			}
			if(((Queen) piece).getPiecesCountByDirection(targetLocation, direction) > 1) {
				throw new IllegalMoveException("Failed to eat more than one piece in one move, try to split your move");
			}

		}

		return true;

	}


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
			if(tile.getPiece()== null && tile.getColor1()== PrimaryColor.BLACK) {
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
	 * @return Location- random black location without pieces
	 */
	public Location getRandomFreeLocation() {

		Random random = new Random();
		ArrayList<Location> emptyLocations = getEmptyLocations();
		Location randomLocation = emptyLocations.get( random.nextInt(emptyLocations.size()));
		return randomLocation;
	}


	/**
	 * gets a piece free random black tile on board
	 * @return Tile- random board tile without pieces
	 */
	public Tile getRandomFreeTile() {

		Random random = new Random();
		ArrayList<Tile> emptyTiles = getEmptyTiles();
		Tile randomTile  = emptyTiles.get( random.nextInt(emptyTiles.size()));
		return randomTile;
	}

	/**
	 * returs random tile on board that is legal for current color
	 * @return Tile- randm board tile that is legal for current player to step on
	 */
	public Tile getRandomLegalTile(){
		Random random = new Random();
		ArrayList<Tile> legalTiles = getAllLegalMoves(Game.getInstance().getCurrentPlayerColor());
		Tile randomTile  = legalTiles.get( random.nextInt(legalTiles.size()));
		System.out.println("random legal Tile = "+randomTile);
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

	/**
	 * gets all possible moves (tiles to move to) for certain color (player)
	 * 
	 * @param PrimaryColor color-BLACK/WHITE
	 * @return ArrayList<Tile> of legal moves for the color
	 */
	public ArrayList<Tile> getAllLegalMoves(PrimaryColor playerColor) {

		final Directions[] upDirections = {Directions.UP_LEFT,Directions.UP_RIGHT};
		final Directions[] downDirections = {Directions.DOWN_LEFT,Directions.DOWN_RIGHT};

		LinkedHashSet<Tile> possibleTileSet = new LinkedHashSet<Tile>();

		ArrayList<Piece> colorPieces = getColorPieces(playerColor);
		for(Piece p : colorPieces) {
			
				possibleTileSet.addAll(p.getPossibleMoves(playerColor));
			
		}
		ArrayList<Tile> possibleTiles= new ArrayList<Tile>(possibleTileSet);
		return possibleTiles;
	}


	/**
	 * gets all the pieces that can be eaten by specific player color
	 * @param player color PrimaryColor BLACK,WHITE
	 * @return ArrayList<Piece> that are edible for color player
	 */
	public ArrayList<Piece> getAllEdiblePiecesByColor(PrimaryColor playerColor){
		ArrayList<Piece> ediblePieces= new ArrayList<Piece>();
		ArrayList<Piece> colorPieces=getColorPieces(playerColor);

		for (Piece p : colorPieces) {
			ediblePieces.addAll(p.getMustEdiblePieces());
		}

		return ediblePieces;
	}


	/**
	 * gets all the pieces that must eat, for specific player color
	 * @param player color PrimaryColor BLACK,WHITE
	 * @return ArrayList<Piece> that are must eat for color player
	 */
	public ArrayList<Piece> getAllNeedToEatPieces(PrimaryColor playerColor){
		ArrayList<Piece> needToEatPieces= new ArrayList<Piece>();
		ArrayList<Piece> colorPieces=getColorPieces(playerColor);

		for (Piece p : colorPieces) {
			if(!p.getMustEdiblePieces().isEmpty()) {
				needToEatPieces.add(p);
			}

		}

		return needToEatPieces;
	}

	/**
	 * removes piece from board pieces and from board tiles
	 * @param piece burning piece
	 * @return true if removed successfully from board, false otherwise
	 */
	public boolean burn(Piece piece, boolean isEaten) {
		if(piece == null) return false;

		boolean result = removePiece(piece);

		if (result) {
			if(isEaten) {
				System.out.println(piece+" has been eaten !!");
			}else {
				System.out.println(piece+" is burnt !!");
			}
		}
		else System.out.println("Error: wasn't able to burn piece "+ piece);

		return result;

	}


	/**
	 * 
	 * @param pieceEatig
	 * @param targetPiece
	 * @return Piece eaten if eating was successful ,null otherwise
	 * @throws IllegalMoveException 
	 */
	public Piece eat(Piece pieceEatig, Piece targetPiece) throws IllegalMoveException {
		if(pieceEatig == null || targetPiece == null) {
			System.out.println("null arguments in Board.eat method call");
			return null;
		}
		Turn turn =Game.getInstance().getTurn();
		Player currPlayer=turn.getCurrentPlayer();
		if(pieceEatig.getColor() != currPlayer.getColor()) {
			throw new IllegalMoveException("eating piece must be the same color as current player playing");
		}

		if(pieceEatig.canEatPiece(targetPiece)) {
			if(burn(targetPiece, true)) {
				pieceEatig.incEatingCntr(1);
				currPlayer.AddScore(100);
				Game.getInstance().getTurn().setEaten(targetPiece);
			}else {
				return null;
			}

		}
		pieceEatig.incEatingCntr(1);

		return null;
	}


	/**
	 *  checks if there is more eating left for color player to any of it's pieces on board
	 *  
	 * @param playerColor PrimaryColor BLACK,WHITE
	 * @return true if there is no more eating left for color player,otherwise false
	 */
	public boolean isAllPiecesEaten(PrimaryColor playerColor) {

		ArrayList<Piece> ediblePieces= getAllEdiblePiecesByColor(playerColor);
		if(ediblePieces.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 *  checks if there is more eating left for specific piece on board
	 *  
	 * @param playerColor PrimaryColor BLACK,WHITE
	 * @return true if there is no more eating left for color player,otherwise false
	 */
	public boolean isAllPiecesEaten(Piece piece) {
		ArrayList<Piece> ediblePieces= piece.getMustEdiblePieces();
		if(ediblePieces.isEmpty()) {
			return true;
		}
		return false;
	}


	/**
	 * checks if player with playerColor has more possible moves on board
	 * 
	 * @param playerColor
	 * @return true if player is stuck, false otherwise
	 */
	public boolean isPlayerStuck(PrimaryColor playerColor) {
		if(getAllLegalMoves(playerColor).isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * Method to be called for moving a piece
	 * @param from location of a piece to move
	 * @param to location of a tile to move to
	 * @param direction the direction to move the piece
	 * @return true if the move has succeeded, otherwise false
	 * @throws IllegalMoveException 
	 * @throws LocationException 
	 */
	//TODO remove method
	public boolean movePiece(Location from, Location to, Directions direction) throws IllegalMoveException, LocationException {
		//		System.out.println("Attempting to move piece from: " + from.getColumn() + "" + from.getRow() + " | to : " + to.getColumn() + "" + to.getRow());
		//		HashMap<Piece, ArrayList<Piece>> toBurn = searchToBurn();
		//		Player currPlayer =Game.getInstance().getTurn().getCurrentPlayer();
		//		Piece piece = null;
		//		Tile fromTile = null, toTile = null;
		//		try {
		//			fromTile = getTileInLocation(from);
		//			toTile = getTileInLocation(to);
		//		} catch (Exception e) {
		//			System.out.println(e.getMessage());
		//			return false;
		//		}
		//		if(fromTile == null || toTile == null) {
		//			System.out.println("Invalid Locations..");
		//			return false;
		//		}
		//		piece = fromTile.getPiece();
		//
		//		if(piece == null) {
		//			this.printBoard();
		//			System.out.println("Tile has no piece!");
		//			return false;
		//		}
		//		if(currPlayer.getColor() != piece.getColor()) {
		//			throw new IllegalMoveException("You cannot move your opponent's piece");
		//		}
		//
		//		if(piece.move(toTile, direction)) {
		//			Turn turn = Game.getInstance().getTurn();
		//			if(toBurn.containsKey(piece)) {
		//				ArrayList<Piece> temp = toBurn.get(piece);
		//				toBurn.remove(piece);
		//				toBurn.put(turn.getLastPieceMoved(), temp);
		//			}
		//			burnAllPiecesMissedEating(toBurn);
		//
		//			if(turn.getMoveCounter()>0) {
		//				Game.getInstance().getTurn().decrementMoveCounter();
		//			}
		//			if(piece.getLocation() != null)
		//				if(piece instanceof Queen) System.out.println("Queen has been moved!");
		//				else System.out.println("Soldier has been moved!");
		//
		//			// is there eating left for the piece
		//			if(!isAllPiecesEaten(Game.getInstance().getTurn().getLastPieceMoved()) && 
		//					Game.getInstance().getTurn().getLastPieceMoved().getEatingCntr() > 0) {
		//				Game.getInstance().getTurn().IncrementMoveCounter();
		//			}
		//			if(turn.getMoveCounter() == 0) {
		//				Game.getInstance().switchTurn(); // TODO Add conditions on move counter - move piece more than once
		//			}
		//
		//			if(getColorPieces(PrimaryColor.WHITE).size() == 0 || getColorPieces(PrimaryColor.BLACK).size() == 0)
		//				Game.getInstance().finishGame();
		//			return true;
		//		}
		//
		//
		return false;
	}

	/**
	 * Used to print the board
	 */
	public void printBoard() {
		System.out.print("\r\n");
		System.out.println("    A | B | C | D | E | F | G | H");
		for(int i = BOARD_SIZE ; i > 0 ; i--) {
			System.out.println("   ___ ___ ___ ___ ___ ___ ___ ___");
			for(int j = 0; j < BOARD_SIZE ; j++) {
				if(j == 0) {
					System.out.print(i + " |");
				} 
				if(this.tiles.get(i).get(j).getPiece() != null) {
					if(this.tiles.get(i).get(j).getPiece().getColor() == PrimaryColor.BLACK) {
						if(this.tiles.get(i).get(j).getPiece() instanceof Soldier)
							System.out.print(" B |");
						else
							System.out.print("B Q|");
					}
					else {
						if(this.tiles.get(i).get(j).getPiece() instanceof Soldier)
							System.out.print(" W |");
						else
							System.out.print("W Q|");
					}
				}else {
					System.out.print("   |");
				}

				if(j==BOARD_SIZE-1) {
					System.out.print(" " + i); 
				}
			}
			System.out.println();
		}

		System.out.println("   ___ ___ ___ ___ ___ ___ ___ ___");
		System.out.println("    A | B | C | D | E | F | G | H\r\n");

	}

	/**
	 * Burns specific pieces 
	 * @param toBurn a HashMap that contains every piece with its must eat pieces of the opponent
	 */
	public void burnAllPiecesMissedEating(HashMap<Piece, ArrayList<Piece>> toBurn) {
		Turn turn = Game.getInstance().getTurn();

		if(toBurn.containsKey(turn.getLastPieceMoved())) {
			if(toBurn.get(turn.getLastPieceMoved()).contains(turn.getEaten())) return;
		}else{
			if(Game.getInstance().getTurn().isLastTileRed()) return;
		}
		//TODO Red Tile to be a condition is this case

		if(toBurn.keySet().size() > 0) {
			Random r = new Random();
			ArrayList<Piece> temp = new ArrayList<>(toBurn.keySet());
			int random = r.nextInt(temp.size());
			burn(temp.get(random), false);
		}
	}


	/**
	 * Searches for every possible must eat situation
	 * @return HashMap with key of pieces, ArrayList of possible edible pieces as value
	 */
	public HashMap<Piece, ArrayList<Piece>> searchToBurn(){
		HashMap<Piece, ArrayList<Piece>> toBurn = new HashMap<>();
		for(Piece p : getColorPieces(Game.getInstance().getTurn().getCurrentPlayer().getColor())) {
			ArrayList<Piece> temp = p.getMustEdiblePieces();
			if(!temp.isEmpty())
				toBurn.put(p, temp);
		}
		return toBurn;
	}


	/**
	 * removes all secondary colors from board tiles
	 */
	public void removeAllSeconderyColorsFromBoard() {
		ArrayList<Tile> boardTiles= getAllBoardTiles();
		if(!this.coloredTilesList.isEmpty()) {
			boardTiles.retainAll(coloredTilesList);
			for(Tile t :boardTiles) {
				Tile basicTile= new Tile(t.getLocation(), t.getColor1(), null, t.getPiece());
				replaceTileInSameTileLocation(basicTile);						
			}
		}
		this.coloredTilesList=null;
		this.coloredTilesList=new ArrayList<Tile>();
		this.orangeTiles=null;
		this.orangeTiles=new ArrayList<Tile>();

	}

	/**
	 * adds all needed color tiles for the turn beginning to board
	 */
	public void initiateBoardSecondaryColors(){
		removeAllSeconderyColorsFromBoard();
		ColoredTilesFactory coloredTilesFactory =  new ColoredTilesFactory();
		//TODO add yellow tiles

		for(int i=0 ; i<YELLOW_TILES_AMOUNT;i++) {
			Tile randTile=null;
			do {
				randTile= getRandomFreeTile();
			}while(coloredTilesList.contains(randTile));
			YellowTile yTile= (YellowTile) coloredTilesFactory.createColoredTile(randTile, SeconderyTileColor.YELLOW);
			replaceTileInSameTileLocation(yTile);
			coloredTilesList.add(yTile);
			System.out.println("adding yellow in "+randTile);
		}


		//add red tiles

		if(canAddRedTile() && isThereLegalTilesNotColored()){

			System.out.println("adding red");
			ArrayList<Tile> possibleRed=getPossibleRedTiles();
			Tile randTile=null;
			if(possibleRed.size() > 0) {
				Random rand=new Random();
				do {
					int tempp = rand.nextInt(possibleRed.size());
					randTile= possibleRed.get(tempp);
					possibleRed.remove(tempp);
				}while(coloredTilesList.contains(randTile) && !possibleRed.isEmpty());
				Tile rTile =coloredTilesFactory.createColoredTile(randTile, SeconderyTileColor.RED);
				replaceTileInSameTileLocation(rTile);
				coloredTilesList.add(rTile);
				System.out.println("adding red in "+randTile);
			}else {
				System.out.println("not adding red");
			}
		}else {
			System.out.println("not adding red");
		}


		//TODO check add blue tiles
		if(canAddBlueTile()){
			System.out.println("adding red");
			Tile randTile=null;
			do {
				randTile = getRandomFreeTile();
			}while(coloredTilesList.contains(randTile));
			BlueTile bTile =(BlueTile)coloredTilesFactory.createColoredTile(randTile, SeconderyTileColor.BLUE);
			replaceTileInSameTileLocation(bTile);
			coloredTilesList.add(bTile);
		}else {
			System.out.println("not adding blue");
		}


		//		System.out.println("board  tiles in init secondary:\n");
		//		for(Tile t :Game.getInstance().getBoard().getAllBoardTiles()) {
		//			System.out.println(t);
		//
		//		}
		//		
		//		
		//		System.out.println("colored list after init: ");
		//		for(Tile t:this.coloredTilesList) {
		//			System.out.println(t);
		//		}
	}

	//helping method for checking if red tile can be added to board or not
	private boolean canAddRedTile() {

		if(isAllPiecesEaten(Game.getInstance().getCurrentPlayerColor())) {
			return true;

		}
		return false;
	}

	//helping method for checking if red tile can be added to board or not
	private boolean canAddBlueTile() {
		int soldierCount=0,queenCount=0;


		ArrayList<Piece> playerPieces = Board.getInstance().getColorPieces(Game.getInstance().getCurrentPlayerColor());
		for(Piece p:playerPieces)
		{
			if(p instanceof Soldier)
			{
				soldierCount++;
			}
			if(p instanceof Queen)
			{
				queenCount++;
			}
		}
		return (soldierCount == 2 && queenCount == 1);
	}

	// helping method checks if there is not colored legal tiles left
	private boolean isThereLegalTilesNotColored(){

		ArrayList<Tile> legalTiles=getAllLegalMoves(Game.getInstance().getCurrentPlayerColor());
		legalTiles.removeAll(coloredTilesList);
		if(legalTiles.isEmpty()) {
			return false;
		}
		return true;
	}

	/**
	 * A green tile to Board
	 */
	public Tile AddGreenTile(){
		ColoredTilesFactory coloredTilesFactory =  new ColoredTilesFactory();

		if(!isThereLegalTilesNotColored()) return null;
		
		Tile randTile=null;
		if(Game.getInstance().getTurn().getLastPieceMoved() != null) {
			if(Game.getInstance().getTurn().getLastPieceMoved().getEatingCntr() > 0 || Game.getInstance().getTurn().isLastTileRed()) {
				ArrayList<Tile> tiles = Game.getInstance().getTurn().getLastPieceMoved().getPossibleMoves(Game.getInstance().getCurrentPlayerColor());
				tiles.removeAll(coloredTilesList);
				if(tiles.isEmpty()) {
					return null;
				}
				
				if(tiles.size() > 0) {
					Random random = new Random();
					do {
						int temp = random.nextInt(tiles.size());
						randTile = tiles.get(temp);
						tiles.remove(temp);
					}while(coloredTilesList.contains(randTile) && tiles.size() > 0);
				}
			}
		}else {
			do {
				randTile= getRandomLegalTile();
				System.out.println("doing do while");
				System.out.println("randTile= "+randTile);
				System.out.println("coloredTiles List \n"+coloredTilesList.size());
				//TODO ask what to do when all possible tiles are taken by other colors
			}while(coloredTilesList.contains(randTile));
		}
		if(randTile == null) return null;
		
		Tile gTile =coloredTilesFactory.createColoredTile(randTile, SeconderyTileColor.GREEN);
		if(replaceTileInSameTileLocation(gTile)) {
			coloredTilesList.add(gTile);
		}else {
			System.out.println("Error: couldn't replace tile when adding green");
		}
		return gTile;
	}

	/**
	 *
	 * Adds orange tile toBoard
	 */
	public void  addOrangeTiles()
	{
		updateBoardToAddingOrange();
		ArrayList<Tile> tiles = null;

		if(Game.getInstance().getTurn().getLastPieceMoved() != null) {
			if(Game.getInstance().getTurn().getLastPieceMoved().getEatingCntr() > 0 || Game.getInstance().getTurn().isLastTileRed()) {
				tiles = Game.getInstance().getTurn().getLastPieceMoved().getPossibleMoves(Game.getInstance().getCurrentPlayerColor());
			}
		}else {
			tiles = Board.getInstance().getAllLegalMoves(Game.getInstance().getCurrentPlayerColor());
			
		}
		
		for(Tile tile:tiles)
		{
			SeconderyTileColor newSecColor= tile.getColor2()==SeconderyTileColor.YELLOW ? SeconderyTileColor.YELLOW_ORANGE:SeconderyTileColor.ORANGE ;

			Tile orangeTile=Board.getInstance().addSeconderyColorToBoardTile(tile,newSecColor);
			this.orangeTiles.add(orangeTile);
		}
	}

	private boolean updateBoardToAddingOrange(){
		ArrayList<Tile> legalTiles = getAllLegalMoves(Game.getInstance().getCurrentPlayerColor());

		ArrayList<Tile> tiles = null;

		if(Game.getInstance().getTurn().getLastPieceMoved() != null) {
			if(Game.getInstance().getTurn().getLastPieceMoved().getEatingCntr() > 0 || Game.getInstance().getTurn().isLastTileRed()) {
				tiles = Game.getInstance().getTurn().getLastPieceMoved().getPossibleMoves(Game.getInstance().getCurrentPlayerColor());
			}
		}else {
			tiles = Board.getInstance().getAllLegalMoves(Game.getInstance().getCurrentPlayerColor());
			
		}
		
		
		for(Tile t:this.coloredTilesList) {
			if(tiles.contains(t) && !(t instanceof YellowTile)) {
				//blue transfer to regular tile
				if(t instanceof BlueTile) {
					replaceTileInSameTileLocation(new Tile(t.getLocation(), t.getColor1(), null, t.getPiece()));
				}else {
					//remove secondary color
					t.setColor2(null);
					replaceTileInSameTileLocation(t);
				}

			}

		}

		return true;

	}

	public void updateColoredTileListAfterOrange(){
		ArrayList<Tile> tiles = null;
		if(Game.getInstance().getTurn().getLastPieceMoved() != null) {
			if(Game.getInstance().getTurn().getLastPieceMoved().getEatingCntr() > 0 || Game.getInstance().getTurn().isLastTileRed()) {
				tiles = Game.getInstance().getTurn().getLastPieceMoved().getPossibleMoves(Game.getInstance().getCurrentPlayerColor());
			}
		}else {
			tiles = Board.getInstance().getAllLegalMoves(Game.getInstance().getCurrentPlayerColor());
			
		}
		
		ArrayList<Tile> coloredTilesToRemove=new ArrayList<Tile>();		
		for(Tile t:this.coloredTilesList) {
			if(tiles.contains(t)) {

				coloredTilesToRemove.add(t);
			}

		}
		System.out.println("colored Tiles To remove:\n"+coloredTilesToRemove);
		this.coloredTilesList.removeAll(coloredTilesToRemove);
		this.coloredTilesList.addAll(this.orangeTiles);
		this.orangeTiles=null;
		this.orangeTiles=new ArrayList<Tile>();
	}
//	//TODO canLocateRedTile
//	private boolean canLocateRedInTile(){
//		//if player already stepped in red
//		//if check only in legal location for piece
//		//else
//		//check all legal moves if they can be red
//		return false;
//	}

	/**
	 * checks for possible tiles for red tile
	 * @return  ArrayList<Tile> of possible red tile
	 */
	private ArrayList<Tile> getPossibleRedTiles(){
		ArrayList<Tile> toReturn = new ArrayList<>();
		
		Game game=Game.getInstance();
		//final Directions[] upDir= {Directions.UP_LEFT,Directions.UP_RIGHT};
		//final Directions[] downDir= {Directions.DOWN_LEFT,Directions.DOWN_RIGHT};
		ArrayList<Tile> legalTiles= getAllLegalMoves(Game.getInstance().getCurrentPlayerColor());
		ArrayList<Tile> blockedTiles=new ArrayList<Tile>();
		legalTiles.removeAll(coloredTilesList);
		//		only one soldier can move
		if(game.getTurn().isLastTileRed()) {
			Piece lastPMoved = game.getTurn().getLastPieceMoved();
			ArrayList<Tile> possibleTiles=lastPMoved.getPossibleMoves(game.getCurrentPlayerColor());
			possibleTiles.removeAll(coloredTilesList);
			if(!possibleTiles.isEmpty()) {
				for(Tile t: possibleTiles) {
					Piece tempPiece=null;
					if(lastPMoved instanceof Soldier) {
						tempPiece=new Soldier(lastPMoved.getId(), lastPMoved.getColor(), t.getLocation());
					}else {
						tempPiece=new Queen(lastPMoved.getId(), lastPMoved.getColor(), t.getLocation());
					}
					if(tempPiece.getPossibleMoves(game.getCurrentPlayerColor()).isEmpty()) {
						blockedTiles.add(t);
					}
				}
				possibleTiles.removeAll(blockedTiles);
			}
			toReturn =  possibleTiles;
		}
		else {
			HashMap<Tile, ArrayList<Piece>> tempCollection = new HashMap<>();
			
			for(Tile t:legalTiles) {
				for(Piece p : getColorPieces(Game.getInstance().getCurrentPlayerColor())){
					boolean canMove = false;
					if(!canMove) {
						try {
							canPieceMove(p, t.getLocation(), Directions.UP_LEFT);
						} catch (LocationException | IllegalMoveException e) {
						}
						canMove = true;
					}
					if(!canMove) {
						try {
							canPieceMove(p, t.getLocation(), Directions.UP_RIGHT);
						} catch (LocationException | IllegalMoveException e) {
						}
						canMove = true;
					}
					if(!canMove) {
						try {
							canPieceMove(p, t.getLocation(), Directions.DOWN_RIGHT);
						} catch (LocationException | IllegalMoveException e) {
						}
						canMove = true;
					}
					if(!canMove) {
						try {
							canPieceMove(p, t.getLocation(), Directions.DOWN_LEFT);
						} catch (LocationException | IllegalMoveException e) {
						}
						canMove = true;
					}
					if(canMove) {
						ArrayList<Piece> pp = null;
						if(!tempCollection.containsKey(t)) {
							pp = new ArrayList<>();
						}else {
							pp = tempCollection.get(t);
						}
						pp.add(p);
						tempCollection.put(t, pp);
					}
				}
			}
			
			ArrayList<Tile> tilesToRemove = new ArrayList<>();
			
			for(Tile t : tempCollection.keySet()) {
				ArrayList<Piece> pieces= tempCollection.get(t);
				for(Piece p : pieces) {
					Piece tempPiece=null;
					if(p instanceof Soldier) {
						tempPiece=new Soldier(p.getId(), p.getColor(), t.getLocation());
					}else {
						tempPiece=new Queen(p.getId(), p.getColor(), t.getLocation());
					}
					if(tempPiece.getPossibleMoves(game.getCurrentPlayerColor()).isEmpty()) {
						if(!tilesToRemove.contains(t))
							tilesToRemove.add(t);
					}
				}
			}
			
			for(Tile t : tilesToRemove) {
				tempCollection.remove(t);
			}
			
			toReturn =  new ArrayList<Tile>(tempCollection.keySet());	
		}
		return toReturn;
	}


}
