package ca.riskgamet31test.controllers;

import java.io.File;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.riskgamet31.controllers.TournamentMainDriver;
/**
 * Tests the Player class
 * 
 * @author ish
 * @version 1.1
 * @since 1.0
 */
public class TestTournamentMode {
	/**
	 *  setup Reference
	 */
	static File xmlFile;
	static File xmlFile1;
	static File xmlFile2;
	static File xmlFile3;
	static TournamentMainDriver tGmd;
	/**
	 * Setup method for tournament test class
	 */
	@Before
	public void setUp()
	{
		ArrayList<String> playerTournaments = new ArrayList<>();  
		playerTournaments.add("BEN");
		playerTournaments.add("AGG");
		playerTournaments.add("RAN");
		playerTournaments.add("CHE");
		xmlFile = new File(System
			    .getProperty("user.dir") + "\\Risk_MapData\\default_map.xml");
		xmlFile1 = new File(System
			    .getProperty("user.dir") + "\\Risk_MapData\\map_25.xml");
		xmlFile2 = new File(System
			    .getProperty("user.dir") + "\\Risk_MapData\\map_30.xml");
		xmlFile3 = new File(System
			    .getProperty("user.dir") + "\\Risk_MapData\\small_map.xml");
		
		tGmd = new TournamentMainDriver(); 
		tGmd.getTournamentMaps().add(xmlFile);
		tGmd.getTournamentMaps().add(xmlFile1);
		tGmd.getTournamentMaps().add(xmlFile2);
		tGmd.getTournamentMaps().add(xmlFile3);
		
		tGmd.setNoOfTurns(30);
		tGmd.setNoOfGames(4);
		tGmd.setTournamentPlayers(playerTournaments);
	}
	/**
	 * test execute method for tournament mode
	 */
	@Test
	public void testTournamentMode()
	{
		tGmd.execute();
		
	}
}
