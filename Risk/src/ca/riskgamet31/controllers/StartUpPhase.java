package ca.riskgamet31.controllers;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;


import ca.riskgamet31.exceptions.InvalidNameException;
import ca.riskgamet31.exceptions.InvalidPlayerNameException;
import ca.riskgamet31.maincomps.GameMap;
import ca.riskgamet31.maincomps.GraphNode;
import ca.riskgamet31.maincomps.Player;
import ca.riskgamet31.utility.Constants;

/**
 * Creates players, assign countries to players and distributes players
 * according to player's input.
 * 
 * @author Divyaprabha Rajendran
 *
 */
public class StartUpPhase
  {
	/**
	 * count of players
	 */
	private int playerCount;
	
	/**
	 * to get count of players
	 * 
	 * @return count of players
	 */
	public int getPlayerCount()
	  {
		return playerCount;
	  }
	  
	/**
	 * to set count of players
	 * 
	 * @param playerCount count of players
	 */
	public void setPlayerCount(int playerCount)
	  {
		this.playerCount = playerCount;
	  }
	  
	/**
	 * returns the number of armies for each player according to number of
	 * players.
	 * 
	 * @param noOfPlayers noOfPlayers
	 * @return numberOfArmies.
	 */
	public int getArmy(int noOfPlayers)
	  {
		switch (noOfPlayers)
		  {
			case 2:
			  return Constants.ARMIES_2_PLAYERS;
			case 3:
			  return Constants.ARMIES_3_PLAYERS;
			case 4:
			  return Constants.ARMIES_4_PLAYERS;
			case 5:
			  return Constants.ARMIES_5_PLAYERS;
			case 6:
			  return Constants.ARMIES_6_PLAYERS;
			default:
			  return Constants.ARMIES_NOTVALID_PLAYERS;
		  }
	  }
	  
	/**
	 * Creates a player object and add it to playermodel
	 * 
	 * @param playerName name of the player
	 * @return player
	 * @throws InvalidPlayerNameException InvalidPlayerNameException
	 * @throws NullPointerException NullPointerException
	 * @throws InvalidNameException if the name has numbers or symbol
	 */
	public Player createPlayers(String playerName) throws NullPointerException,
	     InvalidPlayerNameException, InvalidNameException
	  {
		int army_count = getArmy(playerCount);
		Player player = new Player(playerName, army_count);
		return player;
	  }
	  
	/**
	 * Distribute the countries available among players in roundrobin fashion.
	 * @param players players list
	 * @param map         game map
	 *
	 */
	public void distributeCountries(PlayerModel players, GameMap map)
	  {
		ArrayList<GraphNode> actual_graph_nodes = map.getGameMapGraph()
		    .getGraphNodes();
		int actualSize = map.getGameMapGraph().getGraphNodes().size();
		ArrayList<String> graph_nodes = new ArrayList<>(actualSize);
		
		for (int i = 0; i < actualSize; i++)
		  {
			graph_nodes.add(actual_graph_nodes.get(i).getNodeData()
			    .getCountryName());
			
		  }
		  
		int watchdog = 0;
		SecureRandom random = new SecureRandom();
		
		while (graph_nodes.size() > 0)
		  {
			
			if (watchdog < playerCount)
			  {
				String node = graph_nodes.remove(random.nextInt(graph_nodes
				    .size()));
				Player curr_player = players.getPlayerList().get(watchdog);
				GraphNode selectedNode = null;
				
				for (GraphNode gnode : actual_graph_nodes)
				  {
					if (gnode.getNodeData().getCountryName().equals(node))
					  selectedNode = gnode;
					
				  }
				  
				curr_player.addCountry(selectedNode);
				curr_player.decrementArmies(1);
				selectedNode.getNodeData().setCurrentOccupier(curr_player
				    .getplayerName());
				selectedNode.getNodeData().setArmies(1);
				watchdog = watchdog + 1;
			  } else
			  watchdog = 0;
		  }
	  }
	  
	
	public void distributeCountriesSequ(PlayerModel players, GameMap map)
	  {
		ArrayList<GraphNode> actual_graph_nodes = map.getGameMapGraph()
		    .getGraphNodes();
		int actualSize = map.getGameMapGraph().getGraphNodes().size();
		ArrayList<String> graph_nodes = new ArrayList<>(actualSize);
		
		for (int i = 0; i < actualSize; i++)
		  {
			graph_nodes.add(actual_graph_nodes.get(i).getNodeData()
			    .getCountryName());
			
		  }
		  
		int watchdog = 0;
		
		while (graph_nodes.size() > 0)
		  {
			
			if (watchdog < playerCount)
			  {
				String node = graph_nodes.remove(0);
				Player curr_player = players.getPlayerList().get(watchdog);
				GraphNode selectedNode = null;
				
				for (GraphNode gnode : actual_graph_nodes)
				  {
					if (gnode.getNodeData().getCountryName().equals(node))
					  selectedNode = gnode;
					
				  }
				  
				curr_player.addCountry(selectedNode);
				curr_player.decrementArmies(1);
				selectedNode.getNodeData().setCurrentOccupier(curr_player
				    .getplayerName());
				selectedNode.getNodeData().setArmies(1);
				watchdog = watchdog + 1;
			  } else
			  watchdog = 0;
		  }
	  }
	
	
	/**
	 * Distribute the armies of every player among the countries the player
	 * owns. The method executes till all the player's armies are distributed.
	 * 
	 * @param player player object to distribute armies for
	 */
	public void distributeArmies(Player player)
	  {
		Scanner scan = new Scanner(System.in);
		
		System.out.println();
		System.out.println("Assigning armies for Player " + player
		    .getplayerName());
		while (player.getPlayerArmies() != 0)
		  {
			System.out.println();
			System.out.println("Number of armies left..." + player
			    .getPlayerArmies());
			ArrayList<GraphNode> country_nodes = player.getCountry();
			HashSet<String> owned_by_player = new HashSet<String>();
			for (GraphNode node : country_nodes)
			  {
				owned_by_player.add(node.getNodeData().getCountryName());
				System.out.println(node.toString());
			  }
			System.out.println("Enter the country name");
			String country_name = scan.next().trim().toUpperCase();
			
			if (!country_name.equals(null))
			  {
				if (owned_by_player.contains(country_name))
				  {
					int armies = 0;
					boolean validInput = false;
					String text = "";
					do
					  {
						
						System.out
						    .println("Enter the number of armies to place..");
						do
						  {
							if (scan.hasNextLine())
							  text = scan.nextLine();
						  } while (text.length() == 0);
						if (text.matches("\\d+"))
						  validInput = true;
					  } while (!validInput);
					armies = Integer.parseInt(text);
					
					if (armies <= player.getPlayerArmies())
					  {
						for (GraphNode node : country_nodes)
						  {
							if (node.getNodeData().getCountryName()
							    .equals(country_name.trim()))
							  {
								node.getNodeData().setArmies(node.getNodeData()
								    .getArmies() + armies);
								player.decrementArmies(armies);
							  }
						  }
					  } else
					  {
						System.out.println("No sufficient armies");
					  }
				  } else
				  {
					System.out.println("Country not found...try again");
				  }
			  } else
			  {
				System.out.println("null value...try again");
			  }
		  }
		  
	  }
	  
  }
