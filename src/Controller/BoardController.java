package Controller;

import java.util.ArrayList;

import Model.Board;
import Model.Piece;
import Model.Tile;
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
	
}
