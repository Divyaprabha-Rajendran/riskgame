package ca.riskgamet31.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class to validate input
 * can be used for user input and/or game map file
 * @author Fareed Tayar
 * @version 2.0
 * @since 2.0
 */
public class InputValidator
  {
	
	/**
	 * checks if input is composed of alphabet characters
	 * @param input input to be validated
	 * @return true if input is characters only
	 */
	public boolean validateCharacter(String input)
	  {
		input = (input == null) ? "" : input;
		
		if (input.length() > 0)
		  {
			Pattern stringPattern = Pattern.compile("^[a-zA-Z0-9]*$");
			Matcher matcher = stringPattern.matcher(input);
			return matcher.matches();
		  } else
		  {
			return false;
		  }
	  }
	  
	/**
	 * checks if input is composed of numbers
	 * @param input input to be validated
	 * @return true if input is composed of numbers
	 */
	public boolean validateNumbers(String input)
	  {
		input = (input == null) ? "" : input;
		
		if (input.length() > 0)
		  {
			Pattern stringPattern = Pattern.compile("[1-9]*$");//("^[0-9]*$");
			Matcher matcher = stringPattern.matcher(input);
			return matcher.matches();
		  } else
		  {
			return false;
		  }
	  }
	  
	/**
	 * checks if input is composed of alpha-numeric characters
	 * @param input input to be validated
	 * @return true if input is composed of alpha-numeric
	 */
	public boolean validateAlphaNum(String input)
	  {
		
		input = (input == null) ? "" : input;
		if (input.length() > 0)
		  {
			Pattern stringPattern = Pattern.compile("^[a-zA-Z0-9]*$");
			Matcher matcher = stringPattern.matcher(input);
			return matcher.matches();
		  } else
		  {
			return false;
		  }
	  }
	
	
	  
  }
