package ca.riskgamet31.controllers;

import java.util.ArrayList;

import ca.riskgamet31.exceptions.InvalidPlayerException;
import ca.riskgamet31.maincomps.Player;

/**
 * array list of players
 * 
 * @author Divia
 *
 */
public class PlayerModel
  {
	
	/**
	 * array list of players
	 */
	ArrayList<Player> PlayerList;
	
	/**
	 * constructor to create new array list
	 */
	public PlayerModel()
	  {
		this.PlayerList = new ArrayList<Player>();
	  }
	  
	/**
	 * to get players list
	 * 
	 * @return players list
	 */
	public ArrayList<Player> getPlayerList()
	  {
		return PlayerList;
	  }
	  
	/**
	 * to set player
	 * 
	 * @param player player to be added to the list
	 * @throws InvalidPlayerException InvalidPlayerException
	 */
	public void setPlayerList(Player player) throws InvalidPlayerException
	  {
		validateNewPlayer(player);
		this.PlayerList.add(player);
	  }
	  
	/**
	 * to validate the player
	 * 
	 * @param player player to be validated
	 * @throws InvalidPlayerException InvalidPlayerException
	 */
	public void validateNewPlayer(Player player) throws InvalidPlayerException
	  {
		for (Player curr_player : PlayerList)
		  {
			if (curr_player.getplayerName().equals(player.getplayerName()))
			  {
				throw new InvalidPlayerException("Player already exists");
			  }
		  }
	  }
	  
  }
