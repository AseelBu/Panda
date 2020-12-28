package Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import Exceptions.GameUpgradeException;
import Exceptions.IllegalMoveException;
import Exceptions.LocationException;
import Exceptions.QuestionException;
import Model.Board;
import Model.Game;
import Model.Location;
import Model.Piece;
import Model.Player;
import Model.Queen;
import Model.Soldier;
import Model.Tile;
import Model.Turn;
import Model.YellowTile;
import Utils.Directions;
import Utils.PrimaryColor;
import Utils.SeconderyTileColor;
import View.BoardGUI;

public class BoardController {

	private static BoardController instance=null;
	private BoardGUI boardGUI;
	private int retrievals;
	private boolean answeringQuestion=false;

	//private constructor
	private BoardController() {
		boardGUI = DisplayController.boardGUI;
		retrievals = 0;
	}

	/**
	 * constructor for singleton pattern
	 * @return Instance of the board controller
	 */
	public static BoardController getInstance() 
	{ 
		if (instance == null) 
		{ 
			instance = new BoardController(); 
		} 
		return instance; 
	}

	/**
	 * sets players instances nickname
	 * @param name1 the white player nickname 
	 * @param name2 the black player nickname
	 */
	public void setPlayersNicknames(String name1, String name2) {
		Player.getInstance(0).setNickname(name1);
		Player.getInstance(1).setNickname(name2);
	}

	/**
	 * gets the instances of the 2 players of the current game
	 * @return Player[] of the current game
	 */
	public Player[] getPlayers() {
		return new Player[] {Player.getInstance(0), Player.getInstance(1)};
	}

	/**
	 * 
	 * @return BoardGUI the boardGUI instance
	 */
	public BoardGUI getBoardGUI() {
		return boardGUI;
	}

	/**
	 * 
	 * @param board the boardGUI instance to be set
	 */
	public void setBoardGUI(BoardGUI board) {
		this.boardGUI = board;
	}

	/**
	 * checks if question window is currently open
	 * @return the answeringQuestion
	 */
	public boolean isAnsweringQuestion() {
		return answeringQuestion;
	}

	/**
	 * updates controller that question window is open or closed
	 * @param answeringQuestion the answeringQuestion to set
	 */
	public void setAnsweringQuestion(boolean answeringQuestion) {
		this.answeringQuestion = answeringQuestion;
	}

	/**
	 * used to get a specific tile from board by its location
	 * @param row the tile row
	 * @param col the tile column
	 * @return Tile the tile in (row,col)
	 */
	public Tile getTile(int row, char col) {
		if(Board.getInstance() == null) return null;
		ArrayList<Tile> tilesInRow = Board.getInstance().getTilesMap().get(row);
		if(tilesInRow == null) return null;
		return tilesInRow.get(col - 'A');
	}

	/**
	 * Gets the tile color
	 * @param row the tile row
	 * @param col the tile column
	 * @return PrimaryColor of the tile 
	 */
	public PrimaryColor getTileColor(int row, char col) {
		Tile tile = getTile(row,col);
		if(tile == null) return null;
		return tile.getColor1();
	}


	/**
	 * method to add piece to board's display
	 * @param piece the piece to add to board
	 * @return true if added, otherwise false
	 */
	public boolean addPieceToBoard(Piece piece) {
		return boardGUI.addPieceToBoard(piece.getLocation().getRow(), piece.getLocation().getColumn(), piece.getColor(), (piece instanceof Soldier));
	}

	/**
	 * loads board by initial standard pieces
	 */
	public void loadPiecesToBoard() {
		for(Piece p : Board.getInstance().getPieces())
			boardGUI.addPieceToBoard(p.getLocation().getRow(), p.getLocation().getColumn(), p.getColor(), (p instanceof Soldier));
	}

	/**
	 * loads the tile colors from the board model to the board gui
	 */
	public void loadTilesColors(){
		for(Tile t: Board.getInstance().getColoredTilesList()) {
			boardGUI.addColoredTileToBoard(t.getLocation().getRow(), t.getLocation().getColumn(), t.getColor2());
		}
	}

