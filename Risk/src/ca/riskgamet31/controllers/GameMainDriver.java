package ca.riskgamet31.controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.xml.transform.TransformerException;

import ca.riskgamet31.exceptions.InvalidContinentException;
import ca.riskgamet31.exceptions.InvalidCountryException;
import ca.riskgamet31.exceptions.InvalidGraphException;
import ca.riskgamet31.exceptions.InvalidLinkException;
import ca.riskgamet31.exceptions.InvalidNameException;
import ca.riskgamet31.exceptions.InvalidPlayerCountInput;
import ca.riskgamet31.exceptions.InvalidPlayerException;
import ca.riskgamet31.exceptions.InvalidPlayerNameException;
import ca.riskgamet31.maincomps.Card;
import ca.riskgamet31.maincomps.DeckOfCards;
import ca.riskgamet31.maincomps.GameMap;
import ca.riskgamet31.maincomps.GraphNode;
import ca.riskgamet31.maincomps.Player;
import ca.riskgamet31.utility.Constants;
import ca.riskgamet31.utility.InputValidator;
import ca.riskgamet31.utility.SaveGame;
import ca.riskgamet31.utility.UserInputOutput;
import ca.riskgamet31.views.PhaseView;
import ca.riskgamet31.views.PlayersWorldDominationView;

/**
 * Main driver class for the execution of game. Loading map, creating players
 * and distribution of countries and armies happens here.
 * 
 * @author Divyaprabha Rajendran
 * @version 1.0
 *
 */
