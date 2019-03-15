package ca.riskgamet31.exceptions;

/**
 * Exception class if the graph is not a connected graph.
 * @author Divyaprabha 
 */
public class InvalidGraphException extends Exception
  {
	/**
	 * InvalidGraphException
	 * 
	 * @param cause cause cause for exception
	 */
	public InvalidGraphException(String cause)
	  {
		super(cause);
		System.err.println(cause);
		
	  }
  }
