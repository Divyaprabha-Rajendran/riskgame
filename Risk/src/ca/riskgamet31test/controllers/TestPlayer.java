package ca.riskgamet31test.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;

import javax.naming.InvalidNameException;

import org.junit.BeforeClass;
import org.junit.Test;

import ca.riskgamet31.exceptions.InvalidPlayerNameException;
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
 * @author Chitra
 * @version 1.1
 * @since 1.0
 */
public class TestPlayer
  {
	/**
	 * Country Class Reference
	 */
	static Country c1, c2, c3, c4, c5, c6;
	/**
	 * GraphNode Class Reference
	 */
	static GraphNode g1, g2, g3, g4, g5, g6;
	/**
	 * Player Class Reference
	 */
	static Player p1;
	/**
	 * Graph Class Reference
	 */
	static Graph G1,G2;
	/**
	 * Continent Class Reference
	 */
	static Continent C1,C2;
	/**
	 * GameMap Class Reference
	 */
	static GameMap GM1;
	/**
	 * Card Class Reference
	 */
	static Card card1, card2, card3;
	
	int A1, A2, A3;
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
		
		g1 = new GraphNode(c1);
		g2 = new GraphNode(c2);
		g3 = new GraphNode(c3);
		g4 = new GraphNode(c4);
		g5 = new GraphNode(c5);
		g6 = new GraphNode(c6);
		g1.addNeighbor(g2);
		g1.addNeighbor(g3);
		try
		  {
			p1 = new Player("player1", 7);
		  }
		  catch (NullPointerException | InvalidPlayerNameException e)
		  {
			  e.printStackTrace();
		  } 
		catch (ca.riskgamet31.exceptions.InvalidNameException e) 
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
		C1 = new Continent("Africa", 3, G2);
		C2 = new Continent("Asia", 5, G1);
		HM1.put("Africa", C1);
		HM1.put("Asia", C2);
		GM1.setContinentsList(HM1);
		g1.getNodeData().setCurrentOccupier(p1.getplayerName());
		g2.getNodeData().setCurrentOccupier(p1.getplayerName());
		g3.getNodeData().setCurrentOccupier(p1.getplayerName());
		g4.getNodeData().setCurrentOccupier(p1.getplayerName());
		g5.getNodeData().setCurrentOccupier(p1.getplayerName());
		g6.getNodeData().setCurrentOccupier(p1.getplayerName());
		p1.addCountry(g1);
		p1.addCountry(g2);
		p1.addCountry(g3);
		p1.addCountry(g4);
		p1.addCountry(g5);
		p1.addCountry(g6);
		card1 = new Card("Infantry", c1.getCountryName());
		card2 = new Card("Cavalry", c1.getCountryName());
		card3 = new Card("Artillery", c1.getCountryName());
	  }
	/**
	 * add and remove Country from player graph test method 
	 */  

	@Test
	public void testCountry()
	  {
		
		A1 = p1.getCountry().size();
		p1.addCountry(g1);
		A2 = p1.getCountry().size();
		assertNotEquals(A1, A2);
		p1.removeCountry(g1);
		A3 = p1.getCountry().size();
		assertEquals(A1, A3);
	  }
	/**
	 * Decrement army test method 
	 */  

	@Test
	public void testarmies()
	  {
		int army1 = 12;
		p1.decrementArmies(army1);
		assertNotEquals(army1, p1);
	  }
	/**
	 * Add and remove card from players graph test method 
	 */ 

	@Test
	public void testcard()
	  {
		A1 = p1.getPlayerCards().size();
		p1.addNewCard(card1);
		A2 = p1.getPlayerCards().size();
		assertNotEquals(A1, A2);
		p1.addNewCard(card2);
		p1.addNewCard(card3);
		int A4 = p1.getPlayerCards().size();
		int[] A5 = new int[A4];
		p1.removeCards(A5);
		A3 = p1.getPlayerCards().size();
		assertEquals(0, A3);
	  }
	/**
	 * reinforcement Armies Calculation test method 
	 */ 
  
	@Test
	public void testreinforcementArmiesCalc()
	  {
		int expected = 10;
		assertEquals(expected, p1.reinforcementArmiesCalc(GM1));
	  }
	/**
	 * Distribute armies test method  
	 */
	@Test
	public void testdistributeArmies()
	  {
		int armiesForCountry1BeforeReinforcement = c1.getArmies();
		int armiesForPlayerBeforeReinforcement = p1.getPlayerArmies();
		String input = "Dubai\n10";
		InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
		p1.reinforcement();
		int armiesForCountry1AfterReinforcement = c1.getArmies();
		int armiesForPlayerAfterReinforcement = p1.getPlayerArmies();
		assertEquals(armiesForCountry1BeforeReinforcement + armiesForPlayerBeforeReinforcement, armiesForCountry1AfterReinforcement + armiesForPlayerAfterReinforcement);
	  }

	/**
	 * fortification armies test method  
	 */
	@Test
	public void testfortification()
	  {
		
		int armiesForCountry1BeforeFortification = c1.getArmies();
		int armiesForCountry2BeforeFortification = c2.getArmies();
		String input = "Dubai\nrussia\n5";
		InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
		p1.fortification();
		int armiesForCountry1AfterFortification = c1.getArmies();
		int armiesForCountry2AfterFortification = c2.getArmies();
		assertEquals(armiesForCountry1BeforeFortification + armiesForCountry2BeforeFortification, armiesForCountry1AfterFortification + armiesForCountry2AfterFortification);
		
	  }
}

  
