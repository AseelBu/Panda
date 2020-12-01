package Controller;

import Model.Player;
import View.BoardGUI;
import View.Mainscreen;

public class DisplayController {

	private static DisplayController instance;
	public static Mainscreen mainscreen;
	public static BoardGUI boardGUI;
	
	
	
	
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
	}
	
	public void closeMainscreen() {
		mainscreen.getPrimary().hide();
	}
}
