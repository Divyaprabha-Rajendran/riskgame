package ca.riskgamet31.exceptions;

/**
 * @author Divyaprabha
 * Runtime Exception class if the graph is not a connected graph.
 * 
 */
public class InvalidGraphException extends RuntimeException 
{
	/**
	 *  InvalidGraphException
	 * @param cause cause cause for exception
	 */
  	public InvalidGraphException(String cause)
	{
		super(cause);
		System.err.println(cause);
		
	}
}
