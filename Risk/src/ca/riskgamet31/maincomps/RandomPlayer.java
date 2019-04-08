package ca.riskgamet31.maincomps;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ca.riskgamet31.controllers.GameMainDriver;
import ca.riskgamet31.controllers.MainDriver;
import ca.riskgamet31.exceptions.InvalidNameException;
import ca.riskgamet31.exceptions.InvalidPlayerNameException;
import ca.riskgamet31.utility.Constants;

/**
 * main Players class
 * 
 * @author YD
 * @version 1.0
 * @since 1.0
 */
public class RandomPlayer implements Player
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
	 * Constructs a new Player object
	 * 
	 * @param playersName Player's name
	 * @param army        Armies that player contains for reinforcement phase
	 * @throws NullPointerException       NullPointerException
	 * @throws InvalidNameException       InvalidNameException
	 * @throws InvalidPlayerNameException InvalidPlayerNameException
	 */
	
	public RandomPlayer(String playersName, int army) throws
	    InvalidNameException, InvalidPlayerNameException
	  {
		this.playersName = playersName;
		this.army = army;
		playerCountryGraph = new Graph();
		hand = new Hand();
	  }
	  
	
	  
	/**
	 * to get the name of the player
	 * 
	 * @return player name
	 */
	@Override
	public String getplayerName()
	  {
		return playersName;
	  }
	  
	/**
	 * To add country GraphNode to player's graph
	 * 
	 * @param country graph node
	 */
	@Override
	public void addCountry(GraphNode country)
	  {
		this.playerCountryGraph.addNode(country);
	  }
	  
	/**
	 * to get the player graph
	 * 
	 * @return player graph nodes
	 */
	@Override
	public ArrayList<GraphNode> getPlayerCountriesGNodes()
	  {
		return playerCountryGraph.getGraphNodes();
	  }
	  
	/**
	 * To remove country from player's graph
	 * 
	 * @param country graph node
	 */
	@Override
	public void removeCountry(GraphNode country)
	  {
		this.playerCountryGraph.removeNode(country);
		
	  }
	  

	  
	/**
	 * To increment reinforcement armies in player's graph
	 * 
	 * @param a No. of armies to be incremented
	 */
	@Override
	public void setArmies(int a)
	  {
		this.army = a;
	  }
	  
	/**
	 * To decrement reinforcement armies in player's graph
	 * 
	 * @param a No. of armies to be decremented
	 */
	@Override
	public void decrementArmies(int a)
	  {
		this.army -= a;
	  }
	  
	/**
	 * to get players armies
	 * 
	 * @return number of player armies
	 */
	@Override
	public int getPlayerArmies()
	  {
		return this.army;
	  }
	  
	  
	/**
	 * To add a new card to player's hand
	 * 
	 * @param card card object
	 */
	@Override
	public void addNewCard(Card card)
	  {
		hand.addCard(card);
	  }
	  
	/**
	 * to get player cards
	 * 
	 * @return player cards
	 */
	@Override
	public ArrayList<Card> getPlayerCards()
	  {
		return hand.getCardsFromHand();
	  }
	  
	/**
	 * returns player's hand.
	 * 
	 * @return player's hand.
	 */
	@Override
	public Hand getHand()
	  {
		
		return this.hand;
	  }
	  
	/**
	 * To remove cards from player's hand
	 * 
	 * @param cardIndexes indexes of cards to be removed
	 */
	@Override
	public void removeCards(int[] cardIndexes)
	  {
		hand.removeCardsFromHand(cardIndexes[0], cardIndexes[1], cardIndexes[2]);
	  }
	  
	
	  
	/**
	 * get player graph
	 * 
	 * @return player graph
	 */
	@Override
	public Graph getPlayerGraph()
	  {
		return this.playerCountryGraph;
	  }
	  
	@Override
	public ArrayList<GraphNode> canFortify(){
	  
	  ArrayList<GraphNode> returnedList = new ArrayList<>();
	  
	  List<GraphNode> destinationCountries = new ArrayList<>(this.getPlayerGraph().getGraphNodes()
		  .stream().filter(i -> i.getNodeNeighbors().stream().anyMatch(x -> !x.getNodeData().getCurrentOccupier().equals(this.getplayerName())) )
		  .collect(Collectors.toList())); 
	  
	  
	  List<GraphNode> sourceCountries = new ArrayList<>(this.getPlayerGraph().getGraphNodes()
		  .stream().filter( i -> i.getNodeData().getArmies() > 1).collect(Collectors.toList()));
	  
	  
	  boolean continueSearch = true;
	  
	  ArrayList< ArrayList<GraphNode>> potintialList = new ArrayList<>();
	  
	  
	  for (GraphNode desNode : destinationCountries) {
		
		for (GraphNode sourceNode : sourceCountries)
		  {
			if (!sourceNode.equals(desNode)) {
			  
			if (this.getPlayerGraph().findPath(sourceNode.getNodeData().getCountryName(), desNode.getNodeData().getCountryName())) 
			  {
				ArrayList<GraphNode> pairNodes = new ArrayList<>();
				pairNodes.add(sourceNode);
				pairNodes.add(desNode);
			    potintialList.add(pairNodes); 
			}
			}
		  }
	  }
	  
	  Collections.shuffle(potintialList);
	  
	   if (!potintialList.isEmpty())
		 returnedList =  potintialList.get(0);
	  return returnedList;
	  
	}
	
	@Override
	public void fortification() {
	  
	  ArrayList<GraphNode> fortifyNodes = canFortify();
	  if (fortifyNodes.size() == 2)
		  {
				executeFortification(fortifyNodes);
			  
		  } else
		  {
			
			System.out.println(this
			    .getplayerName() + " can't fortify");
			
		  }
	  
	  
	}
	
	/**
	 * Fortification phase starting method according to user's entered armies,
	 * fortification will be performed
	 * 
	 */
	@Override
	public void executeFortification(ArrayList<GraphNode> fortifyNodes)
	  {
		this.getPlayerGraph().viewGraph();
		SecureRandom random = new SecureRandom();
		int noOfArmies = random.nextInt(fortifyNodes.get(0).getNodeData().getArmies()-1)+1;
			  
			fortifyNodes.get(0).getNodeData().reduceArmies(noOfArmies);
			fortifyNodes.get(1).getNodeData().increaseArmies(noOfArmies);
		  
	  }
	  
	/**
	 * Distribute the armies of every player among the countries the player
	 * owns. The method executes till all the player's armies are distributed.
	 * 
	 */
	@Override
	public void reinforcement()
	  {
		GraphNode reinforceCountry = canReinforce();
		
		System.out.println();
		System.out.println("Assigning armies for Player " + this.getplayerName());
		System.out.println("Number of armies left..." + this.getPlayerArmies());
		reinforceCountry.getNodeData().setArmies(reinforceCountry.getNodeData()
								      .getArmies() + this.getPlayerArmies());
		this.decrementArmies(this.getPlayerArmies());
			
		  
	  }
	  
	/**
	 * Utility method to be used during attack
	 * 
	 * @param driver main game driver
	 * @return both attacking and target countries graph nodes.
	 */
	@Override
	public ArrayList<GraphNode> AttDefCountries(MainDriver driver)
	  {
		ArrayList<GraphNode> dummyList = new ArrayList<>();
		return dummyList;
	  }
	  
	/**
	 * main attack method
	 * 
	 * @param driver main game driver
	 * @return true if attacker won at least one country
	 */
	@Override
	public boolean attack(MainDriver driver)
	  {
		boolean won = false;
		SecureRandom random = new SecureRandom();
		int noOfAttacks = random.nextInt(10);
		
		ArrayList<GraphNode> attDef = new ArrayList<>();
		
		attDef = canAttack();
		int attackedAlready = 0;
		while (attDef.size() == 2  && attackedAlready++ < noOfAttacks && attDef.get(0).getNodeData().getArmies() >1) {
				
		  System.out.println("RANDON bug:"+attDef.get(0)+" "+attDef.get(1));
		  boolean wonRound = this
				    .attackRound(driver, attDef.get(0), attDef.get(1), true);
				attDef.clear();
				attDef = canAttack();
				if (wonRound)
				  won = true;
		}
		
		int noOfPlayers = driver.getPlayerList().getPlayerList().size();
		
		if (noOfPlayers > 1)
			  {
				System.out.println(this
				    .getplayerName() + " choose not to attack any more in this turn or can't attack.");
				this.getPlayerGraph().viewGraph();
			  }
		  
		  
		return won;
	  }
	  
	/**
	 * a method to handle individual attack rounds
	 * 
	 * @param driver              main game driver
	 * @param attackerCountryNode attacking country graph node
	 * @param defenderCountryNode defending country graph node
	 * @param allOut              all-Out option
	 * @return true if attacker occupied the attacked country.
	 */
	@Override
	public boolean attackRound(MainDriver driver,
	    GraphNode attackerCountryNode, GraphNode defenderCountryNode,
	    boolean allOut)
	  {
		String tempText = "";
		int noOfDicesForAttacker = 0;
		int noOfDicesForDefender = 0;
		int[] attackerRolls;
		int[] defenderRolls;
		int attackerLosses = 0;
		int defenderLosses = 0;
		Player defenderObj = null;
		Dice dice = new Dice();
		boolean won = false;
		boolean exitAttack = false;
		do
		  {
			
			int min = 0, max = 0;
			int transferArmies = 0;
			int attackerArmiesToSet = 0, defenderArmiesToSet = 0;
			
			  
				noOfDicesForAttacker = getDiceInputAllOut(attackerCountryNode
				    .getNodeData(), "a");
				
				noOfDicesForDefender = getDiceInputAllOut(defenderCountryNode
				    .getNodeData(), "d");
			  
			  
			attackerRolls = dice.roll(noOfDicesForAttacker);
			defenderRolls = dice.roll(noOfDicesForDefender);
			
			defenderLosses = 0;
			attackerLosses = 0;
			
			if (attackerRolls[0] > defenderRolls[0])
			  {
				defenderLosses++;
			  } else
			  {
				attackerLosses++;
			  }
			
			if (noOfDicesForAttacker > 1 && noOfDicesForDefender > 1)
			  {
				
				if (attackerRolls[1] > defenderRolls[1])
				  {
					defenderLosses++;
					
				  } else
				  {
					attackerLosses++;
				  }
			  }
			
			System.out.println("");
			System.out.println("<Combat Result>" + "|" + attackerCountryNode
			    .getNodeData()
			    .getArmies() + "-" + attackerLosses + ":" + defenderCountryNode
			        .getNodeData().getArmies() + "-" + defenderLosses + "|");
			System.out.println("Attacker dices" + Arrays
			    .toString(attackerRolls));
			System.out.println("Defender dices" + Arrays
			    .toString(defenderRolls));
			
			attackerArmiesToSet = attackerCountryNode.getNodeData()
			    .getArmies() - attackerLosses;
			defenderArmiesToSet = defenderCountryNode.getNodeData()
			    .getArmies() - defenderLosses;
			
			attackerCountryNode.getNodeData().setArmies(attackerArmiesToSet);
			defenderCountryNode.getNodeData().setArmies(defenderArmiesToSet);
			
			if (attackerArmiesToSet <= 1 || defenderArmiesToSet == 0)
			  exitAttack = true;
			
			for (Player defender : driver.getPlayerList().getPlayerList())
			  {
				if (defender.getplayerName().equals(defenderCountryNode
				    .getNodeData().getCurrentOccupier()))
				  {
					defenderObj = defender;
				  }
			  }
			  
			if (defenderCountryNode.getNodeData()
			    .getArmies() < 1 && defenderObj != null)
			  {
				defenderObj.removeCountry(defenderCountryNode);
				this.addCountry(defenderCountryNode);
				defenderCountryNode.getNodeData().setCurrentOccupier(this
				    .getplayerName());
				min = noOfDicesForAttacker;
				max = attackerCountryNode.getNodeData().getArmies() - 1;
				boolean transferArmiesValid = false;
				boolean isInt = false;
				if (min > max)
				  {
					int temp = min;
					min = max;
					max = temp;
				  }
				System.out
				    .println("You must transfer " + min + " to " + max + " armies to your conqured territory");
				  transferArmies = max;
				attackerCountryNode.getNodeData().reduceArmies(transferArmies);
				defenderCountryNode.getNodeData()
				    .increaseArmies(transferArmies);
				won = true;
			  }
			  
			if (defenderObj.getPlayerCountriesGNodes().size() == 0)
			  {
				
				System.out.println(this
				    .getplayerName() + " will receive - " + defenderObj.getHand()
				        .getCardsFromHand()
				        .size() + " - cards from player " + defenderObj
				            .getplayerName());
				
				for (Card card : defenderObj.getHand().getCardsFromHand())
				  {
					this.addNewCard(card);
				  }
				  
				driver.getPlayerList().getPlayerList().remove(defenderObj);
				System.out.println("Player " + defenderObj
				    .getplayerName() + " is removed from game.");
				
			  }
			  
		  } while (allOut && !exitAttack);
		  
		return won;
	  }
	
	  /**
		 * to execute exchange of cards
		 * @param gameMainDriver current gameMainDriver of the game
		 * @param request selected cards by the player
		 * @return true if exchange been successful
		 */
		
	public boolean executeTurnInCard(MainDriver gameMainDriver,
		    String request)
		  {
			      
			Map<String,List<Card>> handsCards = this.getHand().getCardsFromHand().stream()
				.collect(Collectors.groupingBy(Card::getCardType,Collectors.toList()));
			  
			if (handsCards.size() == 3) {
			 
			  for (List<Card> listOfCards : handsCards.values()) {
				  
				this.getHand().getCardsFromHand().remove(listOfCards.get(0));
				}
			  
			}else {
			  
			  boolean continueDelete = true;
			  
			  for (List<Card> listOfCardsPerType : handsCards.values()) {
				  
				if (listOfCardsPerType.size() >= 3 && continueDelete) {
				  
				  this.getHand().getCardsFromHand().remove(listOfCardsPerType.get(0));
				  this.getHand().getCardsFromHand().remove(listOfCardsPerType.get(1));
				  this.getHand().getCardsFromHand().remove(listOfCardsPerType.get(2));
				  continueDelete = false;
				}
				}
			  
			}
					
					this.setArmies(this
					    .getPlayerArmies() + (Constants.turnInCards++ * 5));
					System.out.println("Now " + this
					    .getplayerName() + ": is eligible for " + this
					        .getPlayerArmies() + " armies");
					System.out.println("Current player's hand has " + hand
					    .getCardsFromHand());
			  
			return true;
		  }

	
	@Override
	public ArrayList<GraphNode> canAttack()
	  {
		
		ArrayList<GraphNode> adNodes = new ArrayList<>();
		
		List<GraphNode> destinationCountries = new ArrayList<>(this
		    .getPlayerGraph().getGraphNodes().stream().filter(i -> i
		        .getNodeData().getArmies() > 1).collect(Collectors.toList()));
	
		Collections.shuffle(destinationCountries);
		
		boolean continueSearch = true;
		for (GraphNode countryNode : destinationCountries)
		  {
			if (continueSearch)
			  {
				
				GraphNode destCountry = countryNode.getNodeNeighbors().stream()
				    .filter(y -> !(y.getNodeData().getCurrentOccupier()
				        .equals(this.getplayerName()))).findFirst()
				    .orElse(new GraphNode(new Country("DUMMY")));
				
				if (!destCountry.getNodeData().getCountryName().equals("DUMMY"))
				  {
					adNodes.add(countryNode);
					adNodes.add(destCountry);
					continueSearch = false;
					
				  }
			  }
		  }
		  
		return adNodes;
	  }

		/**
		 * To get the dice input after rolling it
		 * 
		 * @param countryObj country of attacker of defender
		 * @param i          identifier that whether dice is rolling for attacker or
		 *                   defender
		 * @return output of rolled dice input
		 */
		@Override
		public int getDiceInput(Country countryObj, String i)
		  {
					return 0;
		  }

		
	  @Override
	  public GraphNode canReinforce()
		{
		 
			
		  ArrayList<GraphNode> playerCountries = new ArrayList<>(this.getPlayerGraph().getGraphNodes());
		  
		  Collections.shuffle(playerCountries);
		  
		  return playerCountries.stream().findAny().orElse(new GraphNode(new Country("DUMMY")));
		}
	  
  }
