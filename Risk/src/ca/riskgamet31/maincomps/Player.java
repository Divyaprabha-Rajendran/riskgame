package ca.riskgamet31.maincomps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.riskgamet31.controllers.MainDriver;
import ca.riskgamet31.exceptions.InvalidNameException;
import ca.riskgamet31.exceptions.InvalidPlayerNameException;

/**
 * player abstraction interface
 * 
 * @author Fareed Tayar
 * @version 3.0
 */
public interface Player extends java.io.Serializable
  {
	
	/**
	 * validate the input
	 * 
	 * @param playersName name of the player
	 * @throws InvalidNameException       InvalidNameException
	 * @throws InvalidPlayerNameException InvalidPlayerNameException
	 */
	default public void validateInput(String playersName)
	    throws InvalidNameException, InvalidPlayerNameException
	  {
		Pattern name_pattern = Pattern.compile("[^A-Za-z0-9]");
		Matcher match = name_pattern.matcher(playersName);
		if (match.find() == true)
		  {
			throw new InvalidPlayerNameException("Player name contains special characters " + playersName);
		  }
	  }
	  
	/**
	 * to get the name of the player
	 * 
	 * @return player name
	 */
	public String getplayerName();
	
	/**
	 * To add country GraphNode to player's graph
	 * 
	 * @param country graph node
	 */
	public void addCountry(GraphNode country);
	
	/**
	 * to get the player graph
	 * 
	 * @return player graph nodes
	 */
	public ArrayList<GraphNode> getPlayerCountriesGNodes();
	
	/**
	 * To remove country from player's graph
	 * 
	 * @param country graph node
	 */
	public void removeCountry(GraphNode country);
	
	/**
	 * To calculate the reinforcement armies
	 * 
	 * @param gameMap           game map
	 * @param armiescardsAmount amount of armies got by exchanging cards
	 * @return No. of reinforcement armies that player will get
	 */
	default public int reinforcementArmiesCalc(GameMap gameMap,
	    int armiescardsAmount)
	  {
		int armiesForCountries = 0;
		int armiesForContinentsBonus = 0;
		
		int totalArmiesToAdd = 0;
		armiesForCountries = this.getPlayerCountriesGNodes().size() / 3;
		armiesForContinentsBonus = gameMap.bonusArmiesForPlayer(this
		    .getplayerName());
		totalArmiesToAdd = armiesForCountries + armiesForContinentsBonus + armiescardsAmount;
		if (totalArmiesToAdd < 3)
		  {
			totalArmiesToAdd = 3;
		  }
		this.setArmies(totalArmiesToAdd);
		return totalArmiesToAdd;
	  }
	  
	/**
	 * To increment reinforcement armies in player's graph
	 * 
	 * @param a No. of armies to be incremented
	 */
	public void setArmies(int a);
	
	/**
	 * To decrement reinforcement armies in player's graph
	 * 
	 * @param a No. of armies to be decremented
	 */
	public void decrementArmies(int a);
	
	/**
	 * to get players armies
	 * 
	 * @return number of player armies
	 */
	public int getPlayerArmies();
	
	/**
	 * To add a new card to player's hand
	 * 
	 * @param card card object
	 */
	void addNewCard(Card card);
	
	/**
	 * utility method for fortification
	 * 
	 * @return array list of countries to fortify
	 */
	ArrayList<GraphNode> canFortify();
	
	/**
	 * utility method for attack
	 * 
	 * @return array list of countries to attack
	 */
	ArrayList<GraphNode> canAttack();
	
	/**
	 * exchange card method
	 * 
	 * @return true if exchange was successful
	 */
	boolean executeTurnInCard(MainDriver gmd, String request);
	
	/**
	 * to verify selected cards indexes are not the same.
	 * 
	 * @param request selected cards indexes array
	 * @return true if it is OK to continue with the exchange
	 */
	default public boolean checkUniqueNumbers(String[] request)
	  {
		boolean uniqueNumber = true;
		Arrays.sort(request);
		for (int i = 0; i < request.length - 1; i++)
		  {
			if (request[i].equals(request[i + 1]))
			  {
				uniqueNumber = false;
				break;
			  }
		  }
		return uniqueNumber;
	  }
	  
	/**
	 * utility method for reinforcement
	 * 
	 * @return best country to fortify based on palyer's strategy
	 */
	GraphNode canReinforce();
	
	/**
	 * to get player cards
	 * 
	 * @return player cards
	 */
	public ArrayList<Card> getPlayerCards();
	
	/**
	 * returns player's hand.
	 * 
	 * @return player's hand.
	 */
	public Hand getHand();
	
	/**
	 * To remove cards from player's hand
	 * 
	 * @param cardIndexes indexes of cards to be removed
	 */
	public void removeCards(int[] cardIndexes);
	
	/**
	 * get player graph
	 * 
	 * @return player graph
	 */
	public Graph getPlayerGraph();
	
	/**
	 * Fortification phase starting method according to user's entered armies,
	 * fortification will be performed
	 * 
	 */
	public void fortification();
	
	/**
	 * Distribute the armies of every player among the countries the player
	 * owns. The method executes till all the player's armies are distributed.
	 * 
	 */
	public void reinforcement();
	
	/**
	 * Utility method to be used during attack
	 * 
	 * @param driver main game driver
	 * @return both attacking and target countries graph nodes.
	 */
	public ArrayList<GraphNode> AttDefCountries(MainDriver driver);
	
	/**
	 * main attack method
	 * 
	 * @param driver main game driver
	 * @return true if attacker won at least one country
	 */
	public boolean attack(MainDriver driver);
	
	/**
	 * a method to handle individual attack rounds
	 * 
	 * @param driver              main game driver
	 * @param attackerCountryNode attacking country graph node
	 * @param defenderCountryNode defending country graph node
	 * @param allOut              all-Out option
	 * @return true if attacker occupied the attacked country.
	 */
	public boolean attackRound(MainDriver driver, GraphNode attackerCountryNode,
	    GraphNode defenderCountryNode, boolean allOut);
	
	/**
	 * To get the dice input after rolling it
	 * 
	 * @param countryObj country of attacker of defender
	 * @param i          identifier that whether dice is rolling for attacker or
	 *                   defender
	 * @return output of rolled dice input
	 */
	public int getDiceInput(Country countryObj, String i);
	
	/**
	 * To get the dice number for All-Out mode.
	 * 
	 * @param countryObj country of attacker of defender
	 * @param i          identifier that whether dice is rolling for attacker or
	 *                   defender
	 * @return output of rolled dice input
	 */
	default public int getDiceInputAllOut(Country countryObj, String i)
	  {
		
		int noOfDices = 0;
		
		if (i.equals("a"))
		  {
			noOfDices = countryObj.getArmies() - 1 >= 3 ? 3 : countryObj
			    .getArmies() - 1;
		  }
		  
		else
		  {
			noOfDices = countryObj.getArmies() >= 2 ? 2 : countryObj
			    .getArmies();
		  }
		  
		return noOfDices;
	  }
	  
	/**
	 * Distribute the armies of every player among the countries the player
	 * owns. The method executes till all the player's armies are distributed.
	 * 
	 */
	default public void initialdistributeArmies()
	  {
		
		System.out.println();
		this.getPlayerGraph().viewGraph();
		
		System.out.println("\nAssigning armies for Player " + this
		    .getplayerName() + " - Current Armies " + this
		        .getPlayerArmies() + "\n");
		
		while (this.getPlayerArmies() > 0)
		  {
			Iterator<GraphNode> playerGraphIterator = this
			    .getPlayerCountriesGNodes().iterator();
			
			while (playerGraphIterator.hasNext() && this.getPlayerArmies() > 0)
			  {
				
				playerGraphIterator.next().getNodeData().increaseArmies(1);
				this.decrementArmies(1);
			  }
			  
		  }
		  
		this.getPlayerGraph().viewGraph();
		System.out.println("");
		
	  }
	  
	/**
	 * utility method for fortification
	 * 
	 */
	public void executeFortification(ArrayList<GraphNode> fortifyNodes);
	
  }