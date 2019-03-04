package ca.riskgamet31.exceptions;

/**
 * @author Divyaprabha
 * Runtime Exception class for duplicate continent name or continent name as exists in a country list
 * @param cause cause for exception
 */
public class InvalidContinentException extends RuntimeException 
{
	public InvalidContinentException(String cause)
	{
		super(cause);
	}
}
