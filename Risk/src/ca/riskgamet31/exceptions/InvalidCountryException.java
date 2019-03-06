package ca.riskgamet31.exceptions;
/**
 * @author Divyaprabha
 * ERuntime xception class for duplicate country name or country name as exists in a continent list
 * @param cause cause for exception
 */

public class InvalidCountryException extends RuntimeException
{
	public InvalidCountryException(String cause)
	{
		super(cause);
		System.err.println(cause);
		
	}
}
