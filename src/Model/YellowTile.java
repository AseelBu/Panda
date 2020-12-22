package Model;

import Utils.PrimaryColor;

public class YellowTile extends Tile  {


	private  Question question=null;


	protected static abstract class Init <T extends Init<T>> extends Tile.Init<T>{

	//	private Question question=null;

		/**
		 * Draws an available random question
		 */
		//		public T drawQuestion() {
		//			Question q = Game.getInstance().getAvailableRandomQuestion();
		//			System.out.println("drawn question is "+q);
		//			this.question = q;
		//			return self();
		//		}

		public YellowTile build() {
			//			drawQuestion();
			return new YellowTile(this);
		}
	}

	public static class Builder extends Init<Builder>{
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
	 * @param init
	 */
	protected YellowTile(Init<?> init) {
		super(init);
		//		this.question=init.question;

	}




	//getters & setters

	/**
	 * 
	 * @return question in Tile
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
	 * Draws an available random question
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
