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
		Game game= Game.getInstance();
		this.question = game.getRandomQuestion();

	}

	//getters & setters
	public Question getQuestion() {
		return question;
	}

	public void setQuestionId(Question question) {
		this.question = question;
	}


	//	public void drawQuestion() {
	//		// TODO Auto-generated method stub
	//		SysData sysData= SysData.getInstance()
	//;		int index = randomGenerator.nextInt(sysData);
	//	}

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
