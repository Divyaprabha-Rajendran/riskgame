package ca.riskgamet31test.controllers;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.BeforeClass;
import org.junit.Test;

import ca.riskgamet31.controllers.GameMainDriver;
import ca.riskgamet31.exceptions.InvalidPlayerException;
import ca.riskgamet31.exceptions.InvalidPlayerNameException;
import ca.riskgamet31.maincomps.AggressivePlayer;
import ca.riskgamet31.maincomps.Card;
import ca.riskgamet31.maincomps.Continent;
import ca.riskgamet31.maincomps.Country;
import ca.riskgamet31.maincomps.GameMap;
import ca.riskgamet31.maincomps.Graph;
import ca.riskgamet31.maincomps.GraphNode;
import ca.riskgamet31.maincomps.Player;

/**
 * Tests the Player class
 * 
 * @author YD
 * @version 1.1
 * @since 1.0
 */
public class TestAggresivePlayer
  {
	/**
	 * Country Class Reference
	 */
	static Country c1, c2, c3, c4, c5, c6, c7, c8;
	/**
	 * GraphNode Class Reference
	 */
	static GraphNode g1, g2, g3, g4, g5, g6, g7, g8;
	/**
	 * Player Class Reference
	 */
	static Player p1;
	static Player p2;
	static Player p3;
	/**
	 * Graph Class Reference
	 */
	static Graph G1, G2;
	/**
	 * Continent Class Reference
	 */
	static Continent C1, C2, C3;
	/**
	 * GameMap Class Reference
	 */
	static GameMap GM1;
	
	static GameMainDriver driver;
	/**
	 * Card Class Reference
	 */
	static Card card1, card2, card3;
	/**
	 * test data members
	 */
	int A1, A2, A3;
	/**
	 * test data member
	 */
	static HashMap<String, Continent> HM1 = new HashMap<>();
	
	/**
	 * Object created before all the test method
	 * 
	 */	
	@BeforeClass
	public static void testsetup()
	  {
		G1 = new Graph();
		G2 = new Graph();
		c1 = new Country("Dubai");
		c2 = new Country("Russia");
		c3 = new Country("Qator");
		c4 = new Country("India");
		c5 = new Country("China");
		c6 = new Country("Shrilanka");
		c7 = new Country("Pakistan");
		c8 = new Country("Syria");
		c1.setArmies(15);
		c2.setArmies(16);
		c7.setArmies(2);
		c8.setArmies(6);
		g1 = new GraphNode(c1);
		g2 = new GraphNode(c2);
		g3 = new GraphNode(c3);
		g4 = new GraphNode(c4);
		g5 = new GraphNode(c5);
		g6 = new GraphNode(c6);
		g7 = new GraphNode(c7);
		g8 = new GraphNode(c8);
		g1.addNeighbor(g2);
		//g1.addNeighbor(g3);
		g1.addNeighbor(g7);
		g2.addNeighbor(g8);
		g2.addNeighbor(g1);
		try
		  {
			p1 = new AggressivePlayer("player1", 7);
			p2 = new AggressivePlayer("player2", 7);
			p3 = new AggressivePlayer("player3", 7);
		  } catch (NullPointerException | InvalidPlayerNameException e)
		  {
			e.printStackTrace();
		  } catch (ca.riskgamet31.exceptions.InvalidNameException e)
		  {
			
			e.printStackTrace();
		  }
		GM1 = new GameMap();
		G2.addNode(g1);
		G2.addNode(g2);
		G2.addNode(g3);
		G1.addNode(g4);
		G1.addNode(g5);
		G1.addNode(g6);
		G2.addNode(g7);
		G2.addNode(g8);
		C1 = new Continent("Africa", 3, G2);
		C2 = new Continent("Asia", 5, G1);
		C3 = new Continent("Syria", 3,  G2);
		HM1.put("Africa", C1);
		HM1.put("Asia", C2);
		HM1.put("Syria", C3);
		GM1.setContinentsList(HM1);
		g1.getNodeData().setCurrentOccupier(p1.getplayerName());
		g2.getNodeData().setCurrentOccupier(p1.getplayerName());
		g3.getNodeData().setCurrentOccupier(p1.getplayerName());
		g4.getNodeData().setCurrentOccupier(p1.getplayerName());
		g5.getNodeData().setCurrentOccupier(p1.getplayerName());
		g6.getNodeData().setCurrentOccupier(p1.getplayerName());
		g7.getNodeData().setCurrentOccupier(p2.getplayerName());
		g8.getNodeData().setCurrentOccupier(p3.getplayerName());
		p1.addCountry(g1);
		p1.addCountry(g2);
		p1.addCountry(g3);
		p1.addCountry(g4);
		p1.addCountry(g5);
		p1.addCountry(g6);
		p2.addCountry(g7);
		p3.addCountry(g8);
		card1 = new Card("Infantry", c1.getCountryName());
		card2 = new Card("Cavalry", c1.getCountryName());
		card3 = new Card("Artillery", c1.getCountryName());
		driver = new GameMainDriver();
		
		driver.setGameMap(GM1);
		try
		  {
			driver.getPlayerList().setPlayerList(p1);
			driver.getPlayerList().setPlayerList(p2);
			driver.getPlayerList().setPlayerList(p3);
		  } catch (InvalidPlayerException e)
		  {
			e.printStackTrace();
		  }
	  }
	  
	/**
	 * Distribute armies test method
	 */
	@Test
	public void testreinforcement()
	  {
		int playerArmiesBeforeReinforcement = p1.getPlayerArmies();
		int armiesForCountry2BeforeReinforcement = c2.getArmies();
		p1.reinforcement();
		//int armiesForCountry1AfterFortification = c1.getArmies();
		int armiesForCountry2AfterReinforcement = c2.getArmies();
		//System.out.println(armiesForCountry2BeforeFortification);
		//System.out.println(armiesForCountry2AfterFortification);
		//assertEquals(armiesForCountry1BeforeFortification + armiesForCountry2BeforeFortification, armiesForCountry1AfterFortification + armiesForCountry2AfterFortification);
		//assertEquals(armiesForCountry1BeforeFortification * 2, armiesForCountry1AfterFortification);
		assertEquals(armiesForCountry2BeforeReinforcement + playerArmiesBeforeReinforcement, armiesForCountry2AfterReinforcement);
	  }
	  
	/**
	 * fortification armies test method
	 */
	@Test
	public void testfortification()
	  {
		
		int armiesForCountry1BeforeFortification = c1.getArmies();
		int armiesForCountry2BeforeFortification = c2.getArmies();
		p1.fortification();
		int armiesForCountry1AfterFortification = c1.getArmies();
		int armiesForCountry2AfterFortification = c2.getArmies();
		//System.out.println(armiesForCountry2BeforeFortification);
		//System.out.println(armiesForCountry2AfterFortification);
		assertEquals(armiesForCountry1BeforeFortification + armiesForCountry2BeforeFortification, armiesForCountry1AfterFortification + armiesForCountry2AfterFortification);
		//assertEquals(armiesForCountry1BeforeFortification * 2, armiesForCountry1AfterFortification);
		//assertEquals(armiesForCountry2BeforeFortification * 2, armiesForCountry2AfterFortification);
	  }
	  /**
	   * to test attack
	   */
	@Test
	public void testAttack()
	  {
		
		p1.attack(driver);
		assertEquals(c1.getCurrentOccupier(), c8.getCurrentOccupier());
		assertEquals(2, driver.getPlayerList().getPlayerList().size()); 
	  }
	
	/**
	 * countries the cheater player can cheat
	 */
	@Test
	public void testcanFortify()
	{
		ArrayList<GraphNode> fortifyCountries = new ArrayList<>();
		fortifyCountries = p1.canFortify();
		//p1.getPlayerGraph().viewGraph();
		//System.out.println(fortifyCountries.size());
		//System.out.println(fortifyCountries.get(0).getNodeData().getCountryName());
		//System.out.println(fortifyCountries.get(1).getNodeData().getCountryName());
		assertEquals(2, fortifyCountries.size());
		assertEquals("DUBAI", fortifyCountries.get(0).getNodeData().getCountryName());
		assertEquals("RUSSIA", fortifyCountries.get(1).getNodeData().getCountryName());
	}
	
	/**
	 * countries the cheater player can cheat
	 */
	@Test
	public void testcanAttack()
	{
		ArrayList<GraphNode> adNodes = new ArrayList<GraphNode>();
		adNodes = p1.canAttack();
		assertEquals("RUSSIA", adNodes.get(0).getNodeData().getCountryName());
		assertEquals("SYRIA", adNodes.get(1).getNodeData().getCountryName());
		//System.out.println(adNodes.get(0).getNodeData().getCountryName());
	//	System.out.println(adNodes.get(1).getNodeData().getCountryName());
	}
	
	@Test
	public void testCanReinforcement()
	{
		GraphNode a = p1.canReinforce();
		assertEquals("RUSSIA", a.getNodeData().getCountryName());
	}
  }
