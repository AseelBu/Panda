package Exceptions;

public class IllegalMoveException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2118477415355424206L;

	public IllegalMoveException(String errorMessage) {
        super(errorMessage);
    }
}
