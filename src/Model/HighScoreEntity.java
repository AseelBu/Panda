package Model;

import java.io.Serializable;

public class HighScoreEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String nickname;
	private int points;
	
	public HighScoreEntity(String nickname,int points) {
		this.nickname = nickname;
		this.points = points;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public String toString() {
		return "HighScoreEntity [nickname=" + nickname + ", points=" + points + "]";
	}
	
	

}
