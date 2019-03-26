package ca.riskgamet31.utility;

import java.util.Scanner;

/**
 * utility class for requesting an input from user using singleTon pattern
 * 
 * @author Fareed Tayar
 * @version 2.0
 * @since 2.0
 */
public class UserInputOutput
  {
	/**
	 * instance for user input output
	 */
	private static UserInputOutput instance;
	/**
	 * scanner to be used for all the game
	 */
	private static Scanner scanner;
	/**
	 * constructor
	 */
	private UserInputOutput()
	  {
		scanner = new Scanner(System.in);
	  }
	  /**
	   * method to return an instance of the class
	   * @return an instance of the class object
	   */
	public static UserInputOutput getInstance()
	  {
		
		if (instance == null)
		  {
			instance = new UserInputOutput();
		  }
		  
		return instance;
		
	  }
	  
	/**
	 * utility method for requesting user input
	 * 
	 * @param inputMsg the message to show to user
	 * @return user's input
	 */
	public String requestUserInput(String inputMsg)
	  {
		String txt = "";
		
		do
		  {
			System.out.println(inputMsg);
			if (scanner.hasNext())
			  txt = scanner.next().trim().toUpperCase();
		  } while (txt.length() == 0);
		  
		return txt;
	  }
	  
  }
