package Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import Exceptions.GameUpgradeException;
import Exceptions.IllegalMoveException;
import Exceptions.LocationException;
import Model.Answer;
import Model.Board;
import Model.Game;
import Model.Location;
import Model.Piece;
import Model.Player;
import Model.Queen;
import Model.Question;
import Model.Soldier;
import Model.Tile;
import Model.Turn;
import Model.YellowTile;
import Utils.Directions;
import Utils.PrimaryColor;
import Utils.SeconderyTileColor;
import View.BoardGUI;

public class BoardController {

	private static BoardController instance;
	private BoardGUI boardGUI;
	private int retrievals;
	
	private BoardController() {
		boardGUI = DisplayController.boardGUI;
		retrievals = 0;
	}

	public static BoardController getInstance() 
	{ 
		if (instance == null) 
		{ 
			instance = new BoardController(); 
		} 
		return instance; 
	}

	/**
	 * used to get a specific tile from board by its location
	 * @param row
	 * @param col
	 * @return a tile
	 */
	public Tile getTile(int row, char col) {
		if(Board.getInstance() == null) return null;
		ArrayList<Tile> tilesInRow = Board.getInstance().getTilesMap().get(row);
		if(tilesInRow == null) return null;
		return tilesInRow.get(col - 'A');
	}

	public PrimaryColor getTileColor(int row, char col) {
		Tile tile = getTile(row,col);
		if(tile == null) return null;
		return tile.getColor1();
	}

	/**
	 * method to add piece to board's display
	 * @param piece
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
	 * @throws LocationException-location is out game bord bounds
	 * @throws IllegalMoveException 
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

		if(piece.move(toTile, direction)) {
			Turn turn = Game.getInstance().getTurn();
			if(toBurn.containsKey(piece)) {
				ArrayList<Piece> temp = toBurn.get(piece);
				toBurn.remove(piece);
				toBurn.put(turn.getLastPieceMoved(), temp);
			}
			board.burnAllPiecesMissedEating(toBurn);

			if(turn.getMoveCounter()>0) {
				Game.getInstance().getTurn().decrementMoveCounter();
			}

			// TODO step on handling not finished
			//toTile.stepOnBy(piece);

			if(piece.getLocation() != null)
				if(piece instanceof Queen) System.out.println("Queen has been moved!");
				else System.out.println("Soldier has been moved!");

			// is there eating left for the piece
			if(!board.isAllPiecesEaten(Game.getInstance().getTurn().getLastPieceMoved()) && 
					Game.getInstance().getTurn().getLastPieceMoved().getEatingCntr() > 0) {

				try {
					Game.getInstance().getTurn().IncrementMoveCounter();
				} catch (GameUpgradeException e) {
					boardGUI.notifyUpgradeInGame(e.getMessage());
				}


			}
			//TODO check removing this doesn't affect any thing
			//			if(turn.getMoveCounter() == 0) {
			//				Game.getInstance().switchTurn(); // TODO Add conditions on move counter - move piece more than once
			//			}
			//
			//			if(board.getColorPieces(PrimaryColor.WHITE).size() == 0 || board.getColorPieces(PrimaryColor.BLACK).size() == 0)
			//				Game.getInstance().finishGame();
			return true;
		}


		return false;
		//return Board.getInstance().movePiece(fromLocation, toLocation, direction);
	}

	/**
	 * removes piece from board's display
	 * @param location
	 * @param isBurnt
	 */
	public void removePiece(Location location, boolean isBurnt) {
		boardGUI.removePiece(location.getRow(), location.getColumn(), isBurnt);
	}

	/**
	 * checks if the current piece should be burnt after moving it
	 * @param row
	 * @param col
	 * @return true if should be burnt in display, otherwise false
	 */
	public boolean checkBurnCurrent(int row, char col) {
		if(Board.getInstance().getTilesMap().get(row).get(col - 'A').getPiece() == null) return true;
		return false;
	}

	/**
	 * Updates score in display
	 * @param score
	 * @return 
	 */
	public int getPlayerScore(PrimaryColor color) {
		int index = (color == PrimaryColor.WHITE ? 0 : 1);
		return Player.getInstance(index).getCurrentScore();
	}

	/**
	 * Method to switch turn in GUI
	 * @param color
	 */
	public PrimaryColor getPlayerTurn() {
		return Game.getInstance().getTurn().getCurrentPlayer().getColor();
	}

