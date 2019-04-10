package ca.riskgamet31.controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;

import ca.riskgamet31.exceptions.InvalidContinentException;
import ca.riskgamet31.exceptions.InvalidCountryException;
import ca.riskgamet31.exceptions.InvalidGraphException;
import ca.riskgamet31.exceptions.InvalidLinkException;
import ca.riskgamet31.exceptions.InvalidNameException;
import ca.riskgamet31.exceptions.InvalidPlayerException;
import ca.riskgamet31.exceptions.InvalidPlayerNameException;
import ca.riskgamet31.maincomps.Card;
import ca.riskgamet31.maincomps.DeckOfCards;
import ca.riskgamet31.maincomps.GameMap;
import ca.riskgamet31.maincomps.GraphNode;
import ca.riskgamet31.maincomps.Player;
import ca.riskgamet31.utility.Constants;
import ca.riskgamet31.utility.InputValidator;
import ca.riskgamet31.utility.UserInputOutput;

/**
 * Main driver class for tournament mode execution of game. Loading map,
 * creating players and distribution of countries and armies happens here.
 * 
 * @author Fareed Tayar
 * @version 3.0
 * @since 3.0
 */
public class TournamentMainDriver extends Observable implements MainDriver
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
	 * list of maps will be player during the tournament
	 */
	private ArrayList<File> tournamentMaps;
	
	/**
	 * list of players participating in the tournament.
	 */
	private ArrayList<String> tournamentPlayers;
	
	/**
	 * number of games will be player per map during the tournament
	 */
	private int noOfGames;
	
	/**
	 * maximum number of turns allowed during on game.
	 */
	private int noOfTurns;
	
	/**
	 * constructor for tournament main driver
	 */
	public TournamentMainDriver()
	  {
		
		tournamentMaps = new ArrayList<>();
		tournamentPlayers = new ArrayList<>();
		noOfGames = 0;
		noOfTurns = 0;
		risk = null;
		Players = new PlayerModel();
		StartUp = new StartUpPhase();
		Constants.turnInCards = 1;
		
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
	 * to update number of times players exchanged cards
	 * 
	 * @param turnInCardsCount new number of times cards been exchanged.
	 */
	public void setTurnInCardsCount(int turnInCardsCount)
	  {
		Constants.turnInCards = turnInCardsCount;
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
	 * to collect different inputs needed to play the tournament
	 * 
	 * @param GM main driver of the game
	 * @return a positive string indicating collecting needed information.
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
		InputValidator iv = new InputValidator();
		while (continueEditing)
		  {
			String input = "";
			do
			  {
				input = UserInputOutput.getInstance()
				    .requestUserInput("Choose an option...\n1. add maps to tournament...\n2. add players to tournament.\n3. set maximum number of allowed turns per game.\n4. set number of games per map.\n5. start tournament..");
				
			  } while (!iv.validateNumbers(input));
			int option = Integer.parseInt(input);
			
			switch (option)
			  {
				
				case 1:
				  {
					if (tournamentMaps.size() < 5)
					  {
						chooser.setRequestFocusEnabled(true);
						int choice = chooser.showOpenDialog(chooser);
						chooser.setRequestFocusEnabled(true);
						chooser.requestFocus();
						if (choice != JFileChooser.APPROVE_OPTION)
						  xmlFile = new File(System
						      .getProperty("user.dir") + "\\Risk_MapData\\default_map.xml");
						else
						  xmlFile = chooser.getSelectedFile();
						
						if (Desktop.isDesktopSupported())
						  {
							Desktop.getDesktop().open(xmlFile);
							
							try
							  {
								
								GameMap aMap = GM.createGameMap(xmlFile
								    .getPath());
								tournamentMaps.add(xmlFile);
								
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
						  } else
						  {
							System.out.println("not a valid file");
						  }
					  } else
					  {
						System.out
						    .println("Number of maps should be between 1 and 5.");
						System.exit(0);
					  }
					break;
				  }
				case 2:
				  {
					String userInput = UserInputOutput.getInstance()
					    .requestUserInput("Enter Players Type i.e. AGG|RAN|BEN|CHE");
					boolean addPlayerTypes = true;
					String[] playerTypes = userInput.split(Pattern.quote("|"));
					for (int i = 0; i < playerTypes.length; i++)
					  {
						
						if (!playerTypes[i].equals("AGG") && !playerTypes[i]
						    .equals("RAN") && !playerTypes[i]
						        .equals("BEN") && !playerTypes[i].equals("CHE"))
						  {
							addPlayerTypes = false;
							System.out
							    .println(playerTypes[i] + " is not an accepted player type for tournament");
						  }
					  }
					  
					if (playerTypes.length < 2 || playerTypes.length > 4)
					  {
						addPlayerTypes = false;
						System.out
						    .println("Number of players should be between 2 and 4");
					  }
					  
					if (addPlayerTypes)
					  {
						tournamentPlayers.addAll(Arrays.asList(playerTypes));
						System.out.println("Tournament Players types will be:");
						System.out.println(tournamentPlayers.toString());
					  }
					break;
				  }
				case 3:
				  {
					String userInput = "";
					boolean addNumOfTurns = true;
					do
					  {
						userInput = UserInputOutput.getInstance()
						    .requestUserInput("Enter number of allowed turns per game between 10 and 50 turns");
						
					  } while (!iv.validateNumbers(userInput));
					  
					if (Integer.parseInt(userInput) < 10 || Integer
					    .parseInt(userInput) > 50)
					  {
						addNumOfTurns = false;
						System.out
						    .println("Valid number of Turns is between 10 and 50.");
					  }
					if (addNumOfTurns)
					  {
						noOfTurns = Integer.parseInt(userInput);
						System.out
						    .println("Maximum number of turns is set to: " + noOfTurns);
					  }
					break;
				  }
				case 4:
				  {
					String userInput = "";
					
					boolean addNumOfGames = true;
					do
					  {
						userInput = UserInputOutput.getInstance()
						    .requestUserInput("Enter number of games per map between 1 and 5.");
						
					  } while (!iv.validateNumbers(userInput));
					  
					if (Integer.parseInt(userInput) < 1 || Integer
					    .parseInt(userInput) > 5)
					  {
						addNumOfGames = false;
						System.out
						    .println("Valid number of games is between 1 and 5 games per map");
					  }
					if (addNumOfGames)
					  {
						noOfGames = Integer.parseInt(userInput);
						System.out
						    .println("Number of games is set to: " + noOfGames);
					  }
					break;
				  }
				case 5:
				  {
					
					continueEditing = false;
					
					break;
				  }
			  }
		  }
		  
		return "Maps and other input are collected.";
	  }
	  
	/**
	 * Creates players for the game after checking Pre-conditions. A player name
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
		
		StartUp.setPlayerCount(tournamentPlayers.size());
		int i = 1;
		for (String playerName : tournamentPlayers)
		  {
			Player player;
			try
			  {
				player = StartUp.createPlayers(playerName + "|Player" + i++);
				Players.setPlayerList(player);
				
			  } catch (InvalidPlayerNameException | InvalidPlayerException e)
			  {
				e.printStackTrace();
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
	 * play of of game within the tournament
	 * 
	 * @return draw if no winner or winner name.
	 */
	@Override
	public String playGame()
	  {
		
		Players = new PlayerModel();
		StartUp = new StartUpPhase();
		setTurnInCardsCount(1);
		try
		  {
			this.createPlayer();
		  } catch (NullPointerException | InvalidNameException e)
		  {
			
			e.printStackTrace();
		  }
		this.setUpGame();
		
		boolean endGame = false;
		Player currentPlayer;
		int turnID = 0;
		int realTurnID = 0;
		String winner = "Draw";
		boolean won = false;
		
		while (!endGame && realTurnID < this.noOfTurns)
		  {
			realTurnID++;
			won = false;
			currentPlayer = this.Players.getPlayerList().get(turnID++);
			currentPlayer.reinforcementArmiesCalc(risk, 0);
			
			boolean turnedInCards = false;
			if (currentPlayer.getHand().isEligibleToExchange())
			  {
				while (currentPlayer.getHand().mustTurnInCards())
				  {
					turnedInCards = currentPlayer
					    .executeTurnInCard(this, "Must exchange cards: Select cards to exchange, ex: 235");
				  }
				  
				if (!turnedInCards)
				  {
					turnedInCards = currentPlayer
					    .executeTurnInCard(this, "Select cards to exchange, ex: 235 , 999 to exit");
				  }
				  
			  } else
			  {
				System.out.println("You are not eligible to exchange cards.");
			  }
			  
			currentPlayer.reinforcement();
			
			won = currentPlayer.attack(this);
			
			if (this.Players.getPlayerList().size() == 1)
			  {
				endGame = true;
				System.out.println("Player " + this.Players.getPlayerList()
				    .get(0).getplayerName() + " Won the game");
				winner = this.Players.getPlayerList().get(0).getplayerName();
			  }
			  
			if (!endGame)
			  {
				
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
	  
	/**
	 * main executor for the tournament printing a table showing tournament
	 * result.
	 */
	@Override
	public void execute()
	  {
		try
		  {
			this.getFileInput(this);
		  } catch (IOException e)
		  {
			e.printStackTrace();
		  }
		  
		ArrayList<ArrayList<String>> tournamentResult = new ArrayList<>(tournamentMaps
		    .size());
		
		for (int i = 0; i < tournamentMaps.size(); i++)
		  {
			
			ArrayList<String> mapResult = new ArrayList<>();
			
			for (int j = 0; j < noOfGames; j++)
			  {
				
				try
				  {
					this.risk = this.createGameMap(this.tournamentMaps.get(i)
					    .getPath());
				  } catch (Exception e)
				  {
					
					e.printStackTrace();
				  }
				  
				String winner = playGame();
				mapResult.add(winner);
				
			  }
			tournamentResult.add(mapResult);
		  }
		  
		System.out.printf("%-25s", "");
		for (int i = 1; i <= noOfGames; i++)
		  {
			System.out.printf("%-25s", "Game" + i);
			
		  }
		System.out.println("\n");
		int s = 1;
		
		for (ArrayList<String> mapRes : tournamentResult)
		  {
			
			System.out.printf("%-25s", "Map" + s++);
			
			for (String roundResult : mapRes)
			  {
				System.out.printf("%-25s", roundResult);
			  }
			System.out.println("");
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
	 * sets the game map object
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
	 * @return object for the deck of cards
	 */
	public DeckOfCards getDeck()
	  {
		return deck;
	  }
	  
	/**
	 * getting tournament map list
	 * 
	 * @return arraylist for tournament map list
	 */
	public ArrayList<File> getTournamentMaps()
	  {
		return tournamentMaps;
	  }
	  
	/**
	 * set list of maps in tournament mode
	 * 
	 * @param tournamentMaps list of maps to set in tournament mode
	 */
	public void setTournamentMaps(ArrayList<File> tournamentMaps)
	  {
		this.tournamentMaps = tournamentMaps;
	  }
	  
	/**
	 * get tournament players list
	 * 
	 * @return get tournament players list
	 */
	public ArrayList<String> getTournamentPlayers()
	  {
		return tournamentPlayers;
	  }
	  
	/**
	 * to send tournament players
	 * 
	 * @param tournamentPlayers set tournament players
	 */
	public void setTournamentPlayers(ArrayList<String> tournamentPlayers)
	  {
		this.tournamentPlayers = tournamentPlayers;
	  }
	  
	/**
	 * get number of games
	 * 
	 * @return get number of games
	 */
	public int getNoOfGames()
	  {
		return noOfGames;
	  }
	  
	/**
	 * set number of games
	 * 
	 * @param noOfGames set number of games
	 */
	public void setNoOfGames(int noOfGames)
	  {
		this.noOfGames = noOfGames;
	  }
	  
	/**
	 * get number of turns
	 * 
	 * @return get number of turns
	 */
	public int getNoOfTurns()
	  {
		return noOfTurns;
	  }
	  
	/**
	 * set number of turns
	 * 
	 * @param noOfTurns set number of turns
	 */
	public void setNoOfTurns(int noOfTurns)
	  {
		this.noOfTurns = noOfTurns;
	  }
	  
  }
