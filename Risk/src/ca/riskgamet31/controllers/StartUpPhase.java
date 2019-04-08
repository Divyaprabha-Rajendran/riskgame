package ca.riskgamet31.controllers;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.regex.Pattern;

import ca.riskgamet31.exceptions.InvalidNameException;
import ca.riskgamet31.exceptions.InvalidPlayerNameException;
import ca.riskgamet31.maincomps.AggressivePlayer;
import ca.riskgamet31.maincomps.BenevolentPlayer;
import ca.riskgamet31.maincomps.CheaterPlayer;
import ca.riskgamet31.maincomps.GameMap;
import ca.riskgamet31.maincomps.GraphNode;
import ca.riskgamet31.maincomps.HumanPlayer;
import ca.riskgamet31.maincomps.Player;
import ca.riskgamet31.maincomps.RandomPlayer;
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
	 * Creates a player object and add it to player model
	 * 
	 * @param playerData data string for player type and name info
	 * @return Player return created player object.
	 * @throws InvalidPlayerNameException InvalidPlayerNameException
	 * @throws NullPointerException       NullPointerException
	 * @throws InvalidNameException       if the name has numbers or symbol
	 */
	public Player createPlayers(String playerData) throws NullPointerException,
	    InvalidPlayerNameException, InvalidNameException
	  {
		String[] playerInfo = playerData.split(Pattern.quote("|"));
		String playerType = playerInfo[0];
		String playerName = playerInfo[1] + "-" + playerType;
		int army_count = getArmy(playerCount);
		Player player = null;
		switch (playerType)
		  {
			case "HUM":
			  player = new HumanPlayer(playerName, army_count);
			  break;
			case "AGG":
			  player = new AggressivePlayer(playerName, army_count);
			  break;
			case "BEN":
			  player = new BenevolentPlayer(playerName, army_count);
			  break;
			case "CHE":
			  player = new CheaterPlayer(playerName, army_count);
			  break;
			case "RAN":
			  player = new RandomPlayer(playerName, army_count);
			  break;
		  }
		  
		return player;
	  }
	  
	/**
	 * Distribute the countries available among players in roundrobin fashion.
	 * 
	 * @param players players list
	 * @param map     game map
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
	  
  }
