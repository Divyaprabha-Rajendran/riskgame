package ca.riskgamet31.exceptions;

/**
 * Exception if count for number of players is entered unvalid
 * @author Yash Doshi
 * 
 */
public class InvalidPlayerCountInput extends Exception{
	/**
	 * InvalidPlayerCountInput
	 * @param cause cause of the exception
	 */
	public InvalidPlayerCountInput(String cause)
	{
		super(cause);
	}
}
