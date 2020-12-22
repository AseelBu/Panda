package Model;




import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;



public class SysData {

	/**
	 * @author saleh
	 */

	private static SysData instance = null;
	private ArrayList<Question> questions = new ArrayList<Question>();
	private ArrayList<Player> scoreboard = new ArrayList<>();

	/**
	 * Using this singleton instance to access data structures and methods
	 * 
	 * @return - Sysdata Class Instance
	 */
	public static SysData getInstance() {
		if (instance == null) 
		{ 
			instance = new SysData(); 
		}
		return instance;
	}

	/**
	 * Question DataStructure
	 * 
	 * @return
	 */

	public ArrayList<Question> getQuestions() {
		return this.questions;
	}
	
	
	/**
	 * Question Data Setter
	 * @param t - Questions to replace
	 */
	
	public void setQuestions(ArrayList<Question> t) {
		this.questions.clear();
		this.questions.addAll(t);
	}


	/**
	 * ScoreBoard DataStructre
	 * 
	 * @return
	 */
	public ArrayList<Player> getScoreboard() {
		return this.scoreboard;
	}
	
	/**
	 * Scoreboard Data Setter
	 * @param scoreboard = data to replace
	 */
	public void setScoreboard(ArrayList<Player> scoreboard) {
		
		this.scoreboard.clear();
		this.scoreboard = scoreboard;
	}

	/**
	 * Adds scores to the scoreboard
	 * 
	 * @param hs HighScore To Add
	 */
	public void addScoreToHistory(Player hs) {

		this.sortHighscores();

		if (this.getScoreboard().size() < 10) {
			this.getScoreboard().add(hs);
			this.sortHighscores();
			return;
		}

		if (hs.getCurrentScore() <= this.getScoreboard().get(0).getCurrentScore()) {
			return;
		} else {

			this.getScoreboard().add(hs);
			this.getScoreboard().remove(0);
			this.sortHighscores();
			return;

		}

	}



	/**
	 * Add Question To Questions DataStructure
	 * 
	 * @param question to add
	 */
	public void addQuestion(Question q) {

		if (q != null) {
			this.getQuestions().add(q);
		}

	}

	/**
	 * remove a question from the DataStructure
	 * 
	 * @param id - id of question to be removed
	 */
	public void removeQuestion(int id) {

		int i = -1;
		int iterator = 0;

		for (Question q : this.getQuestions()) {

			if (q.getId() == id) {

				i = iterator;
				break;

			}

			iterator++;

		}
		
		if(i == -1) {
			return;
		}
		for(int c = i + 1 ; c <  this.getQuestions().size(); c++) {
			this.getQuestions().get(c).setId(id);
			id++;
			
		}
		if (i != -1) {
			this.questions.remove(i);
		}

	}

	/**
	 * 
	 * @param id               the id of the question to be updated
	 * @param updated_question new question containing all updated details
	 */
	public void editQuestion(int id, Question updated_question) {

		for (Question q : this.getQuestions()) {

			if (q.getId() == id) {
				q.setId(id);
				q.setContent(updated_question.getContent());
				q.setDifficulty(updated_question.getDifficulty());
				q.setTeam(updated_question.getTeam());
				q.updateAnswers(updated_question.getAnswers());
			}

		}

	}

	/**
	 * this method sorts HighScores
	 */
	public void sortHighscores() {

		Collections.sort(this.getScoreboard(), new Comparator<Player>() {
			public int compare(Player p1, Player p2) {
				return Integer.valueOf(p1.getCurrentScore()).compareTo(Integer.valueOf(p2.getCurrentScore()));
			}
		});

	}
	
	public Question getQuesById(int id) {
		
		
		for(Question q : this.getQuestions()) {
			
			if(q.getId() == id){
				return q;
			}
			
		}
		return null;
	}


}
