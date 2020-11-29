package Model;

import java.util.ArrayList;
import java.util.Random;

import Utils.PrimaryColor;

/**
 * 
 * @author firas
 *
 */
public class Game {

	/**
	 * Game Class Instance
	 */
	private static Game instance;

	private Board board;
	private Player players[];
	private GameTimer timer;
	private Turn turn;
	private ArrayList<Question> availableQuestions;
	private ArrayList<Question> unavailableQuestions;

	/**
	 * 
	 * Game Class Constructor
	 */
	private Game() {
		super();

		timer = new GameTimer();
	}
	/**
	 * 
	 * @return Game Singleton Instance
	 */
	public static Game getInstance() 
	{ 
		if (instance == null) 
		{ 
			instance = new Game(); 
		} 
		return instance; 
	}
	/**
	 * destructor for this class
	 */
	public static void destruct() {
		instance = null;
	}

	/**
	 * 
	 * @return game time in seconds (float)
	 */
	public float getGameTime() {
		return timer.getSeconds();
	}

	/**
	 * 
	 * @param seconds (int)
	 */
	public void setGameTime(int seconds) {
		timer.startTimer(seconds);
	}


	/**
	 * Start a game
	 * @throws Exception 
	 */
	public void startGame(Player[] players, ArrayList<Piece> pieces, char cturn) throws Exception {
		if(isGameRunning()) {
			System.out.println("A Game has already started");
			return;
		}
		if(players != null) {
			if(players.length != 2) throw new Exception("Invalid Game Initiation");
		}
		else throw new Exception("Invalid Game Initiation");	
		if(pieces == null)
		{
			throw new Exception("Invalid Game Initiation");
		}
		else if (pieces.size() < 2 || pieces.size() > 12) {
			throw new Exception("Invalid Game Initiation");
		}else if(pieces.size() >= 2) {
			PrimaryColor temp = pieces.get(0).getColor();
			boolean invalid = true;
			for(Piece p : pieces) {
				if(!p.getColor().equals(temp))
					invalid = false;
			}
			if(invalid)
				throw new Exception("Invalid Game Initiation");
		}
		setPlayers(players);

		this.board = Board.getInstance();
		board.initBoardTiles();

		for(Piece piece : pieces)
			board.addPiece(piece);
		if(cturn == 'W')
			turn = new Turn(Player.getInstance(0));
		else
			turn = new Turn(Player.getInstance(1));
		timer.startTimer();
		turn.getTimer().startTimer();
		System.out.println("Game has started");
		System.out.println("\r\n********************************************\r\n");
		System.out.println(turn.getCurrentPlayer().getNickname() + " Player to Move | Color: " + turn.getCurrentPlayer().getColor());
		System.out.println("\r\n********************************************\r\n");
		if(getBoard().isPlayerStuck((turn.getCurrentPlayer().getColor().equals(PrimaryColor.WHITE)) ? PrimaryColor.WHITE : PrimaryColor.BLACK)) {
			getBoard().printBoard();
			finishGame();
			return;
		}
	}

	/**
	 * Start a game
	 * @throws Exception 
	 */
	public void startGame(Player[] players) throws Exception {
		if(isGameRunning()) {
			System.out.println("A Game has already started");
			return;
		}
		if(players != null) {
			if(players.length != 2) throw new Exception("Invalid Game Initiation");
		}
		else throw new Exception("Invalid Game Initiation");	

		setPlayers(players);
		board = Board.getInstance();
		board.initBoardTiles();

		ArrayList<Piece> pieces = new ArrayList<>();

		for(int i = 1 ; i <= 3 ; i++)
			for(char c = 'A' ; c <= 'H' ; c+=2) {
				if(i == 2 && c == 'A') c = 'B';
				Piece soldier = new Soldier(pieces.size(), PrimaryColor.WHITE, new Location(i,c));
				pieces.add(soldier);
			}

		for(int i = 6 ; i <= 8 ; i++)
			for(char c = 'B' ; c <= 'H' ; c+=2) {
				if(i == 7 && c == 'B') c = 'A';
				Piece soldier = new Soldier(pieces.size(), PrimaryColor.BLACK, new Location(i,c));
				pieces.add(soldier);
			}
		for(Piece piece : pieces)
			board.addPiece(piece);

		turn = new Turn(Player.getInstance(0));
		timer.startTimer();
		turn.getTimer().startTimer();
		System.out.println("Game has started");
		System.out.println("\r\n********************************************\r\n");
		System.out.println(turn.getCurrentPlayer().getNickname() + " Player to Move | Color: " + turn.getCurrentPlayer().getColor());
		System.out.println("\r\n********************************************\r\n");
		if(getBoard().isPlayerStuck((turn.getCurrentPlayer().getColor().equals(PrimaryColor.WHITE)) ? PrimaryColor.WHITE : PrimaryColor.BLACK)) {
			getBoard().printBoard();
			finishGame();
			return;
		}
	}

