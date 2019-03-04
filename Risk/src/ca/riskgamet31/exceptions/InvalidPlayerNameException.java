package ca.riskgamet31.exceptions;
/**
 * @author Divyaprabha
 * Exception class if the player name has special characters
 * @param cause cause for exception
 */
public class InvalidPlayerNameException extends Exception 
{
	public InvalidPlayerNameException(String cause) 
	{
		super(cause);
	}
}
