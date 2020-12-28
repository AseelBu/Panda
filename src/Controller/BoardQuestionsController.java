/**
 * 
 */
package Controller;


import Exceptions.QuestionException;
import Model.Game;
import Model.Question;
import Model.SysData;
import Utils.DifficultyLevel;

/**
 * @author aseel
 *
 */
public class BoardQuestionsController {
	private final static int CORRECT_EASY_POINTS=100;
	private final static int CORRECT_MEDIOCRE_POINTS=200;
	private final static int CORRECT_HARD_POINTS=500;
	private final static int WRONG_EASY_POINTS=250;
	private final static int WRONG_MEDIOCRE_POINTS=100;
	private final static int WRONG_HARD_POINTS=50;
	
	private static BoardQuestionsController instance;

	private SysData sysData;
	
	/**
	 * 
	 */
	private BoardQuestionsController() {
		
		//this.sysData = SysData.getInstance();
	}
	
	/**
	 * Get Instance
	 * @return  BoardQuestionController's instance
	 */

	public static BoardQuestionsController getInstance() 
	{ 
		if (instance == null) 
		{ 
			instance = new BoardQuestionsController(); 
		} 
		return instance; 
	}


	/**
	 * checks with model if answer is correct or not based on chosen answer
	 * @param questionId the question id
	 * @param chosenAnswer the chosen answer to check
	 * @return true if the answer is correct, false otherwise
	 * @throws QuestionException  wasn't able to retrieve question from system data
	 */
	public static boolean checkQuestionAnswer(int qId,int chosenAnswer) throws QuestionException {
		int questionId = qId;
		
		Question currentQuestion=SysData.getInstance().getQuesById(questionId);

		if(currentQuestion==null) 
			throw new QuestionException("Error getting the question data from system");
					
		//if answer is correct add points according to qu difficulty
		if(currentQuestion.getCorrectAnswer() == chosenAnswer) {
			addPointsForCorrectAnswer(currentQuestion.getDifficulty());
			return true;
		}
		//if answer is wrong remove points according to qu difficulty
		else {
			removePointsForWrongAnswer(currentQuestion.getDifficulty());
			return false;
		}	
		
	}
	/**
	 * TODO why static?
	 * return difficulty of question by question id
	 * @param qId the question id
	 * @return DifficultyLevel the difficulty level of the question
	 * @throws QuestionException wasn't able to retrieve question from system data
	 */
	public static DifficultyLevel Diffeculty(int qId) throws QuestionException{
	int questionId = qId;
		
		Question currentQuestion=SysData.getInstance().getQuesById(questionId);
		
		if(currentQuestion==null) 
			throw new QuestionException("Error getting the question data from system");
		
	return currentQuestion.getDifficulty();
	}
	
	/**
	 *  TODO why static?
	 * adds point for current player according to question difficulty
	 * @param quDifficulty the question difficulty
	 */
	public static void addPointsForCorrectAnswer(DifficultyLevel quDifficulty){
		int score=Game.getInstance().getPlayerr().getCurrentScore();
		switch(quDifficulty){
		case EASY:Game.getInstance().getPlayerr().setCurrentScore(score+CORRECT_EASY_POINTS);
			break;
		case MEDIOCRE:Game.getInstance().getPlayerr().setCurrentScore(score+CORRECT_MEDIOCRE_POINTS);
			break;
		case HARD:Game.getInstance().getPlayerr().setCurrentScore(score+CORRECT_HARD_POINTS);
			break;
		}
	}
	
	
	/**
	 *  TODO why static?
	 * removes points from current player according to question difficulty
	 * @param quDifficulty the question difficulty
	 */
	public static void removePointsForWrongAnswer(DifficultyLevel quDifficulty){
		int score=Game.getInstance().getPlayerr().getCurrentScore();
		switch(quDifficulty){
		case EASY:Game.getInstance().getPlayerr().setCurrentScore(score-WRONG_EASY_POINTS);
			break;
		case MEDIOCRE:Game.getInstance().getPlayerr().setCurrentScore(score-WRONG_MEDIOCRE_POINTS);
			break;
		case HARD:Game.getInstance().getPlayerr().setCurrentScore(score-WRONG_HARD_POINTS);
			break;
		}

	}
	
}
