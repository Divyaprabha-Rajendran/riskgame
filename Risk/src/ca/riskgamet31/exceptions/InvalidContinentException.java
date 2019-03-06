package ca.riskgamet31.exceptions;

/**
 * @author Divyaprabha
 * Runtime Exception class for duplicate continent name or continent name as exists in a country list
 * 
 */
public class InvalidContinentException extends RuntimeException 
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
