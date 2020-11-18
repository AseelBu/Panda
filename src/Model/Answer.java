
package Model;

public class Answer {
	
	private int id;
	private String content;
	private boolean isCorrect;
	
	public Answer(int id, String content, boolean isCorrect) {
		super();
		this.id = id;
		this.content = content;
		this.isCorrect = isCorrect;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public boolean isCorrect() {
		return isCorrect;
	}
	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	
	@Override
	public String toString() {
		return "Answer [id=" + id + ", content=" + content + ", isCorrect=" + isCorrect + "]";
	}
	

}
