package ca.riskgamet31.utility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/*import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
*/
import ca.riskgamet31.controllers.GameMainDriver;
import ca.riskgamet31.controllers.PlayerModel;
import ca.riskgamet31.maincomps.*;
import ca.riskgamet31.views.PlayersWorldDominationView;

class Backup implements java.io.Serializable 
{
	private String xmlPath;
	private int turnInCardsCount;
    private int turnId;
    private GameMap gameMap;
	private DeckOfCards deck;
    private PlayerModel players;
    
    private PlayersWorldDominationView playerWorldDominationView;
    
	public String getXmlPath() {
		return xmlPath;
	}



	public void setXmlPath(String xmlPath) {
		this.xmlPath = xmlPath;
	}



	public int getTurnInCardsCount() {
		return turnInCardsCount;
	}



	public void setTurnInCardsCount(int turnInCardsCount) {
		this.turnInCardsCount = turnInCardsCount;
	}



	public int getTurnId() {
		return turnId;
	}



	public void setTurnId(int turnId) {
		this.turnId = turnId;
	}



	public DeckOfCards getDeck() {
		return deck;
	}



	public void setDeck(DeckOfCards deck) {
		this.deck = deck;
	}



	public PlayerModel getPlayers() {
		return players;
	}

	public void setPlayers(PlayerModel players) {
		this.players = players;
	}



	public PlayersWorldDominationView getPlayerWorldDominationView() {
		return playerWorldDominationView;
	}



	public void setPlayerWorldDominationView(PlayersWorldDominationView playerWorldDominationView) {
		this.playerWorldDominationView = playerWorldDominationView;
	}



	public GameMap getGameMap()
	  {
		return gameMap;
	  }



	public void setGameMap(GameMap gameMap)
	  {
		this.gameMap = gameMap;
	  }

	
}

 