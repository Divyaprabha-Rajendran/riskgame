package ca.riskgamet31.controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.lang.ref.PhantomReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Scanner;

import javax.swing.JFileChooser;

import ca.riskgamet31.exceptions.InvalidContinentException;
import ca.riskgamet31.exceptions.InvalidCountryException;
import ca.riskgamet31.exceptions.InvalidGraphException;
import ca.riskgamet31.exceptions.InvalidLinkException;
import ca.riskgamet31.exceptions.InvalidNameException;
import ca.riskgamet31.exceptions.InvalidPlayerCountInput;
import ca.riskgamet31.exceptions.InvalidPlayerException;
import ca.riskgamet31.exceptions.InvalidPlayerNameException;
import ca.riskgamet31.maincomps.Continent;
import ca.riskgamet31.maincomps.Country;
import ca.riskgamet31.maincomps.DeckOfCards;
import ca.riskgamet31.maincomps.GameMap;
import ca.riskgamet31.maincomps.Graph;
import ca.riskgamet31.maincomps.GraphNode;
import ca.riskgamet31.maincomps.Player;
import ca.riskgamet31.utility.InputValidator;
import ca.riskgamet31.utility.UserInputRequester;
import ca.riskgamet31.views.PhaseView;
import ca.riskgamet31.views.PlayersWorldDominationView;
import ca.riskgamet31.views.countryView;
import ca.riskgamet31.controllers.CreateMap;

/**
 * Main driver class for the execution of game. Loading map, creating players
 * and distribution of countries and armies happens here.
 * 
 * @author Divyaprabha Rajendran
 * @version 1.0
 *
 */
