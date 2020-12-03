package Controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import Model.Board;
import Model.Game;
import Model.Piece;
import Model.Player;
import Model.SysData;
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
		Player player1 = Player.getInstance(0);
		Player player2 = Player.getInstance(1);
		player1.setNickname("Jack");
		player2.setNickname("Max");

		Player[] players = new Player[] {
								player1,
								player2
								};
		if(players != null) {
			System.out.print("Player 1 : " + players[0].getNickname() + " || ");
			System.out.println("Player 2 : " + players[1].getNickname());
		}
		Game game = Game.getInstance();
		
		try {
			game.startGame(players);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boardGUI = new BoardGUI();
		boardGUI.start(boardGUI.getPrimary());
		boardGUI.initiateGamePlayers(Player.getInstance(0).getNickname(), Player.getInstance(1).getNickname());
		boardGUI.setNewTurn(Game.getInstance().getTurn().getCurrentPlayer().getColor());
	}
	
	public void showBoard(File file) {
		HashMap<Character, ArrayList<Piece>> load = null;
		load = SysData.getInstance().loadGame(file); // path to be changed according to file
		
		Player player1 = Player.getInstance(0);
		Player player2 = Player.getInstance(1);
		player1.setNickname("Jack");
		player2.setNickname("Max");

		Player[] players = new Player[] {
								player1,
								player2
								};
		if(players != null) {
			System.out.print("Player 1 : " + players[0].getNickname() + " || ");
			System.out.println("Player 2 : " + players[1].getNickname());
		}
		
		try {
			if(load.containsKey('W')) {
				Game.getInstance().startGame(players, load.get('W'), 'W');
			}else {
				Game.getInstance().startGame(players, load.get('B'), 'B');
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}
		
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
