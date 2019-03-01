package ca.riskgamet31.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;


import ca.riskgamet31.maincomps.GameMap;
import ca.riskgamet31.maincomps.GraphNode;
import ca.riskgamet31.maincomps.Player;
import ca.riskgamet31.maincomps.PlayerModel;

public class StartUpPhase 
{
	private int playerCount;
	
	
	public int getPlayerCount() {
		return playerCount;
	}

	public void setPlayerCount(int playerCount) {
		this.playerCount = playerCount;
	}

	
	public int getArmy(int NoOfPlayers)
	  {
		  switch (NoOfPlayers)
		  {
		case 3:
			return 35;
		case 4:
			return 30;
		case 5:
			return 25;
		case 6:
			return 20;
		default:
			return -1;
		  }
	  }
	
	
	public Player createPlayers(String playerName)
	{
		int army_count=getArmy(playerCount);
		Player player=new Player(playerName,army_count);
		return player;
	}
	
	public void distributeCountries(PlayerModel players, GameMap map )
	{
		ArrayList<GraphNode> graph_nodes = map.getGameMapGraph().getGraphNodes();
		int watchdog=0;
		for (GraphNode node : graph_nodes)
		{
			if(watchdog<playerCount)
			{
				Player curr_player=players.getPlayerList().get(watchdog);
				//System.out.println(curr_player.getplayersName());
				//System.out.println(node.getNodeData().getCountryName()+"--------->"+node.getNodeData().getArmies());
				System.out.println(curr_player.getplayerName());
				System.out.println(node.getNodeData().getCountryName()+"--------->"+node.getNodeData().getArmies());
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
	
	public void distributeArmies(PlayerModel players)
	{
		Scanner scan = new Scanner(System.in);
		for (Player player : players.getPlayerList())
		{
			System.out.println("Assigning armies for Player "+player.getplayerName());
			while (player.getPlayerArmies()!=0)
			{
				System.out.println("Number of armies left..."+player.getPlayerArmies());
				ArrayList<GraphNode> country_nodes=player.getCountry();
				HashSet<String> owned_by_player=new HashSet<String>();
				for (GraphNode node : country_nodes)
				{
					owned_by_player.add(node.getNodeData().getCountryName());
					System.out.println(node.getNodeData().getCountryName()+"--------->"+node.getNodeData().getArmies());
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
