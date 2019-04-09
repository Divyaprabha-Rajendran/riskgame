package ca.riskgamet31test.utility;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.transform.TransformerException;

import org.junit.Test;

import ca.riskgamet31.controllers.CreateMap;
import ca.riskgamet31.controllers.GameMainDriver;
import ca.riskgamet31.controllers.PlayerModel;
import ca.riskgamet31.controllers.StartUpPhase;
import ca.riskgamet31.exceptions.InvalidContinentException;
import ca.riskgamet31.exceptions.InvalidCountryException;
import ca.riskgamet31.exceptions.InvalidGraphException;
import ca.riskgamet31.exceptions.InvalidLinkException;
import ca.riskgamet31.exceptions.InvalidNameException;
import ca.riskgamet31.maincomps.AggressivePlayer;
import ca.riskgamet31.maincomps.Continent;
import ca.riskgamet31.maincomps.Country;
import ca.riskgamet31.maincomps.DeckOfCards;
import ca.riskgamet31.maincomps.GameMap;
import ca.riskgamet31.maincomps.Graph;
import ca.riskgamet31.maincomps.GraphNode;
import ca.riskgamet31.maincomps.HumanPlayer;
import ca.riskgamet31.maincomps.Player;
import ca.riskgamet31.utility.SaveGame;
import ca.riskgamet31.views.PlayersWorldDominationView;

/**
 * To test the saving of game state and creation of the serialized file with the object.
 * @author Divyaprabha Rajendran.
 * @version 1.0
 */
public class SaveGameTest {

/**
 * GameMainDriver for Loading the game.
 */
	static GameMainDriver game; 
	/**
	 * Test the creation of GameMainDriver object to be compared with the serialized object.
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
	 * To test the creation of gamemaindriver object from the serialized file.
	 *  
	 * @throws TransformerException  TransformerException
	 * @throws IOException   IOException for handling file exception
	 * @throws InvalidGraphException Invalid graph.
	 * @throws InvalidNameException Improper name in countries or continents.
	 * @throws InvalidCountryException Invalid Country
	 * @throws InvalidContinentException Invalid Continent
	 * @throws InvalidLinkException Invalid link in graph.
	 * @throws Exception Any other exception.
	 */
	@Test
	public void testUpdateGame() throws TransformerException, IOException ,InvalidGraphException, InvalidNameException, InvalidCountryException, InvalidContinentException, InvalidLinkException, Exception
	{
				
	    SaveGame save = new SaveGame();
		save.updateGame(game);
		File f = new File(System
			    .getProperty("user.dir") +"\\Risk_MapData\\game_obj.ser");
		assertEquals(true, f.isFile());		
	}
}
