package UnitTests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;

import Controller.QuestionMgmtController;
import Model.Answer;
import Model.Question;
import Model.SysData;
import Model.YellowTile;

public class QuestionAnswer {

	@Test
	public void checkAllAnswers() {
		QuestionAnswering();
	}
	
	
	private void QuestionAnswering() {
		
		Random r = new Random();
		
		// get random question;
		
		QuestionMgmtController.getInstance().LoadQuestions();
		
		
		
		Question q = SysData.getInstance().getQuestions().get(r.nextInt(SysData.getInstance().getQuestions().size()));
		
		// contains wrong answers
		
		ArrayList<Answer> wrongAnswers = new ArrayList<Answer>();
		
		// correct answer 
		
		Answer correctAnswer = null;
		
		for(Answer a : q.getAnswers()) {
			
			if(!a.isCorrect()) {
				wrongAnswers.add(a);
			}
			else {
				correctAnswer = a;
			}	
			
		}
		
		// test Yellow Tile
		
		YellowTile testTile = new YellowTile(null, null, null, null);
		
		testTile.setQuestionId(q);
		
		// test on wrong answers 
		
		for(Answer a : wrongAnswers) {
		
		assertEquals(testTile.isAnswerCorrect(a),false);
		assertFalse("Question is not Correct", testTile.isAnswerCorrect(a));

		
		}
		
		// test on correct answer
		
		assertEquals(testTile.isAnswerCorrect(correctAnswer),true);
		assertTrue("Question is Correct",true);
		
		}

		
	

}
