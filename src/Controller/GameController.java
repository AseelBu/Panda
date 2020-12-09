/**
 * 
 */
package Controller;

import Model.Board;
import Model.Game;
import Model.Player;
import Model.SysData;
import Utils.PrimaryColor;
import View.BoardGUI;
import View.Scoreboard;

/**
 * @author aseel
 *
 */
public class GameController {

	private static GameController instance;

	private GameController() {


		//		this.sysData = SysData.getInstance();
		//		this.highscores_screen = new Scoreboard();


	}

	/**
	 * Get Instance
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


	//switches current turn or finishes game if needed
	public boolean switchTurn() {
		Board board= Board.getInstance();
		boolean didSwitch=false;
		
		if(Game.getInstance().getTurn().getMoveCounter() == 0) {
			Game.getInstance().switchTurn(); // TODO Add conditions on move counter - move piece more than once
			
			didSwitch=true;
		}

		if((board.getColorPieces(PrimaryColor.WHITE).size() == 0 
				|| board.getColorPieces(PrimaryColor.BLACK).size() == 0) || !Game.getInstance().isGameRunning()) {
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
	
	public boolean isGameRunning() {
		return Game.getInstance().isGameRunning();
	}


}
