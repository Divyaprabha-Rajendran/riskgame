package ca.riskgamet31.utility;

import ca.riskgamet31.controllers.PlayerModel;
import ca.riskgamet31.maincomps.DeckOfCards;
import ca.riskgamet31.maincomps.GameMap;
import ca.riskgamet31.views.PlayersWorldDominationView;

/**
 * Backup class for storing data that must be used while resuming. Implements
 * Serializable interface for storing objects.
 * 
 * @author Divyaprabha Rajendran
 * @version 1.0
 */
public class Backup implements java.io.Serializable
  {
	/**
	 * String variable to store the xml path.
	 */
	private String xmlPath;
	/**
	 * turnInCardsCount from the game main driver to be restored later.
	 */
	private int turnInCardsCount;
	/**
	 * To store the players turn to resume game
	 */
	private int turnId;
	/**
	 * Gamemap of the game main driver.
	 */
	private GameMap gameMap;
	/**
	 * To store the DeckOfCards object.
	 */
	private DeckOfCards deck;
	/**
	 * To store the players object.
	 */
	private PlayerModel players;
	
	/**
	 * To store the PlayersWorldDominationView of gamemaindriver.
	 */
	private PlayersWorldDominationView playerWorldDominationView;
	
	/**
	 * Getter for XML path.
	 * 
	 * @return xmlpath path of the xml file.
	 */
	public String getXmlPath()
	  {
		return xmlPath;
	  }
	  
	/**
	 * To set the xml path.
	 * 
	 * @param xmlPath To set the xml path.
	 */
	public void setXmlPath(String xmlPath)
	  {
		this.xmlPath = xmlPath;
	  }
	  
	/**
	 * To get the turnInCardsCount
	 * 
	 * @return turnInCardsCount To get current turnInCardsCount
	 */
	public int getTurnInCardsCount()
	  {
		return turnInCardsCount;
	  }
	  
	/**
	 * To set turnInCardsCount
	 * 
	 * @param turnInCardsCount To set turnInCardsCount
	 */
	public void setTurnInCardsCount(int turnInCardsCount)
	  {
		this.turnInCardsCount = turnInCardsCount;
	  }
	  
	/**
	 * To get turnId
	 * 
	 * @return turnId Players turn in gamemaindriver.
	 */
	public int getTurnId()
	  {
		return turnId;
	  }
	  
	/**
	 * To set turnId
	 * 
	 * @param turnId To set turnId
	 */
	public void setTurnId(int turnId)
	  {
		this.turnId = turnId;
	  }
	  
	/**
	 * To get the DeckOfCards object
	 * 
	 * @return DeckOfCards Cards in the deck of gamemaindriver.
	 */
	public DeckOfCards getDeck()
	  {
		return deck;
	  }
	  
	/**
	 * To set DeckOfCards
	 * 
	 * @param deck To set DeckOfCards
	 */
	public void setDeck(DeckOfCards deck)
	  {
		this.deck = deck;
	  }
	  
	/**
	 * To get the players
	 * 
	 * @return players playermodel object
	 */
	public PlayerModel getPlayers()
	  {
		return players;
	  }
	  
	/**
	 * To set the playmodel object.
	 * 
	 * @param players To set the playmodel object.
	 */
	public void setPlayers(PlayerModel players)
	  {
		this.players = players;
	  }
	  
	/**
	 * To get the PlayersWorldDominationView
	 * 
	 * @return PlayersWorldDominationView PlayersWorldDominationView from
	 *         gamemaindriver.
	 */
	public PlayersWorldDominationView getPlayerWorldDominationView()
	  {
		return playerWorldDominationView;
	  }
	  
	/**
	 * To set PlayersWorldDominationView
	 * 
	 * @param playerWorldDominationView To set PlayersWorldDominationView
	 */
	public void setPlayerWorldDominationView(
	    PlayersWorldDominationView playerWorldDominationView)
	  {
		this.playerWorldDominationView = playerWorldDominationView;
	  }
	  
	/**
	 * To get the gamemap object
	 * 
	 * @return gameMap To get the gamemap object.
	 */
	public GameMap getGameMap()
	  {
		return gameMap;
	  }
	  
	/**
	 * To set the gamemap object
	 * 
	 * @param gameMap To set the gamemap object.
	 */
	public void setGameMap(GameMap gameMap)
	  {
		this.gameMap = gameMap;
	  }
	  
  }
