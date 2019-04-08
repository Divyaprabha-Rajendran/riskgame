package ca.riskgamet31.utility;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.xml.transform.TransformerException;

import ca.riskgamet31.controllers.GameMainDriver;

public class SaveGame implements java.io.Serializable
{

	 Backup currBkp;
	 
	 public SaveGame()
	 {
		 currBkp=new Backup();
	 }
	public void updateGame(GameMainDriver gm) throws TransformerException, IOException
	{
		String xmlFilePath = System
			    .getProperty("user.dir") +"\\Risk_MapData\\game_obj.ser";
	    
		currBkp.setGameMap(gm.getGameMap());
		currBkp.setXmlPath(gm.getGameMap().getGameMapFile());
		currBkp.setTurnInCardsCount(gm.getTurnInCardsCount());
		currBkp.setTurnId(gm.getTurnID());
		
		currBkp.setDeck(gm.getDeck());
		currBkp.setPlayers(gm.getPlayerList());
		
		currBkp.setPlayerWorldDominationView(gm.getPlayerWorldDominationView());
		
		FileOutputStream file = new FileOutputStream(xmlFilePath); 
       ObjectOutputStream out = new ObjectOutputStream(file); 
         
        
       out.writeObject(currBkp); 
         
       out.close(); 
       file.close(); 

	}
	
	
}

