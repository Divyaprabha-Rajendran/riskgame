package ca.riskgamet31.exceptions;

/**
 * @author Divyaprabha
 * Runtime Exception class if the graph is not a connected graph.
 * @param cause cause for exception
 */
public class InvalidGraphException extends RuntimeException 
{
	public InvalidGraphException(String cause)
	{
		super(cause);
	}
}
