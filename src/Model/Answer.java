package Model;
/**
 * 
 * @author Maryam
 *
 */
public class Answer {
	
	private int id;
	private String content;
	private boolean isCorrect;
	
	
	/**
	 * class constructor
	 * @param id - Answer ID
	 * @param content - content of the answer
	 * @param isCorrect - true if the answer is correct, false otherwise
	 */
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
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + id;
		result = prime * result + (isCorrect ? 1231 : 1237);
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
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (id != other.id)
			return false;
		if (isCorrect != other.isCorrect)
			return false;
		return true;
	}




	// getters & setters
	public int getId() {
		return id;
	}

	public String getContent() {
		return content;
	}
	
	public boolean isCorrect() {
		return isCorrect;
	}
	
	
	
	
	@Override
	public String toString() {
		return "Answer:id=" + id + ", content=" + content + ", isCorrect=" + isCorrect ;
	}
	

}
