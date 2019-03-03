package ca.riskgamet31.controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.InvalidNameException;
import javax.swing.JFileChooser;

import ca.riskgamet31.exceptions.InvalidGraphException;
import ca.riskgamet31.exceptions.InvalidPlayerCountInput;
import ca.riskgamet31.exceptions.InvalidPlayerException;
import ca.riskgamet31.exceptions.InvalidPlayerNameException;
import ca.riskgamet31.maincomps.Continent;
import ca.riskgamet31.maincomps.GameMap;
import ca.riskgamet31.maincomps.Graph;
import ca.riskgamet31.maincomps.Player;
import ca.riskgamet31.maincomps.PlayerModel;
import ca.riskgamet31.mapdata.CreateMap;

/**
 * Main driver class for the execution of game.
 * Loading map, creating players and distribution of countries and armies happens here.
 * @author Divyaprabha Rajendran
 * @version 1.0
 *
 */
public class GameMainDriver
  {
	
	private GameMap Risk;
	private PlayerModel Players;
	StartUpPhase StartUp;
		
	public GameMainDriver()
	{
		Risk=null;
		Players=new PlayerModel();
		StartUp = new StartUpPhase();
	}
	
	/**
	 * Gives different options to create a gamemap and returns the file path of the chosen XML file
	 * @return xmlFilePath
	 *
	 */

	public String getFileInput() throws IOException
	{

		File xmlFile = new File(System.getProperty("user.dir")+"\\Risk_MapData\\default_map.xml");
		
		//File xmlFile = new File("Risk_MapData\\default_map.xml"); //commented because of conflicts

		Scanner scan = new Scanner(System.in);
		
	    
	    JFileChooser chooser= new JFileChooser();
	    
	    
	    boolean continueEditing=true;
	    while(continueEditing) 
	    {
	    	System.out.println("Choose an option...");
		    System.out.println("1. open a new map...");
		    System.out.println("2. edit existing map...");
		    System.out.println("3. open default map..");
		    System.out.println("4. start playing..");
		    int option=scan.nextInt();
		    
	    switch(option)
	    {
	    case 1: {
	    		int choice = chooser.showOpenDialog(chooser);
	    		if (choice != JFileChooser.APPROVE_OPTION) return "";
	    	    xmlFile = chooser.getSelectedFile();
	    	    if (Desktop.isDesktopSupported())
	            {
	                Desktop.getDesktop().open(xmlFile);
	            }
	            else
	            {
	                System.out.println("not a valid file");
	            }
	    		break;
	    		}
	    case 2: {
	    		xmlFile = new File(System.getProperty("user.dir")+"\\Risk_MapData\\test_default_map.xml");

	    		//xmlFile = new File("Risk_MapData\\edit_default_map.xml"); //commented because of conflicts

	    		if (Desktop.isDesktopSupported())
	            	{
	                	Desktop.getDesktop().open(xmlFile);
	            	}
	            	else
	            	{
	            		System.out.println("not a valid file");
	            	}
	    		break;
	    		}
	    case 3: {
	    		xmlFile = new File(System.getProperty("user.dir")+"\\Risk_MapData\\default_map.xml");

	    		//xmlFile = new File("Risk_MapData\\default_map.xml"); //commented because of conflictsS
	    		break;
	    		}
	    case 4:
	    		{
	    			continueEditing=false;
	    			break;
	    		}
	    	}
	    };
	    
	    return xmlFile.getPath();
	}
	
	/**
	 * Creates the gamemap from the CreateMap Class for the player.
	 * @param xmlPath
	 *
	 */

	public void createGameMap(String xmlpath)
	{
		CreateMap cmap=new CreateMap(xmlpath);
		HashMap<String,Continent> continentsList = cmap.generateGraph();
		Graph gameMapGraph = new Graph(cmap.getAllCountryNodes());
		if(gameMapGraph.isConnected())
		{
			System.out.println("The graph is valid");
			gameMapGraph.viewGraph();
			Risk=new GameMap(xmlpath, continentsList, gameMapGraph);
		}
		else
			throw new InvalidGraphException("Invalid Map..graph is no connected..");
		
		cmap.displayMap();	
	}
	
	/**
	 * Creates players for the game after checking pre-conditions.
	 * A player name cannot have special characters and duplication is not allowed.
	 * @throws InvalidNameException 
	 * @throws NullPointerException 
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
		no_players=scan.nextInt();
		if(no_players > 6 || no_players <3)
		{
			throw new InvalidPlayerCountInput("You can choose players of 3 to 6");
		}
		}
		catch(InvalidPlayerCountInput e)
		{
			System.out.println(e);
		}
		}while(no_players > 6 || no_players < 3);
		StartUp.setPlayerCount(no_players);
		
		int i=0;
		while(i<no_players)
    	{
			try
			{
    		System.out.println("Enter player name...");
    		String name = scan.next();
    		Player player=StartUp.createPlayers(name);
			Players.setPlayerList(player);
			i=i+1;
			}
			catch(InvalidPlayerException exception )
			{
				System.out.println(exception);
			}
			catch(InvalidPlayerNameException exception)
			{
				System.out.println(exception);
			}
    	}
	}
	
	/**
	 * sets up the game by calling the distributeCountries which randomly distributes countries among players.
	 * Once the countries are distributed, armies are distributed among countries the player own.
	 *
	 */

	public void setUpGame()
	{
		StartUp.distributeCountries(Players, Risk);
		StartUp.distributeArmies(Players);
	}
	
		
	public static void main(String[] args)
	  {
		GameMainDriver driver = new GameMainDriver();
		try 
		{
			String xmlpath=driver.getFileInput();
			driver.createGameMap(xmlpath);
			driver.createPlayer();
			driver.setUpGame();
		} 
		catch (IOException e) 
		{	
			e.printStackTrace();
		} 
		catch (NullPointerException e) 
		{
			e.printStackTrace();
		} 
		catch (InvalidNameException e) 
		{	
			e.printStackTrace();
		}
		
	  }
	  
  }
