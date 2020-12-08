/**
 * 
 */
package Controller;

import java.util.ArrayList;

import Model.Answer;
import Model.Question;
import Model.SysData;
import Utils.DifficultyLevel;

/**
 * @author aseel
 *
 */
public class BoardQuestionsController {
	private final static float CORRECT_EASY_POINTS=100;
	private final static float CORRECT_MEDIOCRE_POINTS=200;
	private final static float CORRECT_HARD_POINTS=500;
	private final static float WRONG_EASY_POINTS=250;
	private final static float WRONG_MEDIOCRE_POINTS=100;
	private final static float WRONG_HARD_POINTS=50;
	
	private static BoardQuestionsController instance;
	
	
	/**
	 * 
	 */
	private BoardQuestionsController() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Get Instance
	 * @return - Controller's instance
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
	 * @param questionId
	 * @param chosenAnswer
	 * @return true if the answer is correct, false otherwise
	 */
	public boolean checkQuestionAnswer(int questionId,Answer chosenAnswer) {
		//**on close the question pop up ->get from boardGUI the chosen answer**
		Question currentQuestion=SysData.getInstance().getQuesById(questionId);
		
		//TODO something went wrong add exception
		if(currentQuestion==null) return false;
		
		//if answer is correct add points according to qu difficulty
		if(currentQuestion.getCorrectAnswer().equals(chosenAnswer)) {
			addPointsForCorrectAnswer(currentQuestion.getDifficulty());
			return true;
		}
		//if answer is wrong remove points according to qu difficulty
		else {
			removePointsForWrongAnswer(currentQuestion.getDifficulty());
			return false;
		}	
		
	}
	
	//TODO implement
	//TODO use the constants:
//	private final static float CORRECT_EASY_POINTS=100;
//	private final static float CORRECT_MEDIOCRE_POINTS=200;
//	private final static float CORRECT_HARD_POINTS=500;
//	private final static float WRONG_EASY_POINTS=250;
//	private final static float WRONG_MEDIOCRE_POINTS=100;
//	private final static float WRONG_HARD_POINTS=50;
	/**
	 * adds point for current player according to question difficulty
	 * @param quDifficulty
	 */
	public void addPointsForCorrectAnswer(DifficultyLevel quDifficulty){
		switch(quDifficulty){
		case EASY:
			break;
		case MEDIOCRE:
			break;
		case HARD:
			break;
		}
	}
	
	//TODO implement
	//TODO use the constants:
//	private final static float CORRECT_EASY_POINTS=100;
//	private final static float CORRECT_MEDIOCRE_POINTS=200;
//	private final static float CORRECT_HARD_POINTS=500;
//	private final static float WRONG_EASY_POINTS=250;
//	private final static float WRONG_MEDIOCRE_POINTS=100;
//	private final static float WRONG_HARD_POINTS=50;
	/**
	 * removes points from current player according to question difficulty
	 * @param quDifficulty
	 */
	public void removePointsForWrongAnswer(DifficultyLevel quDifficulty){
		switch(quDifficulty){
		case EASY:
			break;
		case MEDIOCRE:
			break;
		case HARD:
			break;
		}
	}
	
}