public class GameMainDriver extends Observable
  {
	/**
	 * game map member
	 */
	private GameMap risk;
	/**
	 * player model member
	 */
	private PlayerModel Players;
	/**
	 * startup phase member
	 */
	
	StartUpPhase StartUp;
	
	
	/**
	 * Deck of card for a game
	 */
	DeckOfCards deck;
	
	/**
	 * Turn In count during game
	 */
	public static int turnInCardsCount; 
	/**
	 * phase info data structure to be passed to phase view observer
	 */
	private ArrayList<String> phaseInfo ;
	
	private PlayersWorldDominationView playerWorldDominationView;
	
	/**
	 * constructor for game main driver
	 */
	public GameMainDriver()
	  {
		risk = null;
		Players = new PlayerModel();
		StartUp = new StartUpPhase();
		turnInCardsCount = 1;
		phaseInfo = new ArrayList<>();
		PhaseView phaseview = new PhaseView();
		this.addObserver(phaseview);
		
		playerWorldDominationView = new PlayersWorldDominationView();
	  }
	/**
	 * to get how many times players exchanged cards  
	 * @return number of times players exchanged cards.
	 */
	public int getTurnInCardsCount()
	  {
		
		return turnInCardsCount;
	  }

	/**
	 * to update number of times players exchanged cards
	 * @param turnInCardsCount new number of times cards been exchanged.
	 */
	public void setTurnInCardsCount(int turnInCardsCount)
	  {
		this.turnInCardsCount = turnInCardsCount;
	  }

	/**
	 * to get players list
	 * 
	 * @return players list
	 */
	public PlayerModel getPlayerList()
	  {
		return Players;
	  }
	  
	/**
	 * Gives different options to create a gamemap and returns the file path of
	 * the chosen XML file
	 * 
	 * @param GM main driver of the game
	 * @return xmlFilePath map xml file path
	 * @throws IOException IOException
	 *
	 */
	public String getFileInput(GameMainDriver GM) throws IOException
	  {
		
		File xmlFile = new File(System
		    .getProperty("user.dir") + "\\Risk_MapData\\default_map.xml");
		
		Scanner scan = new Scanner(System.in);
		
		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "\\Risk_MapData"));
		boolean continueEditing = true;
		
		while (continueEditing)
		  {
			System.out.println("Choose an option...");
			System.out.println("1. open and edit existing map...");
			System.out.println("2. open default map..");
			System.out.println("3. start playing..");
			int option = scan.nextInt();
			
			switch (option)
			  {
				
				case 1:
				  {
					
					int choice = chooser.showOpenDialog(chooser);
					if (choice != JFileChooser.APPROVE_OPTION)
					  xmlFile = new File(System
						    .getProperty("user.dir") + "\\Risk_MapData\\default_map.xml");
					else
					  xmlFile = chooser.getSelectedFile();
					if (Desktop.isDesktopSupported())
					  {
						Desktop.getDesktop().open(xmlFile);
					  } else
					  {
						System.out.println("not a valid file");
					  }
					  
					break;
				  }
				case 2:
				  {
					xmlFile = new File(System
					    .getProperty("user.dir") + "\\Risk_MapData\\default_map.xml");
					
					break;
				  }
				case 3:
				  {
					try
					  {
						GM.createGameMap(xmlFile.getPath());
						continueEditing = false;
					  }
					catch(InvalidNameException e)
					{
						//System.out.println(e.getMessage());
					}
					catch(InvalidCountryException e)
					{
						//System.out.println(e.getMessage());
					}
					catch(InvalidContinentException e)
					{
						//System.out.println(e.getMessage());
						
					}
					catch (InvalidGraphException e)
					  {
						//System.out.println(e.getMessage());
						//e.printStackTrace();
					  }
					catch(InvalidLinkException e)
					{
						//System.out.println(e.getMessage());
						//e.printStackTrace();
					}
					catch(Exception e)
					{
						System.err.println(e.getMessage());
						System.err.println("XML file is malformed.");
						//e.printStackTrace();
					}
					
					break;
				  }
			  }
		  }
		;
		return xmlFile.getPath();
	  }
	  
	/**
	 * Creates the game map from the CreateMap Class for the player.
	 * 
	 * @param xmlpath xml file path
	 * @throws InvalidGraphException If the graph is invalid 
	 * @throws InvalidNameException If the name of continent or country has special characters or numbers
	 * @throws InvalidCountryException If there is a duplicate country
	 * @throws InvalidContinentException If there is a duplicate continent.
	 * @throws InvalidLinkException If from and to countries are same.
	 * @throws Exception For handling null values and XML malformed exceptions.
	 */
	public void createGameMap(String xmlpath) throws InvalidGraphException, InvalidNameException,InvalidCountryException,
	InvalidContinentException,InvalidLinkException,Exception
	  {
			CreateMap cmap = new CreateMap(xmlpath);
			
			HashMap<String, Continent> continentsList = cmap.generateGraph();
			Graph gameMapGraph = new Graph(cmap.getAllCountryNodes());
			if (gameMapGraph.isConnected())
			  {
				System.out.println("Risk game file and associated graphs and sub-graphs are valid... :)");
				//gameMapGraph.viewGraph();
				risk = new GameMap(xmlpath, continentsList, gameMapGraph);
			  } else
			  throw new InvalidGraphException("Invalid Map..graph is no connected..");
			
			//cmap.displayMap();
		  
		  
	  }
	  
	/**
	 * Creates players for the game after checking pre-conditions. A player name
	 * cannot have special characters and duplication is not allowed.
	 * 
	 * @throws InvalidNameException InvalidNameException
	 * @throws NullPointerException NullPointerException
	 * 
	 */
	public void createPlayer() throws NullPointerException, InvalidNameException
	  {
		int no_players = 0;
		UserInputRequester uir = new UserInputRequester();
		InputValidator inpV = new InputValidator();
		this.updatePhaseInfo("Stratup game phase", "No players yet", "1- Creating players.\n2- Random distribution of countries.\n3- Players allocate armies to their countries\n");
		this.setChanged();
		this.notifyObservers(phaseInfo);
		do
		  {
			try
			  {
				String userInput = "";
				do
				  {
				userInput = uir.requestUserInput("Enter the number of players...");
				
				  }while(!inpV.validateNumbers(userInput));
				no_players = Integer.parseInt(userInput);
				if (no_players > 6 || no_players < 3)
				  {
					throw new InvalidPlayerCountInput("You can choose players of 3 to 6");
				  }
			  } catch (InvalidPlayerCountInput e)
			  {
				System.out.println(e);
			  }
		  } while (no_players > 6 || no_players < 3);
		  
		StartUp.setPlayerCount(no_players);
		
		int i = 0;
		while (i < no_players)
		  {
			try
			  {
				
				String name = "", userInput="";
				do
				  {
				userInput = uir.requestUserInput("Enter player name...");
				
				  }while(!inpV.validateAlphaNum(userInput));
				
				name = userInput;
				Player player = StartUp.createPlayers(name);
				Players.setPlayerList(player);
				i = i + 1;
			  } catch (InvalidPlayerException exception)
			  {
				System.out.println(exception);
			  } catch (InvalidPlayerNameException exception)
			  {
				System.out.println(exception);
			  }
		  }
	  }
	  
	/**
	 * sets up the game by calling the distributeCountries which randomly
	 * distributes countries among players. Once the countries are distributed,
	 * armies are distributed among countries the player own.
	 *
	 */
	public void setUpGame()
	  {
		
		StartUp.distributeCountries(Players, risk);
		
		ArrayList<Player> players = Players.getPlayerList();
		
		for (Player player : players)
		  {
			StartUp.distributeArmies(player);
		  }
		  
		for (GraphNode gNode : risk.getGameMapGraph().getGraphNodes())
		  {
			gNode.getNodeData().addObserver(gNode.getNodeData().getViewer());
			
		  }
		for (Player player : players)
		  {
			player.reinforcementArmiesCalc(risk);
		  }
		//to set the cards in deck of cards 
		deck = new DeckOfCards(risk.getGameMapGraph().getGraphNodes());
		
	  }
	
	/**
	 * to update current phase information
	 * @param phaseName current phase name
	 * @param playerName current player name
	 * @param phaseActionInfo current phase general actions
	 */
	public void updatePhaseInfo(String phaseName,String playerName, String phaseActionInfo) {
	  
	  this.phaseInfo.clear();
	  
	  phaseInfo.add(phaseName);
	  phaseInfo.add(playerName);
	  phaseInfo.add(phaseActionInfo);
	  
	}
	
	/**
	 * a method representing each turn
	 * 
	 */
	public void playGame()
	  {
		UserInputRequester userInputReq = new UserInputRequester();
		boolean endGame = false;
		Player currentPlayer;
		int turnID = 0;
		
		this.addObserver(this.playerWorldDominationView);
		
		while (!endGame)
		  {
			
			currentPlayer = this.Players.getPlayerList().get(turnID++);
			//to add reinforcement calc for the cards
			// recalculate reinforcement armies for both players
			currentPlayer.reinforcementArmiesCalc(risk);
			
			// reinforcement phase
			//System.out.println("Turn is for " + currentPlayer.getplayerName());
			//System.out.println("\t starting reinforcement phase");
			this.updatePhaseInfo("Reinforcement Phase", currentPlayer.getplayerName(), "1- calculate addtional armies.\n2- allocate additional armies granted to player.\n");
			this.setChanged();
			this.notifyObservers(phaseInfo);
			
			currentPlayer.reinforcement();
			
			/// attack phase
			//System.out.println("\t starting attack phase");
			this.updatePhaseInfo("Attack phase", currentPlayer.getplayerName(), "1- Choose to attack or not.\n2- Attack as many times as needed\n3- win a card if one territory been occupied.");
			this.setChanged();
			this.notifyObservers(phaseInfo);
			
			currentPlayer.attack( this);
			
			if(this.Players.getPlayerList().size() == 1)
				{
					endGame = true;
					System.out.println("Player " + this.Players.getPlayerList().get(0).getplayerName() + " Won the game");
				}
			
			// fortification phase
			
			//System.out.println("\t starting fortification phase");
			this.updatePhaseInfo("Fortification phase", currentPlayer.getplayerName(), "1- Choose to fortify or not.\n2- move as many armies as need to one territory.\n");
			this.setChanged();
			this.notifyObservers(phaseInfo);
			
			String userInput = userInputReq.requestUserInput("Enter Y if you want to fortify");
			if (userInput.toUpperCase().equals("Y"))
			  {
				currentPlayer.fortification();
			  }
			  
			
			if (turnID >= this.Players.getPlayerList().size())
			  turnID = 0;
			
		  }
		  
	  }
	  
	/**
	 * main method of the game
	 * 
	 * @param args command line args
	 */
	public static void main(String[] args)
	  {
		GameMainDriver driver = new GameMainDriver();
		try
		  {
			String xmlpath = driver.getFileInput(driver);
			// driver.createGameMap(xmlpath);
			driver.createPlayer();
			driver.setUpGame();
			driver.risk.viewGameMap();
			driver.playGame();
		  } catch (InvalidNameException e)
		  {
			e.printStackTrace();
			
		  } catch (IOException e)
		  {
			e.printStackTrace();
		  } catch (NullPointerException e)
		  {
			e.printStackTrace();
		  }
		  
	  }
	/**
	 * get the game map object
	 * @return game map object
	 */
	public GameMap getGameMap()
	{
		return risk;
	}
	
	/**
	 * Deck of cards object
	 * @return object for the deck pf cards
	 */
	public DeckOfCards getDeck()
	{
		return deck;
	}
	  
  }
