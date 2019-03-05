package ca.riskgamet31.exceptions;

/**
 * Exception if count for number of players is entered unvalid
 * @author Yash Doshi
 * @param Cause for the exception 
 */
public class InvalidPlayerCountInput extends Exception{
	
	public InvalidPlayerCountInput(String cause)
	{
		super(cause);
	}
}