public class GameMainDriver extends Observable implements MainDriver
  {
	/**
	 * Deck of card for a game.
	 */
	DeckOfCards deck;
	/**
	 * player model member
	 */
	private PlayerModel Players;
	/**
	 * player world domination view
	 */
	private PlayersWorldDominationView playerWorldDominationView;
	
	/**
	 * game map member
	 */
	private GameMap risk;
	
	/**
	 * startup phase member
	 */
	StartUpPhase StartUp;
	
	/**
	 * represents current turn number
	 */
	private int turnID;
	
	/**
	 * constructor for game main driver
	 */
	public GameMainDriver()
	  {
		phaseInfo.clear();
		risk = null;
		Players = new PlayerModel();
		StartUp = new StartUpPhase();
		Constants.turnInCards = 1;
		PhaseView phaseview = new PhaseView();
		this.addObserver(phaseview);
		this.turnID = 0;
		playerWorldDominationView = new PlayersWorldDominationView();
	  }
	  
	/**
	 * generate different attack phase information base on player type
	 * 
	 * @param currentPlayer player playing this round.
	 * @return attack phase info based on player strategy
	 */
	private String attackphaseInfo(Player currentPlayer)
	  {
		String phaseInfoString = "";
		switch (currentPlayer.getClass().getSimpleName())
		  {
			case "HumanPlayer":
			  phaseInfoString = "1- Choose to attack or not.\n2- Attack as many times as needed\n3- Win a card if one territory been occupied.\n";
			  break;
			case "AggressivePlayer":
			  phaseInfoString = "1- Always attack when possible.\n2- keep Attacking from country with highest number of armies\n3- Win a card if one territory been occupied.\n";
			  break;
			case "BenevolentPlayer":
			  phaseInfoString = "1- Never attack\n";
			  break;
			case "CheaterPlayer":
			  phaseInfoString = "1- Always concqure adjecent countries to his owned countries\n";
			  break;
			case "RandomPlayer":
			  phaseInfoString = "1- Random number of attacks.\n2- against random countries\n";
			  break;
		  }
		  
		return phaseInfoString;
	  }
	  
	/**
	 * Creates players for the game after checking pre-conditions. A player name
	 * cannot have special characters and duplication is not allowed.
	 * 
	 * @throws InvalidNameException InvalidNameException
	 * @throws NullPointerException NullPointerException
	 * 
	 */
	@Override
	public void createPlayer() throws NullPointerException, InvalidNameException
	  {
		int no_players = 0;
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
					userInput = UserInputOutput.getInstance()
					    .requestUserInput("Enter the number of players...");
					
				  } while (!inpV.validateNumbers(userInput));
				no_players = Integer.parseInt(userInput);
				if (no_players > 6 || no_players < 2)
				  {
					throw new InvalidPlayerCountInput("You can choose players of 3 to 6");
				  }
			  } catch (InvalidPlayerCountInput e)
			  {
				System.out.println(e);
			  }
		  } while (no_players > 6 || no_players < 2);
		  
		StartUp.setPlayerCount(no_players);
		
		int i = 0;
		while (i < no_players)
		  {
			try
			  {
				
				String name = "", userInput = "";
				
				userInput = UserInputOutput.getInstance()
				    .requestUserInput("Enter Type and name i.e. AGG|Dragon...");
				
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
	 * overrides execute method of main driver abstraction.
	 */
	@Override
	public void execute()
	  {
		
		try
		  {
			String xmlpath = this.getFileInput(this);
			this.createPlayer();
			this.setUpGame();
			this.risk.viewGameMap();
			String winner = this.playGame();
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
	 * generate different fortification phase information base on player type
	 * 
	 * @param currentPlayer player playing this round.
	 * @return fortification phase info based on player strategy
	 */
	private String fortificationPhaseInfo(Player currentPlayer)
	  {
		String phaseInfoString = "";
		switch (currentPlayer.getClass().getSimpleName())
		  {
			case "HumanPlayer":
			  phaseInfoString = "1- Choose to fortify or not.\n2- Move as many armies as need to one territory.\n";
			  break;
			case "AggressivePlayer":
			  phaseInfoString = "1- Always fortify when possible.\n2- fortify a country in order to maximize aggregation in one country.\n";
			  break;
			case "BenevolentPlayer":
			  phaseInfoString = "1- fortify the weakest country\n";
			  break;
			case "CheaterPlayer":
			  phaseInfoString = "1- doubles the number of armies on its countries that have neighbors belong to other players\n";
			  break;
			case "RandomPlayer":
			  phaseInfoString = "1- fortify a random country.\n";
			  break;
		  }
		  
		return phaseInfoString;
		
	  }
	  
	/**
	 * Deck of cards object
	 * 
	 * @return object for the deck of cards
	 */
	public DeckOfCards getDeck()
	  {
		return deck;
	  }
	  
	/**
	 * Gives different options to create a game map and returns the file path of
	 * the chosen XML file
	 * 
	 * @param GM main driver of the game
	 * @return xmlFilePath map xml file path
	 * @throws IOException IOException
	 *
	 */
	@Override
	public String getFileInput(MainDriver GM) throws IOException
	  {
		
		File xmlFile = new File(System
		    .getProperty("user.dir") + "\\Risk_MapData\\default_map.xml");
		
		Scanner scan = new Scanner(System.in);
		
		JFileChooser chooser = new JFileChooser();
		
		chooser.setCurrentDirectory(new File(System
		    .getProperty("user.dir") + "\\Risk_MapData"));
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
						this.risk = GM.createGameMap(xmlFile.getPath());
						
						continueEditing = false;
					  } catch (InvalidNameException e)
					  {
						
					  } catch (InvalidCountryException e)
					  {
						
					  } catch (InvalidContinentException e)
					  {
						
					  } catch (InvalidGraphException e)
					  {
						
					  } catch (InvalidLinkException e)
					  {
						
					  } catch (Exception e)
					  {
						System.err.println(e.getMessage());
						System.err.println("XML file is malformed.");
						
					  }
					  
					break;
				  }
			  }
		  }
		;
		return xmlFile.getPath();
	  }
	  
	/**
	 * get the game map object
	 * 
	 * @return game map object
	 */
	public GameMap getGameMap()
	  {
		return risk;
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
	 * getter for players array list
	 * 
	 * @return array list of players
	 */
	public PlayerModel getPlayers()
	  {
		return Players;
	  }
	  
	/**
	 * getter for player world domination view
	 * 
	 * @return player world domination view
	 */
	public PlayersWorldDominationView getPlayerWorldDominationView()
	  {
		return playerWorldDominationView;
	  }
	  
	/**
	 * getter for turn number
	 * 
	 * @return current turn number
	 */
	public int getTurnID()
	  {
		return turnID;
	  }
	  
	/**
	 * to get how many times players exchanged cards
	 * 
	 * @return number of times players exchanged cards.
	 */
	public int getTurnInCardsCount()
	  {
		
		return Constants.turnInCards;
	  }
	  
	/**
	 * a main single game play method
	 * 
	 */
	@Override
	public String playGame()
	  {
		Scanner in = new Scanner(System.in);
		boolean endGame = false;
		Player currentPlayer;
		String sIn = "a";
		String winner = "NA";
		boolean won = false;
		this.addObserver(this.playerWorldDominationView);
		
		while (!endGame)
		  {
			won = false;
			currentPlayer = this.Players.getPlayerList().get(turnID++);
			currentPlayer.reinforcementArmiesCalc(risk, 0);
			
			System.out.print("Press any key to continue...");
			sIn = in.nextLine();
			// reinforcement phase
			
			this.updatePhaseInfo("Reinforcement Phase", currentPlayer
			    .getplayerName(), reinforcementPhaseInfo(currentPlayer));
			this.setChanged();
			this.notifyObservers(phaseInfo);
			
			currentPlayer.reinforcement();
			
			System.out.print("Press any key to continue...");
			sIn = in.nextLine();
			
			/// Attack phase
			this.updatePhaseInfo("Attack phase", currentPlayer
			    .getplayerName(), attackphaseInfo(currentPlayer));
			this.setChanged();
			this.notifyObservers(phaseInfo);
			
			won = currentPlayer.attack(this);
			
			if (this.Players.getPlayerList().size() == 1)
			  {
				endGame = true;
				System.out.println("Player " + this.Players.getPlayerList()
				    .get(0).getplayerName() + " Won the game");
				winner = this.Players.getPlayerList().get(0).getplayerName();
			  }
			  
			// Fortification phase
			if (!endGame)
			  {
				
				System.out.print("Press any key to continue...");
				sIn = in.nextLine();
				
				this.updatePhaseInfo("Fortification phase", currentPlayer
				    .getplayerName(), fortificationPhaseInfo(currentPlayer));
				this.setChanged();
				this.notifyObservers(phaseInfo);
				
				currentPlayer.fortification();
				if (won)
				  {
					Card card = this.getDeck().drawCard();
					currentPlayer.addNewCard(card);
					System.out.println(currentPlayer
					    .getplayerName() + " has received " + card
					        .getCardName() + " card!");
				  }
				if (turnID >= this.Players.getPlayerList().size())
				  turnID = 0;
			  }
			  
			String userInput = UserInputOutput.getInstance()
			    .requestUserInput("Do you want to save the game Y/N?");
			
			if (userInput.equals("Y"))
			  {
				
				SaveGame saveGame = new SaveGame();
				try
				  {
					saveGame.updateGame(this);
				  } catch (TransformerException | IOException e)
				  {
					
					e.printStackTrace();
				  }
				  
			  }
			  
		  }
		  
		return winner;
	  }
	  
	/**
	 * generate different reinforcement phase information based on player type
	 * 
	 * @param currentPlayer player playing this round.
	 * @return reinforcement phase info based on player strategy
	 */
	private String reinforcementPhaseInfo(Player currentPlayer)
	  {
		
		String phaseInfoString = "";
		switch (currentPlayer.getClass().getSimpleName())
		  {
			case "HumanPlayer":
			  phaseInfoString = "1- Calculate addtional armies.\n2- Allocate additional armies granted to player.\n";
			  break;
			case "AggressivePlayer":
			  phaseInfoString = "1- Calculate addtional armies.\n2- Reinforce the strongest country which can attack.\n";
			  break;
			case "BenevolentPlayer":
			  phaseInfoString = "1- Calculate addtional armies.\n2- Reinforce the weakest country.\n";
			  break;
			case "CheaterPlayer":
			  phaseInfoString = "1- Calculate addtional armies.\n2- double the number of armies in all player's countries.\n";
			  break;
			case "RandomPlayer":
			  phaseInfoString = "1- Calculate addtional armies.\n2- Reinforce a random country.\n";
			  break;
		  }
		  
		return phaseInfoString;
	  }
	  
	/**
	 * setter for deck of cards
	 * 
	 * @param deck deck of cards
	 */
	public void setDeck(DeckOfCards deck)
	  {
		this.deck = deck;
	  }
	  
	/**
	 * get the game map object
	 * 
	 * @param risk game map object
	 */
	public void setGameMap(GameMap risk)
	  {
		this.risk = risk;
	  }
	  
	/**
	 * setter for players array list
	 * 
	 * @param players players array list
	 */
	public void setPlayers(PlayerModel players)
	  {
		Players = players;
	  }
	  
	/**
	 * setter for player world domination view
	 * 
	 * @param playerWorldDominationView player world domination view
	 */
	public void setPlayerWorldDominationView(
	    PlayersWorldDominationView playerWorldDominationView)
	  {
		this.playerWorldDominationView = playerWorldDominationView;
	  }
	  
	/**
	 * setter for turn ID
	 * 
	 * @param turnID turn ID
	 */
	public void setTurnID(int turnID)
	  {
		this.turnID = turnID;
	  }
	  
	/**
	 * to update number of times players exchanged cards
	 * 
	 * @param turnInCardsCount new number of times cards been exchanged.
	 */
	public void setTurnInCardsCount(int turnInCardsCount)
	  {
		Constants.turnInCards = turnInCardsCount;
	  }
	  
	/**
	 * sets up the game by calling the distributeCountries which randomly
	 * distributes countries among players. Once the countries are distributed,
	 * armies are distributed among countries the player own.
	 *
	 */
	@Override
	public void setUpGame()
	  {
		
		StartUp.distributeCountries(Players, risk);
		
		ArrayList<Player> players = Players.getPlayerList();
		
		for (Player player : players)
		  {
			player.initialdistributeArmies();
		  }
		  
		for (GraphNode gNode : risk.getGameMapGraph().getGraphNodes())
		  {
			gNode.getNodeData().addObserver(gNode.getNodeData().getViewer());
			
		  }
		for (Player player : players)
		  {
			player.reinforcementArmiesCalc(risk, 0);
		  }
		  
		deck = new DeckOfCards(risk.getGameMapGraph().getGraphNodes());
		
	  }
	  
  }
