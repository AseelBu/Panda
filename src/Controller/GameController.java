/**
 * 
 */
package Controller;

import java.util.HashMap;

import Model.Board;
import Model.Game;
import Model.Location;
import Model.Piece;
import Model.Player;
import Model.Soldier;
import Utils.PrimaryColor;

/**
 * @author aseel
 *
 */
public class GameController {

	private static GameController instance;

	private GameController() {

	}
 
	/**
	 * Get Game Controller Instance
	 * @return - Controller's instance
	 */
	public static GameController getInstance() 
	{ 
		if (instance == null) 
		{ 
			instance = new GameController(); 
		} 
		return instance; 
	}


	/**
	 * switches current turn or finishes game if needed
	 * @return true if turn did switch,false otherwise
	 */
	public boolean switchTurn() {
		Board board= Board.getInstance();
		Game game= Game.getInstance();
		boolean didSwitch=false;		
		
		if(game.getTurn().getMoveCounter() == 0) {
			Game.getInstance().switchTurn();
			didSwitch=true;
		}

		if(!Game.getInstance().isGameRunning() || Game.getInstance().isGameFinished()) {
			Game.getInstance().finishGame();
			didSwitch=true;
			Player player = BoardController.getInstance().getWinner();
			DisplayController.boardGUI.notifyWinner(player.getNickname(), player.getCurrentScore(), player.getColor());
			return false;
		}
		
		board.initiateBoardSecondaryColors();
		BoardController.getInstance().loadTilesColors();
		
		return didSwitch;
	}
	
	/**
	 * checks if the game is still running
	 * @return true if the game is running, false otherwise
	 */
	public boolean isGameRunning() {
		return Game.getInstance().isGameRunning();
	}

	/*
	 * pauses the game
	 */
	public void pauseGame() {
		Game.getInstance().getTimer().pauseTimer();
		Game.getInstance().getTurn().getTimer().pauseTimer();
	}

	/**
	 * unpauses the game
	 */
	public void unpauseGame() {
		Game.getInstance().getTimer().unpauseTimer();
		Game.getInstance().getTurn().getTimer().unpauseTimer();
	}
	/**
	 * unpauses the game only, without turn
	 */
	public void unpauseGame2() {
		Game.getInstance().getTimer().unpauseTimer();
	}
	/**
	 * resets turn's timer
	 */
	public void resetTurn() {
		Game.getInstance().getTurn().getTimer().resetTimer();
	}
	
	/**
	 * 
	 * @return HashMap of queens
	 */
	public HashMap<String,String> queensMode(){
		HashMap<String, String> queens = new HashMap<>();
		Board board = Board.getInstance();
		for(int i = 1 ; i <= 3 ; i++)
			for(char c = board.getColumnLowerBound() ; c <= board.getColumnUpperBound() ; c+=2) {
				if(i == 2 && c == board.getColumnLowerBound()) c = (char)(board.getColumnLowerBound()+1);
				queens.put(i + "_" + c, "Queen_WHITE");
			}
		//adding white soldiers to board
		for(int i = board.getBoardSize()-2 ; i <= board.getBoardSize() ; i++)
			for(char c = (char) (board.getColumnLowerBound()+1) ; c <= board.getColumnUpperBound() ; c+=2) {
				if(i == board.getBoardSize()-1 && c == (char)(board.getColumnLowerBound()+1)) c = board.getColumnLowerBound();
				queens.put(i + "_" + c, "Queen_BLACK");
			}
		return queens;
	}
}
