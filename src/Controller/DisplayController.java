package Controller;

import Model.Board;
import Model.Game;
import Model.Player;
import View.BoardGUI;
import View.Mainscreen;
import View.ManageQuestions;
import View.Scoreboard;

public class DisplayController {

	private static DisplayController instance;
	public static Mainscreen mainscreen;
	public static BoardGUI boardGUI;
	public static ManageQuestions manageQuestions;
	public static Scoreboard scoreboard;
	
	private DisplayController() {
		
	}
	
	public static DisplayController getInstance() 
	{ 
		if (instance == null) 
		{ 
			instance = new DisplayController(); 
		} 
		return instance; 
	}
	
	public void showBoard() {
		boardGUI = new BoardGUI();
		boardGUI.start(boardGUI.getPrimary());
		boardGUI.initiateGamePlayers(Player.getInstance(0).getNickname(), Player.getInstance(1).getNickname());
		boardGUI.setNewTurn(Game.getInstance().getTurn().getCurrentPlayer().getColor());
	}
	
	public void showMainScreen() {
		mainscreen = new Mainscreen();
		mainscreen.start(mainscreen.getPrimary());

	}
	
	public void showManageQuestions() {
		manageQuestions = new ManageQuestions();
		manageQuestions.start(manageQuestions.getPrimary());
	}
	
	public void showScoreboard() {
		scoreboard = new Scoreboard();
		scoreboard.start(scoreboard.getPrimary());
	}
	
	public void closeMainscreen() {
		mainscreen.getPrimary().hide();
	}
	
	public void closeBoard() {
		boardGUI.getPrimary().hide();
	}
	
	public void closeScoreboard() {
		scoreboard.getPrimary().hide();
	}
	
	public void closeManageQuestions() {
		manageQuestions.getPrimary().hide();
	}
	
}
