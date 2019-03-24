package ca.riskgamet31.utility;

import java.util.Scanner;

/**
 * utility class for requesting an input from user
 * @author Fareed Tayar
 *@version 2.0
 *@since 2.0
 */
public class UserInputRequester2
  {
	
	/**
	 * utility method for requesting user input
	 * @param inputMsg the message to show to user
	 * @return user's input
	 */
	public String requestUserInput(String inputMsg)
	  {
		
		Scanner sysIn = new Scanner(System.in);
		String txt = "";
		
		do
		  {
			System.out.println(inputMsg);
			if (sysIn.hasNext())
			  txt = sysIn.next().trim().toUpperCase();
		  } while (txt.length() == 0);
		  
		return txt;
	  }
	  
  }
