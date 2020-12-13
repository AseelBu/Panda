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
		this.board = Board.getInstance();
		board.initBasicBoardTiles();
		availableQuestions = new ArrayList<>();
		availableQuestions.addAll(SysData.getInstance().getQuestions());
		unavailableQuestions=new ArrayList<Question>();
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
	 * load a game from given board status
	 * @param players
	 * @param pieces
	 * @param cturn
	 * @return true if game successfully started,false if it was finished
	 * @throws Exception
	 */
	public boolean startGame(Player[] players, ArrayList<Piece> pieces, char cturn) throws Exception {
		if(isGameRunning()) {
			System.out.println("A Game has already started");
			return true;
		}
		if(players != null) {
			if(players.length != 2) throw new Exception("Invalid Game Initiation- game should contain exactly 2 players!!!");
		}
		else throw new Exception("Invalid Game Initiation- no players provided!!!!");	
		if(pieces == null)
		{
			throw new Exception("Invalid Game Initiation- no provided pieces !!!!");
		}

		else if (pieces.size() < 2 || pieces.size() > 24) {

			throw new Exception("Invalid Game Initiation-number of provided pieces can't be "+pieces.size()+". should be between (2,12)");
		}else if(pieces.size() >= 2) {
			PrimaryColor temp = pieces.get(0).getColor();
			boolean invalid = true;
			for(Piece p : pieces) {

				// to check there are pieces of two colors

				if(!p.getColor().equals(temp))
					invalid = false;
			}
			if(invalid)
				throw new Exception("Invalid Game Initiation");
		}
		setPlayers(players);


		board.addPiecesToBoard(pieces);
		
		if(cturn == 'W')
			turn = new Turn(Player.getInstance(0));
		else
			turn = new Turn(Player.getInstance(1));

		board.initiateBoardSecondaryColors();

		timer.startTimer();
		turn.getTimer().startTimer();
		System.out.println("Game has started");
		System.out.println("\r\n********************************************\r\n");
		System.out.println(turn.getCurrentPlayer().getNickname() + " Player to Move | Color: " + turn.getCurrentPlayer().getColor());
		System.out.println("\r\n********************************************\r\n");
		if(isGameFinished()) {
			getBoard().printBoard();
			finishGame();
			return false;
		}
		return true;
	}

	/**
	 * Start a game
	 * @throws Exception 
	 */
	public void startGame(Player[] players) throws Exception {
		Board board = Board.getInstance();
		if(isGameRunning()) {
			System.out.println("A Game has already started");
			return;
		}
		if(players != null) {
			if(players.length != 2) throw new Exception("Invalid Game Initiation- game have to start with exactly 2 players");
		}
		else throw new Exception("Invalid Game Initiation- game started without players");	

		setPlayers(players);

		ArrayList<Piece> pieces = new ArrayList<>();

		//adding black soldiers to board
		for(int i = 1 ; i <= 3 ; i++)
			for(char c = board.getColumnLowerBound() ; c <= board.getColumnUpperBound() ; c+=2) {
				if(i == 2 && c == board.getColumnLowerBound()) c = (char)(board.getColumnLowerBound()+1);
				Piece soldier = new Soldier(pieces.size(), PrimaryColor.WHITE, new Location(i,c));
				pieces.add(soldier);
			}
		//adding white soldiers to board
		for(int i = board.getBoardSize()-2 ; i <= board.getBoardSize() ; i++)
			for(char c = (char) (board.getColumnLowerBound()+1) ; c <= board.getColumnUpperBound() ; c+=2) {
				if(i == board.getBoardSize()-1 && c == (char)(board.getColumnLowerBound()+1)) c = board.getColumnLowerBound();
				Piece soldier = new Soldier(pieces.size(), PrimaryColor.BLACK, new Location(i,c));
				pieces.add(soldier);
			}



		board.addPiecesToBoard(pieces);

		turn = new Turn(Player.getInstance(0));
		board.initiateBoardSecondaryColors();
		timer.startTimer();
		turn.getTimer().startTimer();
		System.out.println("Game has started");
		System.out.println("\r\n********************************************\r\n");
		System.out.println(turn.getCurrentPlayer().getNickname() + " Player to Move | Color: " + turn.getCurrentPlayer().getColor());
		System.out.println("\r\n********************************************\r\n");
//		if(getBoard().isPlayerStuck((turn.getCurrentPlayer().getColor().equals(PrimaryColor.WHITE)) ? PrimaryColor.WHITE : PrimaryColor.BLACK)) {
//			getBoard().printBoard();
//			finishGame();
//			return;
//		}
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
	 * checks if game is over 
	 * @return true if game should finish, false otherwise
	 */
	public boolean isGameFinished() {
		PrimaryColor opponentColor= getCurrentPlayerColor().equals(PrimaryColor.WHITE)? PrimaryColor.BLACK:PrimaryColor.WHITE;
		if(getBoard().isPlayerStuck(getCurrentPlayerColor())
				||board.getColorPieces(getCurrentPlayerColor()).isEmpty() 
				|| board.getColorPieces(opponentColor).isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * finish a game
	 */
	public void finishGame() {
		if(!isGameRunning()) return;
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
		timer.stopTimer();

		//		if(winner > -1)
		//			BoardController.getInstance().finishGame(players[winner - 1].getNickname(),
		//					players[winner - 1].getCurrentScore(), players[winner - 1].getColor());
		//			
		SysData.getInstance().addScoreToHistory(players[0]);
		SysData.getInstance().addScoreToHistory(players[1]);

		//		destruct();
		//		Board.destruct();
		//		Player.destruct();
		//		System.exit(1); 
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

		//TODO move to controller 
		if(getBoard().isPlayerStuck((turn.getCurrentPlayer().getColor().equals(PrimaryColor.WHITE)) ? PrimaryColor.WHITE : PrimaryColor.BLACK)) {
			finishGame();
			return; 
		}
		board.removeAllSeconderyColorsFromBoard();
		//		board.initiateBoardSecondaryColors();
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

	public GameTimer getTimer() {
		return timer;
	}

	public void setTimer(GameTimer timer) {
		this.timer = timer;
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

	public PrimaryColor getCurrentPlayerColor() {
		return this.turn.getCurrentPlayer().getColor();
	}

	public Player getPlayerr()
	{
		return this.turn.getCurrentPlayer();
	}
}