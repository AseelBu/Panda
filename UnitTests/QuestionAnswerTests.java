

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import Controller.QuestionMgmtController;
import Model.Answer;
import Model.Game;
import Model.Player;
import Model.Question;
import Model.SysData;
import Model.Turn;
import Model.YellowTile;

public class QuestionAnswerTests {
	
	private static Question q;




	@BeforeClass
	public static void onceExecutedBeforeAll() {
		
		Random r = new Random();
		
		// get random question;
		
		QuestionMgmtController.getInstance().LoadQuestions();
		
		QuestionAnswerTests.q = SysData.getInstance().getQuestions().get(r.nextInt(SysData.getInstance().getQuestions().size()));
		
		// test Yellow Tile
		
		YellowTile testTile = new YellowTile(null, null, null, null);
				
		testTile.setQuestionId(q);
    }
	
	@Test
	public void AnswerIncorrectly() {
					
		
		
		ArrayList<Answer> wrongAnswers = new ArrayList<Answer>();
		
		// correct answer 
		
		
		for(Answer a : QuestionAnswerTests.q.getAnswers()) {
			
			if(!a.isCorrect()) {
				wrongAnswers.add(a);
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
		
	
		
		}
	
	@Test
	public void AnswerCorrectly() {
			
		// test Yellow Tile
		
		Answer CorrectAnswer = null;
		
		YellowTile testTile = new YellowTile(null, null, null, null);
				
		testTile.setQuestionId(q);
		
		for(Answer a : QuestionAnswerTests.q.getAnswers()) {
			
		
			if(a.isCorrect()) {
				CorrectAnswer = a;
				break;
			}
			
		}
		
		// test on correct answer
		
		assertEquals(testTile.isAnswerCorrect(CorrectAnswer),true);
		assertTrue("Question is Correct",true);
			
	}

		
	

}
