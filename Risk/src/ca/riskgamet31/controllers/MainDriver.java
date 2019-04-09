package ca.riskgamet31.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import ca.riskgamet31.exceptions.InvalidContinentException;
import ca.riskgamet31.exceptions.InvalidCountryException;
import ca.riskgamet31.exceptions.InvalidGraphException;
import ca.riskgamet31.exceptions.InvalidLinkException;
import ca.riskgamet31.exceptions.InvalidNameException;
import ca.riskgamet31.maincomps.Continent;
import ca.riskgamet31.maincomps.GameMap;
import ca.riskgamet31.maincomps.Graph;

/**
 * an abstraction for main game driver for the execution of game. Loading map,
 * creating players and distribution of countries and armies happens here.
 * 
 * @author Fareed Tayr.
 * @version 1.0
 *
 */
public interface MainDriver
  {
	/**
	 * phase info data structure to be passed to phase view observer
	 */
	ArrayList<String> phaseInfo = new ArrayList<String>();
	
	/**
	 * Gives different options to create a gamemap and returns the file path of
	 * the chosen XML file
	 * 
	 * @param GM main driver of the game
	 * @return xmlFilePath map xml file path
	 * @throws IOException IOException
	 *
	 */
	public abstract String getFileInput(MainDriver GM) throws IOException;
	
	/**
	 * Creates the game map from the CreateMap Class for the player.
	 * 
	 * @param xmlpath XML file path
	 * @return Game map object
	 * @throws InvalidGraphException     If the graph is invalid
	 * @throws InvalidNameException      If the name of continent or country has
	 *                                   special characters or numbers
	 * @throws InvalidCountryException   If there is a duplicate country
	 * @throws InvalidContinentException If there is a duplicate continent.
	 * @throws InvalidLinkException      If from and to countries are same.
	 * @throws Exception                 For handling null values and XML
	 *                                   malformed exceptions.
	 */
	default public GameMap createGameMap(String xmlpath)
	    throws InvalidGraphException, InvalidNameException,
	    InvalidCountryException, InvalidContinentException,
	    InvalidLinkException, Exception
	  {
		CreateMap cmap = new CreateMap(xmlpath);
		
		HashMap<String, Continent> continentsList = cmap.generateGraph();
		Graph gameMapGraph = new Graph(cmap.getAllCountryNodes());
		if (gameMapGraph.isConnected())
		  {
			System.out
			    .println("Risk game file and associated graphs and sub-graphs are valid... :)");
			
			GameMap risk = new GameMap(xmlpath, continentsList, gameMapGraph);
			return risk;
		  } else
		  throw new InvalidGraphException("Invalid Map..graph is no connected..");
		
	  }
	  
	/**
	 * Creates players for the game after checking pre-conditions. A player name
	 * cannot have special characters and duplication is not allowed.
	 * 
	 * @throws InvalidNameException InvalidNameException
	 * @throws NullPointerException NullPointerException
	 * 
	 */
	public void createPlayer() throws NullPointerException,
	    InvalidNameException;
	
	/**
	 * sets up the game by calling the distributeCountries which randomly
	 * distributes countries among players. Once the countries are distributed,
	 * armies are distributed among countries the player own.
	 *
	 */
	public void setUpGame();
	
	/**
	 * to update current phase information
	 * 
	 * @param phaseName       current phase name
	 * @param playerName      current player name
	 * @param phaseActionInfo current phase general actions
	 */
	default public void updatePhaseInfo(String phaseName, String playerName,
	    String phaseActionInfo)
	  {
		
		this.phaseInfo.clear();
		
		phaseInfo.add(phaseName);
		phaseInfo.add(playerName);
		phaseInfo.add(phaseActionInfo);
		
	  }
	  
	/**
	 * a method representing each turn
	 * 
	 * @return name of winner.
	 */
	public String playGame();
	
	/**
	 * main method of the game
	 * 
	 */
	public void execute();
	
	/**
	 * Array list for players
	 * 
	 * @return an array list of players
	 */
	public PlayerModel getPlayerList();
	
  }
