package ca.riskgamet31.controllers;

import ca.riskgamet31.utility.LoadGame;
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
		switch(userSelection) {
		  case"1":
		GameMainDriver singleGameDriver = new GameMainDriver();
		singleGameDriver.execute();
		break;
		  case"2":
			TournamentMainDriver tournament = new TournamentMainDriver();
			tournament.execute();			
			break;
		  case"3":
			
			LoadGame loadGame = new LoadGame();
			try
			  {
				loadGame.resumeGame();
			  } catch (Exception e)
			  {
				// TODO Auto-generated catch block
				e.printStackTrace();
			  }
			
			break;
		}
	  }
	  
  }
