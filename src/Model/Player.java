package Model;

import java.io.Serializable;

import Controller.BoardController;
import Utils.PrimaryColor;

/**
 * 
 * @author Maryam
 *
 */
public class Player implements Serializable {

	/**
	 * Class Serial
	 */
	private static final long serialVersionUID = 1L;
	private String nickname;
	private PrimaryColor color;
	private int currentScore;
	private static Player instances[] = new Player[2];
	private static Boolean initiated = false;

	/**
	 * Player class constructor
	 * @param color
	 */
	private Player(PrimaryColor color) {
		setColor(color);
		currentScore = 0;
	}

	// Getters and Setters
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
		BoardController.getInstance().updateScoreInDisplay(this.getColor(), getCurrentScore());
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
		BoardController.getInstance().updateScoreInDisplay(this.getColor(), getCurrentScore());
		return getCurrentScore();
	}
	
	/**
	 * Class Instance
	 * @param index 0 or 1 - 0 for white player, 1 for black
	 * @return 
	 */
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
	
	/**
	 * destructor for this class
	 */
	public static void destruct() {
		instances = null;
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

}
