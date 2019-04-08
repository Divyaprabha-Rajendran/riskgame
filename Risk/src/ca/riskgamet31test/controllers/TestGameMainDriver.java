
package ca.riskgamet31test.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import ca.riskgamet31.controllers.GameMainDriver;
import ca.riskgamet31.controllers.MainDriver;
import ca.riskgamet31.controllers.PlayerModel;
import ca.riskgamet31.controllers.StartUpPhase;
import ca.riskgamet31.exceptions.InvalidContinentException;
import ca.riskgamet31.exceptions.InvalidCountryException;
import ca.riskgamet31.exceptions.InvalidGraphException;
import ca.riskgamet31.exceptions.InvalidLinkException;
import ca.riskgamet31.exceptions.InvalidPlayerNameException;
import ca.riskgamet31.maincomps.GameMap;
import ca.riskgamet31.maincomps.Player;
import ca.riskgamet31.maincomps.RandomPlayer;

/**
 * Tests the GameMainDriver class for creating players
 * 
 * 
 * @author Chitra
 * @version 1.1
 * @since 1.0
 * 
 */
public class TestGameMainDriver
  {
	/**
	 * GameMainDriver Class Reference
	 */
	static GameMainDriver G1;
	/**
	 * StartUpPhase Class Reference
	 * 
	 */
	static StartUpPhase s1;
	/**
	 * PlayerModel Class Reference
	 * 
	 */
	static PlayerModel P1, pm;
	/**
	 * Player Class Reference
	 * 
	 */
	static RandomPlayer p1, p2;
	static GameMap M1;
	
	/**
	 * Object created before all the test method
	 * 
	 * @throws NullPointerException                           when value is null
	 * @throws ca.riskgamet31.exceptions.InvalidNameException when Name is
	 *                                                        invalid
	 * @throws InvalidPlayerNameException                     when player name
	 *                                                        is duplicate
	 */
	@BeforeClass
	public static void Testsetup() throws NullPointerException,
	    ca.riskgamet31.exceptions.InvalidNameException,
	    InvalidPlayerNameException
	  {
		G1 = new GameMainDriver();
		s1 = new StartUpPhase();
		P1 = new PlayerModel();
		pm = new PlayerModel();
		p1 = new RandomPlayer("p1", 3);
		p2 = new RandomPlayer("p2", 4);
		
	  }
	  
	/**
	 * 
	 * Testing the Create Player method
	 * 
	 * @throws ca.riskgamet31.exceptions.InvalidNameException when Name is
	 *                                                        invalid
	 * @throws NullPointerException                           when value is null
	 * 
	 */
	@Test
	public void TestcreatePlayer() throws NullPointerException,
	    ca.riskgamet31.exceptions.InvalidNameException
	  {
		P1 = G1.getPlayerList();
		int a1 = P1.getPlayerList().size();
		String inputfromtestplayer = "Dubai\n10\nrussia\n2\n" + "Dubai\nRussia\n5\n" + "Y\ndubai\npakistan\ny\n3\n";
		String input = "3\nAGG|P1\nAGG|P2\nAGG|P3\n" + inputfromtestplayer;
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		System.out.println(a1);
		G1.createPlayer();
		int a2 = P1.getPlayerList().size();
		System.out.println(a2);
		assertNotEquals(a2, a1);
		
	  }
	  
	/**
	 * 
	 * Testing the Startup phase method
	 * 
	 * @throws InvalidGraphException                          when map is
	 *                                                        invalid graph
	 * @throws ca.riskgamet31.exceptions.InvalidNameException when the Name
	 *                                                        given is invalid
	 * @throws InvalidCountryException                        when the country
	 *                                                        is duplicate
	 * @throws InvalidContinentException                      when the continent
	 *                                                        is duplicate
	 * @throws InvalidLinkException                           when the link is
	 *                                                        disconnected
	 * @throws Exception                                      when there is
	 *                                                        exception is
	 *                                                        unexpectedly
	 */
	@Test
	public void TestStartup() throws InvalidGraphException,
	    ca.riskgamet31.exceptions.InvalidNameException, InvalidCountryException,
	    InvalidContinentException, InvalidLinkException, Exception
	  {
		File xmlFile = new File(System
		    .getProperty("user.dir") + "\\Risk_MapData\\small_map.xml");
		pm.setPlayerList(p1);
		pm.setPlayerList(p2);
		s1.setPlayerCount(2); 
	    M1=G1.createGameMap(xmlFile.getPath());
	    s1.distributeCountriesSequ(pm, M1);
		System.out.println(p1.getPlayerCountriesGNodes());
		System.out.println(p2.getPlayerCountriesGNodes());
		assertEquals("[INDIA:1:p1]", p1.getPlayerGraph().getGraphNodes().get(2)
		    .toString());
	  }
	  
	/**
	 * reading the invalid map
	 * 
	 * @throws InvalidGraphException   when map is invalid graph
	 * @throws InvalidCountryException when the country is duplicate
	 * @throws InvalidLinkException    when the link is disconnected
	 * @throws Exception               when there is exception is unexpectedly
	 */
	@Test(expected = ca.riskgamet31.exceptions.InvalidNameException.class)
	public void TestCreateMap() throws InvalidGraphException,
	    InvalidCountryException, InvalidLinkException, Exception
	  {
		File xmlFile = new File(System
		    .getProperty("user.dir") + "\\Risk_MapData\\test_default_map_invalid_contient_name.xml");
		G1.createGameMap(xmlFile.getPath());
		
	  }
	
	/**
	 * reading the disconnected continent
	 * 
	 * @throws InvalidGraphException   when map is invalid graph
	 * @throws InvalidCountryException when the country is duplicate
	 * @throws InvalidLinkException    when the link is disconnected
	 * @throws Exception               when there is exception is unexpectedly
	 */
	@Test(expected = ca.riskgamet31.exceptions.InvalidGraphException.class)
	public void TestCreateMap2() throws InvalidGraphException,
	    InvalidCountryException, InvalidLinkException, Exception
	  {
		File xmlFile = new File(System
		    .getProperty("user.dir") + "\\Risk_MapData\\test_map_continent_not_connected.xml");
		G1.createGameMap(xmlFile.getPath());
		
	  }
	
	/**
	 * reading the disconnected map
	 * 
	 * @throws InvalidGraphException   when map is invalid graph
	 * @throws InvalidCountryException when the country is duplicate
	 * @throws InvalidLinkException    when the link is disconnected
	 * @throws Exception               when there is exception is unexpectedly
	 */
	@Test(expected = ca.riskgamet31.exceptions.InvalidGraphException.class)
	public void TestCreateMap3() throws InvalidGraphException,
	    InvalidCountryException, InvalidLinkException, Exception
	  {
		File xmlFile = new File(System
		    .getProperty("user.dir") + "\\Risk_MapData\\test_map_not_connected.xml");
		G1.createGameMap(xmlFile.getPath());
		
	  }
	
	
	
	
  }