	/**
	 * returns direction by specified move locations
	 * @param fromRow
	 * @param fromCol
	 * @param toRow
	 * @param toCol
	 * @return
	 */
	public Directions getDirection(int fromRow, char fromCol, int toRow, char toCol, boolean isSoldier) {
		int diffCol = toCol - fromCol;
		int diffRow = toRow - fromRow;

		if(isSoldier) {
			if(diffCol > 0 && diffRow > 0) return Directions.UP_RIGHT;
			if(diffCol > 0 && diffRow < 0) return Directions.DOWN_RIGHT;
			if(diffCol < 0 && diffRow > 0) return Directions.UP_LEFT;
			if(diffCol < 0 && diffRow < 0) return Directions.DOWN_LEFT;
		}else {
			if(fromCol == 'H' && toCol == 'A') {
				if(fromRow == 8 && toRow == 1) return Directions.UP_RIGHT;
				if(diffRow > 0) return Directions.UP_RIGHT;
				else if(diffRow < 0) return Directions.DOWN_RIGHT;
			}else if(fromCol == 'A' && toCol == 'H') {
				if(fromRow == 1 && toRow == 8) return Directions.DOWN_LEFT; 
				if(diffRow > 0) return Directions.UP_LEFT;
				else if(diffRow < 0) return Directions.DOWN_LEFT;
			}else if(fromRow == 1 && toRow == 8) {
				if(fromCol == 'H' && toCol == 'A') return Directions.DOWN_RIGHT;
				if(diffCol > 0) return Directions.DOWN_RIGHT;
				else if(diffCol < 0) return Directions.DOWN_LEFT;
			}else if(fromRow == 8 && toRow == 1) {
				if(fromCol == 'A' && toCol == 'H') return Directions.UP_LEFT;
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
	 * @param row
	 * @param col
	 * @return true if the location is valid, otherwise false;
	 */
	public boolean validateLocation(int row, char col) {
		if ((row>=1 && row <= Board.getInstance().getBoardSize()) && 
				(col >= Board.getInstance().getColumnLowerBound() && col <= Board.getInstance().getColumnUpperBound())) {
			return true;
		}
		return false;
	}

	public boolean pieceExists(int row, char col, PrimaryColor color) {
		try {
			Location location = new Location(row, col);
		} catch (Exception e) {
			return false;
		}
		Piece piece = ((Tile) Board.getInstance().getTilesMap().get(row).get(col - 'A')).getPiece();
		if(piece == null) return false;
		if(piece.getColor() != color) return false;
		return true;
	}

	//TODO finish game without winner always
	public void finishGame(String Winname, int score, PrimaryColor color) {
		boardGUI.notifyWinner(Winname, score, color);
//		boardGUI.destruct();
	}
	
	/**
	 * This method is only called on initial open of winner display 
	 * @return winner score
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

	public BoardGUI getBoard() {
		return boardGUI;
	}

	public void setBoard(BoardGUI board) {
		this.boardGUI = board;
	}


	/**
	 * activates the powers of the colored tile based on it's color
	 * @param row
	 * @param col
	 * @param tileColor

	 */
	public String stepOnColorTile(int row,char col,SeconderyTileColor tileColor) {
		try {
			System.out.println("stepping on "+tileColor+" tile");

			switch(tileColor) {
			case RED: {		
				
				Game.getInstance().getTurn().IncrementMoveCounter();
				return null;
			}
			case GREEN: {
				Game.getInstance().getTurn().getCurrentPlayer().AddScore(50);
				return null;
			}

		case YELLOW:{ //getIdQuestion(row,col);
			DisplayController.getInstance().showQuestion(((YellowTile) Board.getInstance().getTilesMap().get(row).get(col - 'A')).getQuestion(), 
					Game.getInstance().getTurn().getCurrentPlayer().getColor()); return null;}
		//
			case YELLOW_ORANGE:{//getIdQuestion(row,col);
				DisplayController.getInstance().showQuestion(((YellowTile) Board.getInstance().getTilesMap().get(row).get(col - 'A')).getQuestion(),
						Game.getInstance().getTurn().getCurrentPlayer().getColor()); return null;}
				//TODO QuestionPOPUP
				//call boardGUI to open pop up question with blur on screen
				//continue in checkQuestionAnswer
		
			case BLUE:{
				DisplayController.boardGUI.showRetrievalSelection(getAllAvailableRetrievals());
				return "BLUE";
			}


			}
		} catch (GameUpgradeException e) {
			return e.getMessage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null;
	}
	
//	public Question getIdQuestion(int row,char col)
//	{
//		Question q = ((YellowTile) Board.getInstance().getTilesMap().get(row).get(col - 'A')).getQuestion();
//		
//		return q;
//	}

	public ArrayList<Tile> getAllColoredTiles(){
		return Board.getInstance().getColoredTilesList();
	}

	public HashMap<Integer,ArrayList<Character>> getAllAvailableRetrievals() {
		HashMap<Integer,ArrayList<Character>> tiles = new HashMap<>();
		TreeSet<Tile> unavailable = new TreeSet<>();
		PrimaryColor turn = Game.getInstance().getTurn().getCurrentPlayer().getColor();
		for(Piece piece : Board.getInstance().getPieces()) {
			try {
				unavailable.add(Board.getInstance().getTileInLocation(piece.getLocation()));
			} catch (LocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
	 * @param row
	 * @param col
	 * @param pieceColor
	 */
	public void retrieveSoldier(int row, char col, PrimaryColor pieceColor) {
		try {
			Board.getInstance().addPiece(new Soldier((25 + retrievals),pieceColor, new Location(row, col)));
		} catch (LocationException e) {
			e.printStackTrace();
		}
		DisplayController.boardGUI.addPieceToBoard(row, col, pieceColor, true);
	}

	public void refreshScoreInBoardGUI() {
		DisplayController.boardGUI.setPlayerScore(Player.getInstance(0).getColor(), Player.getInstance(0).getCurrentScore());
		DisplayController.boardGUI.setPlayerScore(Player.getInstance(1).getColor(), Player.getInstance(1).getCurrentScore());
	}
	
}
