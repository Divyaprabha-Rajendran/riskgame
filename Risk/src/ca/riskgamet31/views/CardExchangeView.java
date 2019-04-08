package ca.riskgamet31.views;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import ca.riskgamet31.controllers.GameMainDriver;
import ca.riskgamet31.maincomps.Hand;
import ca.riskgamet31.maincomps.Player;
import ca.riskgamet31.utility.InputValidator;
import ca.riskgamet31.utility.UserInputOutput;

/**
 * card exchange view using Observer pattern
 * @author Fareed Tayar
 *@since 2.0
 *version 2.0
 */
public class CardExchangeView implements Observer,Serializable
  {
	
	/**
	 * a no args constructor
	 */
	private GameMainDriver gameMainDriver;
	
	public CardExchangeView(Observable gameMD)
	  {
		this.gameMainDriver = (GameMainDriver)gameMD;
	  }
	  
	/**
	 * to process exchange of cards
	 * @param currentPlayer current player exchanging the cards
	 */
	public void processCardExchange(Player currentPlayer)
	  {
		
		Hand playerHand = currentPlayer.getHand();
		System.out
		    .println("--------------------- Exchange Cards view -------------------------");
		System.out.println(currentPlayer
		    .getplayerName() + ": is eligible for " + currentPlayer
		        .getPlayerArmies() + " armies");
		System.out.println("Current Player's hand has " + playerHand
		    .getCardsFromHand());
		boolean turnedInCards = false;
		if (playerHand.isEligibleToExchange())
		  {
			while (playerHand.mustTurnInCards())
			  {
				turnedInCards = currentPlayer
				    .executeTurnInCard(this.gameMainDriver, "Must exchange cards: Select cards to exchange, ex: 235");
			  }
			  
			if (!turnedInCards)
			  {
				turnedInCards = currentPlayer
				    .executeTurnInCard(this.gameMainDriver,"Select cards to exchange, ex: 235 , 999 to exit");
			  }
			  
		  } else
		  {
			System.out.println("You are not eligible to exchange cards.");
		  }
		  
	  }
	  
	/**
	 *to be executed when the view is triggered
	 *@param o the observable object
	 *@param arg data object passed from observed object
	 */
	@Override
	public void update(Observable o, Object arg)
	  {
		Player currentPlayer = (Player) arg;
		processCardExchange(currentPlayer);
		
	  }
	  
	
  }
