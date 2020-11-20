package Model;

import java.util.ArrayList;

import com.sun.glass.ui.Timer;

public class Turn {
	private GameTimer timer;
	private int MoveCounter;
	private ArrayList<Piece> LastPieceMoved;
	private Player currentPlayer;
	
	//Constructor
	public Turn(GameTimer timer, int moveCounter, ArrayList<Piece> lastPieceMoved, Player currentPlayer) {
		super();
		this.timer = timer;
		MoveCounter = moveCounter;
		LastPieceMoved = lastPieceMoved;
		this.currentPlayer = currentPlayer;
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
	public ArrayList<Piece> getLastPieceMoved() {
		return LastPieceMoved;
	}
	public void setLastPieceMoved(ArrayList<Piece> lastPieceMoved) {
		LastPieceMoved = lastPieceMoved;
	}
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	public void setCurrentPlayer(Player currentPlayer) {
		this.currentPlayer = currentPlayer;
	}


	@Override
	public String toString() {
		return "Turn [timer=" + timer + ", MoveCounter=" + MoveCounter + ", LastPieceMoved=" + LastPieceMoved
				+ ", currentPlayer=" + currentPlayer + "]";
	}
}
