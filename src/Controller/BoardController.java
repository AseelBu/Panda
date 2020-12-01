package Controller;

import java.util.ArrayList;

import Model.Board;
import Model.Location;
import Model.Piece;
import Model.Soldier;
import Model.Tile;
import Utils.Directions;
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
	
	public Tile getTile(int row, char col) {
		if(Board.getInstance() == null) return null;
		ArrayList<Tile> tilesInRow = Board.getInstance().getTilesMap().get(row);
		if(tilesInRow == null) return null;
		return tilesInRow.get(col - 'A');
	}
	
	public boolean addPieceToBoard(Piece piece) {
		return board.addPieceToBoard(piece);
	}
	
	public void loadPiecesToBoard() {
		for(Piece p : Board.getInstance().getPieces())
			DisplayController.boardGUI.addPieceToBoard(p);
	}
	
	public boolean movePiece(int fromRow, char fromCol, int toRow, char toCol, Directions direction) {
		Location fromLocation = new Location(fromRow, fromCol);
		Location toLocation = new Location(toRow, toCol);
		return Board.getInstance().movePiece(fromLocation, toLocation, direction);
	}
	
	public void removePiece(Location location, boolean isBurnt) {
		board.removePiece(location.getRow(), location.getColumn(), isBurnt);
	}
	
	public void upgradeSoldier(Soldier soldier) {
		board.upgradeToQueen(soldier.getLocation().getRow(), soldier.getLocation().getColumn());
	}
	
	public boolean checkBurnCurrent(int row, char col) {
		if(Board.getInstance().getTilesMap().get(row).get(col - 'A').getPiece() == null) return true;
		return false;
	}
}
