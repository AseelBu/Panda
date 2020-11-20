package Model;

/**
 * 
 * @author firas
 *
 */
public class Game {

	private Board board;
	private Player players[];
	private GameTimer timer;
	private Turn turn;
	
	
	/**
	 * Game constructor
	 * @param players only 2 players allowed
	 * @throws Exception thown whenever provided wrong player count
	 */
	public Game(Player[] players) throws Exception {
		super();
		if(players != null) {
			if(players.length != 2) throw new Exception("Invalid Game");
			
			setBoard(Board.getInstance());
			setPlayers(players);
			timer = new GameTimer();
		}
		else throw new Exception("Invalid Game");
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
	
	
	
}