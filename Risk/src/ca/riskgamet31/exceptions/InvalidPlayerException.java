package ca.riskgamet31.exceptions;
/**
 * @author Divyaprabha
 * Exception class for the duplication of player name.
 * @param cause cause for exception
 */
public class InvalidPlayerException extends Exception 
{
	public InvalidPlayerException(String cause) 
	{
		super(cause);
	}
}
