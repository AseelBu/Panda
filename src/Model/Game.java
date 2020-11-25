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
	public void startGame(Player[] players, ArrayList<Piece> pieces) throws Exception {
		if(isGameRunning()) {
			System.err.println("A Game has already started");
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
		else if (pieces.size() < 2) {
			throw new Exception("Invalid Game Initiation");
		}
		setPlayers(players);
		this.board = Board.getInstance();
		for(Piece piece : pieces)
			board.addPiece(piece);
		timer.startTimer();
		//turn = new Turn(players[0]); Turn class constructor should have only 1 parameter (Player)
		System.out.println("Game has started");
	}
	
	/**
	 * Start a game
	 * @throws Exception 
	 */
	public void startGame(Player[] players) throws Exception {
		if(isGameRunning()) {
			System.err.println("A Game has already started");
			return;
		}
		if(players != null) {
			if(players.length != 2) throw new Exception("Invalid Game Initiation");
		}
		else throw new Exception("Invalid Game Initiation");	
		
		setPlayers(players);
		board = Board.getInstance();
		standardGameTiles();

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
		
		timer.startTimer();
		//turn = new Turn(players[0]); Turn class constructor should have only 1 parameter (Player)
		System.out.println("Game has started");
	}
	
	/**
	 * Pause a game
	 */
	public void pauseGame() {
		if(!isGameRunning()) {
			System.err.println("No Game is currrently running..");
			return;
		}
		if(timer.getPauseTime() > 0) {
			System.err.println("Game paused already..");
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
			System.err.println("No Game is currrently running..");
			return;
		}
		if(timer.getPauseTime() < 0) {
			System.err.println("Game is not paused..");
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
			System.err.println("Load Game Failed..");
		}
		for(Piece p : pieces)
			board.addPiece(p);
	}
	/**
	 * finish a game
	 */
	public void finishGame() {
		int winner = -1;
		if(players[0].getCurrentScore() > players[1].getCurrentScore())
			winner = 1;
		else if (players[0].getCurrentScore() < players[1].getCurrentScore())
			winner = 2;
		else {
			if(board.getColorPieces(PrimaryColor.BLACK).size() == 0) winner = 1;
			else if(board.getColorPieces(PrimaryColor.WHITE).size() == 0) winner = 2;
		}
		
		switch(winner){
			case 1: {
				System.out.println(players[0].getNickname() + " Has Won, Congratulations!!!");
				break;
			}
			case 2 : {
				System.out.println(players[1].getNickname() + " Has Won, Congratulations!!!");
				break;
			}
			default:{
				System.out.println("It's a tie (draw)..");
			}	
		}
		timer.stopTimer();
	}
	
	/**
	 * switching turns between players
	 */
	public void switchTurn(){
		
	}
	/**
	 * 
	 * @return true if the game is running, otherwise false
	 */
	public boolean isGameRunning() {
		if(timer.getStartTime() == -1) return false;
		return true;
	}
	/**
	 * Add standard tiles to the game.
	 */
	public void standardGameTiles() {
		int count = 0;
		for(int i = 1 ; i <= 8 ; i+=2) {
			for(char c = 'A' ; c <= 'H' ; c+=2) {
				board.addTile(new Tile(new Location(i, c), PrimaryColor.BLACK));
				board.addTile(new Tile(new Location(i, (char) ( c + 1)), PrimaryColor.WHITE));
			}
		}
		
		for(int i = 2 ; i <= 8 ; i+=2) {
			for(char c = 'A' ; c <= 'H' ; c+=2) {
				board.addTile(new Tile(new Location(i, c), PrimaryColor.WHITE));
				board.addTile(new Tile(new Location(i, (char) ( c + 1)), PrimaryColor.BLACK));
			}
		}
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