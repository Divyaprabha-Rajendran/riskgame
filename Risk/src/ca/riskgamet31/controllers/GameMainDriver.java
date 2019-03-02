package ca.riskgamet31.controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import javax.swing.JFileChooser;

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
		File xmlFile = new File("Risk_MapData\\default_map.xml");
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
	    		xmlFile = new File("Risk_MapData\\edit_default_map.xml");
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
	    		xmlFile = new File("Risk_MapData\\default_map.xml");
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
		
		Risk=new GameMap(xmlpath, continentsList, gameMapGraph);
		
		cmap.displayMap();	
	}
	
	/**
	 * Creates players for the game after checking pre-conditions.
	 * A player name cannot have special characters and duplication is not allowed.
	 * 
	 */

	public void createPlayer()
	{
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the number of players...");
		int no_players=scan.nextInt();
		StartUp.setPlayerCount(no_players);
		Pattern name_pattern = Pattern.compile("[^A-Za-z0-9]");
		int i=0;
		while(i<no_players)
    	{
    		System.out.println("Enter player name...");
    		String name = scan.next();
    		if (!name.equals(null))
    		{
    		Matcher match = name_pattern.matcher(name);
    			if(!match.find()==true)
    			{
    				Player player=StartUp.createPlayers(name);
    				Players.setPlayerList(player);
    				i=i+1;
    			}
    			else
    				System.out.println("invalid player name..");
    		}
    		else
    		{
    			System.out.println("Please enter player name again..");
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
		
	  }
	  
  }