	/**
	 * Pause a game
	 */
	public void pauseGame() {
		if(!isGameRunning()) {
			System.out.println("No Game is currrently running..");
			return;
		}
		if(timer.getPauseTime() > 0) {
			System.out.println("Game paused already..");
			return;
		}

		// turn.pause()  to be added to turn class
		timer.pauseTimer();
		System.out.printf("Game paused || Full Game Timer %.2f Seconds.\n", timer.getSeconds());
	}
	/*
	 * un-pause a game
	 */
	public void unpauseGame() {
		if(!isGameRunning()) {
			System.out.println("No Game is currrently running..");
			return;
		}
		if(timer.getPauseTime() < 0) {
			System.out.println("Game is not paused..");
			return;
		}
		// turn.unpause()  to be added to turn class
		timer.unpauseTimer();
		System.out.printf("Game unpaused || Full Game Timer %.2f Seconds.\n", timer.getSeconds());
	}
	/**
	 * save game as txt file
	 */
	public void saveGame() {

	}
	/**
	 * load game from txt file
	 */
	public void loadGame(ArrayList<Piece> pieces) {
		boolean invalid = false;
		if(pieces.size() < 2) {
			invalid = true;
		}
		boolean hasBlack = false;
		boolean hasWhite = false;
		for(Piece p : pieces) {
			if(p.getColor() == PrimaryColor.BLACK)
				hasBlack = true;
			else if(p.getColor() == PrimaryColor.WHITE)
				hasWhite = true;
		}
		if(invalid || !hasWhite || !hasBlack) {
			System.out.println("Load Game Failed..");
		}
		for(Piece p : pieces)
			board.addPiece(p);
	}
	/**
	 * finish a game
	 */
	public void finishGame() {
		getBoard().printBoard();
		int winner = -1;
		if(players[0].getCurrentScore() > players[1].getCurrentScore())
			winner = 1;
		else if (players[0].getCurrentScore() < players[1].getCurrentScore())
			winner = 2;
		if(winner == -1) {
			if(getBoard().getColorPieces(PrimaryColor.WHITE).size() > getBoard().getColorPieces(PrimaryColor.BLACK).size()){
				winner = 1;
			}else if(getBoard().getColorPieces(PrimaryColor.WHITE).size() < getBoard().getColorPieces(PrimaryColor.BLACK).size()) {
				winner = 2;
			}
		}
		if(winner > 0) {
			System.out.println("-------___-----___----------___-----------\r\n"
						+ "------/  /-----\\  \\--------/  /-----------\r\n"
						+ "-----/__/-------\\__\\------/__/------------\r\n"
						+ "-----------___----------------------------\r\n"
						+ "-----------\\  \\-------___-----------------\r\n"
						+ "------___---\\__\\-----/  /------___--------\r\n"
						+ "-----/  /-----------/__/-------\\  \\-------\r\n"
						+ "----/__/------------------------\\__\\------\r\n"
						+ "---------------------------------_--------\r\n"
						+ "                                | |      \r\n"
						+ "  ___ ___  _ __   __ _ _ __ __ _| |_ ___ \r\n"
						+ " / __/ _ \\| '_ \\ / _` | '__/ _` | __/ __|\r\n"
						+ "| (_| (_) | | | | (_| | | | (_| | |_\\__ \\\r\n"
						+ " \\___\\___/|_| |_|\\__, |_|  \\__,_|\\__|___/\r\n"
						+ "                  __/ |                  \r\n"
						+ "                 |___/  \r\n"
						+ "------------------------------------------\r\n"
						+ players[winner - 1].getNickname() + " Has Won, Congratulations!!!\r\n"
						+ "Score: " + players[winner - 1].getCurrentScore() + "\r\n"
						+ "Color: " + players[winner - 1].getColor() + "\r\n"
						+ "------------------------------------------");
		}else {
			System.out.println("It's a tie (draw)..");
		}
		SysData.getInstance().addScoreToHistory(players[0]);
		SysData.getInstance().addScoreToHistory(players[1]);
		
		timer.stopTimer();
		
		System.exit(1); //TODO On Implementing GUI, to replace this
	}

