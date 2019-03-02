package ca.riskgamet31.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

import ca.riskgamet31.*;
import ca.riskgamet31.maincomps.Continent;
import ca.riskgamet31.maincomps.GameMap;
import ca.riskgamet31.maincomps.Graph;
import ca.riskgamet31.maincomps.GraphNode;
import ca.riskgamet31.maincomps.Player;
import ca.riskgamet31.maincomps.PlayerModel;
import ca.riskgamet31.mapdata.CreateMap;

public class StartGame 
{
	GameMap Risk;
	PlayerModel Players;
	StartUpPhase StartUp;
	
	public StartGame() 
	{
		Risk=null;
		Players=new PlayerModel();
		StartUp = new StartUpPhase();
	}
	
	public void getMapInput()
	{
		
	}
	
	public void createGameMap(String xmlpath)
	{
		CreateMap cmap=new CreateMap(xmlpath);
		HashMap<String,Continent> continentsList = cmap.generateGraph();
		Graph gameMapGraph = new Graph(cmap.getAllCountryNodes());
		
		Risk=new GameMap(xmlpath, continentsList, gameMapGraph);
		
		cmap.displayMap();	
	}
	
	public void startUp(int no_players) 
	{
		StartUp.setPlayerCount(no_players);
		Scanner scan = new Scanner(System.in);
		for (int i=0;i<no_players;i++)
    	{
    		System.out.println("Enter player name...");
    		String name = scan.nextLine();
    		Player player=StartUp.createPlayers(name);
    		Players.setPlayerList(player);
    	}
		StartUp.distributeCountries(Players, Risk);
		StartUp.distributeArmies(Players);
	}
	
	
    public static void main(String args[])
    {
    	try
    	{
    	System.out.println("Welcome to RISK...");
    	StartGame game=new StartGame();
    	Scanner scan = new Scanner(System.in);
    	System.out.println("Enter the XML path...");
    	String xmlpath=scan.nextLine();
    	game.createGameMap(xmlpath);
    	
    	System.out.println("Enter the number of players..");
    	int no_players = Integer.parseInt(scan.nextLine());
    		
    	game.startUp(no_players);
    	System.out.println("Start up phase completed....");
    	
    	for (Player player : game.Players.getPlayerList())
    	{
    		System.out.println(player.getplayerName());
    		for (GraphNode node : player.getCountry())
    		{
    			System.out.println(node.getNodeData().getCountryName()+"--------->"+node.getNodeData().getArmies());
    		}
    	}
    	}
    	catch(InputMismatchException | NumberFormatException e)
    	{
    		System.out.println("Please, enter a valid input");
    	}
    	catch(Exception e)
    	{
    		System.out.println("Error: " + e.getMessage());
    	}
    }
}
