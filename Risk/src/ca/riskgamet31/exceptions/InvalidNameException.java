package ca.riskgamet31.exceptions;

/**
 * @author Divyaprabha
 * Runtime Exception class if the country or continent has special characters
 * 
 */
public class InvalidNameException extends RuntimeException
{
	/**
	 *  InvalidNameException
	 *  @param cause cause of the exception
	 */
  public InvalidNameException(String cause)
	{
		super(cause);
		System.err.println(cause);
	}
}
