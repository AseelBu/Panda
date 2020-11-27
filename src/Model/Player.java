package Model;

import java.io.Serializable;
import java.util.ArrayList;



import Utils.PrimaryColor;

/**
 * 
 * @author Maryam
 *
 */
public class Player implements Serializable {

	private String nickname;
	private PrimaryColor color;
	private int currentScore;
	private static Player instances[] = new Player[2];
	private static Boolean initiated = false;

	private Player(PrimaryColor color) {
		setColor(color);
		currentScore = 0;
	}

	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public PrimaryColor getColor() {
		return color;
	}
	public void setColor(PrimaryColor color) {
		this.color = color;
	}
	public int getCurrentScore() {
		return currentScore;
	}
	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}
	@Override
	public String toString() {
		return nickname + "|" + color + ": " + currentScore ;
	}

	/**
	 * 
	 * @param f
	 * @add score to CurrentScore
	 */
	public long AddScore(float scoreToAdd) 
	{

		int newScore;
		newScore=(int) (getCurrentScore()+scoreToAdd);
		setCurrentScore(newScore);
		return getCurrentScore();
	}
	/**
	 * 
	 * @param f
	 * @deduct score from CurrentScore
	 */
	public long DeductScore(float score) {
		int newScore;
		newScore=(int) (getCurrentScore()-score);
		setCurrentScore(newScore);
		return getCurrentScore();

	}

	public static Player getInstance(int index)
	{
		if(index > 1 ) return null;

		tryInitiate();

		if(instances[index] == null)
		{
			if(index == 0)
				instances[index] = new Player(PrimaryColor.WHITE);
			else
				instances[index] = new Player(PrimaryColor.BLACK);
		}

		return instances[index];
	}

	private static Boolean tryInitiate()
	{
		if(initiated) return false;

		for (int i = 0; i < instances.length; i++)
		{
			instances[i] = null;
		}

		initiated = true;

		return true;
	}

//	public int getDeadSoldierCount() {
//		Board board = Board.getInstance();
//		int aliveCntr =0;
//		ArrayList<Piece> playerPieces =	board.getColorPieces(getColor());
//		for(Piece p : playerPieces) {
//			if(p instanceof Soldier) {
//				aliveCntr++;
//			}
//		}
//		return 12-aliveCntr;
//
//	}
//
//	public int getDeadQueenCount() {
//		Board board = Board.getInstance();
//		int aliveCntr =0;
//		ArrayList<Piece> playerPieces =	board.getColorPieces(getColor());
//		for(Piece p : playerPieces) {
//			if(p instanceof Queen) {
//				aliveCntr++;
//			}
//		}
//		//can't know without knowing how many pieces converted
//		return 12-aliveCntr;
//	}

}
