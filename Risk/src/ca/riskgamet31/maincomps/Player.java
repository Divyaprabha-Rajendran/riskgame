package ca.riskgamet31.maincomps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import ca.riskgamet31.mapdata.CreateMap;

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
	 * Processing list for the graph node used to check path
	 */
	private LinkedList<GraphNode> processingList;
	/**
	 * Player Graph
	 */
	private Graph playerCountryGraph;
	/**
	 * Turn In count during game
	 */
	public static int turnInCardsCount; // static because for every player object the count should be incremented
	
	/**
	 * Constructs a new Player object
	 * @param Player's name
	 * @param Armies that player contains for reinforcement phase
	 */
	public Player(String playersName,int army) throws NullPointerException
	{
		 if (playersName == null) throw new NullPointerException("Null Player name");
		 this.playersName=playersName;
		 this.army=army; 
		// this.countryNode=new ArrayList<GraphNode>(); 
		 //this.continent=new ArrayList<Continent>(); 
		 playerCountryGraph = new Graph();
		 hand = new Hand();
	}
	
	public String getplayerName() 
	{
		return playersName;
	}
	/**
	 * To add country GraphNode to player's graph
	 * @param Country graph node
	 */
	public void addCountry(GraphNode country)
	{
		this.playerCountryGraph.addNode(country);
	}
	
	public ArrayList<GraphNode> getCountry()
	{
		return playerCountryGraph.getGraphNodes();
	}
	/**
	 * To remove country from player's graph
	 * @param Country graph node
	 */
	public void removeCountry(GraphNode country)
	{
		this.playerCountryGraph.removeNode(country);
		
	}
  
//YD 
	/**
	 * To calculate the reinforcement armies
	 * @return No. of reinforcement armies that player will get
	 */
	public int reinforcementArmiesCalc()
	  {
		  int armiesForCountries = 0;
		  int armiesForContinentsBonus = 0;
		  int armiesForCards = 0;
		  int totalArmiesToAdd = 0;
		  GameMap gm = new GameMap();
		  armiesForCountries = this.getCountry().size() / 3;
		  armiesForContinentsBonus = gm.bonusArmiesForPlayer(this.getplayerName());
		  armiesForCards = this.turnInCardsArmies();
		  totalArmiesToAdd = armiesForCountries + armiesForContinentsBonus + armiesForCards;
		  if(totalArmiesToAdd < 3)
		  {
			  totalArmiesToAdd = 3;
		  }
		  this.incrementArmies(totalArmiesToAdd);
		  return totalArmiesToAdd;
	  }
  
  //YD 
	/**
	 * To increment reinforcemet armies in player's graph
	 * @param No. of armies to be incremented
	 */
  public void incrementArmies(int a)
  {
	  this.army += a; 
  }
  
  //YD
  	/**
	 * To decrement reinforcemet armies in player's graph
	 * @param No. of armies to be decremented
	 */
  public void decrementArmies(int a)
  {
	  this.army -= a; 
  }
  
  //YD
  public int getPlayerArmies()
  {
	  return this.army;
  }
  //YD
  public int getTurnInCards()
  {
	  turnInCardsCount++;
	  return turnInCardsCount;
  }
  //YD
  	/**
	 * To add a new card to player's hand
	 * @param card object
	 */
  public void addNewCard(Card card)
  {
	  hand.addCard(card);
  }
  //YD
  public ArrayList<Card> getPlayerCards()
  {
	  return hand.getCardsFromHand();
  }
  //YD
  	/**
	 * To remove cards from player's hand
	 * @param indicies of cards to be removed
	 */
  public void removeCards(int[] cardIndexes)
  {
	  hand.removeCardsFromHand(cardIndexes[0], cardIndexes[1], cardIndexes[2]);
  }
  //YD
  	/**
	 * To calculate the reinforcement armies by turning in cards
	 * @return reinforcement armies by turning in cards value
	 */
  public int turnInCardsArmies()
  {
	  int turnInCardsArmiesCount = turnInCardsCount * 5;
	  return turnInCardsArmiesCount;
  }
  //YD
  	/**
	 * Reinfrce phase starting method
	 * according to user's entered armies, reinforcement will be performed
	 * @param Name of country to reinforce
	 */
  public void reinforce(String countryName) {
		CreateMap mp = new CreateMap();
		Country co = mp.getCountryByName(countryName);
		if(co != null)
		{
			if(co.getCurrentOccupier().equalsIgnoreCase("dummy") || this.getplayerName().equals(co.getCurrentOccupier()))
			{
				int armiesToPut = 0;
				//rnd = new Random();
				this.reinforcementArmiesCalc();
				int reinforcementArmiesCount = this.getPlayerArmies();
				System.out.println("Total available reinforcements are " + reinforcementArmiesCount);
				System.out.println("How many armies do you want to put to reinforce country " + countryName);
				Scanner s = new Scanner(System.in);
				try {
					armiesToPut = Integer.parseInt(s.nextLine());
				}
				catch(NumberFormatException e)
				{
					System.out.println("Please, enter a valid input");
				}
				if(armiesToPut > reinforcementArmiesCount)
				{
					System.out.println("Input should be less than reinforcements you have");
				}
				else
				{
					this.decrementArmies(armiesToPut);
					co.increaseArmies(armiesToPut);
				}
			}
			else
			{
				System.out.println("Please choose correct input");
			}
		}
		else
		{
			System.out.println("Please choose correct input");
		}
	}
