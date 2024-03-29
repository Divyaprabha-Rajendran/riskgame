package ca.riskgamet31.maincomps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

import ca.riskgamet31.controllers.MainDriver;
import ca.riskgamet31.exceptions.InvalidNameException;
import ca.riskgamet31.exceptions.InvalidPlayerNameException;
import ca.riskgamet31.utility.Constants;
import ca.riskgamet31.utility.InputValidator;
import ca.riskgamet31.utility.UserInputOutput;

/**
 * Human Players class implementing human strategy
 * 
 * @author YD
 * @version 1.0
 * @since 1.0
 */
public class HumanPlayer implements Player
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
	public HumanPlayer(String playersName, int army)
	    throws InvalidNameException, InvalidPlayerNameException
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
	  
	/**
	 * Fortification phase starting method according to user's entered armies,
	 * fortification will be performed
	 * 
	 */
	@Override
	public void fortification()
	  {
		this.getPlayerGraph().viewGraph();
		
		Graph mp = this.getPlayerGraph();
		String toCountry = " ", fromCountry = " ";
		int noOfArmies = 0;
		boolean path = false;
		String tempText = "";
		Country fromCountryobj = new Country("dumy");
		Country toCountryobj = new Country("dumy");
		boolean secondtime = false;
		boolean exit = false;
		
		if (UserInputOutput.getInstance()
		    .requestUserInput("Do you want to Fortify Y/N").equals("Y"))
		  {
			do
			  {
				
				if (secondtime)
				  {
					tempText = UserInputOutput.getInstance()
					    .requestUserInput("Do you want to continue fortification?");
					if (!tempText.equals("Y"))
					  exit = true;
				  }
				if (!exit)
				  {
					boolean valid = false;
					do
					  {
						System.out.println();
						
						tempText = UserInputOutput.getInstance()
						    .requestUserInput("Enter base country");
						
						final String fromcountry1 = tempText;
						fromCountry = fromcountry1;
						if (this.getPlayerGraph().getGraphNodes().stream()
						    .anyMatch((x) -> x.getNodeData().getCountryName()
						        .equals(fromcountry1) && x.getNodeData()
						            .getArmies() > 1))
						  valid = true;
						else
						  System.out
						      .println("check country name, ownership and no of armies");
					  } while (!valid);
					  
					tempText = "";
					valid = false;
					do
					  {
						
						tempText = UserInputOutput.getInstance()
						    .requestUserInput("Enter target country:");
						
						final String toCountry1 = tempText;
						toCountry = toCountry1;
						
						if ((this.getPlayerGraph().getGraphNodes().stream()
						    .map((x) -> x.getNodeData().getCountryName())
						    .anyMatch((x) -> x
						        .equals(toCountry1))) && !toCountry
						            .equals(fromCountry))
						  {
							valid = true;
						  } else
						  System.out
						      .println("check country name and ownership");
					  } while (!valid);
					  
					tempText = "";
					
					boolean armiesNotInt = true;
					do
					  {
						System.out.println();
						tempText = UserInputOutput.getInstance()
						    .requestUserInput("Enter number of Armies:");
						String nOArmies = tempText;
						
						if (nOArmies.matches("\\d+"))
						  {
							armiesNotInt = false;
							
							noOfArmies = Integer.parseInt(nOArmies);
						  } else
						  System.out.println("Please enter valid input");
					  } while (armiesNotInt);
					  
					for (GraphNode gNode : this.getPlayerGraph()
					    .getGraphNodes())
					  {
						
						if ((gNode.getNodeData().getCountryName()
						    .equals(fromCountry)))
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
					
					secondtime = true;
				  }
				  
			  } while ((!path || noOfArmies >= fromCountryobj
			      .getArmies() || toCountry.equals(fromCountry)) && !exit);
			if (!exit)
			  {
				for (GraphNode gNode : this.getPlayerGraph().getGraphNodes())
				  {
					
					if ((gNode.getNodeData().getCountryName()
					    .equals(toCountry)))
					  toCountryobj = gNode.getNodeData();
				  }
				  
				fromCountryobj.reduceArmies(noOfArmies);
				toCountryobj.increaseArmies(noOfArmies);
			  }
		  }
	  }
	  
	/**
	 * Distribute the armies of every player among the countries the player
	 * owns. The method executes till all the player's armies are distributed.
	 * 
	 */
	@Override
	public void reinforcement()
	  {
		System.out.println();
		System.out.println("Assigning armies for Player " + this
		    .getplayerName());
		while (this.getPlayerArmies() != 0)
		  {
			System.out.println();
			System.out.println("Number of armies left..." + this
			    .getPlayerArmies());
			ArrayList<GraphNode> country_nodes = this
			    .getPlayerCountriesGNodes();
			HashSet<String> owned_by_player = new HashSet<String>();
			for (GraphNode node : country_nodes)
			  {
				owned_by_player.add(node.getNodeData().getCountryName());
				
				System.out.println(node.toString());
			  }
			String country_name = UserInputOutput.getInstance()
			    .requestUserInput("Enter the country name");
			if (!country_name.equals(null))
			  {
				if (owned_by_player.contains(country_name))
				  {
					
					int armies = 0;
					boolean validInput = false;
					String text = "";
					do
					  {
						text = UserInputOutput.getInstance()
						    .requestUserInput("Enter the number of armies to place..");
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
	 * Utility method to be used during attack
	 * 
	 * @param driver main game driver
	 * @return both attacking and target countries graph nodes.
	 */
	@Override
	public ArrayList<GraphNode> AttDefCountries(MainDriver driver)
	  {
		GraphNode attackerCountryNode = new GraphNode(new Country("dummy"));
		GraphNode defenderCountryNode = new GraphNode(new Country("dummy"));
		
		boolean selectanother = true;
		
		boolean validAcountry = false;
		boolean validDcountry = false;
		String Acountry = "", Dcountry = "";
		do
		  {
			validAcountry = false;
			Acountry = UserInputOutput.getInstance()
			    .requestUserInput("Enter attacking country name or N to exit:");
			final String Acountry1 = Acountry;
			
			if (this.getPlayerGraph().getGraphNodes().stream().anyMatch((x) -> x
			    .getNodeData().getCountryName().equals(Acountry1) && x
			        .getNodeData().getArmies() > 1 && x.getNodeNeighbors()
			            .stream().anyMatch(y -> !(y.getNodeData()
			                .getCurrentOccupier().equals(this
			                    .getplayerName())))))
			  {
				validAcountry = true;
				for (GraphNode gNode : this.getPlayerGraph().getGraphNodes())
				  {
					
					if ((gNode.getNodeData().getCountryName().equals(Acountry)))
					  attackerCountryNode = gNode;
				  }
			  } else
			  {
				if (!Acountry.equals("N"))
				  System.out
				      .println("check country name, ownership and number of armies");
			  }
		  } while (!validAcountry && !Acountry.equals("N"));
		  
		if (validAcountry)
		  {
			do
			  {
				
				System.out.println("Available country to attack are:");
				
				for (GraphNode neighbor : attackerCountryNode
				    .getNodeNeighbors())
				  {
					
					if (neighbor.getNodeData().getCurrentOccupier() != this
					    .getplayerName())
					  
					  System.out.println(neighbor.toString());
				  }
				  
				Dcountry = UserInputOutput.getInstance()
				    .requestUserInput("Enter target country or N to exit:");
				
				final String Dcountry1 = Dcountry;
				if (attackerCountryNode.getNodeNeighbors().stream().map(x -> x
				    .getNodeData()).anyMatch(y -> y.getCountryName()
				        .equals(Dcountry1) && !(y.getCurrentOccupier()
				            .equals(this.getplayerName()))))
				  {
					validDcountry = true;
					
					for (GraphNode gNode : attackerCountryNode
					    .getNodeNeighbors())
					  {
						
						if ((gNode.getNodeData().getCountryName()
						    .equals(Dcountry)))
						  defenderCountryNode = gNode;
					  }
				  } else
				  {
					if (!Dcountry.equals("N"))
					  System.out.println("Target country is not valid");
				  }
				  
			  } while (!validDcountry && !Dcountry.equals("N"));
		  }
		  
		ArrayList<GraphNode> ADCountryNodes = new ArrayList<>();
		
		if (validAcountry && validDcountry)
		  {
			
			ADCountryNodes.add(attackerCountryNode);
			ADCountryNodes.add(defenderCountryNode);
			
		  }
		  
		return ADCountryNodes;
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
		boolean allOut = false;
		boolean attack = false;
		ArrayList<GraphNode> attDef = new ArrayList<>();
		GraphNode attackerCountryNode = new GraphNode(new Country("dummy"));
		GraphNode defenderCountryNode = new GraphNode(new Country("dummy"));
		int noOfPlayers = driver.getPlayerList().getPlayerList().size();
		boolean mayAttack = this.getPlayerGraph().getGraphNodes().stream()
		    .anyMatch((y) ->
		      {
			    boolean armiesCondition = y.getNodeData().getArmies() > 1;
			    boolean defenderCondition = y.getNodeNeighbors().stream()
			        .allMatch(z -> z.getNodeData().getCurrentOccupier()
			            .equals(this.getplayerName()));
			    return armiesCondition && !defenderCondition;
		      });
		
		if (mayAttack && noOfPlayers > 1)
		  {
			
			this.getPlayerGraph().viewGraph();
			
			do
			  {
				
				String userInput = UserInputOutput.getInstance()
				    .requestUserInput("Do you want to attack Y/N?");
				
				attack = userInput.toUpperCase().equals("Y") ? true : false;
				if (attack)
				  {
					this.getPlayerGraph().viewGraph();
					attDef = AttDefCountries(driver);
					
					if (attDef.size() == 2)
					  {
						attackerCountryNode = attDef.remove(0);
						defenderCountryNode = attDef.remove(0);
						
						String allOutOption = UserInputOutput.getInstance()
						    .requestUserInput("Attack in All-Out mode Y/N?");
						allOut = allOutOption.toUpperCase()
						    .equals("Y") ? true : false;
						
						boolean wonRound = this
						    .attackRound(driver, attackerCountryNode, defenderCountryNode, allOut);
						
						if (wonRound)
						  won = true;
					  }
					  
				  }
				  
				noOfPlayers = driver.getPlayerList().getPlayerList().size();
				mayAttack = this.getPlayerGraph().getGraphNodes().stream()
				    .anyMatch((y) ->
				      {
					    boolean armiesCondition = y.getNodeData()
					        .getArmies() > 1;
					    boolean defenderCondition = y.getNodeNeighbors()
					        .stream().allMatch(z -> z.getNodeData()
					            .getCurrentOccupier().equals(this
					                .getplayerName()));
					    return armiesCondition && !defenderCondition;
				      });
				
			  } while (attack && noOfPlayers > 1 && mayAttack);
			  
		  } else
		  {
			
			if (noOfPlayers > 1)
			  {
				System.out.println(this
				    .getplayerName() + " can't attack from his countries - below");
				this.getPlayerGraph().viewGraph();
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
	public boolean attackRound(MainDriver driver, GraphNode attackerCountryNode,
	    GraphNode defenderCountryNode, boolean allOut)
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
			
			if (!allOut)
			  {
				noOfDicesForAttacker = getDiceInput(attackerCountryNode
				    .getNodeData(), "a");
				noOfDicesForDefender = getDiceInput(defenderCountryNode
				    .getNodeData(), "d");
			  } else
			  {
				noOfDicesForAttacker = getDiceInputAllOut(attackerCountryNode
				    .getNodeData(), "a");
				noOfDicesForDefender = getDiceInputAllOut(defenderCountryNode
				    .getNodeData(), "d");
			  }
			  
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
				do
				  {
					do
					  {
						tempText = UserInputOutput.getInstance()
						    .requestUserInput("Enter number of armies to transfer");
						String trArmies = tempText;
						
						if (trArmies.matches("\\d+"))
						  {
							isInt = true;
							
							transferArmies = Integer.parseInt(trArmies);
						  } else
						  System.out.println("Please enter valid input");
					  } while (!isInt);
					if (transferArmies < min || transferArmies > max)
					  System.out.println("Please enter input from valid range");
					else
					  transferArmiesValid = true;
					
				  } while (!transferArmiesValid);
				  
				attackerCountryNode.getNodeData().reduceArmies(transferArmies);
				defenderCountryNode.getNodeData()
				    .increaseArmies(transferArmies);
				won = true;
			  }
			  
			if (defenderObj.getPlayerCountriesGNodes().size() == 0)
			  {
				
				System.out.println(this
				    .getplayerName() + " will receive - " + defenderObj
				        .getHand().getCardsFromHand()
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
	 * Distribute the armies of every player among the countries the player
	 * owns. The method executes till all the player's armies are distributed.
	 * 
	 * 
	 */
	@Override
	public void initialdistributeArmies()
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
			ArrayList<GraphNode> country_nodes = this
			    .getPlayerCountriesGNodes();
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
		int noOfDices = 0;
		String tempText = "";
		boolean dicesNotInt = true;
		boolean diceCountValid = true;
		int maxDice = 0;
		if (i.equals("a"))
		  maxDice = 3;
		else
		  maxDice = 2;
		do
		  {
			do
			  {
				if (i.equals("a"))
				  {
					System.out
					    .println("Available Armies to attack are:" + (countryObj
					        .getArmies() - 1));
					
					tempText = UserInputOutput.getInstance()
					    .requestUserInput("Enter number of armies to attack with (max-3):");
					System.out.println();
				  } else
				  {
					System.out
					    .println("Available Armies to defend are:" + (countryObj
					        .getArmies()));
					tempText = UserInputOutput.getInstance()
					    .requestUserInput("Enter number of armies to defend (max-2):");
				  }
				  
				String nODices = tempText;
				
				if (nODices.matches("\\d+"))
				  {
					dicesNotInt = false;
					
					noOfDices = Integer.parseInt(nODices);
				  } else
				  System.out.println("Please enter a valid input");
			  } while (dicesNotInt);
			  
			if (noOfDices < 1 || noOfDices > maxDice || noOfDices > (i
			    .equals("a") ? countryObj.getArmies() - 1 : countryObj
			        .getArmies()))
			  {
				System.out
				    .println("Number of armies should be between 1 and (" + (i
				        .equals("a") ? "3 max), keep one army in your country." : "2 max)."));
			  } else
			  {
				diceCountValid = false;
			  }
		  } while (diceCountValid);
		return noOfDices;
	  }
	  
	/**
	 * utility method for fortification main method, not used
	 * 
	 * @return and empty array list
	 */
	@Override
	public ArrayList<GraphNode> canFortify()
	  {
		return null;
	  }
	  
	/**
	 * utility method for attack method
	 * 
	 * @return not used , an empty list.
	 */
	@Override
	public ArrayList<GraphNode> canAttack()
	  {
		return null;
	  }
	  
	/**
	 * utility method for reinforcement not used
	 * 
	 * @return an empty list.
	 */
	@Override
	public GraphNode canReinforce()
	  {
		return null;
	  }
	  
	/**
	 * to execute exchange of cards
	 * 
	 * @param gameMainDriver current gameMainDriver of the game
	 * @param request        selected cards by the player
	 * @return true if exchange been successful
	 */
	@Override
	public boolean executeTurnInCard(MainDriver gameMainDriver, String request)
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
				
				this.setArmies(this
				    .getPlayerArmies() + (Constants.turnInCards++ * 5));
				System.out.println("Now " + this
				    .getplayerName() + ": is eligible for " + this
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
	 * executes fortification , not used
	 * 
	 */
	@Override
	public void executeFortification(ArrayList<GraphNode> fortifyNodes)
	  {
		
	  }
	  
  }