	/**
	 * move piece, made to be controlled by GUI
	 * @param fromRow
	 * @param fromCol
	 * @param toRow
	 * @param toCol
	 * @param direction
	 * @return true if moved, otherwise false
	 * @throws LocationException location is out game board bounds
	 * @throws IllegalMoveException the move wasn't legal for some reason
	 */
	public boolean movePiece(int fromRow, char fromCol, int toRow, char toCol, Directions direction)throws LocationException, IllegalMoveException {
		Board board =Board.getInstance();
		Location fromLocation = new Location(fromRow, fromCol);
		Location toLocation = new Location(toRow, toCol);
		HashMap<Piece, ArrayList<Piece>> toBurn = board.searchToBurn();
		Player currPlayer =Game.getInstance().getTurn().getCurrentPlayer();
		Piece piece = null;
		Tile fromTile = null, toTile = null;
		try {
			fromTile = board.getTileInLocation(fromLocation);
			toTile = board.getTileInLocation(toLocation);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		if(fromTile == null || toTile == null) {
			System.out.println("Invalid Locations..");
			return false;
		}
		piece = fromTile.getPiece();

		if(piece == null) {
			board.printBoard();
			System.out.println("Tile has no piece!");
			return false;
		}
		if(currPlayer.getColor() != piece.getColor()) {
			throw new IllegalMoveException("You cannot move your opponent's piece");
		}

		if(Game.getInstance().getTurn().getLastPieceMoved() != null) {
			if(Game.getInstance().getTurn().getLastPieceMoved().getEatingCntr() > 0) {
				if(!Game.getInstance().getTurn().getLastPieceMoved().equals(piece)) {
					throw new IllegalMoveException("You can only move the piece in tile "+Game.getInstance().getTurn().getLastPieceMoved().getLocation());
				}
			}
		}

		if(Game.getInstance().getTurn().isLastTileRed() && !Game.getInstance().getTurn().getLastPieceMoved().equals(piece)) {
			throw new IllegalMoveException("You can only move the piece in tile "+Game.getInstance().getTurn().getLastPieceMoved().getLocation());
		}

		if(piece.move(toTile, direction)) {
			Turn turn = Game.getInstance().getTurn();
			if(toBurn.containsKey(piece)) {
				ArrayList<Piece> temp = toBurn.get(piece);
				toBurn.remove(piece);
				toBurn.put(turn.getLastPieceMoved(), temp);
			}
			Piece burnt = board.burnAllPiecesMissedEating(toBurn);
			if(burnt != null) {
				System.out.println("===============++++++++" + burnt.getLocation());
				DisplayController.boardGUI.showBurn(burnt.getLocation().getRow(), burnt.getLocation().getColumn());
			}

			if(turn.getMoveCounter()>0) {
				Game.getInstance().getTurn().decrementMoveCounter();
			}



			if(piece.getLocation() != null)
				if(piece instanceof Queen) System.out.println("Queen has been moved!");
				else System.out.println("Soldier has been moved!");

			// is there eating left for the piece
			if(!board.isAllPiecesEaten(Game.getInstance().getTurn().getLastPieceMoved()) && 
					Game.getInstance().getTurn().getLastPieceMoved().getEatingCntr() > 0) {

				try {
					Game.getInstance().getTurn().IncrementMoveCounter();
				} catch (GameUpgradeException e) {
					System.out.println(e.getMessage());
					//					boardGUI.notifyUpgradeInGame(e.getMessage());
				}

			}

			return true;
		}
		return false;
	}

	/**
	 * removes piece from board's display
	 * @param location of the piece 
	 * @param isBurnt boolean parameter,true if the removing caused by burning ,false if it's caused by eating or something else
	 */
	public void removePiece(Location location, boolean isBurnt) {
		boardGUI.removePiece(location.getRow(), location.getColumn(), isBurnt);
	}

	/**
	 * checks if the current piece should be burnt after moving it
	 * @param row the row of piece location
	 * @param col the column of piece location
	 * @return true if should be burnt in display, otherwise false
	 * @throws LocationException given location is out of board boundaries
	 */
	public boolean checkBurnCurrent(int row, char col) throws LocationException {
		Piece pieceInLoc = Board.getInstance().getTileInLocation(new Location(row, col)).getPiece();
		if(pieceInLoc == null) return true;

		return false;
	}

	/**
	 * gets player score from Model to be Used in GUI
	 * @param color the player color
	 * @return int the score of the player
	 */
	public int getPlayerScore(PrimaryColor color) {
		int index = (color == PrimaryColor.WHITE ? 0 : 1);
		return Player.getInstance(index).getCurrentScore();
	}

	/**
	 * gets current turn player color from Model.
	 * used to switch turn in GUI
	 * @return color
	 */
	public PrimaryColor getCurrentPlayerColor() {
		return Game.getInstance().getCurrentPlayerColor();
	}

	/**
	 * returns direction by specified move locations
	 * @param fromRow the current location row of the piece
	 * @param fromCol the current location column of the piece
	 * @param toRow the target location row of the move
	 * @param toCol the target location column of the move
	 * @return Directions the direction of the move
	 */
	public Directions getDirection(int fromRow, char fromCol, int toRow, char toCol, boolean isSoldier) {
		int diffCol = toCol - fromCol;
		int diffRow = toRow - fromRow;
		int boardSize = Board.getInstance().getBoardSize();
		char columnLowerBound=Board.getInstance().getColumnLowerBound();
		char columnUpperBound=Board.getInstance().getColumnUpperBound();

		if(isSoldier) {
			if(diffCol > 0 && diffRow > 0) return Directions.UP_RIGHT;
			if(diffCol > 0 && diffRow < 0) return Directions.DOWN_RIGHT;
			if(diffCol < 0 && diffRow > 0) return Directions.UP_LEFT;
			if(diffCol < 0 && diffRow < 0) return Directions.DOWN_LEFT;
		}else {
			if(fromCol == columnUpperBound && toCol == columnLowerBound) {
				if(fromRow == boardSize && toRow == 1) return Directions.UP_RIGHT;
				if(diffRow > 0) return Directions.UP_RIGHT;
				else if(diffRow < 0) return Directions.DOWN_RIGHT;
			}else if(fromCol == columnLowerBound && toCol == columnUpperBound) {
				if(fromRow == 1 && toRow == boardSize) return Directions.DOWN_LEFT; 
				if(diffRow > 0) return Directions.UP_LEFT;
				else if(diffRow < 0) return Directions.DOWN_LEFT;
			}else if(fromRow == 1 && toRow == boardSize) {
				if(fromCol == columnUpperBound && toCol ==columnLowerBound) return Directions.DOWN_RIGHT;
				if(diffCol > 0) return Directions.DOWN_RIGHT;
				else if(diffCol < 0) return Directions.DOWN_LEFT;
			}else if(fromRow == boardSize && toRow == 1) {
				if(fromCol == columnLowerBound && toCol == columnUpperBound) return Directions.UP_LEFT;
				if(diffCol > 0) return Directions.UP_RIGHT;
				else if(diffCol < 0) return Directions.DOWN_RIGHT;
			}else {
				return getDirection(fromRow, fromCol, toRow, toCol, !isSoldier);
			}
		}
		return null;
	}

	/**
	 * Method to validate location is within the board bounds
	 * @param row the location row
	 * @param col the location column
	 * @return true if the location is valid, otherwise false;
	 */
	public boolean validateLocation(int row, char col) {
		if ((row>=1 && row <= Board.getInstance().getBoardSize()) && 
				(col >= Board.getInstance().getColumnLowerBound() && col <= Board.getInstance().getColumnUpperBound())) {
			return true;
		}
		return false;
	}

	/**
	 *  checks if tile in specific location contains a piece of current player or not
	 * @param row the location row
	 * @param col the location column
	 * @param color the current player color
	 * @return true if the piece in location exists and it's for the current player
	 * @throws LocationException the location is out of board boundaries
	 */
	public boolean pieceExists(int row, char col, PrimaryColor color) throws LocationException {

		Piece piece = Board.getInstance().getTileInLocation(new Location(row, col)).getPiece();
		if(piece == null) return false;
		if(piece.getColor() != color) return false;
		return true;
	}

	/**
	 * triggers window opening to notify who won the game
	 * @param Winname the name of the game winner
	 * @param score the score of the winner player
	 * @param color the color of the winner player
	 */
	public void finishGame(String Winname, int score, PrimaryColor color) {
		boardGUI.notifyWinner(Winname, score, color);

	}

	/**
	 * This method is only called on initial open of winner display.
	 * It get's the winner of the game.  
	 * @return Player the winner player
	 */
	public Player getWinner() {
		if(Game.getInstance().isGameRunning()) return null;
		int winner = -1;
		if(Player.getInstance(0).getCurrentScore() > Player.getInstance(1).getCurrentScore())
			winner = 1;
		else if (Player.getInstance(0).getCurrentScore() < Player.getInstance(1).getCurrentScore())
			winner = 2;
		if(winner == -1) {
			if(Game.getInstance().getBoard().getColorPieces(PrimaryColor.WHITE).size() > 
			Game.getInstance().getBoard().getColorPieces(PrimaryColor.BLACK).size()){
				winner = 1;
			}else if(Game.getInstance().getBoard().getColorPieces(PrimaryColor.WHITE).size() < 
					Game.getInstance().getBoard().getColorPieces(PrimaryColor.BLACK).size()) {
				winner = 2;
			}
		}
		if(winner > -1) {
			return Player.getInstance(winner-1);
		}
		System.out.println(winner);
		return null;
	}

	public void forceFinishGame() {
		Game.getInstance().finishGame();
		Player player = BoardController.getInstance().getWinner();
		if(player != null)
			DisplayController.boardGUI.notifyWinner(player.getNickname(), player.getCurrentScore(), player.getColor());
		else
			DisplayController.boardGUI.notifyWinner(null, Integer.MIN_VALUE, PrimaryColor.WHITE);
		boardGUI.destruct();
	}



	/**
	 * activates the powers of the colored tile based on it's color
	 * @param row the row of the tile
	 * @param col the column of the tile
	 * @param tileColor the secondary color of the tile
	 */
	public String stepOnColorTile(int row,char col,SeconderyTileColor tileColor) {


		System.out.println("stepping on "+tileColor+" tile");
		try {
			switch(tileColor) {
			case RED: {		
				Game.getInstance().getTurn().setLastTileRed(true);
				try {
					Game.getInstance().getTurn().IncrementMoveCounter();						
				}catch (GameUpgradeException e) {
					return null;
				}					
				return null;
			}
			case GREEN: {
				Game.getInstance().getTurn().getCurrentPlayer().AddScore(50);
				return null;
			}


			case YELLOW:
			case YELLOW_ORANGE:{
				YellowTile yt;

				yt = ((YellowTile) Board.getInstance().getTileInLocation(new Location(row, col)));

				yt.drawQuestion(); 

				SoundController.getInstance().play30();

				DisplayController.getInstance().showQuestion(yt.getQuestion(), Game.getInstance().getCurrentPlayerColor());

				this.answeringQuestion = true;

				return null;
			}
			case BLUE:{
				HashMap<Integer, ArrayList<Character>> tiles = getAllAvailableRetrievals();
				if(!tiles.keySet().isEmpty()) {
					Game.getInstance().getTimer().pauseTimer();
					Game.getInstance().getTurn().getTimer().pauseTimer();
					DisplayController.boardGUI.showRetrievalSelection(tiles);
				}
				return "BLUE";
			}
			default:
				break;
			}
		}catch (LocationException |QuestionException e) {
			boardGUI.notifyByError(e.getMessage());
		}

		return null;
	}

	/**
	 * 
	 * @return ArrayList<Tile> colored tiles
	 */
	public ArrayList<Tile> getAllColoredTiles(){
		return Board.getInstance().getColoredTilesList();
	}

	/**
	 * 
	 * @return HashMap of available retrievals locations by row and column as Integer and Character respectively 
	 */
	public HashMap<Integer,ArrayList<Character>> getAllAvailableRetrievals() {
		HashMap<Integer,ArrayList<Character>> tiles = new HashMap<>();
		TreeSet<Tile> unavailable = new TreeSet<>();
		PrimaryColor turn = Game.getInstance().getTurn().getCurrentPlayer().getColor();
		for(Piece piece : Board.getInstance().getPieces()) {
			try {
				unavailable.add(Board.getInstance().getTileInLocation(piece.getLocation()));
			} catch (LocationException e) {
				continue;
			}
			int row = piece.getLocation().getRow();
			char col = piece.getLocation().getColumn();
			if(piece.getColor() != turn) {
				for(int i = -2 ; i <= 2 ; i++) {
					if(i == 0) continue;
					for(int j = -2 ; j <= 2 ; j++) {
						if(j == 0) continue;
						try {
							Location tempLocation = new Location(row + i, (char) ((char) col + j));
							unavailable.add(Board.getInstance().getTileInLocation(tempLocation));
						} catch (LocationException e) {
							continue;
						}
					}
				}
			}
		}

		for(int i = 1 ; i <= Board.getInstance().getBoardSize() ; i++) {
			for(char c = Board.getInstance().getColumnLowerBound() ; c <= Board.getInstance().getColumnUpperBound() ; c++) {
				Tile tile = null;
				try {
					tile = Board.getInstance().getTileInLocation(new Location(i,c));
				} catch (LocationException e) {
					e.printStackTrace();
				}
				if(tile == null) continue;
				if(tile.getColor1() == PrimaryColor.WHITE) continue;
				if(i == 1 && (turn.equals(PrimaryColor.BLACK))) continue; // check if retrieving soldier & instant upgrade available
				if(i == Board.getInstance().getBoardSize() && (turn.equals(PrimaryColor.WHITE))) continue; 
				if(unavailable.contains(tile)) {
					unavailable.remove(tile);
					continue;
				}
				if(tiles.containsKey(i)) {
					tiles.get(i).add(c);
				}else {
					tiles.put(i, new ArrayList<Character>());
					tiles.get(i).add(c);
				}
			}
		}
		return tiles;
	}

	/**
	 * Provide location by row & column as parameters to add them to the boardGUI and actual board
	 * @param row the location row of retrieval
	 * @param col the location column of retrieval
	 * @param pieceColor the color of the retrieved piece 
	 */
	public void retrieveSoldier(int row, char col, PrimaryColor pieceColor) {
		try {
			Board.getInstance().addPiece(new Soldier((25 + retrievals),pieceColor, new Location(row, col)));
		} catch (LocationException e) {
			e.printStackTrace();
		}
		DisplayController.boardGUI.addPieceToBoard(row, col, pieceColor, true);
	}

	/**
	 * updates players scores in view
	 */
	public void refreshScoreInBoardGUI() {
		DisplayController.boardGUI.setPlayerScore(Player.getInstance(0).getColor(), Player.getInstance(0).getCurrentScore());
		DisplayController.boardGUI.setPlayerScore(Player.getInstance(1).getColor(), Player.getInstance(1).getCurrentScore());
	}

	/**
	 * 
	 * @param fromRow
	 * @param fromCol
	 * @param toRow
	 * @param toCol
	 * @return direction by the selected source and target locations
	 */
	public Directions getMoveDirection(int fromRow, char fromCol, int toRow, char toCol) {
		Location from = null;
		Location to = null;
		try {
			from = new Location(fromRow, fromCol);
			to = new Location(toRow, toCol);
		} catch (LocationException e) {
			return null;
		}
		return 	from.getRelativeDirection(to);
	}

	/**
	 * This method is used to create Piece for Edit mode
	 * Will add tile only in View's side
	 * 
	 * @param allPieces pieces HashMap, key is the location (row_col), Value is the type i.e. Soldier_BLACK
	 * @return ArrayList<Piece>
	 */
	public ArrayList<Piece> createPieces(HashMap<String,String> allPieces) {
		ArrayList<Piece> pieces = new ArrayList<>();
		int i = 1;
		for(String str : allPieces.keySet()) {
			String location[] = str.split("_");
			int row = Integer.valueOf(location[0]);
			char col = location[1].toCharArray()[0];
			String type[] = allPieces.get(str).split("_");
			try {
				Piece piece = null;
				if(type[0].matches("Soldier")) {
					piece = new Soldier(i,(type[1].matches("WHITE") ? PrimaryColor.WHITE : PrimaryColor.BLACK)
							,new Location(row, col ));
				}else {
					piece = new Queen(i,(type[1].matches("WHITE") ? PrimaryColor.WHITE : PrimaryColor.BLACK)
							,new Location(row, col ));
				}
				if(piece != null)
					pieces.add(piece);
			} catch (LocationException e) {
				continue;
			}
			i++;
		}
		return pieces;
	}

	/**
	 * This method is used to create tile for Edit mode
	 * Will add tile only in View's side
	 * @param pieces
	 * @param row
	 * @param col
	 * @return the added tile
	 */
	public Tile createTile(ArrayList<Piece> pieces, int row, char col) {
		Tile tile = null;
		try {
			PrimaryColor color = null;
			if(row % 2 == 1 && (col - 'A') % 2 == 0) {
				color = PrimaryColor.BLACK;
			}else if(row % 2 == 0 && (col - 'A') % 2 == 1) {
				color = PrimaryColor.BLACK;
			}else {
				color = PrimaryColor.WHITE;
			}
			tile = new Tile.Builder(new Location(row, col), color).build();
			Piece piece = null;
			for(Piece p : pieces) {
				if(p.getLocation().getRow() == row && p.getLocation().getColumn() == col) {
					piece = p;
					break;
				}
			}
			tile.setPiece(piece);

		} catch (LocationException e) {
			return tile;
		}
		return tile;
	}
}
