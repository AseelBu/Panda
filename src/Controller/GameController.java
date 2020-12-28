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

		// TODO can inside be removed?
		//		this.sysData = SysData.getInstance();
		//		this.highscores_screen = new Scoreboard();


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
		
		//DisplayController.boardGUI.setPlayerScore(game.getCurrentPlayerColor(),BoardController.getInstance().getPlayerScore(game.getCurrentPlayerColor()));
		
		
		if(game.getTurn().getMoveCounter() == 0) {
			Game.getInstance().switchTurn(); // TODO Add conditions on move counter - move piece more than once
			
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
	 * un-pauses the game
	 */
	public void unpauseGame() {
		Game.getInstance().getTimer().unpauseTimer();
		Game.getInstance().getTurn().getTimer().unpauseTimer();
	}
}