	/**
	 * switching turns between players
	 */
	public void switchTurn(){
		System.out.println("\r\n********************************************");
		
		turn.finishTurn(this);
		System.out.println(Player.getInstance(0).getNickname() + " Score: " + Player.getInstance(0).getCurrentScore());
		System.out.println(Player.getInstance(1).getNickname() + " Score: " + Player.getInstance(1).getCurrentScore());
		int index = (turn.getCurrentPlayer().getColor().equals(PrimaryColor.WHITE)) ? 1 : 0;
		System.out.println("Switching Turn to player : " + Player.getInstance(index).getNickname() + " | Color: " + Player.getInstance(index).getColor());
		this.turn = new Turn(Player.getInstance(index));
		if(getBoard().isPlayerStuck((turn.getCurrentPlayer().getColor().equals(PrimaryColor.WHITE)) ? PrimaryColor.WHITE : PrimaryColor.BLACK)) {
			finishGame();
			return;
		}
		turn.getTimer().startTimer();
		System.out.println("********************************************\r\n");

	}
	
	/**
	 * 
	 * @return true if the game is running, otherwise false
	 */
	public boolean isGameRunning() {
		if(timer.getStartTime() == -1) return false;
		return true;
	}
	
	//////////////////////////  Questions Related

	/**
	 * 
	 * @param questions collection of questions
	 */
	public void loadQuestions(ArrayList<Question> questions) {
		setAvailableQuestions(questions);
	}

	/**
	 * 
	 * @return random Question 
	 */
	public Question getAvailableRandomQuestion() {
		if(availableQuestions.isEmpty())
			return null;
		Random rand = new Random();
		Question random = availableQuestions.get(rand.nextInt(availableQuestions.size()));


		if(availableQuestions.size() == 1) {
			availableQuestions.addAll(unavailableQuestions);
			unavailableQuestions.clear();
		}else {
			unavailableQuestions.add(random);
			availableQuestions.remove(random);
		}

		return random;
	}

	//////////////////////////  End Questions Related


	////////////////////////// Getters & Setters
	/**
	 * 
	 * @return game board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * 
	 * @param board
	 */
	public void setBoard(Board board) {
		this.board = board;
	}

	/**
	 * 
	 * @return array of players
	 */
	public Player[] getPlayers() {
		return players;
	}

	/**
	 * set players of the game
	 * @param players
	 */
	public void setPlayers(Player[] players) {
		this.players = players;
	}

	/**
	 * 
	 * @return Turn of a player
	 */
	public Turn getTurn() {
		return turn;
	}

	/**
	 * sets the turn of a player in a game
	 * @param turn
	 */
	public void setTurn(Turn turn) {
		this.turn = turn;
	}

	public ArrayList<Question> getAvailableQuestions() {
		return availableQuestions;
	}

	public void setAvailableQuestions(ArrayList<Question> availableQuestions) {
		if(availableQuestions == null)
			this.availableQuestions = new ArrayList<Question>();
		else
			this.availableQuestions = availableQuestions;
	}
}