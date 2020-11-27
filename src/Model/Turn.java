package Model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
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
	private int MoveCounter;
	private Piece LastPieceMoved;
	private Player currentPlayer;

	
	//constructor
	public Turn(Player currentPlayer) {
		super();
		this.timer = new GameTimer();
		MoveCounter = 0;
		LastPieceMoved = null;
		this.currentPlayer = currentPlayer;
	}
	
	//setter and getter
	public void setLastPieceMoved(Piece lastPieceMoved) {
		LastPieceMoved = lastPieceMoved;
	}
	
	public Piece getLastPieceMoved() {
		return LastPieceMoved;
	}
	

	
	public GameTimer getTimer() {
		return timer;
	}
	
	public void setTimer(GameTimer timer) {
		this.timer = timer;
	}
	
	public int getMoveCounter() {
		return MoveCounter;
	}
	public void setMoveCounter(int moveCounter) {
		MoveCounter = moveCounter;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	//tostring
	@Override
	public String toString() {
		return "Turn [timer=" + timer + ", MoveCounter=" + MoveCounter + ", LastPieceMoved=" + LastPieceMoved
				+ ", currentPlayer=" + currentPlayer + "]";
	}

	//An green tile will appear after 30 seconds
	public void ShowGreenAfter30()
	{
		 Random rand = new Random(); 
		 ArrayList<Tile> t=Board.getInstance().getAllLegalMoves(currentPlayer.getColor());
		//	t.get(rand.nextInt(t.size())).setColor2(SeconderyTileColor.GREEN);
			Board.getInstance().addColorToBoardTile(t.get(rand.nextInt(t.size())),SeconderyTileColor.GREEN);
	}
	
	//An orange tile will appear after 90 seconds
	public void  ShowOrangeAfter90()
	{
			 ArrayList<Tile> t=Board.getInstance().getAllLegalMoves(currentPlayer.getColor());
			 for(Tile e:t)
			 {
				 Board.getInstance().addColorToBoardTile(e,SeconderyTileColor.ORANGE);
			 }
	}
	
//	//Initializes the board tiles by player
//	public void  initiateBoardTiles()
//	{  
//		HashMap<Integer, ArrayList<Tile>> selects = new HashMap<Integer, ArrayList<Tile>>();
//		selects=Board.getInstance().getTilesMap();
//
//		for(Entry<Integer, ArrayList<Tile>> entry : selects.entrySet()) {
//		    int key = entry.getKey();
//		    if(key%2==0)
//		    {
//		    	int i=0;
//		    	for(Tile t:entry.getValue())
//		    	{
//		    		if(i%2==0)
//		    		t.setColor1(PrimaryColor.WHITE);
//		    		else
//		    		t.setColor1(PrimaryColor.BLACK);
//		    		i++;
//		    	}
//		    }
//		    else
//		    {
//		    	int j=0;
//		    	for(Tile s:entry.getValue())
//		    	{
//		    		if(j%2==0)
//			    		s.setColor1(PrimaryColor.BLACK);
//			    		else
//			    		s.setColor1(PrimaryColor.WHITE);
//			    		j++;
//		    	}
//		    }
//		  
//
//		    // do what you have to do here
//		    // In your case, another loop.
//		}
//		
//	}
	
	//Scoring calculation according to the time of the turn
	 public long CalculateTimeScore()
	 {
		 if(timer.getSeconds()>60)
		 {
			 return(currentPlayer.DeductScore(timer.getSeconds()-60));
		 }
		 else
		 {
			 return(currentPlayer.AddScore(60-timer.getSeconds()));
		 }
	 }
	 
	 //Add another step to the counter
	 public void IncrementMoveCounter()
	 {
		 //if(board.getInstance().canPieceEat(LastPieceMoved)!=null)
			 this.MoveCounter++;
	 }
	 
	 //Check if currentPlayer can have Blue tile on the board
	 public boolean  isCandidateForBlueTile()
	 {
		 int soldierCount=0,queenCount=0;
		if( currentPlayer.getColor().equals(PrimaryColor.BLACK))
		{
		 ArrayList<Piece> piece1=Board.getInstance().getAllEdiblePiecesByColor(currentPlayer.getColor());
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
			 ArrayList<Piece> piece2=Board.getInstance().getAllEdiblePiecesByColor(currentPlayer.getColor());
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
		return(soldierCount==2&& queenCount==1);
	 }
	 public void pauseTurn()
	 {
		 timer.pauseTimer();
	 }
	 public void unpauseTurn()
	 {
		 timer.unpauseTimer();
	 }
	 public void finishTurn()
	 {
		timer.stopTimer();
		CalculateTimeScore();
		LastPieceMoved.resetEatingCntr();
	 }
}
