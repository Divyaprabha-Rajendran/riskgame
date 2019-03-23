package ca.riskgamet31.views;


import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import ca.riskgamet31.controllers.GameMainDriver;
import ca.riskgamet31.maincomps.Hand;
import ca.riskgamet31.maincomps.Player;
import ca.riskgamet31.utility.InputValidator;
import ca.riskgamet31.utility.UserInputRequester;

public class CardExchangeView implements Observer
  {

	
	
	
	public CardExchangeView()
	{
	  
	}
	
	public boolean executeTurnInCard(Player currentPlayer,Hand hand,String request) {
	  
	  UserInputRequester uir = new UserInputRequester();
	  InputValidator inpValidator = new InputValidator();
	  boolean executed = false;
	  
	  String userInput = "";
	  String[] selectedCards;
	  boolean validSelection = false;
	  do
	{
		  
	  do {
	  userInput = uir.requestUserInput(request);
	  
	  }while(!inpValidator.validateNumbers(userInput) && userInput.length() == 3);
	  
	  selectedCards = userInput.split("(?<=\\G.{1})");
	  
	  validSelection = Integer.parseInt(selectedCards[0]) <= hand.getCardsFromHand().size() && Integer.parseInt(selectedCards[1]) <= hand.getCardsFromHand().size() && Integer.parseInt(selectedCards[2]) <= hand.getCardsFromHand().size();
		  
	 if (validSelection && !Arrays.toString(selectedCards).equals("999") && hand.canTurnInCards(Integer.parseInt(selectedCards[0]), Integer.parseInt(selectedCards[1]), Integer.parseInt(selectedCards[2])))
	   {
		hand.removeCardsFromHand(Integer.parseInt(selectedCards[0]), Integer.parseInt(selectedCards[1]), Integer.parseInt(selectedCards[2])); 
	   
		currentPlayer.setArmies(currentPlayer.getPlayerArmies() + (GameMainDriver.turnInCardsCount++*5));
	    System.out.println("Now "+currentPlayer.getplayerName() + ": is eligible for " + currentPlayer.getPlayerArmies() +" armies");
		System.out.println("Current player's hand has "+ hand.getCardsFromHand());
		executed = true;
	   }else {
		 System.out.println("Wrong cards selection");
	   }
		}
	  while (!Arrays.toString(selectedCards).equals("999") && !executed);
	  
		return executed;
	}
	
	
	public void processCardExchange(Player currentPlayer) {
	  
	  //1- display current hand content, player reinforcement amount.
	  // if applicable to exchange propose.
	  		// if exchanged increase armies of this player with the proper amount.
	  		// display new hand and new reinforcement amount
	  //else
	  	// say not applicable.
	  Hand playerHand =  currentPlayer.getHand();
	  System.out.println("--------------------- Exchange Cards view -------------------------");
	  System.out.println(currentPlayer.getplayerName() + ": is eligible for " + currentPlayer.getPlayerArmies() + " armies");
	  System.out.println("Current Player's hand has "+ playerHand.getCardsFromHand());
	  boolean turnedInCards = false;
	  if (playerHand.isEligibleToExchange())
		{
	  while (playerHand.mustTurnInCards())
		{
		turnedInCards =  this.executeTurnInCard(currentPlayer, playerHand, "Must exchange cards: Select cards to exchange, ex: 235");
		}
	  
	  	if (!turnedInCards)
	  	  {
	  		turnedInCards =  this.executeTurnInCard(currentPlayer, playerHand, "Select cards to exchange, ex: 235 , 999 to exit");
	  	  }
	  
		}
	  else {
		  System.out.println("You are not eligible to exchange cards.");
		}
	  
	}
	
  @Override
  public void update(Observable o, Object arg)
	{
	  Player currentPlayer = (Player)arg;
	  processCardExchange(currentPlayer);
	  
	  
	}
	
  }
