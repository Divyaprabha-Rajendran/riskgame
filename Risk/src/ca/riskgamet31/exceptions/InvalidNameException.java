package ca.riskgamet31.exceptions;

public class InvalidNameException extends RuntimeException
{
	public InvalidNameException(String reason) 
	{
		super(reason);
	}
}
