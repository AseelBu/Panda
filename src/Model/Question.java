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
	 * Question class constructor
	 * @param id - question ID
	 * @param content - question's content
	 * @param difficulty - difficulty of the question
	 * @param answers - collection of answers
	 * @param team
	 */
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
	
	/**
	 * Used to update answers
	 * @param updatedAnswers
	 */
	public void updateAnswers(ArrayList<Answer> updatedAnswers) {
		this.answers = new ArrayList<Answer>();
		
		for(Answer a : updatedAnswers) {
			this.addAnswer(a);
		}
	}
	
	/**
	 * returns the correct answer
	 * @return answer-the correct answer of the question
	 */
	public Answer getCorrectAnswer() {
		for(Answer a : this.answers) {
			if (a.isCorrect()==true) {
				return a;
			}
		}
		return null;
		
	}


	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answers == null) ? 0 : answers.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((difficulty == null) ? 0 : difficulty.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (answers == null) {
			if (other.answers != null)
				return false;
		} else if (!answers.equals(other.answers))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (difficulty != other.difficulty)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Question: id=" + id + ", content=" + content + ", difficulty=" + difficulty + ", answers=" + answers
				+ ", team=" + team ;
	}
	
	
	
	
	

}
