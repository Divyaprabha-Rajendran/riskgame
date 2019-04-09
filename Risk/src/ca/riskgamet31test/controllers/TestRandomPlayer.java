package ca.riskgamet31test.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
import ca.riskgamet31.maincomps.RandomPlayer;

/**
 * Tests the Player class
 * 
 * @author YD
 * @version 1.1
 * @since 1.0
 */
public class TestRandomPlayer
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
			p1 = new RandomPlayer("player1", 7);
			p2 = new RandomPlayer("player2", 7);
			p3 = new RandomPlayer("player3", 7);
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
	 * countries the cheater player can cheat
	 */
	@Test
	public void testcanFortify()
	{
		HashSet<ArrayList<GraphNode>> cntr = new HashSet<>();
		ArrayList<GraphNode> a = p1.canFortify();
		ArrayList<GraphNode> b = p1.canFortify();
		ArrayList<GraphNode> c = p1.canFortify();
		ArrayList<GraphNode> d = p1.canFortify();
		cntr.add(a);
		cntr.add(b);
		cntr.add(c);
		cntr.add(d);
		System.out.println(cntr);
		System.out.println(cntr.size());
		assertNotEquals(1, cntr.size());
	}
	
	/**
	 * countries the cheater player can cheat
	 */
	@Test
	public void testcanAttack()
	{
		HashSet<ArrayList<GraphNode>> cntr = new HashSet<>();
		ArrayList<GraphNode> a = p1.canAttack();
		ArrayList<GraphNode> b = p1.canAttack();
		ArrayList<GraphNode> c = p1.canAttack();
		ArrayList<GraphNode> d = p1.canAttack();
		cntr.add(a);
		cntr.add(b);
		cntr.add(c);
		cntr.add(d);
		System.out.println(cntr);
		System.out.println(cntr.size());
		assertNotEquals(1, cntr.size());
	}
	
	@Test
	public void testCanReinforcement()
	{
		HashSet<String> cntr = new HashSet<>();
		GraphNode a = p1.canReinforce();
		GraphNode b = p1.canReinforce();
		GraphNode c = p1.canReinforce();
		GraphNode d = p1.canReinforce();
		cntr.add(a.getNodeData().getCountryName());
		cntr.add(b.getNodeData().getCountryName());
		cntr.add(c.getNodeData().getCountryName());
		cntr.add(d.getNodeData().getCountryName());
		System.out.println(cntr);
		System.out.println(cntr.size());
		assertNotEquals(1, cntr.size());
	}
  }
