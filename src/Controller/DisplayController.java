package Controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import Exceptions.QuestionException;
import Model.Answer;
import Model.Board;
import Model.Game;
import Model.Piece;
import Model.Player;
import Model.Question;
import Utils.PrimaryColor;
import View.BoardEdit;
import View.BoardGUI;
import View.InstructionsGame;
import View.MainscreenGUI;
import View.ManageQuestions;
import View.Nicknames;
import View.QuestionGUI;
import View.Scoreboard;
import View.Winner;
import View.pointsTable;

/**
 * Control's all the displays
 *
 */
public class DisplayController {

	private static DisplayController instance;
	public static MainscreenGUI mainscreen;
	public static BoardGUI boardGUI;
	public static pointsTable points;
	public static ManageQuestions manageQuestions;
	public static Scoreboard scoreboard;
	public static Nicknames nicknames;
	public static QuestionGUI questions;
	public static InstructionsGame instructions;
	public static Winner winner;
	public static BoardEdit boardEdit;

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
	
	/**
	 * Opens board display
	 * @param players
	 */
	public void showBoard(Player[] players) {
		if(players != null) {
			System.out.print("Player 1 : " + players[0].getNickname() + " || ");
			System.out.println("Player 2 : " + players[1].getNickname());
		}
		Game game = Game.getInstance();
		
		try {
			game.startGame(players);
		} catch (Exception e) {
			System.out.println(game.getGameTime());
			nicknames.notifyError(e.getMessage());
			this.showMainScreen();
			if(boardGUI != null)
				if(boardGUI.getPrimary() != null)
					if(boardGUI.getPrimary().isShowing())
						closeBoard();
			if(!mainscreen.getPrimary().isShowing())
				showMainScreen();
			return;
		}
		
		boardGUI = new BoardGUI();
		BoardController.getInstance().setBoardGUI(boardGUI);
		boardGUI.start(boardGUI.getPrimary());
		boardGUI.initiateGamePlayers(Player.getInstance(0).getNickname(), Player.getInstance(1).getNickname());
		boardGUI.setNewTurn(Game.getInstance().getCurrentPlayerColor());
		GameTimerController fullTimer = new GameTimerController();
		game.getTimer().startTimer();
		fullTimer.start();
	}
	
	/**
	 * Opens board display
	 * @param players
	 * @param file
	 */
	public void showBoard(Player[] players,File file) {
		HashMap<Character, ArrayList<Piece>> load = null;
		load = MiscController.getInstance().loadGame(file); 
		
		if(load == null) {
			nicknames.notifyError("File cannot be loaded.. check the file format!");
			this.showMainScreen();
			return;
		}
	
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
			nicknames.notifyError(e.getMessage());
			e.printStackTrace();
			if(boardGUI != null)
				if(boardGUI.getPrimary() != null)
					if(boardGUI.getPrimary().isShowing())
						closeBoard();
			if(!mainscreen.getPrimary().isShowing())
				showMainScreen();
			return;
		}
		
		boardGUI = new BoardGUI();
		BoardController.getInstance().setBoardGUI(boardGUI);
		boardGUI.start(boardGUI.getPrimary());
		boardGUI.initiateGamePlayers(Player.getInstance(0).getNickname(), Player.getInstance(1).getNickname());
		boardGUI.setNewTurn(Game.getInstance().getCurrentPlayerColor());
		
		if(Board.getInstance().isPlayerStuck(Game.getInstance().getCurrentPlayerColor())) {
			Player player = BoardController.getInstance().getWinner();
			if(player != null)
				DisplayController.boardGUI.notifyWinner(player.getNickname(), player.getCurrentScore(), player.getColor());
			else
				DisplayController.boardGUI.notifyWinner(null, Integer.MIN_VALUE, PrimaryColor.WHITE);
			closeBoard();
			boardGUI.destruct();
			return;
		}

