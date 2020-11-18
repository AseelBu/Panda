package Model;

public class YellowTile {
	private int questionId;
	
	//Constructor

	public YellowTile(int questionId) {
		super();
		this.questionId = questionId;
	}
	
	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	@Override
	public String toString() {
		return "YellowTile [questionId=" + questionId + "]";
	}
}
