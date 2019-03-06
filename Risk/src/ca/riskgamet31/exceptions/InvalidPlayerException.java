package ca.riskgamet31.exceptions;

/**
 * @author Divyaprabha Exception class for the duplication of player name.
 * 
 */
public class InvalidPlayerException extends Exception
  {
	/**
	 * InvalidPlayerException
	 * 
	 * @param cause cause of the exception
	 */
	public InvalidPlayerException(String cause)
	  {
		super(cause);
	  }
  }
