package ca.riskgamet31.exceptions;

/**
 * @author Divyaprabha Runtime Exception class if the from and to country are
 *         same or either of the country has a continent names
 * 
 */
public class InvalidLinkException extends RuntimeException
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
