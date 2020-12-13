

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import Controller.BoardQuestionsController;
import Controller.QuestionMgmtController;
import Model.Answer;
import Model.Game;
import Model.Player;
import Model.Question;
import Model.SysData;
import Model.Turn;
import Utils.DifficultyLevel;

/**
 * 
 * @author saleh
 *
 */
public class ScoreAfterAnswerTests {
	
	@BeforeClass
    public static void onceExecutedBeforeAll() {
		
        QuestionMgmtController.getInstance().LoadQuestions();
        Player player1 = Player.getInstance(0);

		
		player1.setNickname("Tester");
		Player[] players = new Player[] {
				player1,
				};
		
				
		Game.getInstance().setPlayers(players);
		Game.getInstance().setTurn(new Turn(players[0]));
    }
	

	/**
	 * Test ID: 6
	 */
	@Test
	public void answerIntermediate() {
	
		
		// reset score
		
		Game.getInstance().getPlayerr().setCurrentScore(0);
		
		ArrayList<Question> intermediateQuestions = new ArrayList<Question>();
		
		// add all intermediate questions
		
		for(Question q : SysData.getInstance().getQuestions()) {
			if(q.getDifficulty().equals(DifficultyLevel.MEDIOCRE)) {
				intermediateQuestions.add(q);
			}
		}
		
		for(Question q : intermediateQuestions) {
			
			// answer correctly
			int oldscore = Game.getInstance().getPlayerr().getCurrentScore();
			BoardQuestionsController.checkQuestionAnswer(q.getId(), q.getCorrectAnswer());
			
			// answer correctly
			assertEquals(Game.getInstance().getPlayerr().getCurrentScore(),oldscore+200);
			
			// update variable
			oldscore = Game.getInstance().getPlayerr().getCurrentScore();
			
			// answer incorrectly
			
			int answerId = getWrongAnswer(q);
			
			BoardQuestionsController.checkQuestionAnswer(q.getId(), answerId);
			
			// answer incorrectly
			assertEquals(Game.getInstance().getPlayerr().getCurrentScore(),oldscore-100);
						
			
		}
		
	}
	
	/**
	 * Test ID: 7
	 */
	@Test
	public void answerEasy() {
		
		
		Game.getInstance().getPlayerr().setCurrentScore(0);
		
		ArrayList<Question> easyQuestions = new ArrayList<Question>();
		
		// add all intermediate questions
		
		for(Question q : SysData.getInstance().getQuestions()) {
			if(q.getDifficulty().equals(DifficultyLevel.EASY)) {
				easyQuestions.add(q);
			}
		}
		
		for(Question q : easyQuestions) {
			
			// answer correctly
			int oldscore = Game.getInstance().getPlayerr().getCurrentScore();
			BoardQuestionsController.checkQuestionAnswer(q.getId(), q.getCorrectAnswer());
			
			// answer correctly
			assertEquals(Game.getInstance().getPlayerr().getCurrentScore(),oldscore+100);
			
			// update variable
			oldscore = Game.getInstance().getPlayerr().getCurrentScore();
			
			// answer incorrectly
			
			int answerId = getWrongAnswer(q);
			
			BoardQuestionsController.checkQuestionAnswer(q.getId(), answerId);
			
			// answer incorrectly
			assertEquals(Game.getInstance().getPlayerr().getCurrentScore(),oldscore-250);
						
			
		}
	}
	
	/**
	 * Test ID: 8
	 */
	@Test
	public void answerHard() {
		
		Game.getInstance().getPlayerr().setCurrentScore(0);
		
		ArrayList<Question> hardQuestions = new ArrayList<Question>();
		
		// add all intermediate questions
		
		for(Question q : SysData.getInstance().getQuestions()) {
			if(q.getDifficulty().equals(DifficultyLevel.HARD)) {
				hardQuestions.add(q);
			}
		}
		
		for(Question q : hardQuestions) {
			
			// answer correctly
			int oldscore = Game.getInstance().getPlayerr().getCurrentScore();
			BoardQuestionsController.checkQuestionAnswer(q.getId(), q.getCorrectAnswer());
			
			// answer correctly
			assertEquals(Game.getInstance().getPlayerr().getCurrentScore(),oldscore+500);
			
			// update variable
			oldscore = Game.getInstance().getPlayerr().getCurrentScore();
			
			// answer incorrectly
			
			int answerId = getWrongAnswer(q);
			
			BoardQuestionsController.checkQuestionAnswer(q.getId(), answerId);
			
			// answer incorrectly
			assertEquals(Game.getInstance().getPlayerr().getCurrentScore(),oldscore-50);
						
			
		}
	}
	

	
	/**
	 * returns a wrong answer for the matter of testing
	 * @param q - questions to reuturn answer from
	 * @return wrong answer id
	 */
	public int getWrongAnswer(Question q) {
		
		for(Answer a : q.getAnswers()) {
			if(!a.isCorrect()) {
				return a.getId();
			}
		}
		
		return -1;
		
		
	}

}
