package ca.riskgamet31.maincomps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
public class CheaterPlayer implements Player
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
	
	public CheaterPlayer(String playersName, int army) throws
	    InvalidNameException, InvalidPlayerNameException
	  {
		//validateInput(playersName);
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
	  
	  ArrayList<GraphNode> destinationCountries = new ArrayList<>(this.getPlayerGraph().getGraphNodes()
		  .stream().filter(i -> i.getNodeNeighbors().stream()
			  .anyMatch(x -> !x.getNodeData().getCurrentOccupier().equals(this.getplayerName())) )
		  .collect(Collectors.toList())); 
	  
	  
	  	  return destinationCountries;
	  
	}
	
	@Override
	public void fortification() {
	  
	  ArrayList<GraphNode> fortifyNodes = canFortify();
	  
	  for (GraphNode countryNode : fortifyNodes) {
				
		countryNode.getNodeData().increaseArmies(countryNode.getNodeData().getArmies());  
		
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
		  
	  }
	  
	/**
	 * Distribute the armies of every player among the countries the player
	 * owns. The method executes till all the player's armies are distributed.
	 * 
	 */
	@Override
	public void reinforcement()
	  {
		System.out.println("All Country armies will be doubled for " + this.getplayerName());
		
		for (GraphNode countryNode : this.getPlayerGraph().getGraphNodes()) {
		  
		  countryNode.getNodeData().setArmies(countryNode.getNodeData().getArmies()*2);
		  
		}
		
		System.out.println("All Countries armies are doubled for " + this.getplayerName());
		
		this.decrementArmies(this.getPlayerArmies());
			
		  
	  }
	  
	/**
	 * Utility method to be used during attack
	 * 
	 * @param driver main game driver
	 * @return both attacking and target countries graph nodes.
	 */
	@Override
	public ArrayList<GraphNode> AttDefCountries(GameMainDriver driver)
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
	public boolean attack(GameMainDriver driver)
	  {
		boolean won = true;
		//boolean allOut = false;
		//boolean attack = false;
		//ArrayList<GraphNode> attDef = new ArrayList<>();
		
		
		
		ArrayList<GraphNode> destinationCountries = new ArrayList<>(this.getPlayerGraph().getGraphNodes()
			  .stream().filter(x -> x.getNodeData().getArmies() > 1).filter(i -> i.getNodeNeighbors().stream()
				  .anyMatch(x -> !x.getNodeData().getCurrentOccupier().equals(this.getplayerName())) )
			  .collect(Collectors.toList())); 
		  
		//attDef = canAttack();
		
       for (GraphNode countryNode : destinationCountries) {
		  
    	  for (GraphNode neighbor : countryNode.getNodeNeighbors()) {
    		
    		if (!neighbor.getNodeData().getCurrentOccupier().equals(this.getplayerName())) {
    		  
    		  neighbor.getNodeData().setCurrentOccupier(this.getplayerName());
    		  neighbor.getNodeData().setArmies(1);
    		  countryNode.getNodeData().setArmies(countryNode.getNodeData().getArmies()-1);
    		  
    		  this.getPlayerGraph().addNode(neighbor);
    		  
    		  for (Player defender : driver.getPlayerList().getPlayerList())
    			  {
    				if (defender.getplayerName().equals(neighbor
    				    .getNodeData().getCurrentOccupier()))
    				  {
    					defender.removeCountry(neighbor);
    					
    				  }
    			  }
    		}
    		
    	  }
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
	public boolean attackRound(GameMainDriver driver,
	    GraphNode attackerCountryNode, GraphNode defenderCountryNode,
	    boolean allOut)
	  {
			  
		return true;
	  }
	
	  /**
		 * to execute exchange of cards
		 * @param currentPlayer current player
		 * @param hand currently player's hand
		 * @param request selected cards by the player
		 * @return true if exchange been successful
		 */
		
	public boolean executeTurnInCard(GameMainDriver gameMainDriver,
		    String request)
		  {
			//"Infantry", "Cavalry", "Artillery"
			
			   //long distinctCards = this.getHand().getCardsFromHand().stream().distinct().count();
			      
			Map<String,List<Card>> handsCards = this.getHand().getCardsFromHand().stream()
				.collect(Collectors.groupingBy(Card::getCardType,Collectors.toList()));
			  
			/// 3 distinct cards
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
					    .getPlayerArmies() + (gameMainDriver.turnInCardsCount++ * 5));
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
		destinationCountries.sort((x2, x1) -> x1.getNodeData().getArmies() - x2
		    .getNodeData().getArmies());
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
		  
		  return new GraphNode(new Country("DUMMY"));
		}
	  
  }
