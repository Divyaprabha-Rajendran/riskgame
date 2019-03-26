package ca.riskgamet31.views;

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
public class CardExchangeView implements Observer
  {
	
	/**
	 * a no args constructor
	 */
	public CardExchangeView()
	  {
		
	  }
	  
	/**
	 * to execute exchange of cards
	 * @param currentPlayer current player
	 * @param hand currently player's hand
	 * @param request selected cards by the player
	 * @return true if exchange been successful
	 */
	public boolean executeTurnInCard(Player currentPlayer, Hand hand,
	    String request)
	  {
		
		InputValidator inpValidator = new InputValidator();
		boolean executed = false;
		
		String userInput = "";
		String[] selectedCards;
		boolean validSelection = false;
		do
		  {
			
			do
			  {
				userInput = UserInputOutput.getInstance()
				    .requestUserInput(request);
			  } while (!inpValidator.validateNumbers(userInput) || userInput
			      .length() != 3);
			  
			selectedCards = userInput.split("(?<=\\G.{1})");
			
			validSelection = (Integer.parseInt(selectedCards[0]) <= hand
			    .getCardsFromHand().size() && Integer
			        .parseInt(selectedCards[0]) > 0) && (Integer
			            .parseInt(selectedCards[1]) <= hand.getCardsFromHand()
			                .size() && Integer
			                    .parseInt(selectedCards[1]) > 0) && (Integer
			                        .parseInt(selectedCards[2]) <= hand
			                            .getCardsFromHand().size() && Integer
			                                .parseInt(selectedCards[2]) > 0) && checkUniqueNumbers(selectedCards);
			
			if (validSelection && !Arrays.toString(selectedCards)
			    .equals("999") && hand.canTurnInCards(Integer
			        .parseInt(selectedCards[0]), Integer
			            .parseInt(selectedCards[1]), Integer
			                .parseInt(selectedCards[2])))
			  {
				hand.removeCardsFromHand(Integer
				    .parseInt(selectedCards[0]), Integer
				        .parseInt(selectedCards[1]), Integer
				            .parseInt(selectedCards[2]));
				
				currentPlayer.setArmies(currentPlayer
				    .getPlayerArmies() + (GameMainDriver.turnInCardsCount++ * 5));
				System.out.println("Now " + currentPlayer
				    .getplayerName() + ": is eligible for " + currentPlayer
				        .getPlayerArmies() + " armies");
				System.out.println("Current player's hand has " + hand
				    .getCardsFromHand());
				executed = true;
			  } else
			  {
				System.out.println("Wrong cards selection");
			  }
		  } while (!Arrays.toString(selectedCards).equals("999") && !executed);
		  
		return executed;
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
				turnedInCards = this
				    .executeTurnInCard(currentPlayer, playerHand, "Must exchange cards: Select cards to exchange, ex: 235");
			  }
			  
			if (!turnedInCards)
			  {
				turnedInCards = this
				    .executeTurnInCard(currentPlayer, playerHand, "Select cards to exchange, ex: 235 , 999 to exit");
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
	  
	/**
	 * to verify selected cards indexes are not the same.
	 * 
	 * @param request selected cards indexes array
	 * @return true if it is OK to continue with the exchange
	 */
	public boolean checkUniqueNumbers(String[] request)
	  {
		boolean uniqueNumber = true;
		Arrays.sort(request);
		for (int i = 0; i < request.length-1; i++)
		  {
			if (request[i].equals(request[i + 1]))
			  {
				uniqueNumber = false;
				break;
			  }
		  }
		return uniqueNumber;
	  }
  }
