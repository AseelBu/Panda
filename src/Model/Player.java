package Model;

public class Player {
	
	public String Nickname;
	public String Color;
	public int currentScore;
	
	public Player(String nickname, String color, int currentScore) {
		super();
		Nickname = nickname;
		Color = color;
		this.currentScore = currentScore;
	}
	
	public String getNickname() {
		return Nickname;
	}
	public void setNickname(String nickname) {
		Nickname = nickname;
	}
	public String getColor() {
		return Color;
	}
	public void setColor(String color) {
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
	



}
