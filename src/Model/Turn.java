package Model;
import java.util.ArrayList;
import Utils.PrimaryColor;
import Utils.SeconderyTileColor;
import java.util.Random; 

/**
 * 
 * @author Maryam
 *
 */
public class Turn {
	private GameTimer timer;
	private int moveCounter;
	private Piece lastPieceMoved;
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

	//tostring
	@Override
	public String toString() {
		return "Turn [timer=" + timer + ", MoveCounter=" + moveCounter + ", LastPieceMoved=" + lastPieceMoved
				+ ", currentPlayer=" + currentPlayer + "]";
	}

	/**
	 * A green tile will appear after 30 seconds
	 */
	public void ShowGreenAfter30()
	{
		Random rand = new Random(); 
		ArrayList<Tile> t=Board.getInstance().getAllLegalMoves(currentPlayer.getColor());
		//	t.get(rand.nextInt(t.size())).setColor2(SeconderyTileColor.GREEN);
		Board.getInstance().addColorToBoardTile(t.get(rand.nextInt(t.size())),SeconderyTileColor.GREEN);
	}

	/**
	 * An orange tile will appear after 90 seconds
	 */
	public void  ShowOrangeAfter90()
	{

		ArrayList<Tile> t=Board.getInstance().getAllLegalMoves(currentPlayer.getColor());
		for(Tile e:t)
		{
			Board.getInstance().addColorToBoardTile(e,SeconderyTileColor.ORANGE);
		}
	}

	/**
	 *  Scoring calculation according to the time of the turn
	 * @return added score depending on time
	 */
	public long CalculateTimeScore()
	{
		if(timer.getSeconds()>60)
		{
			return(currentPlayer.DeductScore(timer.getSeconds()-60));
		}
		else
		{
			System.out.println("Turn Time: " + timer.getSeconds());
			return(currentPlayer.AddScore(60-timer.getSeconds()));
		}
	}

	/**
	 * Add another step to the move counter
	 */
	public void IncrementMoveCounter()
	{
		this.moveCounter++;
		System.out.println("You just got another move !!");
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
	public boolean  isCandidateForBlueTile()
	{
		int soldierCount=0,queenCount=0;

		if( currentPlayer.getColor().equals(PrimaryColor.BLACK))
		{
			ArrayList<Piece> piece1 = Board.getInstance().getColorPieces(currentPlayer.getColor());
			for(Piece p:piece1)
			{
				if(p instanceof Soldier)
				{
					soldierCount++;
				}
				if(p instanceof Queen)
				{
					queenCount++;
				}
			}
		}
		else
		{
			ArrayList<Piece> piece2 = Board.getInstance().getColorPieces(currentPlayer.getColor());
			for(Piece t:piece2)
			{
				if(t instanceof Soldier)
				{
					soldierCount++;
				}
				if(t instanceof Queen)
				{
					queenCount++;
				}
			}
		}
		return (soldierCount == 2 && queenCount == 1);
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
		timer.stopTimer();
		CalculateTimeScore();
		lastPieceMoved.resetEatingCntr();
		board.replacePiece(lastPieceMoved, lastPieceMoved);

	}
}
