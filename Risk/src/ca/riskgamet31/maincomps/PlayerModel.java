package ca.riskgamet31.maincomps;

import java.util.ArrayList;

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

	public void setPlayerList(Player player) 
	{
		this.PlayerList.add(player);
	}
      
      
      
}
