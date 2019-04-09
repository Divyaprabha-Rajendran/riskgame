package ca.riskgamet31.utility;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.xml.transform.TransformerException;

import ca.riskgamet31.controllers.GameMainDriver;

/**
 * Creating a backup object and storing the serialized object during the
 * runtime.
 * 
 * @author Divyaprabha Rajendran
 * @version 1.0
 */
public class SaveGame implements java.io.Serializable
  {
	
	Backup currBkp;
	
	public SaveGame()
	  {
		currBkp = new Backup();
	  }
	  
	/**
	 * creates a backup object by storing the data as serialized object in a
	 * file.
	 *
	 * @param game To save the current state of the game.
	 * 
	 * @throws TransformerException Exception in transforming the object into
	 *                              serilaizable
	 * @throws IOException          Exception while handling the input and
	 *                              output to the file
	 */
	
	public void updateGame(GameMainDriver game) throws TransformerException,
	    IOException
	  {
		String xmlFilePath = System
		    .getProperty("user.dir") + "\\Risk_MapData\\game_obj.ser";
		
		currBkp.setGameMap(game.getGameMap());
		currBkp.setXmlPath(game.getGameMap().getGameMapFile());
		currBkp.setTurnInCardsCount(game.getTurnInCardsCount());
		currBkp.setTurnId(game.getTurnID());
		
		currBkp.setDeck(game.getDeck());
		currBkp.setPlayers(game.getPlayerList());
		
		currBkp.setPlayerWorldDominationView(game
		    .getPlayerWorldDominationView());
		
		FileOutputStream file = new FileOutputStream(xmlFilePath);
		ObjectOutputStream out = new ObjectOutputStream(file);
		
		out.writeObject(currBkp);
		
		out.close();
		file.close();
		
	  }
	  
  }
