package ca.riskgamet31.exceptions;
/**
 * @author Divyaprabha
 * Runtime Exception class if the country or continent has special characters
 * @param cause cause for exception
 */

public class InvalidNameException extends RuntimeException
{
	public InvalidNameException(String cause) 
	{
		super(cause);
		System.err.println(cause);
	}
}
