package ca.riskgamet31.maincomps;

import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.lang.model.element.NestingKind;

import ca.riskgamet31.controllers.GameMainDriver;
import ca.riskgamet31.exceptions.InvalidNameException;
import ca.riskgamet31.exceptions.InvalidPlayerNameException;
import ca.riskgamet31.utility.UserInputRequester;

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
	public int reinforcementArmiesCalc(GameMap gameMap, int armiescardsAmount)
	  {
		int armiesForCountries = 0;
		int armiesForContinentsBonus = 0;
		
		int totalArmiesToAdd = 0;
		armiesForCountries = this.getCountry().size() / 3;
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
		return GameMainDriver.turnInCardsCount;
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
	 * returns player's hand.
	 * @return player's hand.
	 */
	public Hand getHand() {
	  
	  return this.hand;
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
		
		return this.getTurnInCards() * 5;
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
		UserInputRequester uir = new UserInputRequester();
		this.getPlayerGraph().viewGraph();
		
		Graph mp = this.getPlayerGraph();
		String toCountry = " ", fromCountry=" ";
		int noOfArmies = 0;
		boolean path = false;
		String tempText = "";
		Country fromCountryobj = new Country("dumy");
		Country toCountryobj = new Country("dumy");
		boolean secondtime = false;
		boolean exit = false;
		do
		  {
			
			if (secondtime)
			  {
			  tempText = uir.requestUserInput("Do you want to continue fortification?");
			  if (!tempText.equals("Y"))
				exit = true;
			  }
			// get base country
			if (!exit)
			  {
			boolean valid = false;
			do
			  {
				System.out.println();
				
				tempText = uir.requestUserInput("Enter base country");
				
				final String fromcountry1 = tempText;
				fromCountry = fromcountry1;
				if (this.getPlayerGraph().getGraphNodes().stream().anyMatch((x) -> x.getNodeData().getCountryName()
				        .equals(fromcountry1) 
				        && x.getNodeData().getArmies() >1 ))
				  valid = true;
				else
				  System.out.println("check country name, ownership and no of armies");
			  } while (!valid);
			  
			// get destination country
			tempText = "";
			valid = false;
			do
			  {
				
				tempText = uir.requestUserInput("Enter target country:");
				
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
				System.out.println();
				tempText = uir.requestUserInput("Enter number of Armies:");  
				String nOArmies = tempText;
				
				if (nOArmies.matches("\\d+"))
				  {
					armiesNotInt = false;
					
					noOfArmies = Integer.parseInt(nOArmies);
				  } else
				  System.out.println("Please enter valid input");
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
				
			secondtime = true;  
			  }
			
		  } while ((!path || noOfArmies >= fromCountryobj
		      .getArmies() || toCountry.equals(fromCountry)) && !exit);
		  if (!exit)
			{
		for (GraphNode gNode : this.getPlayerGraph().getGraphNodes())
		  {
			
			if ((gNode.getNodeData().getCountryName().equals(toCountry)))
			  toCountryobj = gNode.getNodeData();
		  }
		  
		fromCountryobj.reduceArmies(noOfArmies);
		toCountryobj.increaseArmies(noOfArmies);
			}
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
	 * Utility method to be used during attack
	 * @param driver main game driver
	 * @return both attacking and target countries graph nodes.
	 */
	public ArrayList<GraphNode> AttDefCountries(GameMainDriver driver)
	{
	  
	  GraphNode attackerCountryNode = new GraphNode(new Country("dummy"));
	  GraphNode defenderCountryNode = new GraphNode(new Country("dummy"));
	  UserInputRequester uir = new UserInputRequester();
	  boolean selectanother = true;
			
			// get base country
			boolean validAcountry = false;
			boolean validDcountry = false;
			String Acountry,Dcountry;
			do
			  {
				validAcountry = false;
				Acountry = uir.requestUserInput("Enter attacking country name or N to exit:" );
				final String Acountry1 = Acountry;
				
				if (this.getPlayerGraph().getGraphNodes().stream().anyMatch((x) -> x.getNodeData().getCountryName().equals(Acountry1) 
					&& x.getNodeData().getArmies() >1 
					&& x.getNodeNeighbors().stream().anyMatch(y -> !(y.getNodeData().getCurrentOccupier().equals(this.getplayerName())))))
				  {
				  validAcountry = true;
				  for (GraphNode gNode : this.getPlayerGraph().getGraphNodes())
					  {
						
						if ((gNode.getNodeData().getCountryName().equals(Acountry)))
							attackerCountryNode = gNode;
					  }
				  }
				else
				  {
					if (!Acountry.equals("N"))
				  System.out.println("check country name, ownership and number of armies");
				  }
			  } while (!validAcountry && !Acountry.equals("N"));
			  
			// get destination country
			
			if (validAcountry)
			  {
			do
			  {
				
				System.out.println("Available country to attack are:");
				
				for (GraphNode neighbor : attackerCountryNode.getNodeNeighbors()) {
				  
				  if (neighbor.getNodeData().getCurrentOccupier() != this.getplayerName())
					
					System.out.println(neighbor.toString());
				}
				
				Dcountry = uir.requestUserInput("Enter target country or N to exit:" );
				final String Dcountry1 = Dcountry;
				if (
					attackerCountryNode.getNodeNeighbors().stream().map(x -> x.getNodeData())
					.anyMatch(y -> y.getCountryName().equals(Dcountry1) && !(y.getCurrentOccupier().equals(this.getplayerName()))))
				  {
					validDcountry = true;
					
					for (GraphNode gNode : attackerCountryNode.getNodeNeighbors())
					  {
						
						if ((gNode.getNodeData().getCountryName().equals(Dcountry)))
							defenderCountryNode = gNode;
					  }
				  } else
				  {
					if (!Dcountry.equals("N"))
					System.out.println("Target country is not valid");  
				  }
			  
	
			  }while (!validDcountry && !Dcountry.equals("N"));
	}
	
	ArrayList<GraphNode> ADCountryNodes = new ArrayList<>();		
			
	if (validAcountry && validDcountry) {
	  
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
	public boolean attack(GameMainDriver driver)
	{
	    UserInputRequester uir = new UserInputRequester();
		boolean won = false;
		boolean allOut = false;
		boolean attack = false;
		ArrayList<GraphNode> attDef= new ArrayList<>();
		GraphNode attackerCountryNode = new GraphNode(new Country("dummy"));
		GraphNode defenderCountryNode = new GraphNode(new Country("dummy"));
		int noOfPlayers = noOfPlayers = driver.getPlayerList().getPlayerList().size();
		
	  if (this.getPlayerGraph().getGraphNodes().stream().map(x -> x.getNodeData()).anyMatch((y) -> y.getArmies()>1) && noOfPlayers > 1)
		  {
			
			this.getPlayerGraph().viewGraph();

		do {
			
			String userInput = uir.requestUserInput("Do you want to attack Y/N?");
			attack = userInput.toUpperCase().equals("Y")?true:false;   
			if (attack)
			  {
				this.getPlayerGraph().viewGraph();
				attDef = AttDefCountries(driver);		
		
				if (attDef.size() == 2)
				  {
					attackerCountryNode = attDef.remove(0);
					defenderCountryNode = attDef.remove(0);
				  
				String allOutOption = uir.requestUserInput("Attack in All-Out mode Y/N?");
				
				allOut = allOutOption.toUpperCase().equals("Y")?true:false;
				
			boolean	wonRound = this.attackRound(driver,attackerCountryNode,defenderCountryNode,allOut);
			
			if(wonRound)
				won = true;
			  }
			
			  }
			
			noOfPlayers = driver.getPlayerList().getPlayerList().size();
			
			}while(attack && noOfPlayers >1);
			
		  }else {
			
			if (noOfPlayers > 1)
			  {
				System.out.println(this.getplayerName() + " does not have enough armies in any country to attack");
			  }
		  }
			
	  
	 return won; 
	}
	
	
	
	/**
	 * a method to handle individual attack rounds
	 * @param driver main game driver
	 * @param attackerCountryNode attacking country graph node
	 * @param defenderCountryNode defending country graph node
	 * @param allOut all-Out option
	 * @return true if attacker occupied the attacked country.
	 */
	public boolean attackRound(GameMainDriver driver, GraphNode attackerCountryNode, GraphNode defenderCountryNode, boolean allOut) 
	{
		Scanner s = new Scanner(System.in);
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
		do {
		  
		  int min = 0, max = 0;
			int transferArmies = 0;
			int attackerArmiesToSet = 0, defenderArmiesToSet = 0;
		  
		  
			if (!allOut)
			  {
				//to get dice input for attacker
				noOfDicesForAttacker = getDiceInput(attackerCountryNode.getNodeData(), "a");
				//to get dice input for defender
				noOfDicesForDefender = getDiceInput(defenderCountryNode.getNodeData(), "d");
			  }else
				{
				//to get dice input for attacker
					noOfDicesForAttacker = getDiceInputAllOut(attackerCountryNode.getNodeData(), "a");
					//to get dice input for defender
					noOfDicesForDefender = getDiceInputAllOut(defenderCountryNode.getNodeData(), "d");
				}
			
			attackerRolls = dice.roll(noOfDicesForAttacker);
			defenderRolls = dice.roll(noOfDicesForDefender);
			
			
			defenderLosses = 0;
			attackerLosses = 0;
			
				if (attackerRolls[0] > defenderRolls[0]) {
					defenderLosses++;
				}
				else {
					attackerLosses++;
				}
				// Index 1 = second highest pair
				if (noOfDicesForAttacker > 1 && noOfDicesForDefender > 1) {
				
					if (attackerRolls[1] > defenderRolls[1]) {
						defenderLosses++;
						
					} else {
						attackerLosses++;
					}
				}
				// Calculate losses
				System.out.println("");
				System.out.println("<Combat Result>");
				System.out.println("Attacker dices"+Arrays.toString(attackerRolls));
				System.out.println("Defender dices"+ Arrays.toString(defenderRolls));
				
				attackerArmiesToSet = attackerCountryNode.getNodeData().getArmies() - attackerLosses;
				defenderArmiesToSet = defenderCountryNode.getNodeData().getArmies() - defenderLosses;
				
				attackerCountryNode.getNodeData().setArmies(attackerArmiesToSet);
				defenderCountryNode.getNodeData().setArmies(defenderArmiesToSet);
					
				if (attackerArmiesToSet <= 1 || defenderArmiesToSet == 0)
				  exitAttack = true;
				
				
				for(Player defender : driver.getPlayerList().getPlayerList())
				{
					if(defender.getplayerName().equals(defenderCountryNode.getNodeData().getCurrentOccupier()))
					{
						defenderObj = defender;
					}
				}
				
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
						System.out.println("Enter number of armies to transfer");
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
						  System.out.println("Please enter valid input");
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
				  
				  System.out.println(this.getplayerName()+" will receive"+ defenderObj.hand.getCardsFromHand().size()+" cards from player "+defenderObj.getplayerName());
					
				  for(Card card : defenderObj.hand.getCardsFromHand())
					{
						this.addNewCard(card);
						//defenderObj.getPlayerCards().remove(card);
					}
					
				//---------	//defenderObj.hand.getCardsFromHand();
					driver.getPlayerList().getPlayerList().remove(defenderObj);
					System.out.println("Player "+defenderObj.getplayerName()+" is removed from game.");
					
					//------------- if attacker has more than 5 cards then he has to draw cards immediately
				}
			
		}while(allOut && !exitAttack);
				
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
			  {
			System.out.println("Available Armies to attack are:" + (countryObj.getArmies()-1));	
			System.out.println("Enter number of armies to attack with (max-3):");
			  }
			else
			  {
			System.out.println("Available Armies to defend are:" + (countryObj.getArmies()));		
			System.out.println("Enter number of armies to defend (max-2):");
			  }
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
			  System.out.println("Please enter a valid input");
		  }while(dicesNotInt);
		
		//check that number of dices should be between 1 to 3;
			
			if(noOfDices  < 1 || noOfDices > maxDice || noOfDices > (i.equals("a")?countryObj.getArmies()-1:countryObj.getArmies()))
			{
				System.out.println("Number of armies should be between 1 and ("+ (i.equals("a")? "3 max), keep one army in your country.":"2 max)."));
			}
			else
			{
				diceCountValid = false;
			}
		  } while (diceCountValid);
			//attackerRolls = dice.roll(noOfDices);
			return noOfDices;
	}
	  
	/**
	 * To get the dice number for All-Out mode.
	 * @param countryObj country of attacker of defender
	 * @param i identifier that whether dice is rolling for attacker or defender
	 * @return output of rolled dice input
	 */
	public int getDiceInputAllOut(Country countryObj, String i)
	{
		
		int noOfDices = 0;
		
		if (i.equals("a"))
		  {
			noOfDices = countryObj.getArmies()-1 >= 3 ? 3 : countryObj.getArmies()-1;
		  }
		  
		else
		  {
			noOfDices = countryObj.getArmies() >= 2 ? 2 : countryObj.getArmies();
		  }
		
			return noOfDices;
	}

  
  
  
  }
