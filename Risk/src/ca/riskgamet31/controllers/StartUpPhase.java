package ca.riskgamet31.controllers;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;


import ca.riskgamet31.maincomps.GameMap;
import ca.riskgamet31.maincomps.GraphNode;
import ca.riskgamet31.maincomps.Player;
import ca.riskgamet31.maincomps.PlayerModel;
import ca.riskgamet31.Configuration.Constants;

/**
 * Creates players, assign countries to players and distributes players according to player's input.
 * @author Divyaprabha Rajendran
 *
 */
public class StartUpPhase 
{
	private int playerCount;
	
	
	public int getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	/**
	 * returns the number of armies for each player according to number of players.
	 * @param noOfPlayers
	 * @return numberOfArmies.
	 */

	public int getArmy(int noOfPlayers)
	  {
		  switch (noOfPlayers)
		  {
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
	 *Creates a player object and add it to playermodel
	 * @param playername
	 * @return player
	 *
	 */

	public Player createPlayers(String playerName)
	{
		int army_count=getArmy(playerCount);
		Player player=new Player(playerName,army_count);
		return player;
	}
	
	/**
	 * Distribute the countries available among players in  roundrobin fashion.
	 * @param players
	 * @param gamemap
	 *
	 */
	
	public void distributeCountries(PlayerModel players, GameMap map )
	{
		ArrayList<GraphNode> graph_nodes = map.getGameMapGraph().getGraphNodes();
		int watchdog=0;
		// changed by fareed to provide random distribution even for the same map file
		//for (GraphNode node : graph_nodes)
		SecureRandom random = new SecureRandom();
		
		while (graph_nodes.size() > 0){
			GraphNode node = graph_nodes.remove(random.nextInt(graph_nodes.size()));
		  if(watchdog<playerCount)
			{
				Player curr_player=players.getPlayerList().get(watchdog);
				//System.out.println(curr_player.getplayersName());
				//System.out.println(node.getNodeData().getCountryName()+"--------->"+node.getNodeData().getArmies());
				//System.out.println(curr_player.getplayerName());
				//System.out.println(node.getNodeData().getCountryName()+"--------->"+node.getNodeData().getArmies());
				curr_player.addCountry(node);
				curr_player.decrementArmies(1);
				node.getNodeData().setCurrentOccupier(curr_player.getplayerName());
				node.getNodeData().setArmies(1);
				watchdog=watchdog+1;
			}
			else
				watchdog=0;
		}
	}
	
	/**
	 * Distribute the armies of every player among the countries the player owns. 
	 * The method executes till all the player's armies are distributed.
	 * @param players 
	 */
	
	public void distributeArmies(PlayerModel players)
	{
		Scanner scan = new Scanner(System.in);
		for (Player player : players.getPlayerList())
		{	System.out.println();
			System.out.println("Assigning armies for Player "+player.getplayerName());
			while (player.getPlayerArmies()!=0)
			{
			  	System.out.println();
				System.out.println("Number of armies left..."+player.getPlayerArmies());
				ArrayList<GraphNode> country_nodes=player.getCountry();
				HashSet<String> owned_by_player=new HashSet<String>();
				for (GraphNode node : country_nodes)
				{
					owned_by_player.add(node.getNodeData().getCountryName());
					//System.out.println(node.getNodeData().getCountryName()+"--------->"+node.getNodeData().getArmies());
					System.out.println(node.toString());
				}
				System.out.println("Enter the country name");
				String country_name = scan.next().trim().toUpperCase();
				
				if (!country_name.equals(null))
				{
				if(owned_by_player.contains(country_name))
				{
					System.out.println("Enter the number of armies to place..");
					int armies=scan.nextInt();
					System.out.println(armies);
					if(armies<=player.getPlayerArmies())
					{
						for (GraphNode node : country_nodes)
						{
							//System.out.println(node.getNodeData().getCountryName()+"--------->"+node.getNodeData().getArmies());
							if (node.getNodeData().getCountryName().equals(country_name.trim()))
							{
								node.getNodeData().setArmies(node.getNodeData().getArmies()+armies);
								player.decrementArmies(armies);
							}
						}
					}
					else
					{
						System.out.println("No sufficient armies");
					}
				}
				else
				{
					System.out.println("Country not found...try again");
				}
				}
				else
				{
					System.out.println("null value...try again");
				}
			}
		}
	}
			
}
