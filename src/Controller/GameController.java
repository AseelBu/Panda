/**
 * 
 */
package Controller;

import Model.Board;
import Model.Game;
import Model.Player;

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
}
