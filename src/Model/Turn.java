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
	private Board board;
	SeconderyTileColor secColor;
	
	//constructor
	public Turn(GameTimer timer, int moveCounter, Piece lastPieceMoved, Player currentPlayer, Board board,
			SeconderyTileColor secColor) {
		super();
		this.timer = timer;
		MoveCounter = moveCounter;
		LastPieceMoved = lastPieceMoved;
		this.currentPlayer = currentPlayer;
		this.board = board;
		this.secColor = secColor;
	}
	
	//setter and getter
	public void setLastPieceMoved(Piece lastPieceMoved) {
		LastPieceMoved = lastPieceMoved;
	}
	public Piece getLastPieceMoved() {
		return LastPieceMoved;
	}
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}

	public SeconderyTileColor getSecColor() {
		return secColor;
	}
	public void setSecColor(SeconderyTileColor secColor) {
		this.secColor = secColor;
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
				+ ", currentPlayer=" + currentPlayer + ", board=" + board + ", secColor=" + secColor + "]";
	}

	//An green tile will appear after 30 seconds
	public void ShowGreenAfter30()
	{
		 Random rand = new Random(); 
		 int i=0;
		if(timer.getPauseTime()-timer.getAnonStartTime()>=30)
		{
			 ArrayList<Tile> Alltiles=this.board.getLegalMoves(this.secColor.GREEN);
			 i=rand.nextInt(Alltiles.size());
			 Alltiles.get(i).setColor2(this.secColor.GREEN);
		}
	}
	
	//An orange tile will appear after 90 seconds
	public void  ShowOrangeAfter90()
	{
		 Random rand = new Random(); 
		 int i=0;
		if(timer.getPauseTime()-timer.getAnonStartTime()>=90)
		{
			 ArrayList<Tile> Alltiles=this.board.getLegalMoves(this.secColor);
			 i=rand.nextInt(Alltiles.size());
			 Alltiles.get(i).setColor2(this.secColor.ORANGE);
		}
	}
	
	//Initializes the board tiles by player
	public void  initiateBoardTiles()
	{  
		HashMap<Integer, ArrayList<Tile>> selects = new HashMap<Integer, ArrayList<Tile>>();
		selects=board.getTilesMap();

		for(Entry<Integer, ArrayList<Tile>> entry : selects.entrySet()) {
		    int key = entry.getKey();
		    if(key%2==0)
		    {
		    	int i=0;
		    	for(Tile t:entry.getValue())
		    	{
		    		if(i%2==0)
		    		t.setColor1(PrimaryColor.WHITE);
		    		else
		    		t.setColor1(PrimaryColor.BLACK);
		    		i++;
		    	}
		    }
		    else
		    {
		    	int j=0;
		    	for(Tile s:entry.getValue())
		    	{
		    		if(j%2==0)
			    		s.setColor1(PrimaryColor.BLACK);
			    		else
			    		s.setColor1(PrimaryColor.WHITE);
			    		j++;
		    	}
		    }
		  

		    // do what you have to do here
		    // In your case, another loop.
		}
		
	}
	
	//Scoring calculation according to the time of the queue
	 public long CalculateTimeScore()
	 {
		 if( timer.getPauseTime()-timer.getAnonStartTime()>60)
		 {
			 return(currentPlayer.DeductScore(timer.getPauseTime()-timer.getAnonStartTime()));
		 }
		 else
		 {
			 return(currentPlayer.AddScore(timer.getPauseTime()-timer.getAnonStartTime()));
		 }
	 }
	 
	 //Add another step to the counter
	 public void IncrementMoveCounter()
	 {
		 if(board.canPieceEat(LastPieceMoved)!=null)
			 this.MoveCounter++;
	 }
	 
	 //Check if currentPlayer can have Blue tile on the board
	 public boolean  isCandidateForBlueTile()
	 {
		 int soldierCount=0,queenCount=0;
		if( currentPlayer.getColor().equals(PrimaryColor.BLACK))
		{
		 ArrayList<Piece> piece1=board.getBlackPieces();
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
			 ArrayList<Piece> piece2=board.getWhitePieces();
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
	
}
