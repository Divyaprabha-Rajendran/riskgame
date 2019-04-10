package ca.riskgamet31.utility;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import ca.riskgamet31.controllers.GameMainDriver;
import ca.riskgamet31.controllers.PlayerModel;
import ca.riskgamet31.exceptions.InvalidContinentException;
import ca.riskgamet31.exceptions.InvalidCountryException;
import ca.riskgamet31.exceptions.InvalidGraphException;
import ca.riskgamet31.exceptions.InvalidLinkException;
import ca.riskgamet31.exceptions.InvalidNameException;
import ca.riskgamet31.maincomps.DeckOfCards;
import ca.riskgamet31.views.PlayersWorldDominationView;

/**
 * Restoring the data from the serialized object.
 * 
 * @author Divyaprabha Rajendran
 * @version 1.0
 */
public class LoadGame
  {
	/**
	 * Backup Object to save the game state
	 */
	Backup currBkp;
	
	/**
	 * Constructor for creating the backup object.
	 */
	public LoadGame()
	  {
		currBkp = new Backup();
	  }
	  
	/**
	 * creates a new gamemaindriver object from the serialized object and starts
	 * playing where the game crashed or stopped.
	 * 
	 * @throws InvalidGraphException     Exception if the graph is invalid for
	 *                                   reasons like null value.
	 * @throws InvalidNameException      Exception if the countries or
	 *                                   continents having not proper names.
	 * @throws InvalidCountryException   Exception if the country is not valid.
	 * @throws InvalidContinentException Exception if the continent is not
	 *                                   valid.
	 * @throws InvalidLinkException      Exception if the links established are
	 *                                   not proper.
	 */
	
	public void resumeGame() throws InvalidGraphException, InvalidNameException,
	    InvalidCountryException, InvalidContinentException,
	    InvalidLinkException, Exception
	  {
		
		GameMainDriver gm = new GameMainDriver();
		String xmlFilePath = System
		    .getProperty("user.dir") + "\\Risk_MapData\\RiskGameSave.ser";
		
		FileInputStream file = new FileInputStream(xmlFilePath);
		ObjectInputStream in = new ObjectInputStream(file);
		
		currBkp = (Backup) in.readObject();
		
		in.close();
		file.close();
		
		gm.setGameMap(currBkp.getGameMap());
		
		int turnInCardsCount = currBkp.getTurnId();
		
		gm.setTurnInCardsCount(turnInCardsCount);
		
		int turnId = currBkp.getTurnId();
		
		gm.setTurnID(turnId);
		
		DeckOfCards deck = currBkp.getDeck();
		
		gm.setDeck(deck);
		
		PlayerModel players = currBkp.getPlayers();
		
		gm.setPlayers(players);
		
		PlayersWorldDominationView playerWorldDominationView = currBkp
		    .getPlayerWorldDominationView();
		
		gm.setPlayerWorldDominationView(playerWorldDominationView);
		
		gm.playGame();
		
	  }
	  
  }
