package ca.riskgamet31.exceptions;

/**
 * Exception class if the from and to country are same or either of the country has a continent names
 * @author Divyaprabha 
 */
public class InvalidLinkException extends Exception
  {
	/**
	 * InvalidLinkException
	 * 
	 * @param cause cause of the error
	 */
	public InvalidLinkException(String cause)
	  {
		super(cause);
		System.err.println(cause);
		
	  }
  }
