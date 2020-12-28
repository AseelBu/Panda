package Model;

import Exceptions.GameUpgradeException; 

/**
 * 
 * @author Maryam
 *
 */
public class Turn {

	private final int SECONDS_LIMIT_FOR_SCORE = 60;
	private Timer timer;
	private int moveCounter;
	private Piece lastPieceMoved=null;
	private boolean isLastTileRed=false;
	private Player currentPlayer;
	private Piece eaten=null; //last eaten piece


	/**
	 * Turn class constructor
	 * @param currentPlayer
	 */
	public Turn(Player currentPlayer) {
		this.timer = new Timer();
		this.moveCounter = 0;
		this.currentPlayer = currentPlayer;
		System.out.println("new turn started...");
	}


	//setters and getters
	/**
	 * sets last piece moved in this turn
	 * @param lastPieceMoved in this turn
	 */
	public void setLastPieceMoved(Piece lastPieceMoved) {
		this.lastPieceMoved = lastPieceMoved;
	}

	/**
	 * gets the last piece moved in this turn
	 * @return Piece last piece moved
	 */
	public Piece getLastPieceMoved() {
		return lastPieceMoved;
	}

	/**
	 * gets this turn timer
	 * @return GameTimer of this turn
	 */
	public Timer getTimer() {
		return timer;
	}


	/**
	 * gets move counter for this turn
	 * @return int this.moveCounter value
	 */
	public int getMoveCounter() {
		return moveCounter;
	}



	/**
	 * gets current player in this turn
	 * @return Player the currentPlayer
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * 
	 * @param currentPlayer the currentPlayer to set
	 */
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	/**
	 * 
	 * @return Piece the last eaten piece in this turn 
	 */
	public Piece getEaten() {
		return eaten;
	}

	/**
	 * 
	 * @param eaten the last eaten piece to set
	 */
	public void setEaten(Piece eaten) {
		this.eaten = eaten;
	}

	/**
	 * @param isLastTileRed the isLastTileRed to set
	 */
	public void setLastTileRed(boolean isLastTileRed) {
		this.isLastTileRed = isLastTileRed;
	}

	/**
	 * @return the isLastTileRed
	 */
	public boolean isLastTileRed() {
		return isLastTileRed;
	}

	/**
	 *  Scoring calculation according to the time of the turn
	 * @return added score depending on time
	 */
	public long CalculateTimeScore()
	{
		if(timer.getSeconds()>SECONDS_LIMIT_FOR_SCORE)
		{
			return(currentPlayer.DeductScore((float) (Math.floor(timer.getSeconds())-SECONDS_LIMIT_FOR_SCORE)));
		}
		else
		{
			System.out.println("Turn Time: " + Math.floor(timer.getSeconds()));
			return(currentPlayer.AddScore((float) (SECONDS_LIMIT_FOR_SCORE-Math.floor(timer.getSeconds()))));
		}
	}

	/**
	 * Add another step to the move counter
	 * @throws GameUpgradeException - message for adding move
	 */
	public void IncrementMoveCounter() throws GameUpgradeException
	{
		this.moveCounter++;
		throw new GameUpgradeException("You just got another move !!");
	}

	/**
	 * removes 1 step from the move counter
	 */
	public void decrementMoveCounter()
	{
		this.moveCounter--;
	}


	/**
	 * pauses the turn
	 */
	public void pauseTurn(){
		timer.pauseTimer();
	}

	/**
	 * unpauses the turn
	 */
	public void unpauseTurn(){
		timer.unpauseTimer();
	}

	/**
	 * finishes the turn
	 * @param game - game instance
	 */
	public void finishTurn(Game game)
	{
		Board board = Board.getInstance();
		lastPieceMoved=game.getTurn().getLastPieceMoved();
		timer.pauseTimer();
		CalculateTimeScore();
		lastPieceMoved.resetEatingCntr();
		board.replacePiece(lastPieceMoved, lastPieceMoved);


	}

	@Override
	public String toString() {
		return "Turn [timer=" + timer + ", MoveCounter=" + moveCounter + ", LastPieceMoved=" + lastPieceMoved
				+ ", currentPlayer=" + currentPlayer + "]";
	}
}
