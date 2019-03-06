package ca.riskgamet31.exceptions;
/**
 * @author Divyaprabha
 * ERuntime xception class for duplicate country name or country name as exists in a continent list
 * 
 */
public class InvalidCountryException extends RuntimeException
{
	/**
	 * invalid country exception
	 * @param cause cause of the exception
	 */
	public InvalidCountryException(String cause)
	{
		super(cause);
		System.err.println(cause);
		
	}
}
