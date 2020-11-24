package Model;

import Utils.PrimaryColor;

/**
 * 
 * @author Maryam
 *
 */
public class Player {
	
	public String Nickname;
	public PrimaryColor Color;
	public int currentScore;
	 private static Player instances[] = new Player[2];
     private static Boolean initiated = false;
	
	//Constructor

	public Player() {
		
	}
	public String getNickname() {
		return Nickname;
	}
	public void setNickname(String nickname) {
		Nickname = nickname;
	}
	public PrimaryColor getColor() {
		return Color;
	}
	public void setColor(PrimaryColor color) {
		Color = color;
	}
	public int getCurrentScore() {
		return currentScore;
	}
	public void setCurrentScore(int currentScore) {
		this.currentScore = currentScore;
	}
	@Override
	public String toString() {
		return "Player [Nickname=" + Nickname + ", Color=" + Color + ", currentScore=" + currentScore + "]";
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
        tryInitiate();

        if(instances[index] == null)
        {
            instances[index] = new Player();
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
