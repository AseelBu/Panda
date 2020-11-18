package Model;

import java.util.ArrayList;

import com.sun.glass.ui.Timer;

public class Game {
	private ArrayList<Board> board;
	private ArrayList<Player> players;
	private ArrayList<Turn> turn;
	private Timer timer;
	private static Game instance;

	public static void setInstance(Game instance) {
		Game.instance = instance;
	}


	//Constructor

	public Game(ArrayList<Board> board, ArrayList<Player> players, ArrayList<Turn> turn, Timer timer) {
		super();
		this.board = board;
		this.players = players;
		this.turn = turn;
		this.timer = timer;
	}
	
	
	public ArrayList<Board> getBoard() {
		return board;
	}
	public void setBoard(ArrayList<Board> board) {
		this.board = board;
	}
	public ArrayList<Player> getPlayers() {
		return players;
	}
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	public ArrayList<Turn> getTurn() {
		return turn;
	}
	public void setTurn(ArrayList<Turn> turn) {
		this.turn = turn;
	}
	public Timer getTimer() {
		return timer;
	}
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	
	@Override
	public String toString() {
		return "Game [board=" + board + ", players=" + players + ", turn=" + turn + ", timer=" + timer + "]";
	}



}
