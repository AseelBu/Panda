package Model;
import java.util.ArrayList;
import Utils.PrimaryColor;
import Utils.SeconderyTileColor;
import java.util.Random;

import Exceptions.GameUpgradeException; 

/**
 * 
 * @author Maryam
 *
 */
public class Turn {
	private GameTimer timer;
	private int moveCounter;
	private Piece lastPieceMoved;
	private boolean isLastTileRed=false;
	private Player currentPlayer;
	private Piece eaten;


	/**
	 * Turn class constructor
	 * @param currentPlayer
	 */
	public Turn(Player currentPlayer) {
		super();
		this.timer = new GameTimer();
		moveCounter = 0;
		lastPieceMoved = null;
		eaten = null;
		this.currentPlayer = currentPlayer;
		System.out.println("new turn started...");
	}

	
	/**
	 * setter and getter
	 * @param lastPieceMoved
	 */
	public void setLastPieceMoved(Piece lastPieceMoved) {
		this.lastPieceMoved = lastPieceMoved;
	}

	public Piece getLastPieceMoved() {
		return lastPieceMoved;
	}
	public GameTimer getTimer() {
		return timer;
	}

	public void setTimer(GameTimer timer) {
		this.timer = timer;
	}

	public int getMoveCounter() {
		return moveCounter;
	}
	public void setMoveCounter(int moveCounter) {
		this.moveCounter = moveCounter;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
	public Piece getEaten() {
		return eaten;
	}
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


	//tostring
	@Override
	public String toString() {
		return "Turn [timer=" + timer + ", MoveCounter=" + moveCounter + ", LastPieceMoved=" + lastPieceMoved
				+ ", currentPlayer=" + currentPlayer + "]";
	}

	

	/**
	 *  Scoring calculation according to the time of the turn
	 * @return added score depending on time
	 */
	public long CalculateTimeScore()
	{
		if(timer.getSeconds()>60)
		{
			return(currentPlayer.DeductScore((float) (Math.floor(timer.getSeconds())-60)));
		}
		else
		{
			System.out.println("Turn Time: " + Math.floor(timer.getSeconds()));
			return(currentPlayer.AddScore((float) (60-Math.floor(timer.getSeconds()))));
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
	 * Check if currentPlayer can have Blue tile on the board
	 * @return true if tile can be blue tile,false otherwise
	 */
//	public boolean  isCandidateForBlueTile()
//	{
//		int soldierCount=0,queenCount=0;
//
//		if( currentPlayer.getColor().equals(PrimaryColor.BLACK))
//		{
//			ArrayList<Piece> piece1 = Board.getInstance().getColorPieces(currentPlayer.getColor());
//			for(Piece p:piece1)
//			{
//				if(p instanceof Soldier)
//				{
//					soldierCount++;
//				}
//				if(p instanceof Queen)
//				{
//					queenCount++;
//				}
//			}
//		}
//		else
//		{
//			ArrayList<Piece> piece2 = Board.getInstance().getColorPieces(currentPlayer.getColor());
//			for(Piece t:piece2)
//			{
//				if(t instanceof Soldier)
//				{
//					soldierCount++;
//				}
//				if(t instanceof Queen)
//				{
//					queenCount++;
//				}
//			}
//		}
//		return (soldierCount == 2 && queenCount == 1);
//	}

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
}