		GameTimerController fullTimer = new GameTimerController(); 
		Game.getInstance().getTimer().startTimer();
		fullTimer.start();
	}
	
	/**
	 * Opens board display
	 * @param players
	 * @param pieces
	 * @param turn
	 */
	public void showBoard(Player[] players,ArrayList<Piece> pieces, PrimaryColor turn) {	
		if(players != null) {
			System.out.print("Player 1 : " + players[0].getNickname() + " || ");
			System.out.println("Player 2 : " + players[1].getNickname());
		}
		System.out.println(pieces);
		try {
			Game.getInstance().startGame(players, pieces, (turn.equals(PrimaryColor.WHITE) ? 'W' : 'B'));
		}catch (Exception e) {
			System.out.println(Game.getInstance().getGameTime());
			nicknames.notifyError(e.getMessage());
			e.printStackTrace();
			if(boardGUI != null)
				if(boardGUI.getPrimary() != null)
					if(boardGUI.getPrimary().isShowing())
						closeBoard();
			if(!mainscreen.getPrimary().isShowing())
				showMainScreen();
			return;
		}
		DisplayController.getInstance().closeBoardEit();
		boardGUI = new BoardGUI();
		BoardController.getInstance().setBoardGUI(boardGUI);
		boardGUI.start(boardGUI.getPrimary());
		boardGUI.initiateGamePlayers(Player.getInstance(0).getNickname(), Player.getInstance(1).getNickname());
		boardGUI.setNewTurn(Game.getInstance().getTurn().getCurrentPlayer().getColor());
		
		if(Board.getInstance().isPlayerStuck(Game.getInstance().getTurn().getCurrentPlayer().getColor())) {
			Player player = BoardController.getInstance().getWinner();
			if(player != null)
				DisplayController.boardGUI.notifyWinner(player.getNickname(), player.getCurrentScore(), player.getColor());
			else
				DisplayController.boardGUI.notifyWinner(null, Integer.MIN_VALUE, PrimaryColor.WHITE);
			closeBoard();
			boardGUI.destruct();
			return;
		}

		GameTimerController fullTimer = new GameTimerController(); 
		Game.getInstance().getTimer().startTimer();
		fullTimer.start();
	}
	
	/**
	 * Shows main screen
	 */
	public void showMainScreen() {
		mainscreen = new MainscreenGUI();
		mainscreen.start(mainscreen.getPrimary());

	}
	
	/**
	 * Shows manage questions screen
	 */
	public void showManageQuestions(){
		
		manageQuestions = QuestionMgmtController.getInstance().getQuestionScreen();
		try {
			manageQuestions.start(manageQuestions.getPrimary());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * shows instructions screen
	 */
	public void showInstructionsGame() {
		instructions = new InstructionsGame();
		instructions.start(instructions .getPrimary());
		
	}
	/**
	 * shows point's table screen
	 */
	public void showPointsTable() {
		points = new pointsTable();
		points.start(instructions .getPrimary());
		
	}
	/**
	 * shows scoreboard screen
	 */
	public void showScoreboard() {
		ScoreBoardController.getInstance().loadHistory();
		scoreboard = new Scoreboard();
		scoreboard.start(scoreboard.getPrimary());
	}
	
	/**
	 * shows nicknames screen
	 */
	public void showNicknames() {
		nicknames = new Nicknames();
		nicknames.start(nicknames.getPrimary());
	}
	/**
	 * shows nicknames screen
	 * @param file
	 */
	public void showNicknames(File file) {
		nicknames = new Nicknames();
		nicknames.start(nicknames.getPrimary());
		nicknames.setFile(file);
	}
	
	/**
	 * shows nicknames screen
	 * @param pieces
	 * @param turn
	 */
	public void showNicknames(HashMap<String,String> pieces, PrimaryColor turn) {
		nicknames = new Nicknames();
		nicknames.start(nicknames.getPrimary());
		nicknames.setPieces(pieces);
		nicknames.setTurn(turn);
	}
	/**
	 * shows question screen
	 * @param question
	 * @param turnColor
	 * @throws QuestionException
	 */
	public void showQuestion(Question question, PrimaryColor turnColor) throws QuestionException {
		questions = new QuestionGUI(turnColor);
		questions.start(questions.getPrimary());
		HashMap<Integer, String> answers = new HashMap<>();
		
		for(Answer a : question.getAnswers()) {
			answers.put(a.getId(), a.getContent());
		}
		
		questions.loadDesign(question.getId(), question.getContent(), answers, question.getDifficulty());
	}
	/**
	 * shows winner screen
	 * @param name
	 * @param score
	 * @param color
	 */
	public void showWinner(String name, int score,PrimaryColor color) {
		winner = new Winner();
		winner.start(winner.getPrimary());
		winner.loadDisplay(name, score, color);
	}
	/**
	 * shows custom board screen
	 */
	public void showBoardEdit() {
		boardEdit = new BoardEdit();
		boardEdit.start(boardEdit.getPrimary());
	}
	
	/**
	 * closes custom board screen
	 */
	public void closeBoardEit() {
		if(boardEdit == null)
		{
			if(mainscreen != null)
				mainscreen.getPrimary().hide();
			return;
		}
		boardEdit.getPrimary().hide();
	}
	
	/**
	 * closes main screen
	 */
	public void closeMainscreen() {
		mainscreen.getPrimary().hide();
	}
	/**
	 * closes board screen
	 */
	public void closeBoard() {
		boardGUI.getPrimary().hide();
		boardGUI.destruct();
		Game.destruct();
		Board.destruct();
		Player.destruct();
	}
	/**
	 * closes scoreboard screen
	 */
	public void closeScoreboard() {
		scoreboard.getPrimary().hide();
		showMainScreen();
	}
	/**
	 * closes manage question's screen
	 */
	public void closeManageQuestions() {
		QuestionMgmtController.getInstance().getQuestionScreen().getPrimary().hide();
		showMainScreen();

	}
	/**
	 * closes instructions screen
	 */
	public void closeInstructionsGame() {
		instructions.getPrimary().hide();
		showMainScreen();

	}
	/**
	 * closes point's table screen
	 */
	public void closePointstable() {
		points.getPrimary().hide();
		showInstructionsGame();

	}
	/**
	 * closes winners screen
	 */
	public void closeWinner() {
		winner.getPrimary().hide();
	}
	/**
	 * closes nicknames screen
	 */
	public void closeNicknames() {
		nicknames.getPrimary().hide();
	}
}
