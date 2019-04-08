package ca.riskgamet31.controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Observable;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;

import com.sun.xml.internal.fastinfoset.tools.TransformInputOutput;

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
import ca.riskgamet31.utility.Constants;
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
	 * player world domination view
	 */
	private PlayersWorldDominationView playerWorldDominationView;
	
	/**
	 * constructor for game main driver
	 */
	private ArrayList<File> tournamentMaps;
	
	private ArrayList<String> tournamentPlayers;
	
	private int noOfGames;
	
	private int noOfTurns;
	
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
		//phaseInfo = new ArrayList<>();
		
		//playerWorldDominationView = new PlayersWorldDominationView();
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
			System.out.println("1. add maps to tournament...");
			System.out.println("2. add players to tournament.");
			System.out.println("3. set maximum number of allowed turns per game.");
			System.out.println("4. set number of games per map.");
			System.out.println("5. start playing..");
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
						
						try
						  {
							
							GameMap aMap = GM.createGameMap(xmlFile.getPath());
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
					  
					break;
				  }
				case 2:
				  {
					String userInput = UserInputOutput.getInstance().requestUserInput("Enter Players Type i.e. AGG|RAN|BEN|CHE");
					
					String[] playerTypes = userInput.split(Pattern.quote("|"));
					
					tournamentPlayers.addAll(Arrays.asList(playerTypes));
					
					break;
				  }
				case 3:
					  {
						String userInput = UserInputOutput.getInstance().requestUserInput("Enter maximum number of allowed turns per game");
						
						noOfTurns = Integer.parseInt(userInput);
						
						break;
					  }
				case 4:
					  {
						String userInput = UserInputOutput.getInstance().requestUserInput("Enter number of games per map");
						
						noOfGames = Integer.parseInt(userInput);
						
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
		
		StartUp.setPlayerCount(tournamentPlayers.size());
		int i = 1;
		for (String playerName : tournamentPlayers) {
		Player player;
		try
		  {
			player = StartUp.createPlayers(playerName+"|Player"+i++);
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
	 * a method representing each turn
	 * 
	 */
	@Override
	public String playGame()
	  {
		
		//phaseInfo.clear();
		
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
			//this.risk.viewGameMap();
		
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
					    .executeTurnInCard(this,"Select cards to exchange, ex: 235 , 999 to exit");
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
				winner = this.Players.getPlayerList()
				    .get(0).getplayerName();
			  }
			
			// Fortification phase
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
	  
	
	private String fortificationPhaseInfo(Player currentPlayer)
	  {
		// "1- Choose to fortify or not.\n2- Move as many armies as need to one territory.\n"
		String phaseInfoString = "";
		switch (currentPlayer.getClass().getSimpleName()){
		  case "HumanPlayer":
			phaseInfoString=  "1- Choose to fortify or not.\n2- Move as many armies as need to one territory.\n";
			break;
		  case "AggressivePlayer":
			phaseInfoString =  "1- Always fortify when possible.\n2- fortify a country in order to maximize aggregation in one country.\n";
			break;
		  case "BenevolentPlayer":
			phaseInfoString =  "1- fortify the weakest country\n";
			break;
		  case "CheaterPlayer":
			phaseInfoString =  "1- doubles the number of armies on its countries that have neighbors belong to other players\n";
			break;
		  case "RandomPlayer":
			phaseInfoString =  "1- fortify a random country.\n";
			break;
		}
		
		return phaseInfoString;

	  }

	private String attackphaseInfo(Player currentPlayer)
	  {
		//"1- Choose to attack or not.\n2- Attack as many times as needed\n3- Win a card if one territory been occupied."
		String phaseInfoString = "";
		switch (currentPlayer.getClass().getSimpleName()){
		  case "HumanPlayer":
			phaseInfoString=  "1- Choose to attack or not.\n2- Attack as many times as needed\n3- Win a card if one territory been occupied.\n";
			break;
		  case "AggressivePlayer":
			phaseInfoString =  "1- Always attack when possible.\n2- keep Attacking from country with highest number of armies\n3- Win a card if one territory been occupied.\n";
			break;
		  case "BenevolentPlayer":
			phaseInfoString =  "1- Never attack\n";
			break;
		  case "CheaterPlayer":
			phaseInfoString =  "1- Always concqure adjecent countries to his owned countries\n";
			break;
		  case "RandomPlayer":
			phaseInfoString =  "1- Random number of attacks.\n2- against random countries\n";
			break;
		}
		
		return phaseInfoString;
	  }

	private String reinforcementPhaseInfo(Player currentPlayer)
	  {
		
		
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
			this.getFileInput(this);
		  } catch (IOException e)
		  {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
		
		ArrayList<ArrayList<String>> tournamentResult = new ArrayList<>(tournamentMaps.size());
		
		for (int i = 0 ; i < tournamentMaps.size() ; i++) {
		  
		  ArrayList<String> mapResult = new ArrayList<>();
		  
		  for (int j = 0; j < noOfGames ; j++)
			{
		  
		  try
			  {
				//this.setTurnInCardsCount(1);
				this.risk = this.createGameMap(this.tournamentMaps.get(i).getPath());
			  } catch (Exception e)
			  {
				
				e.printStackTrace();
			  }  

		  String winner = playGame();
		  mapResult.add(winner);
		  
			}
		  tournamentResult.add(mapResult); 
		}
		
		/*System.out.print("\t");
		for(int i=1 ; i <= noOfGames; i++)
		  System.out.print("Game"+i+"\t\t");
		System.out.println("");
		  int s = 1;
		for (ArrayList<String> mapRes : tournamentResult) {
		  
		  System.out.print("Map-"+s+++"  ");
		  for (String roundResult : mapRes) {
			System.out.print(roundResult+"\t");
		  }
		  System.out.println("");
		  
		}*/
		
		//String.format("%20s %20s %12s"
		
		System.out.printf("%-25s","");
		for(int i=1 ; i <= noOfGames; i++)
		  {
		  System.out.printf("%-25s","Game"+i);
		  
	  }
		  System.out.println("\n");
		  int s = 1;
		
		  for (ArrayList<String> mapRes : tournamentResult) {
			  
			  System.out.printf("%-25s","Map"+s++);
			  
			  for (String roundResult : mapRes) {
				System.out.printf("%-25s",roundResult);
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
