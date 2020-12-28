package Model;

import Utils.PrimaryColor;


/**
 * 
 * @author aseel
 * class describes yellow tile in board.
 * it inherits Tile attributes and actions
 */
public class YellowTile extends Tile  {


	private  Question question=null;

	/**
	 * 
	 * this YellowTile builder
	 *
	 * @param <T>
	 */
	protected static abstract class Init <T extends Init<T>> extends Tile.Init<T>{
	
		public YellowTile build() {
			return new YellowTile(this);
		}
	}
	
	/**
	 * inner Builder class to build superclass
	 */
	public static class Builder extends Init<Builder>{
		/**
		 * Builder class constructor
		 * @param location the location of the tile
		 * @param color1 the primary color of the tile black/white
		 */
		public Builder(Location location,PrimaryColor color1) {
			super.location = location;
			super.color1 = color1;	
		}
		@Override
		protected Builder self() {
			return this;
		}

	}

	/**
	 * Constructor
	 * @param init superclass object
	 */
	protected YellowTile(Init<?> init) {
		super(init);

	}


	//getters & setters
	/**
	 * 
	 * @return Question the question in Tile
	 */
	public Question getQuestion() {
		return question;
	}


	/**
	 * @param question the question to set
	 */
	public void setQuestion(Question question) {
		this.question = question;
	}


	/**
	 * Draws an available random question from current game
	 */
	public void drawQuestion() {

		Question q = Game.getInstance().getAvailableRandomQuestion();
		System.out.println("drawn question is "+q);
		this.setQuestion(q);
	}

	/**
	 * Checks if chosen answer is correct
	 * @param answer chosen answer
	 * @return correct/not correct
	 */
	public boolean isAnswerCorrect(Answer answer) {
		
		boolean result= false;
		try {
			if(this.question.getCorrectAnswer()==answer.getId()) {
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
		return "YellowTile: "+super.getLocation()+" secondaryColor:"+getColor2()+"\nquestion=" + question +"\n";
	}
}
