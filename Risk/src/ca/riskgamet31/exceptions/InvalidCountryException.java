package ca.riskgamet31.exceptions;

/**
 * Exception class for duplicate country name or
 * country name as exists in a continent list
 * @author Divyaprabha 
 * 
 */
public class InvalidCountryException extends Exception
  {
	/**
	 * invalid country exception
	 * 
	 * @param cause cause of the exception
	 */
	public InvalidCountryException(String cause)
	  {
		super(cause);
		System.err.println(cause);
		
	  }
  }
