/**
 * 
 */
package Controller;


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
		// TODO Auto-generated constructor stub
		this.sysData = SysData.getInstance();
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
	public static boolean checkQuestionAnswer(int qId,int chosenAnswer) {
		
		//System.out.println("*********************************************885555558  "+ q);
		//System.out.println("*********************************************885555558  "+ chosenAnswer);

		
		int questionId = qId;
		
		//**on close the question pop up ->get from boardGUI the chosen answer**
		Question currentQuestion=SysData.getInstance().getQuesById(questionId);
		
		
		//TODO something went wrong add exception
		if(currentQuestion==null) return false;
		
		//if answer is correct add points according to qu difficulty
		if(currentQuestion.getCorrectAnswer()==chosenAnswer) {
			addPointsForCorrectAnswer(currentQuestion.getDifficulty());
		 //  System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");

			return true;
		}
		//if answer is wrong remove points according to qu difficulty
		else {
		//	System.out.println("*********************************************885555558 wwwweeeeeeeww  ");

			removePointsForWrongAnswer(currentQuestion.getDifficulty());
			return false;
		}	
		
	}
	
	
	/**
	 * adds point for current player according to question difficulty
	 * @param quDifficulty
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
	 * removes points from current player according to question difficulty
	 * @param quDifficulty
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
