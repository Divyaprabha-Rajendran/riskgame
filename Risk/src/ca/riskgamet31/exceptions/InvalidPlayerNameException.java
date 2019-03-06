package ca.riskgamet31.exceptions;
/**
 * @author Divyaprabha
 * Exception class if the player name has special characters
 * 
 */
public class InvalidPlayerNameException extends Exception 
{
  /**
   * InvalidPlayerNameException
   * @param cause cause of the exception
   */
	public InvalidPlayerNameException(String cause) 
	{
		super(cause);
	}
}
