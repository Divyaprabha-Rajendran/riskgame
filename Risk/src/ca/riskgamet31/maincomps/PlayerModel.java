package ca.riskgamet31.maincomps;

import java.util.ArrayList;

import ca.riskgamet31.exceptions.InvalidLinkException;
import ca.riskgamet31.exceptions.InvalidPlayerException;

public class PlayerModel 
{
      ArrayList<Player> PlayerList;
      
    public PlayerModel()
     {   	
    	this.PlayerList=new ArrayList<Player>(); 
     }

	public ArrayList<Player> getPlayerList() 
	{
		return PlayerList;
	}

	public void setPlayerList(Player player) throws InvalidPlayerException 
	{
		validateNewPlayer(player);
		this.PlayerList.add(player);
	}
      
    public void validateNewPlayer(Player player) throws InvalidPlayerException
    {
    	for  (Player curr_player : PlayerList)
    	{
    		if(curr_player.getplayerName().equals(player.getplayerName()))
    		{
    			throw new InvalidPlayerException("Player already exists");
    		}
    	}
    }
	
      
}
