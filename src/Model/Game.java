package Model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Game class is singleton
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
	 * Game constructor
	 * @param players only 2 players allowed
	 */
	private Game(Player[] players) {
		super();
		setBoard(Board.getInstance());
		setPlayers(players);
		timer = new GameTimer();
	}
	
	/**
	 * 
	 * @param players only 2 players allowed
	 * @param pieces - a 64 sized array, 8 rows, 8 columns
	 */
	
	private Game(Player[] players, ArrayList<Piece> pieces) {
		super();
		setBoard(Board.getInstance());
		setPlayers(players);
		timer = new GameTimer();
		
		for(Piece piece : pieces)
			board.addPiece(piece);
	}

	/**
	 * a static method used to initiate Game class
	 * @param players only 2 players allowed
	 * @throws Exception - if provided invalid players array
	 */
	public static void initiateGame(Player[] players) throws Exception {	
		if(players != null) {
			if(players.length != 2) throw new Exception("Invalid Game");
		}
		else throw new Exception("Invalid Game");	
		instance = new Game(players);
	}
	
	/**
	 * 
	 * @param players players only 2 players allowed
	 * @param pieces pieces to be added to the start of the game
	 * @throws Exception - if provided invalid players array
	 */
	public static void initiateGame(Player[] players, ArrayList<Piece> pieces) throws Exception {	
		if(players != null) {
			if(players.length != 2) throw new Exception("Invalid Game");
		}
		else throw new Exception("Invalid Game");	
		if(pieces == null)
		{
			System.out.println("Invalid Game Initiation, Standard game has been created!");
			initiateGame(players);
			return;
		}
		else if (pieces.size() < 2) {
			System.out.println("Invalid Game Initiation, Standard game has been created!");
			initiateGame(players);
			return;
		}
		instance = new Game(players, pieces);
	}
	
	/**
	 * 
	 * @return class instance
	 */
	public static Game getInstance(){
		return instance;
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
	 */
	public void startGame() {
		board = Board.getInstance();
		timer.startTimer();
	}
	
	/**
	 * Pause a game
	 */
	public void pauseGame() {
		
	}
	/*
	 * un-pause a game
	 */
	public void unpauseGame() {
		
	}
	/**
	 * save game as txt file
	 */
	public void saveGame() {
		
	}
	/**
	 * load game from txt file
	 */
	public void loadGame() {
		
	}
	/**
	 * finish a game
	 */
	public void finishGame() {
			
	}
	/**
	 * switching turns between players
	 */
	public void switchTurn(){
		
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