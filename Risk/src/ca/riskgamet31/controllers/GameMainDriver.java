package ca.riskgamet31.controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import ca.riskgamet31.maincomps.DeckOfCards;
import ca.riskgamet31.maincomps.GameMap;
import ca.riskgamet31.maincomps.Graph;
import ca.riskgamet31.maincomps.Player;
import ca.riskgamet31.controllers.CreateMap;

/**
 * Main driver class for the execution of game. Loading map, creating players
 * and distribution of countries and armies happens here.
 * 
 * @author Divyaprabha Rajendran
 * @version 1.0
 *
 */
public class GameMainDriver
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
	 * constructor for game main driver
	 */
	
	/**
	 * Deck of card for a game
	 */
	DeckOfCards deck;
	public GameMainDriver()
	  {
		risk = null;
		Players = new PlayerModel();
		StartUp = new StartUpPhase();
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
				System.out.println("The Map graph is valid");
				gameMapGraph.viewGraph();
				risk = new GameMap(xmlpath, continentsList, gameMapGraph);
			  } else
			  throw new InvalidGraphException("Invalid Map..graph is no connected..");
			
			cmap.displayMap();
		  
		  
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
		
		Scanner scan = new Scanner(System.in);
		do
		  {
			try
			  {
				System.out.println("Enter the number of players...");
				no_players = scan.nextInt();
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
				System.out.println("Enter player name...");
				String name = scan.next();
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
		  
		for (Player player : players)
		  {
			player.reinforcementArmiesCalc(risk);
		  }
		//to set the cards in deck of cards 
		deck = new DeckOfCards(risk.getGameMapGraph().getGraphNodes());
	  }
	  
	/**
	 * a method representing each turn
	 * 
	 */
	public void playGame()
	  {
		
		boolean endGame = false;
		Player currentPlayer;
		int turnID = 0;
		Scanner in = new Scanner(System.in);
		String text1 = "";
		boolean won = false;
		while (!endGame)
		  {
			
			currentPlayer = this.Players.getPlayerList().get(turnID++);
			//to add reinforcement calc for the cards
			// recalculate reinforcement armies for both players
						currentPlayer.reinforcementArmiesCalc(risk);
			
			// reinforcement phase
			System.out.println("Turn is for " + currentPlayer.getplayerName());
			System.out.println("\t starting reinforcement phase");
			currentPlayer.distributeArmies();
			
			/// attack phase
			do {
				
				System.out.println("\t starting attack phase");
				System.out.println("Enter Y if you want to attack");
				// check if game should end , if yes break
				do
				  {
					if (in.hasNextLine())
					  text1 = in.nextLine();
				  } while (text1.length() == 0);
				  
				if (text1.toUpperCase().equals("Y"))
				  {
				
				boolean	wonRound = currentPlayer.attack(this);
				if(wonRound)
					won = true;
				  }
				}while(text1.toUpperCase().equals("Y"));
				
				if(won)
				{
					currentPlayer.addNewCard(this.getDeck().drawCard());
				}
			
			// fortification phase
			String text = "";
			System.out.println("\t starting fortification phase");
			System.out.println("Enter Y if you want to fortify");
			do
			  {
				if (in.hasNextLine())
				  text = in.nextLine();
			  } while (text.length() == 0);
			  
			if (text.toUpperCase().equals("Y"))
			  {
				currentPlayer.fortification();
			  }
			if(this.Players.getPlayerList().size() == 1)
			{
				endGame = true;
				System.out.println("Player " + this.Players.getPlayerList().get(0).getplayerName() + " Won the game");
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
