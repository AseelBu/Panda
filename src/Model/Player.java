package Model;

import java.io.Serializable;

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
		return "Player [Nickname=" + nickname + ", Color=" + color + ", currentScore=" + currentScore + "]";
	}
	
	/**
	 * 
	 * @param f
	 * @add score to CurrentScore
	 */
	public long AddScore(float f) 
		{
			
		   int newScore;
		   newScore=(int) (getCurrentScore()+f);
			setCurrentScore(newScore);
			return getCurrentScore();
		}
	/**
	 * 
	 * @param f
	 * @deduct score from CurrentScore
	 */
	public long DeductScore(float f) {
		   int newScore;
		   newScore=(int) (getCurrentScore()-f);
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



}
