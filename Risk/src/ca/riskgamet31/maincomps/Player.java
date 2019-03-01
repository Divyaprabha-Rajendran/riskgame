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
 * @author Chitra
 * @version 1.0
 * @since 1.0
 */
public class Player
{
   
	private final String playersName;
	private int army; //rem=name
	private Hand hand; // create a player graph..
	private LinkedList<GraphNode> processingList;
	private Graph playerCountryGraph;
	public static int turnInCardsCount; // static because for every player object the count should be incremented
	
	
	public Player(String playersName,int army) throws NullPointerException
	{
		 if (playersName == null) throw new NullPointerException("Null Player name");
		 this.playersName=playersName;
		 this.army=army; 
		// this.countryNode=new ArrayList<GraphNode>(); 
		 //this.continent=new ArrayList<Continent>(); 
		 hand = new Hand();
	}
	
	public String getplayerName() 
	{
		return playersName;
	}
		
	public void addCountry(GraphNode country)
	{
		this.playerCountryGraph.addNode(country);
	}
	
	public ArrayList<GraphNode> getCountry()
	{
		return playerCountryGraph.getGraphNodes();
	}
	
	public void removeCountry(GraphNode country)
	{
		this.playerCountryGraph.removeNode(country);
		
	}
  
//YD 
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
  public void incrementArmies(int a)
  {
	  this.army += a; 
  }
  
  //YD
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
  public void removeCards(int[] cardIndexes)
  {
	  hand.removeCardsFromHand(cardIndexes[0], cardIndexes[1], cardIndexes[2]);
  }
  //YD
  public int turnInCardsArmies()
  {
	  int turnInCardsArmiesCount = turnInCardsCount * 5;
	  return turnInCardsArmiesCount;
  }
  //YD
  public void reinforce(String countryName) {
		CreateMap mp = new CreateMap();
		Country co = mp.getCountryByName(countryName);
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
//YD
public void fortification(String countryOne, String countryTwo)
{
	  	CreateMap mp = new CreateMap();
		Country coOne = mp.getCountryByName(countryOne);
		Country coTwo = mp.getCountryByName(countryTwo);
		int armiesToTransfer = 0;
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
//YD
public LinkedList<GraphNode> getProcessingList()
{
	return processingList;
}
//YD
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
