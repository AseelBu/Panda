package Model;

import java.io.Serializable;

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
	private static final long serialVersionUID = 6221768933754989694L;
	private static final int WHITE = 0;
	private static final int BLACK = 1;
	private String nickname;
	private PrimaryColor color;
	private int currentScore;
	private static Player instances[] = new Player[2];
	private static Boolean initiated = false;

	/**
	 * Gets the Single instance of player based on index
	 * @param index 0 or 1, 0 for white player and 1 for black
	 * @return Player instance based on the index
	 */
	public static Player getInstance(int index)
	{
		if(index > BLACK ) return null;

		tryInitiate();

		if(instances[index] == null)
		{
			if(index == WHITE)
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
		initiated = false;
	}

	//helping method to initiate players Singleton instances
	private static Boolean tryInitiate()
	{
		if(initiated) return false;

		instances = new Player[2];

		for (int i = 0; i < instances.length; i++)
		{
			instances[i] = null;
		}

		initiated = true;

		return true;
	}

	/**
	 * Player class constructor
	 * @param color of the player BLACK or WHITE
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


	/**
	 * add score to this player
	 * 
	 * @param scoreToAdd the score to add for this player
	 * @return long the updated score for this player
	 * 
	 */
	public long AddScore(float scoreToAdd) 
	{
		int newScore;
		newScore=(int) (getCurrentScore()+scoreToAdd);
		setCurrentScore(newScore);

		return getCurrentScore();
	}

	/**
	 * deducts score from this player
	 * @param score the amount of scores to deduct
	 * @return long the updated score for this player
	 */
	public long DeductScore(float score) {
		int newScore;
		newScore=(int) (getCurrentScore()-score);
		setCurrentScore(newScore);
		return getCurrentScore();
	}


	@Override
	public String toString() {
		return nickname + "|" + color + ": " + currentScore ;
	}
}
