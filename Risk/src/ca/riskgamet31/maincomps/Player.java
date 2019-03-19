package ca.riskgamet31.maincomps;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.riskgamet31.controllers.GameMainDriver;
import ca.riskgamet31.exceptions.InvalidNameException;
import ca.riskgamet31.exceptions.InvalidPlayerNameException;

/**
 * main Players class
 * 
 * @author YD
 * @version 1.0
 * @since 1.0
 */
public class Player
  {
	/**
	 * Player's Name
	 */
	private final String playersName;
	/**
	 * Armies that player contains for reinforcement phase
	 */
	private int army;
	/**
	 * Player's hand to hold cards
	 */
	private Hand hand;
	
	/**
	 * Player Graph
	 */
	private Graph playerCountryGraph;
	/**
	 * Turn In count during game
	 */
	public static int turnInCardsCount; 
	/**
	 * Constructs a new Player object
	 * 
	 * @param playersName  Player's name
	 * @param army Armies that player contains for reinforcement phase
	 * @throws NullPointerException NullPointerException
	 * @throws InvalidNameException InvalidNameException
	 * @throws InvalidPlayerNameException InvalidPlayerNameException
	 */
	public Player(String playersName, int army) throws NullPointerException,
	    InvalidNameException, InvalidPlayerNameException
	  {
		if (playersName == null)
		  throw new NullPointerException("Null Player name");
		validateInput(playersName);
		this.playersName = playersName;
		this.army = army;
		playerCountryGraph = new Graph();
		hand = new Hand();
	  }
	  
	/**
	 * validate the input
	 * 
	 * @param playersName name of the player
	 * @throws InvalidNameException InvalidNameException
	 * @throws InvalidPlayerNameException InvalidPlayerNameException
	 */
	public void validateInput(String playersName) throws InvalidNameException,
	    InvalidPlayerNameException
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
	public String getplayerName()
	  {
		return playersName;
	  }
	  
	/**
	 * To add country GraphNode to player's graph
	 * 
	 * @param country graph node
	 */
	public void addCountry(GraphNode country)
	  {
		this.playerCountryGraph.addNode(country);
	  }
	  
	/**
	 * to get the player graph
	 * 
	 * @return player graph nodes
	 */
	public ArrayList<GraphNode> getCountry()
	  {
		return playerCountryGraph.getGraphNodes();
	  }
	  
	/**
	 * To remove country from player's graph
	 * 
	 * @param country graph node
	 */
	public void removeCountry(GraphNode country)
	  {
		this.playerCountryGraph.removeNode(country);
		
	  }
	  
	
	/**
	 * To calculate the reinforcement armies
	 * @param gameMap game map
	 * @return  No. of reinforcement armies that player will get
	 */
	public int reinforcementArmiesCalc(GameMap gameMap)
	  {
		int armiesForCountries = 0;
		int armiesForContinentsBonus = 0;
		int armiesForCards = 0;
		int totalArmiesToAdd = 0;
		armiesForCountries = this.getCountry().size() / 3;
		armiesForContinentsBonus = gameMap.bonusArmiesForPlayer(this
		    .getplayerName());
		totalArmiesToAdd = armiesForCountries + armiesForContinentsBonus + armiesForCards;
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
	public void setArmies(int a)
	  {
		this.army = a;
	  }
	  
	/**
	 * To decrement reinforcement armies in player's graph
	 * 
	 * @param a No. of armies to be decremented
	 */
	public void decrementArmies(int a)
	  {
		this.army -= a;
	  }
	  
	/**
	 * to get players armies
	 * 
	 * @return number of player armies
	 */
	public int getPlayerArmies()
	  {
		return this.army;
	  }
	  
	/**
	 * to get player turn in cards
	 * 
	 * @return player turn in cards
	 */
	public int getTurnInCards()
	  {
		turnInCardsCount++;
		return turnInCardsCount;
	  }
	  
	/**
	 * To add a new card to player's hand
	 * 
	 * @param card card object
	 */
	public void addNewCard(Card card)
	  {
		hand.addCard(card);
	  }
	  
	/**
	 * to get player cards
	 * 
	 * @return player cards
	 */
	public ArrayList<Card> getPlayerCards()
	  {
		return hand.getCardsFromHand();
	  }
	  
	/**
	 * To remove cards from player's hand
	 * 
	 * @param cardIndexes indexes of cards to be removed
	 */
	public void removeCards(int[] cardIndexes)
	  {
		hand.removeCardsFromHand(cardIndexes[0], cardIndexes[1], cardIndexes[2]);
	  }
	  
	/**
	 * To calculate the reinforcement armies by turning in cards
	 * 
	 * @return reinforcement armies by turning in cards value
	 */
	public int turnInCardsArmies()
	  {
		int turnInCardsArmiesCount = turnInCardsCount * 5;
		return turnInCardsArmiesCount;
	  }
	  
	/**
	 * get player graph
	 * 
	 * @return player graph
	 */
	public Graph getPlayerGraph()
	  {
		return this.playerCountryGraph;
	  }
	  
	/**
	 * Fortification phase starting method according to user's entered armies,
	 * fortification will be performed
	 * 
	 */
	public void fortification()
	  {
		System.out.println(this.getplayerName() + " fortification...");
		this.getPlayerGraph().viewGraph();
		Scanner s = new Scanner(System.in);
		Graph mp = this.getPlayerGraph();
		String toCountry, fromCountry;
		int noOfArmies = 0;
		boolean path = false;
		String tempText = "";
		Country fromCountryobj = new Country("dumy");
		Country toCountryobj = new Country("dumy");
		do
		  {
			
			// get base country
			boolean valid = false;
			do
			  {
				System.out.println("enter base country:");
				
				do
				  {
					if (s.hasNext())
					  tempText = s.next().trim().toUpperCase();
				  } while (tempText.length() == 0);
				  
				final String fromcountry1 = tempText;
				fromCountry = fromcountry1;
				if (this.getPlayerGraph().getGraphNodes().stream().map((x) -> x
				    .getNodeData().getCountryName()).anyMatch((x) -> x
				        .equals(fromcountry1)))
				  valid = true;
				else
				  System.out.println("check country name and ownership");
			  } while (!valid);
			  
			// get destination country
			tempText = "";
			valid = false;
			do
			  {
				System.out.println("enter destination country:");
				// test
				do
				  {
					if (s.hasNextLine())
					  tempText = s.nextLine().trim().toUpperCase();
				  } while (tempText.length() == 0);
				  
				final String toCountry1 = tempText;
				toCountry = toCountry1;
				
				if ((this.getPlayerGraph().getGraphNodes().stream().map((x) -> x
				    .getNodeData().getCountryName()).anyMatch((x) -> x
				        .equals(toCountry1))) && !toCountry.equals(fromCountry))
				  {
					valid = true;
				  } else
				  System.out.println("check country name and ownership");
			  } while (!valid);
			  
			tempText = "";
			// get number of armies
			
			boolean armiesNotInt = true;
			do
			  {
				System.out.println("enter number of Armies:");
				do
				  {
					if (s.hasNextLine())
					  tempText = s.nextLine();
				  } while (tempText.length() == 0);
				  
				String nOArmies = tempText;
				
				if (nOArmies.matches("\\d+"))
				  {
					armiesNotInt = false;
					
					noOfArmies = Integer.parseInt(nOArmies);
				  } else
				  System.out.println("please enter valid input");
			  } while (armiesNotInt);
			  
			for (GraphNode gNode : this.getPlayerGraph().getGraphNodes())
			  {
				
				if ((gNode.getNodeData().getCountryName().equals(fromCountry)))
				  fromCountryobj = gNode.getNodeData();
			  }
			  
			if (this.getPlayerGraph().findPath(fromCountry, toCountry))
			  path = true;
			else
			  System.out.println("there is no path between base and distination countries.");
			
			if (noOfArmies >= fromCountryobj.getArmies())
			  System.out.println("not enough armies to move");
			if (toCountry.equals(fromCountry))
			  System.out
			      .println("source and destination countries should be different");
			
		  } while (!path || noOfArmies >= fromCountryobj
		      .getArmies() || toCountry.equals(fromCountry));
		  
		for (GraphNode gNode : this.getPlayerGraph().getGraphNodes())
		  {
			
			if ((gNode.getNodeData().getCountryName().equals(toCountry)))
			  toCountryobj = gNode.getNodeData();
		  }
		  
		fromCountryobj.reduceArmies(noOfArmies);
		toCountryobj.increaseArmies(noOfArmies);
		
	  }
	  
	/**
	 * Distribute the armies of every player among the countries the player
	 * owns. The method executes till all the player's armies are distributed.
	 * 
	 */
	public void reinforcement()
	  {
		Scanner scan = new Scanner(System.in);
		
		System.out.println();
		System.out.println("Assigning armies for Player " + this
		    .getplayerName());
		while (this.getPlayerArmies() != 0)
		  {
			System.out.println();
			System.out.println("Number of armies left..." + this
			    .getPlayerArmies());
			ArrayList<GraphNode> country_nodes = this.getCountry();
			HashSet<String> owned_by_player = new HashSet<String>();
			for (GraphNode node : country_nodes)
			  {
				owned_by_player.add(node.getNodeData().getCountryName());
				
				System.out.println(node.toString());
			  }
			System.out.println("Enter the country name");
			String country_name = scan.next().trim().toUpperCase();
			
			if (!country_name.equals(null))
			  {
				if (owned_by_player.contains(country_name))
				  {
					
					int armies = 0;
					boolean validInput = false;
					String text = "";
					do
					  {
						
						System.out
						    .println("Enter the number of armies to place..");
						do
						  {
							if (scan.hasNextLine())
							  text = scan.nextLine();
						  } while (text.length() == 0);
						  
						if (text.matches("\\d+"))
						  validInput = true;
						
					  } while (!validInput);
					armies = Integer.parseInt(text);
					
					System.out.println(armies);
					if (armies <= this.getPlayerArmies())
					  {
						for (GraphNode node : country_nodes)
						  {
							
							if (node.getNodeData().getCountryName()
							    .equals(country_name.trim()))
							  {
								node.getNodeData().setArmies(node.getNodeData()
								    .getArmies() + armies);
								this.decrementArmies(armies);
							  }
						  }
					  } else
					  {
						System.out.println("No sufficient armies");
					  }
				  } else
				  {
					System.out.println("Country not found...try again");
				  }
			  } else
			  {
				System.out.println("null value...try again");
			  }
		  }
		  
	  }
	
	/**
	 * attack phase starting method
	 * @param driver main driver of the game
	 * @return true if conquered the country.
	 */
	public boolean attack(GameMainDriver driver) // we will need gameMap to check adgecency between two countries owened by two different players
	{
		Scanner s = new Scanner(System.in);
		String attackerCountry, defenderCountry;
		boolean path = false;
		String tempText = "";
		int noOfDicesForAttacker = 0;
		int noOfDicesForDefender = 0;
		int[] attackerRolls;
		int[] defenderRolls;
		int attackerLosses = 0;
		int defenderLosses = 0;
		Country attackerCountryobj = new Country("dumy");
		Country defenderCountryobj = new Country("dumy");
		GraphNode attackerCountryNode = new GraphNode(attackerCountryobj);
		GraphNode defenderCountryNode = new GraphNode(defenderCountryobj);
		Player defenderObj = null;
		Dice dice = new Dice();
		this.getPlayerGraph().viewGraph();
		int min = 0, max = 0;
		int transferArmies = 0;
		boolean won = false;
		int attackerArmiesToSet = 0, defenderArmiesToSet = 0;
		do
		  {
			
			// get base country
			boolean valid = false;
			do
			  {
				System.out.println("enter attacker country:");
				
				do
				  {
					if (s.hasNext())
					  tempText = s.next().trim().toUpperCase();
				  } while (tempText.length() == 0);
				  
				final String fromcountry1 = tempText;
				attackerCountry = fromcountry1;
				if (this.getPlayerGraph().getGraphNodes().stream().map((x) -> x
				    .getNodeData().getCountryName()).anyMatch((x) -> x
				        .equals(fromcountry1)))
				  valid = true;
				else
				  System.out.println("check country name and ownership");
				
			  } while (!valid);
			  
			// get destination country
			tempText = "";
			valid = false;
			do
			  {
				System.out.println("enter defender country:");
				// test
				do
				  {
					if (s.hasNextLine())
					  tempText = s.nextLine().trim().toUpperCase();
				  } while (tempText.length() == 0);
				  
				final String toCountry1 = tempText;
				defenderCountry = toCountry1;
				
				if ((this.getPlayerGraph().getGraphNodes().stream().map((x) -> x
				    .getNodeData().getCountryName()).anyMatch((x) -> x
				        .equals(toCountry1))) && !defenderCountry.equals(attackerCountry))
				  {
					System.out.println("both countries should not be owned by a same player");
				  } else
				  {
					  valid = true;
					  
				  }
			  } while (!valid);
			
			for (GraphNode gNode : this.getPlayerGraph().getGraphNodes())
			  {
				
				if ((gNode.getNodeData().getCountryName().equals(attackerCountry)))
					attackerCountryNode = gNode;
			  }
			
			for (GraphNode gNode : driver.getGameMap().getGameMapGraph().getGraphNodes())
			  {
				
				if ((gNode.getNodeData().getCountryName().equals(defenderCountry)))
					defenderCountryNode = gNode;
			  }
			if (attackerCountryNode.getNodeNeighbors().contains(defenderCountryNode))
			{
				if(attackerCountryNode.getNodeData().getArmies() < 2)
				{
					System.out.println("attacker country should have atleast more than one army to attack");
					path = false;
				}
				else
				  path = true;
			}
				else
				  System.out.println("there is no path between base and distination countries.");
			
			
		  }while(!path || attackerCountry.equals(defenderCountry));
			//make country objs from country names
			
			
			//to get dice input for attacker
			noOfDicesForAttacker = getDiceInput(attackerCountryNode.getNodeData(), "a");
			attackerRolls = dice.roll(noOfDicesForAttacker);
			//to get dice input for defender
			noOfDicesForDefender = getDiceInput(defenderCountryNode.getNodeData(), "d");
			defenderRolls = dice.roll(noOfDicesForDefender);
				
				if (attackerRolls[0] > defenderRolls[0]) {
					defenderLosses++;
				}
				else if (attackerRolls[0] <= defenderRolls[0]) {
					attackerLosses++;
				}
				// Index 1 = second highest pair
				if (noOfDicesForAttacker > 1 && noOfDicesForDefender > 1) {
				
					if (attackerRolls[1] > defenderRolls[1]) {
						defenderLosses++;
						
					} else if (attackerRolls[1] <= defenderRolls[1]) {
						attackerLosses++;
					}
				}
				// Calculate losses
				System.out.println("<COMBAT REPORT>");
				attackerArmiesToSet = attackerCountryNode.getNodeData().getArmies() - attackerLosses;
				defenderArmiesToSet = defenderCountryNode.getNodeData().getArmies() - defenderLosses;
				if(attackerArmiesToSet >= 0 && defenderArmiesToSet >= 0)
				{
					attackerCountryNode.getNodeData().setArmies(attackerArmiesToSet);
					defenderCountryNode.getNodeData().setArmies(defenderArmiesToSet);
				}
				//attackerCountryNode.getNodeData().reduceArmies(attackerLosses);
				//defenderCountryNode.getNodeData().reduceArmies(defenderLosses);
				//attackerCountryNode.getNodeData().increaseArmies(defenderLosses);
				//defenderCountryNode.getNodeData().increaseArmies(attackerLosses);
				for(Player defender : driver.getPlayerList().getPlayerList())
				{
					if(defender.getplayerName().equals(defenderCountryNode.getNodeData().getCurrentOccupier()))
					{
						defenderObj = defender;
					}
				}
				//attackerCountryNode.getNodeData().getCurrentOccupier()
				
				if(defenderCountryNode.getNodeData().getArmies() < 1 && defenderObj != null)
				{
				 //remove country from defender player's list
				//add country to attacker's list
				//if defender has no country left then remove that palyer from players list
				//set occupier name fot defender country as attacker name
					defenderObj.removeCountry(defenderCountryNode);
					this.addCountry(defenderCountryNode);
					defenderCountryNode.getNodeData().setCurrentOccupier(this.getplayerName());
					//transfer armies min and max
					min = noOfDicesForAttacker;
					max = attackerCountryNode.getNodeData().getArmies() - 1;
					boolean transferArmiesValid = false;
					boolean isInt = false;
					if(min > max)
					{
						int temp = min;
						min = max;
						max = temp;
					}
					System.out.println("You must transfer " + min + " to " + max + " armies to your conqured territory");
					do
					{
						do
						{
						System.out.println("enter number of armies to transfer");
						//transferArmies = s.nextInt();
						//tempText = "";
						do
						  {
							if (s.hasNextLine())
							  tempText = s.next();
						  } while (tempText.length() == 0);
						  
						String trArmies = tempText;
						
						if (trArmies.matches("\\d+"))
						  {
							isInt = true;
							
							transferArmies = Integer.parseInt(trArmies);
						  } else
						  System.out.println("please enter valid input");
						}while(!isInt);
						if(transferArmies < min || transferArmies > max)
							System.out.println("Please enter input from valid range");
						else
							transferArmiesValid = true;
						
					}while(!transferArmiesValid);
					attackerCountryNode.getNodeData().reduceArmies(transferArmies);
					defenderCountryNode.getNodeData().increaseArmies(transferArmies);
					won = true;
				}
				
				if(defenderObj.getCountry().size() == 0)
				{
					for(Card card : defenderObj.hand.getCardsFromHand())
					{
						this.addNewCard(card);
						defenderObj.getPlayerCards().remove(card);
					}
				//---------	//defenderObj.hand.getCardsFromHand();
					driver.getPlayerList().getPlayerList().remove(defenderObj);
					
					//------------- if attacker has more than 5 cards then he has to draw cards immediately
				}
				
		  return won;
	}
	
	/**
	 * To get the dice input after rolling it
	 * @param countryObj country of attacker of defender
	 * @param i identifier that whether dice is rolling for attacker or defender
	 * @return output of rolled dice input
	 */
	public int getDiceInput(Country countryObj, String i)
	{
		Scanner s = new Scanner(System.in);
		int noOfDices = 0;
		String tempText = "";
		boolean dicesNotInt = true;
		boolean diceCountValid = true;
		int maxDice = 0;
		if(i.equals("a"))
			maxDice = 3;
		else
			maxDice = 2;
		do {
		do
		  {
			if(i.equals("a"))
			System.out.println("enter number of dices to roll for attacker:");
			else
			System.out.println("enter number of dices to roll for defender:");
			
			do
			  {
				if (s.hasNextLine())
				  tempText = s.next();
			  } while (tempText.length() == 0);
			  
			String nODices = tempText;
			
			if (nODices.matches("\\d+"))
			  {
				dicesNotInt = false;
				
				noOfDices = Integer.parseInt(nODices);
			  } else
			  System.out.println("please enter valid input");
		  }while(dicesNotInt);
		
		//check that number of dices should be between 1 to 3;
			
			if(noOfDices  < 1 || noOfDices > maxDice || noOfDices > countryObj.getArmies())
			{
				System.out.println("number of dices should be between 1 to 3 or number of dices should be less than attacker country armies");
			}
			else
			{
				diceCountValid = false;
			}
		  } while (diceCountValid);
			//attackerRolls = dice.roll(noOfDices);
			return noOfDices;
	}
	  
  }
