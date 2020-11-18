/**
 * 
 */
package Model;

import java.util.ArrayList;

import Utils.DifficultyLevel;

/**
 * @author aseel
 *
 */
public class Question {
	
	private int id;
	private String content;
	private DifficultyLevel difficulty;
	private ArrayList<Answer> answers;
	private String team;
	
	/**
	 * @param id
	 * @param content
	 * @param difficulty
	 * @param answers
	 * @param team
	 */
	//Constructor

	public Question(int id, String content, DifficultyLevel difficulty, ArrayList<Answer> answers, String team) {
		super();
		this.id = id;
		this.content = content;
		this.difficulty = difficulty;
		this.answers = answers;
		this.team = team;
	}

	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @return the difficulty
	 */
	public DifficultyLevel getDifficulty() {
		return difficulty;
	}

	/**
	 * @param difficulty - the difficulty to set
	 */
	public void setDifficulty(DifficultyLevel difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * @return the team
	 */
	public String getTeam() {
		return team;
	}

	/**
	 * @param team - the team to set
	 */
	public void setTeam(String team) {
		this.team = team;
	}

	/**
	 * @return the answers
	 */
	public ArrayList<Answer> getAnswers() {
		return answers;
	}
	
	/**
	 * 
	 * @param answer
	 * @return true if answer added successfully, false otherwise
	 */
	public Boolean addAnswer(Answer answer) {
		if(answer != null) {
			return this.answers.add(answer);
		}
		return false;
		
	}
	
	
	/**
	 * 
	 * @param answer
	 * @return true if answer removed successfully, false otherwise
	 */
	public Boolean removeAnswer(Answer answer) {
		if(answer != null) {
			return this.answers.remove(answer);
		}
		return false;
		
	}
	
	

}
