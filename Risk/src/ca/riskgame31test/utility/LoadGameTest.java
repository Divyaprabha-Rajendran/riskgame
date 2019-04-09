package ca.riskgame31test.utility;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

import org.junit.Test;

import ca.riskgamet31.controllers.CreateMap;
import ca.riskgamet31.controllers.GameMainDriver;
import ca.riskgamet31.controllers.PlayerModel;
import ca.riskgamet31.exceptions.InvalidLinkException;
import ca.riskgamet31.maincomps.AggressivePlayer;
import ca.riskgamet31.maincomps.Continent;
import ca.riskgamet31.maincomps.DeckOfCards;
import ca.riskgamet31.maincomps.GameMap;
import ca.riskgamet31.maincomps.Graph;
import ca.riskgamet31.maincomps.HumanPlayer;
import ca.riskgamet31.utility.Backup;
import ca.riskgamet31.views.PlayersWorldDominationView;

/**
 * To test the loading of game from the serialized object. 
 * @author Divyaprabha Rajendran
 * @version 1.0
 */
public class LoadGameTest {

	/**
	 * GameMainDriver for Loading the game.
	 */
	static GameMainDriver game;
	/**
	 * Test the creation of GameMainDriver object to be serialized.
	 * @throws InvalidLinkException Invalid link in graph.
	 * @throws Exception Exception.
	 */
	@Test
	public void testLoadGame() throws InvalidLinkException, Exception {
		game=new GameMainDriver();
		String xmlPath = System.getProperty("user.dir")+ "\\Risk_MapData\\small_map.xml";
		CreateMap cmap = new CreateMap(xmlPath);
		HashMap<String, Continent> continentsList = cmap.generateGraph();
		Graph gameMapGraph = new Graph(cmap.getAllCountryNodes());
		GameMap risk = new GameMap(xmlPath, continentsList, gameMapGraph);
		game.setGameMap(risk);
		game.setTurnInCardsCount(10);
		PlayerModel pm = new PlayerModel();
		pm.setPlayerList(new HumanPlayer("human",4));
		pm.setPlayerList(new AggressivePlayer("dragon",10));
	    game.setPlayers(pm);
	    game.setTurnID(2);
	    DeckOfCards deck = new DeckOfCards(game.getGameMap().getGameMapGraph().getGraphNodes());
	    game.setDeck(deck);
	    game.setPlayerWorldDominationView(new PlayersWorldDominationView());
	    assertNotNull(game);
	}

	/**
	 * To test the creation of serialized object in a file.
	 * @throws IOException IOException 
	 * @throws ClassNotFoundException ClassNotFoundException
	 */
	@Test
	public void testResumeGame() throws IOException, ClassNotFoundException {
		
		String xmlFilePath = System
			    .getProperty("user.dir") +"\\Risk_MapData\\game_obj.ser";
    	    	
    	FileInputStream file = new FileInputStream(xmlFilePath); 
        ObjectInputStream in = new ObjectInputStream(file); 
           
        Backup currBkp = (Backup)in.readObject(); 
          
        in.close(); 
        file.close(); 
        
        assertEquals(currBkp.getGameMap().getGameMapFile(),game.getGameMap().getGameMapFile());
        assertEquals(currBkp.getGameMap().getContinentsList().size(), game.getGameMap().getContinentsList().size());
        assertEquals(currBkp.getGameMap().getGameMapGraph().getGraphNodes().size(), game.getGameMap().getGameMapGraph().getGraphNodes().size());
        assertEquals(currBkp.getPlayers().getPlayerList().size(),game.getPlayerList().getPlayerList().size());
        assertEquals(currBkp.getTurnId(), game.getTurnID());
        assertEquals(currBkp.getTurnInCardsCount(), game.getTurnInCardsCount());
	}

}
