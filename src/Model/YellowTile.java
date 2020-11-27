package Model;

import Utils.PrimaryColor;
import Utils.SeconderyTileColor;

public class YellowTile extends Tile {


	private Question question;


	/**
	 * Constructor
	 * 
	 * @param location
	 * @param color1
	 * @param color2
	 * @param piece
	 * @param questionId
	 */
	public YellowTile(Location location,PrimaryColor color1,SeconderyTileColor color2,Piece piece) {
		super(location, color1, color2, piece);
	}

	/**
	 * getters & setters
	 */
	public Question getQuestion() {
		return question;
	}
	public void setQuestionId(Question question) {
		this.question = question;
	}
	
	
	
	/**
	 * Draws an available random question
	 */
	public void drawQuestion() {
		
		Question k = Game.getInstance().getAvailableRandomQuestion();
		this.setQuestionId(k);
		
	}
	
	/**
	 * Checks if chosen answer is correct
	 * @param answer chosen answer
	 * @return correct/not correct
	 */
	public boolean isAnswerCorrect(Answer answer) {
		// TODO Auto-generated method stub
		boolean result= false;
		try {
			if(this.question.getCorrectAnswer().equals(answer)) {
				result = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String toString() {
		return "YellowTile: [question=" + question + "]\n";
	}
}
