package ca.riskgamet31.exceptions;

/**
 * Exception class for duplicate continent name or
 * continent name as exists in a country list
 * @author Divyaprabha 
 */
public class InvalidContinentException extends Exception
  {
	/**
	 * @param cause cause for exception
	 */
	public InvalidContinentException(String cause)
	  {
		super(cause);
		System.err.println(cause);
		
	  }
  }
