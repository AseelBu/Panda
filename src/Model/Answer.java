
package Model;

public class Answer {
	
	private int id;
	private String content;
	private boolean isCorrect;
	
	//Constructor

	public Answer(int id, String content, boolean isCorrect) {
		super();
		this.id = id;
		this.content = content;
		this.isCorrect = isCorrect;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Answer other = (Answer) obj;
		if (id != other.id)
			return false;
		return true;
	}


	// getters & setters
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
