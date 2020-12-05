package Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import Model.Game;
import Model.Piece;
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
			System.out.println(game.getGameTime());
			System.out.println("Invalid Game Initiation");
			if(boardGUI != null)
				if(boardGUI.getPrimary() != null)
					if(boardGUI.getPrimary().isShowing())
						closeBoard();
			if(!mainscreen.getPrimary().isShowing())
				showMainScreen();
			return;
		}
		
		boardGUI = new BoardGUI();
		BoardController.getInstance().setBoard(boardGUI);
		boardGUI.start(boardGUI.getPrimary());
		boardGUI.initiateGamePlayers(Player.getInstance(0).getNickname(), Player.getInstance(1).getNickname());
		boardGUI.setNewTurn(Game.getInstance().getTurn().getCurrentPlayer().getColor());
		TimerController fullTimer = new TimerController();
		game.getTimer().startTimer();
		fullTimer.start();
	}
	
	public void showBoard(File file) {
		HashMap<Character, ArrayList<Piece>> load = null;
		load = MiscController.getInstance().loadGame(file); 
		
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
			System.out.println(Game.getInstance().getGameTime());
			System.out.println("Invalid Game Initiation");
			if(boardGUI != null)
				if(boardGUI.getPrimary() != null)
					if(boardGUI.getPrimary().isShowing())
						closeBoard();
			if(!mainscreen.getPrimary().isShowing())
				showMainScreen();
			return;
		}
		
		boardGUI = new BoardGUI();
		BoardController.getInstance().setBoard(boardGUI);
		boardGUI.start(boardGUI.getPrimary());
		boardGUI.initiateGamePlayers(Player.getInstance(0).getNickname(), Player.getInstance(1).getNickname());
		boardGUI.setNewTurn(Game.getInstance().getTurn().getCurrentPlayer().getColor());
		TimerController fullTimer = new TimerController(); 
		Game.getInstance().getTimer().startTimer();
		fullTimer.start();
	}
	
	public void showMainScreen() {
		mainscreen = new Mainscreen();
		mainscreen.start(mainscreen.getPrimary());

	}
	
	public void showManageQuestions() throws Exception {
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
