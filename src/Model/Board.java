/**
 * 
 */
package Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
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

	private static Board instance;

	/**
	 * Board class constructor
	 */
	private Board(){
		pieces = new ArrayList<Piece>();
		tiles = new HashMap<Integer, ArrayList<Tile>>(BOARD_SIZE);
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
	public void initBoardTiles() {
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
	public Tile getTileInLocation(Location location) throws Exception {

		if (location == null) return null;
		ArrayList<Tile> tilesInRow = getTilesinRow(location.getRow());
		if(tilesInRow != null) {
			return tilesInRow.get(location.getColumn() - 'A');
		}else {
			throw new Exception("Error: Row " + location.getRow() + " has no tiles");
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
	public boolean replaceTile(Tile tile) {

		boolean isSuccess = false;
		int tileRow = tile.getLocation().getRow();
		int tileCol = tile.getLocation().getColumn();

		ArrayList<Tile> boardRow= tiles.get(tileRow);

		boardRow.set(tileCol - 'A', tile);
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
			this.replaceTile(tile);
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
	 */
	public void addColorToBoardTile(Tile tilee,SeconderyTileColor color) {
		Location tLoc =tilee.getLocation();

		Tile tile=null;
		try {
			tile = getTileInLocation(tLoc);
			tile.setColor2(color);
			this.replaceTile(tile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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


	/**
	 * checks if the piece can move to targetLocation depending on all pieces locations on board
	 * @param piece
	 * @param targetLocation
	 * @return true if it's legal to move the piece ,otherwise false
	 */
	public boolean canPieceMove(Piece piece, Location targetLocation, Directions direction) {
		// is tile black location
		Tile targetTile=null;
		try {
			targetTile = getTileInLocation(targetLocation);
			if (targetTile.getColor1()!=PrimaryColor.BLACK) {
				System.out.println("piece can't move to this "+targetLocation+", you can move only on black tiles! ");
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		// does tile contain another piece
		if(targetTile.getPiece() != null) {
			System.out.println("piece can't move to this "+targetLocation+", it contains another Piece!");
			return false;
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
						System.out.println("soldier can't move 2 steps if you are not eating another piece");
						return false;
					}
				}
				else if (steps!=1) {
					System.out.println("soldier can only move 2 steps if you are eating ,and 1 step if you are not");
					return false;
				}

			}// if it's moving backwards it's because it's eating for second time
			else if(((piece.getColor()==PrimaryColor.BLACK) && (direction == Directions.UP_LEFT || direction == Directions.UP_RIGHT)) 
					|| ((piece.getColor()==PrimaryColor.WHITE) && (direction == Directions.DOWN_LEFT|| direction == Directions.DOWN_RIGHT))  ) {
				if(steps !=2) {
					System.out.println("soldier can't move backwards unless you are moving 2 steps while eating in sequence");
					return false;
				}//if 2 steps
				else {
					int eatingCntr =piece.getEatingCntr();
					if (eatingCntr<1) {
						System.out.println("soldier can't move backwards unless you are eating for the second time");
						return false;
					}//if in  eating sequence
					else {
						//if there is nothing to eat while moving in 2
						if(ediblePiece == null) {
							System.out.println("soldier can't move 2 steps if you are not eating another piece");
							return false;
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
				System.err.println("Illegal Move!");
				return false;
			}
			if(((Queen) piece).isPieceBlockedByDirection(targetLocation, direction)) {
				System.err.println("Queen is blocked!");
				return false;
			}
			try {
				if(((Queen) piece).getPiecesCountByDirection(targetLocation, direction) > 1) {
					System.err.println("Failed to eat more than one piece in one move, try to split your move");
					return false;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
			if(p instanceof Soldier) {
				try {
					//adding tiles without eating
					Location pieceLocal = p.getLocation();
					Directions[] direc = null;
					if(playerColor == PrimaryColor.WHITE)
						direc = upDirections;
					else 
						direc = downDirections;
					for (Directions dir : direc) {
						Location tempLoc = pieceLocal.addToLocationDiagonally(dir, 1);
						
						if(tempLoc != null) {
							Tile locTile = getTileInLocation(tempLoc);
							if( locTile.isEmpty()) {
								possibleTileSet.add(locTile);
							}
						}
					}
					Directions[] eat = null;
					if(playerColor == PrimaryColor.WHITE)
						eat = downDirections;
					else 
						eat = upDirections;
					
					//adding tiles with eating
					for (Directions dir :direc) {
						Piece ediblePiece=p.getEdiblePieceByDirection(dir);
						if(ediblePiece != null) {
							Location afterEatLoc=ediblePiece.getLocation().addToLocationDiagonally(dir, 1);
							if(afterEatLoc != null) {
								possibleTileSet.add(getTileInLocation(afterEatLoc));
							}
						}
					}
					if(p.getEatingCntr()>=1) {
						for (Directions dir :eat) {
							Piece ediblePiece=p.getEdiblePieceByDirection(dir);
							if(ediblePiece != null) {
								Location afterEatLoc=ediblePiece.getLocation().addToLocationDiagonally(dir, 1);
								if(afterEatLoc != null) {
									possibleTileSet.add(getTileInLocation(afterEatLoc));
								}
							}
						}
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}else if(p instanceof Queen){
				try {
					Collection<HashMap<Character, Tile>> temp = null;
					temp = ((Queen) p).getAllAvailableMovesByDirection(Directions.UP_LEFT).values();
					temp.iterator().next();
					possibleTileSet.addAll(temp.iterator().next().values());

					temp = ((Queen) p).getAllAvailableMovesByDirection(Directions.UP_RIGHT).values();
					temp.iterator().next();
					possibleTileSet.addAll(temp.iterator().next().values());
					
					temp = ((Queen) p).getAllAvailableMovesByDirection(Directions.DOWN_LEFT).values();
					temp.iterator().next();
					possibleTileSet.addAll(temp.iterator().next().values());
					
					temp = ((Queen) p).getAllAvailableMovesByDirection(Directions.DOWN_RIGHT).values();
					temp.iterator().next();
					possibleTileSet.addAll(temp.iterator().next().values());
					
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				}
			}
			//TODO handle queen
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
	 */
	public Piece eat(Piece pieceEatig, Piece targetPiece) {
		if(pieceEatig == null || targetPiece == null) {
			System.out.println("null arguments in Board.eat method call");
			return null;
		}
		Turn turn =Game.getInstance().getTurn();
		Player currPlayer=turn.getCurrentPlayer();
		if(pieceEatig.getColor() != currPlayer.getColor()) {
			System.err.println("eating piece must be the same color as current player playing");
			return null;
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
	 */
	public boolean movePiece(Location from, Location to, Directions direction) {
		System.out.println("Attempting to move piece from: " + from.getColumn() + "" + from.getRow() + " | to : " + to.getColumn() + "" + to.getRow());
		HashMap<Piece, ArrayList<Piece>> toBurn = searchToBurn();
		Player currPlayer =Game.getInstance().getTurn().getCurrentPlayer();
		Piece piece = null;
		Tile fromTile = null, toTile = null;
		try {
			fromTile = getTileInLocation(from);
			toTile = getTileInLocation(to);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
		if(fromTile == null || toTile == null) {
			System.err.println("Invalid Locations..");
			return false;
		}
		piece = fromTile.getPiece();

		if(piece == null) {
			System.err.println("Tile has no piece!");
			return false;
		}
		if(currPlayer.getColor() != piece.getColor()) {
			System.err.println("You cannot move your opponent's piece");
			return false;
		}

		if(piece.move(toTile, direction)) {
			Turn turn = Game.getInstance().getTurn();
			if(toBurn.containsKey(piece)) {
				ArrayList<Piece> temp = toBurn.get(piece);
				toBurn.remove(piece);
				toBurn.put(turn.getLastPieceMoved(), temp);
			}
			burnAllPiecesMissedEating(toBurn);
			
			if(turn.getMoveCounter()>0) {
				Game.getInstance().getTurn().decrementMoveCounter();
			}
			if(piece.getLocation() != null)
				if(piece instanceof Queen) System.out.println("Queen has been moved!");
				else System.out.println("Soldier has been moved!");
			
			// is there eating left for the piece
			if(!isAllPiecesEaten(piece) && Game.getInstance().getTurn().getLastPieceMoved().getEatingCntr() > 0) {
				Game.getInstance().getTurn().IncrementMoveCounter();
			}
			if(turn.getMoveCounter()==0) {
				Game.getInstance().switchTurn(); // TODO Add conditions on move counter - move piece more than once
			}
			return true;
		}


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
		
		if(toBurn.containsKey(turn.getLastPieceMoved()))
			if(toBurn.get(turn.getLastPieceMoved()).contains(turn.getEaten())) return;
		//TODO Red Tile to be a condition is this case
		for(Piece p : toBurn.keySet()) {
			burn(p, false);
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


}
