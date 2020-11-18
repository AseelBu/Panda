package Model;

import java.util.ArrayList;

import com.sun.glass.ui.Timer;

public class Turn {
	private Timer timer;
	private int MoveCounter;
	private ArrayList<Piece> LastPieceMoved;
	private ArrayList<Piece> currentPlayer;
	
	public Turn(Timer timer, int moveCounter, ArrayList<Piece> lastPieceMoved, ArrayList<Piece> currentPlayer) {
		super();
		this.timer = timer;
		MoveCounter = moveCounter;
		LastPieceMoved = lastPieceMoved;
		this.currentPlayer = currentPlayer;
	}
	
	public Timer getTimer() {
		return timer;
	}
	public void setTimer(Timer timer) {
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
	public ArrayList<Piece> getCurrentPlayer() {
		return currentPlayer;
	}
	public void setCurrentPlayer(ArrayList<Piece> currentPlayer) {
		this.currentPlayer = currentPlayer;
	}


	@Override
	public String toString() {
		return "Turn [timer=" + timer + ", MoveCounter=" + MoveCounter + ", LastPieceMoved=" + LastPieceMoved
				+ ", currentPlayer=" + currentPlayer + "]";
	}
}
