package ca.riskgamet31.maincomps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import ca.riskgamet31.controllers.MainDriver;
import ca.riskgamet31.exceptions.InvalidNameException;
import ca.riskgamet31.exceptions.InvalidPlayerNameException;

/**
 * main Players class
 * 
 * @author YD
 * @version 1.0
 * @since 1.0
 */
public class BenevolentPlayer implements Player
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
	
	public BenevolentPlayer(String playersName, int army) throws
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
	  
	  List<GraphNode> destinationCountries = new ArrayList<>(this.getPlayerGraph().getGraphNodes().stream().filter(i -> i.getNodeNeighbors().stream().anyMatch(x -> !x.getNodeData().getCurrentOccupier().equals(this.getplayerName())) ).collect(Collectors.toList())); 
	  
	  destinationCountries.sort((x1,x2) -> x1.getNodeData().getArmies() - x2.getNodeData().getArmies());
	  
	  List<GraphNode> sourceCountries = new ArrayList<>(this.getPlayerGraph().getGraphNodes()
		  .stream().filter( i -> i.getNodeData().getArmies() > 1).collect(Collectors.toList()));
	  
	  sourceCountries.sort((x2,x1) -> x1.getNodeData().getArmies() - x2.getNodeData().getArmies());
	  
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
	  
	  
	  Comparator<ArrayList<GraphNode>> sumOfArmiesComparator = (x,y) -> {
		return (x.get(0).getNodeData().getArmies() + x.get(1).getNodeData().getArmies()) 
			-
		(y.get(0).getNodeData().getArmies() + y.get(1).getNodeData().getArmies());
	  };
	  
	  returnedList = potintialList.stream().max(sumOfArmiesComparator).orElse(new ArrayList<>()); 
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
		int noOfArmies = fortifyNodes.get(0).getNodeData().getArmies()-1;
			  
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
		  
		return false;
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
			  
		return false;
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
						return true;
		  }

	
	@Override
	public ArrayList<GraphNode> canAttack()
	  {
		ArrayList<GraphNode> dummyNode = new ArrayList<>();

		return  Stream.of(new GraphNode(new Country("DUMMY")))
			.collect(Collectors.toCollection(ArrayList<GraphNode>::new));
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
		  GraphNode retNode = getPlayerGraph().getGraphNodes().stream()
			  .filter(i -> i.getNodeNeighbors().stream().anyMatch(x -> !x.getNodeData().getCurrentOccupier().equals(this.getplayerName())))
			  .min((i,y) -> i.getNodeData().getArmies()-y.getNodeData().getArmies()).orElse(new GraphNode(new Country("Not Avaialbe")));
		  return retNode;
		}
	  
  }
