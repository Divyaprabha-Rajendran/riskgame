package ca.riskgamet31.controllers;

import ca.riskgamet31.utility.UserInputOutput;

public class RiskMainDriver
  {
	
	public void displayOptions() {
	  
	  System.out.println("------------------Welcome to Risk Game----------------");
	  System.out.println("1- For new Single Game.");
	  System.out.println("2- For Tournament Game.");
	  System.out.println("3- For continue a saved game.");
	  
	}
	
	
	public static void main(String[] args)
	  {
		// option for single game or tournament
		RiskMainDriver riskMainDriver = new RiskMainDriver();
		riskMainDriver.displayOptions();
		String userSelection = UserInputOutput.getInstance().requestUserInput("Enter your selection");
		// if single game Game main driver object then execute method
		GameMainDriver driver = new GameMainDriver();
		driver.execute();
		// if tournament do something about it.
		
		
		
		
	  }
	  
  }
