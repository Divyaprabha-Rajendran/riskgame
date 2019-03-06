package ca.riskgamet31.maincomps;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.InvalidNameException;

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
	public static int turnInCardsCount; // static because for every player
	                                    // object the count should be
	                                    // incremented
	
	/**
	 * Constructs a new Player object
	 * 
	 * @param        Player's name
	 * @param Armies that player contains for reinforcement phase
	 * @throws InvalidPlayerNameException
	 * @throws InvalidNameException
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
	 * @throws InvalidNameException
	 * @throws InvalidPlayerNameException
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
	 * @param Country graph node
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
	 * @param Country graph node
	 */
	public void removeCountry(GraphNode country)
	  {
		this.playerCountryGraph.removeNode(country);
		
	  }
	  
	// YD
	/**
	 * To calculate the reinforcement armies
	 * 
	 * @return No. of reinforcement armies that player will get
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
		// armiesForCards = this.turnInCardsArmies();
		totalArmiesToAdd = armiesForCountries + armiesForContinentsBonus + armiesForCards;
		if (totalArmiesToAdd < 3)
		  {
			totalArmiesToAdd = 3;
		  }
		this.setArmies(totalArmiesToAdd);
		return totalArmiesToAdd;
	  }
	  
	// YD
	/**
	 * To increment reinforcemet armies in player's graph
	 * 
	 * @param No. of armies to be incremented
	 */
	public void setArmies(int a)
	  {
		this.army = a;
	  }
	  
	/**
	 * To decrement reinforcemet armies in player's graph
	 * 
	 * @param No. of armies to be decremented
	 */
	public void decrementArmies(int a)
	  {
		this.army -= a;
	  }
	  
	/**
	 * to get palyers armies
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
	 * @param card object
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
	 * @param indicies of cards to be removed
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
	 * get palyer graph
	 * 
	 * @return palyer graph
	 */
	public Graph getPlayerGraph()
	  {
		return this.playerCountryGraph;
	  }
	  
	/**
	 * Fortification phase starting method according to user's entered armies,
	 * fortification will be performrd
	 * 
	 * @param Name of country, from which armies we need to fortify
	 * @param Name of country, to which armies we will fortify
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
			  System.out
			      .println("there is no path between base and distination countries.");
			
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
	 * @param players
	 */
	public void distributeArmies()
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
	  
  }
