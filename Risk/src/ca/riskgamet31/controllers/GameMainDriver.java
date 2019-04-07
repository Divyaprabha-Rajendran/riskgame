package ca.riskgamet31.controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
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
import ca.riskgamet31.maincomps.Card;
import ca.riskgamet31.maincomps.Continent;
import ca.riskgamet31.maincomps.DeckOfCards;
import ca.riskgamet31.maincomps.GameMap;
import ca.riskgamet31.maincomps.Graph;
import ca.riskgamet31.maincomps.GraphNode;
import ca.riskgamet31.maincomps.Player;
import ca.riskgamet31.utility.InputValidator;
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
	public int turnInCardsCount;
	
	/**
	 * player world domination view
	 */
	private PlayersWorldDominationView playerWorldDominationView;
	
	/**
	 * constructor for game main driver
	 */
	public GameMainDriver()
	  {
		phaseInfo.clear();
		risk = null;
		Players = new PlayerModel();
		StartUp = new StartUpPhase();
		turnInCardsCount = 1;
		//phaseInfo = new ArrayList<>();
		PhaseView phaseview = new PhaseView();
		this.addObserver(phaseview);
		
		playerWorldDominationView = new PlayersWorldDominationView();
	  }
	  
	/**
	 * to get how many times players exchanged cards
	 * 
	 * @return number of times players exchanged cards.
	 */
	public int getTurnInCardsCount()
	  {
		
		return turnInCardsCount;
	  }
	  
	/**
	 * to update number of times players exchanged cards
	 * 
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
	  
 
	/**
	 * a method representing each turn
	 * 
	 */
	@Override
	public String playGame()
	  {
		
		boolean endGame = false;
		Player currentPlayer;
		int turnID = 0;
		String winner = "NA";
		boolean won = false;
		this.addObserver(this.playerWorldDominationView);
		
		while (!endGame)
		  {
			won = false;
			currentPlayer = this.Players.getPlayerList().get(turnID++);
			currentPlayer.reinforcementArmiesCalc(risk, 0);
			
			String userI = UserInputOutput.getInstance().requestUserInput("Press any key to Continue....");
			
			// reinforcement phase
			
			this.updatePhaseInfo("Reinforcement Phase", currentPlayer
			    .getplayerName(), reinforcementPhaseInfo(currentPlayer));
			this.setChanged();
			this.notifyObservers(phaseInfo);
			
			currentPlayer.reinforcement();
			
			userI = UserInputOutput.getInstance().requestUserInput("Press any key to Continue....");
			/// Attack phase
			this.updatePhaseInfo("Attack phase", currentPlayer
			    .getplayerName(), "1- Choose to attack or not.\n2- Attack as many times as needed\n3- Win a card if one territory been occupied.");
			this.setChanged();
			this.notifyObservers(phaseInfo);
			
			won = currentPlayer.attack(this);
			
			if (this.Players.getPlayerList().size() == 1)
			  {
				endGame = true;
				System.out.println("Player " + this.Players.getPlayerList()
				    .get(0).getplayerName() + " Won the game");
				winner = this.Players.getPlayerList()
				    .get(0).getplayerName();
			  }
			
			userI = UserInputOutput.getInstance().requestUserInput("Press any key to Continue....");
			// Fortification phase
			if (!endGame)
			  {
				this.updatePhaseInfo("Fortification phase", currentPlayer
				    .getplayerName(), "1- Choose to fortify or not.\n2- Move as many armies as need to one territory.\n");
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
		  }
		  
		return winner;
	  }
	  
	private String reinforcementPhaseInfo(Player currentPlayer)
	  {
		
		
		//HumanPlayer
		//AggressivePlayer
		//BenevolentPlayer
		//CheaterPlayer
		//RandomPlayer
		
		//"1- Calculate addtional armies.\n2- Allocate additional armies granted to player.\n"
		String phaseInfoString = "";
		switch (currentPlayer.getClass().getSimpleName()){
		  case "HumanPlayer":
			phaseInfoString=  "1- Calculate addtional armies.\n2- Allocate additional armies granted to player.\n";
			break;
		  case "AggressivePlayer":
			phaseInfoString =  "1- Calculate addtional armies.\n2- Reinforce the strongest country which can attack.\n";
			break;
		  case "BenevolentPlayer":
			phaseInfoString =  "1- Calculate addtional armies.\n2- Reinforce the weakest country.\n";
			break;
		  case "CheaterPlayer":
			phaseInfoString =  "1- Calculate addtional armies.\n2- double the number of armies in all player's countries.\n";
			break;
		  case "RandomPlayer":
			phaseInfoString =  "1- Calculate addtional armies.\n2- Reinforce a random country.\n";
			break;
		}
		
		return phaseInfoString;
	  }


	@Override
	public void execute ()
	  {
		
		try
		  {
			String xmlpath = this.getFileInput(this);
			this.createPlayer();
			this.setUpGame();
			this.risk.viewGameMap();
			this.playGame();
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
	 * 
	 * @return game map object
	 */
	public GameMap getGameMap()
	  {
		return risk;
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
	 * Deck of cards object
	 * 
	 * @return object for the deck pf cards
	 */
	public DeckOfCards getDeck()
	  {
		return deck;
	  }
	  
  }
