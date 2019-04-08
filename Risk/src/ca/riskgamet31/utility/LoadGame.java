package ca.riskgamet31.utility;

import org.w3c.dom.*;

import ca.riskgamet31.controllers.GameMainDriver;
import ca.riskgamet31.controllers.PlayerModel;
import ca.riskgamet31.exceptions.InvalidContinentException;
import ca.riskgamet31.exceptions.InvalidCountryException;
import ca.riskgamet31.exceptions.InvalidGraphException;
import ca.riskgamet31.exceptions.InvalidLinkException;
import ca.riskgamet31.exceptions.InvalidNameException;
import ca.riskgamet31.maincomps.DeckOfCards;
import ca.riskgamet31.maincomps.GameMap;
import ca.riskgamet31.maincomps.Player;
import ca.riskgamet31.views.PlayersWorldDominationView;

import javax.xml.parsers.*;
import java.io.*;

public class LoadGame 
{
	Backup currBkp;
	 
	 public LoadGame()
	 {
		 currBkp=new Backup();
	 }
    public void resumeGame() throws InvalidGraphException, InvalidNameException, InvalidCountryException, InvalidContinentException, InvalidLinkException, Exception
    {
      
      GameMainDriver gm = new GameMainDriver();
    	String xmlFilePath = System
			    .getProperty("user.dir") +"\\Risk_MapData\\game_obj.ser";
    	    	
    	FileInputStream file = new FileInputStream(xmlFilePath); 
        ObjectInputStream in = new ObjectInputStream(file); 
           
        currBkp = (Backup)in.readObject(); 
          
        in.close(); 
        file.close(); 
        
       // String xmlpath = currBkp.getXmlPath();
        
        //GameMap gMap = gm.createGameMap(xmlpath);
        
        gm.setGameMap(currBkp.getGameMap());
        
        int turnInCardsCount = currBkp.getTurnId();
        
        gm.setTurnInCardsCount(turnInCardsCount);
        
        int turnId = currBkp.getTurnId();
        
        gm.setTurnID(turnId);
        
        DeckOfCards deck = currBkp.getDeck();
        
        gm.setDeck(deck);
        
        PlayerModel players = currBkp.getPlayers();
        
        gm.setPlayers(players);
        
        PlayersWorldDominationView playerWorldDominationView = currBkp.getPlayerWorldDominationView();
        
        gm.setPlayerWorldDominationView(playerWorldDominationView);
        
        gm.playGame();
        
        
    }
    
}
