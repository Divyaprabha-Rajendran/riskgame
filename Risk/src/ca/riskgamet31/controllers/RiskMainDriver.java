package ca.riskgamet31.controllers;

import ca.riskgamet31.utility.LoadGame;
import ca.riskgamet31.utility.UserInputOutput;

/**
 * the main launcher for the game
 * 
 * @author Fareed Tayar
 * @version 3.0
 * @since 3.0
 */
public class RiskMainDriver
  {
	
	/**
	 * to display main options to end user
	 */
	public void displayOptions()
	  {
		System.out
		    .println("------------------Welcome to Risk Game----------------");
		System.out.println("1- For new Single Game.");
		System.out.println("2- For Tournament Game.");
		System.out.println("3- For continue a saved game.");
		
	  }
	  
	/**
	 * main method launching the game
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args)
	  {
		RiskMainDriver riskMainDriver = new RiskMainDriver();
		riskMainDriver.displayOptions();
		String userSelection = UserInputOutput.getInstance()
		    .requestUserInput("Enter your selection");
		switch (userSelection)
		  {
			case "1":
			  GameMainDriver singleGameDriver = new GameMainDriver();
			  singleGameDriver.execute();
			  break;
			case "2":
			  TournamentMainDriver tournament = new TournamentMainDriver();
			  tournament.execute();
			  break;
			case "3":
			  
			  LoadGame loadGame = new LoadGame();
			  try
				{
				  loadGame.resumeGame();
				} catch (Exception e)
				{
				  
				  e.printStackTrace();
				}
				
			  break;
		  }
	  }
	  
  }