//YD
	/**
	 * Fortification phase starting method
	 * according to user's entered armies, fortification will be performrd
	 * @param Name of country, from which armies we need to fortify
	 * @param Name of country, to which armies we will fortify 
	 */
public void fortification(String countryOne, String countryTwo)
{
	  	CreateMap mp = new CreateMap();
		Country coOne = mp.getCountryByName(countryOne);
		Country coTwo = mp.getCountryByName(countryTwo);
		int armiesToTransfer = 0;
		if(coOne != null && coTwo != null)
		{
			if(this.getplayerName().equals(coOne.getCurrentOccupier()) && this.getplayerName().equals(coTwo.getCurrentOccupier()))
			{
				//check that both countries have same occupier
				if(this.findPath(countryOne, countryTwo))
				{
					System.out.println("Enter how many armies you want to fortify from " + countryOne + " to " + countryTwo);
					Scanner s = new Scanner(System.in);
					try {
						armiesToTransfer = Integer.parseInt(s.nextLine());
					}
					catch(NumberFormatException e)
					{
						System.out.println("Please, enter a valid input");
					}
					if(coOne.getArmies() >= armiesToTransfer)
					{
						coOne.reduceArmies(armiesToTransfer);
						coTwo.increaseArmies(armiesToTransfer);	
					}
					else
					{
						System.out.println("You do not have " + armiesToTransfer + " armies to transfer from " + countryOne + " to " + countryTwo);
					}
				}
				else
				{
					System.out.println("There is no valid path between " + countryOne + " to " + countryTwo);
				}
			}
			else
			{
				System.out.println("Both countries are not own by same player");
			}
		}
		else
		{
			System.out.println("Input is not valid");
		}
}
//YD
public LinkedList<GraphNode> getProcessingList()
{
	return processingList;
}
//YD
/**
 * To check that whether there is any path possible from given country to given country 
 * @param Country name from the path should be checked
 * @param Country name to the given country
 * @return whether that path is exist or not
 */
public boolean findPath(String fromCountry, String toCountry)
{
	  boolean pathExists = false;
		int tempdistance = 0;
		cleanProcessingData();
		Iterator<GraphNode> graphIterator = this.getCountry().iterator();
		GraphNode rootNode;
		GraphNode fromNode = null, toNode = null;
		while (graphIterator.hasNext())
		  {
			GraphNode tempNode = graphIterator.next();
			if (tempNode.getNodeData().getCountryName().equals(fromCountry.toUpperCase()))
			  fromNode = tempNode;
			if (tempNode.getNodeData().getCountryName().equals(toCountry.toUpperCase()))
			  toNode = tempNode;
		  }
		  
		if (fromNode == null || toNode == null)
		   return false;
		else if (fromNode.equals(toNode))
			  return true;
			else
			  {
				rootNode = fromNode;
				rootNode.setDistance(tempdistance++);
				this.getProcessingList().add(rootNode);
				
				while (!this.getProcessingList().isEmpty() && !pathExists)
				  {
					GraphNode node = this.getProcessingList().removeFirst();
					ArrayList<GraphNode> nodeNeighbors = node.getNodeNeighbors();
					
					for (GraphNode neighborC : nodeNeighbors)
					  {
						if (neighborC.getDistance() < 0)
						  {
							neighborC.setDistance(tempdistance);
							neighborC.setParentNode(node);
							this.getProcessingList().add(neighborC);
							if (neighborC.equals(toNode))
							  pathExists = true;
						  }
					  }
					  
				  }
				  
			  }
			return pathExists;
		  }
//YD
/**
 * to clear all graphnodes from processing list
 */
public void cleanProcessingData()
{
	
	this.getProcessingList().clear();
	
	for (GraphNode node : this.getCountry())
	  {
		node.setDistance(-1);
		node.setParentNode(null);
	  }
}  
}
