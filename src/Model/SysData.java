package Model;

import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;

/**
 * @author Saleh
 */
public class SysData {

	private static SysData instance = null;
	private static final int HIGHSCORES_AMOUNT = 10;
	private ArrayList<Question> questions = new ArrayList<Question>();
	private ArrayList<Player> scoreboard = new ArrayList<Player>();

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
	 * @return ArrayList<Question> of Questions in the system
	 */

	public ArrayList<Question> getQuestions() {
		return this.questions;
	}


	/**
	 * Questions Data Setter
	 * @param questions  The questions to replace
	 */

	public void setQuestions(ArrayList<Question> questions) {
		this.questions.clear();
		this.questions.addAll(questions);
	}


	/**
	 * Gets ScoreBoard DataStructre
	 * 
	 * @return ArrayList<Player> of players in score board
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

		if (this.getScoreboard().size() < HIGHSCORES_AMOUNT) {
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
	 * Add Question to system's questions
	 * 
	 * @param question to add
	 */
	public void addQuestion(Question q) {

		if (q != null) {
			this.getQuestions().add(q);
		}

	}

	/**
	 * removes a question from the system's questions
	 * 
	 * @param id the id of question to be removed
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
	 * Updates question in the system's questions
	 * @param id the id of the question to be updated
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

	/**
	 * returns question from the system by id
	 * @param id the id of the question
	 * @return Question the matching question for the id from the system's questions
	 */
	public Question getQuesById(int id) {
		for(Question q : this.getQuestions()) {

			if(q.getId() == id){
				return q;
			}
		}
		return null;
	}
}
