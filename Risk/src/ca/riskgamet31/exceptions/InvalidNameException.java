package ca.riskgamet31.exceptions;

/**
 * Exception class if the country or continent has special characters
 * 
 * @author Divyaprabha
 */
public class InvalidNameException extends Exception
  {
	/**
	 * InvalidNameException
	 * 
	 * @param cause cause of the exception
	 */
	public InvalidNameException(String cause)
	  {
		super(cause);
		System.err.println(cause);
	  }
  }
