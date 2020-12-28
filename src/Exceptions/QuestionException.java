/**
 * 
 */
package Exceptions;

/**
 * @author aseel
 *
 *all related question exceptions like Invalid Question,Invalid answer,Question has no Answers
 */
public class QuestionException extends Exception {

	private static final long serialVersionUID = 7571384313759718551L;

	/**
	 * @param message
	 */
	public QuestionException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
