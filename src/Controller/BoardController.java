package Controller;

import java.util.ArrayList;

import Model.Board;
import Model.Location;
import Model.Piece;
import Model.Soldier;
import Model.Tile;
import Utils.Directions;
import Utils.PrimaryColor;
import View.BoardGUI;

public class BoardController {

	private static BoardController instance;
	private BoardGUI board;
	
	private BoardController() {
		board = new BoardGUI();
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
	
	/**
	 * method to add piece to board's display
	 * @param piece
	 * @return true if added, otherwise false
	 */
	public boolean addPieceToBoard(Piece piece) {
		return board.addPieceToBoard(piece);
	}
	
	/**
	 * loads board by initial standard pieces
	 */
	public void loadPiecesToBoard() {
		for(Piece p : Board.getInstance().getPieces())
			DisplayController.boardGUI.addPieceToBoard(p);
	}
	
	/**
	 * move piece, made to be controlled by GUI
	 * @param fromRow
	 * @param fromCol
	 * @param toRow
	 * @param toCol
	 * @param direction
	 * @return true if moved, otherwise false
	 */
	public boolean movePiece(int fromRow, char fromCol, int toRow, char toCol, Directions direction) {
		Location fromLocation = new Location(fromRow, fromCol);
		Location toLocation = new Location(toRow, toCol);
		return Board.getInstance().movePiece(fromLocation, toLocation, direction);
	}
	
	/**
	 * removes piece from board's display
	 * @param location
	 * @param isBurnt
	 */
	public void removePiece(Location location, boolean isBurnt) {
		board.removePiece(location.getRow(), location.getColumn(), isBurnt);
	}
	
	/**
	 * upgrades soldier to queen
	 * @param soldier
	 */
	public void upgradeSoldier(Soldier soldier) {
		board.upgradeToQueen(soldier.getLocation().getRow(), soldier.getLocation().getColumn());
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
	 */
	public void updateScoreInDisplay(PrimaryColor color, int newScore) {
		board.setPlayerScore(color, newScore);
	}
	
	/**
	 * Method to switch turn in GUI
	 * @param color
	 */
	public void switchTurn(PrimaryColor color) {
		board.setNewTurn(color);
		//TODO more to be added, such as turn time
	}
}
