package ca.riskgamet31.exceptions;
/**
 * @author Divyaprabha
 * Runtime Exception class if the from and to country are same or either of the country has a continent names
 * @param cause cause for exception
 */

public class InvalidLinkException extends RuntimeException 
{
	public InvalidLinkException(String cause)
	{
		super(cause);
	}
}
