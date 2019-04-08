package ca.riskgamet31test.controllers;

import static org.junit.Assert.assertNotEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.BeforeClass;
import org.junit.Test;

import ca.riskgamet31.controllers.StartUpPhase;
import ca.riskgamet31.exceptions.InvalidPlayerNameException;
import ca.riskgamet31.maincomps.Country;
import ca.riskgamet31.maincomps.GraphNode;
import ca.riskgamet31.maincomps.Player;
import ca.riskgamet31.maincomps.RandomPlayer;

/**
 * Tests the StartupPhase class
 * 
 * @author Chitra
 * @version 1.1
 * @since 1.0
 */
public class TestStartupPhase
  {
	/**
	 * StartUpPhase Class Reference
	 */
	static StartUpPhase S1;
	/**
	 * Player Class Reference
	 */
	static RandomPlayer p1, p2;
	/**
	 * Country Class Reference
	 */
	static Country C1, C2;
	/**
	 * GraphNode Class Reference
	 */
	static GraphNode G1, G2;
	
	/**
	 * Object created before all the test method
	 * 
	 * @throws NullPointerException                           when value is null
	 * @throws InvalidPlayerNameException                     when player name
	 *                                                        is duplicate
	 * @throws ca.riskgamet31.exceptions.InvalidNameException when Name is
	 *                                                        invalid
	 */
	@BeforeClass
	public static void Testsetup() throws NullPointerException,
	    InvalidPlayerNameException,
	    ca.riskgamet31.exceptions.InvalidNameException
	  {
		S1 = new StartUpPhase();
		p1 = new RandomPlayer("P1", 4);
		C1 = new Country("india");
		C2 = new Country("china");
		G1 = new GraphNode(C1);
		G2 = new GraphNode(C2);
		p1.addCountry(G1);
		p1.addCountry(G2);
		
	  }
	  
	/**
	 * Distribute Armies among the country test method
	 * 
	 */
	@Test
	public void TestdistributeArmies()
	  {
		int a1 = p1.getPlayerArmies();
		String input = "india\n1\nchina\n3\n";
		InputStream in = new ByteArrayInputStream(input.getBytes());
		System.setIn(in);
		p1.initialdistributeArmies();
		int a2 = p1.getPlayerArmies();
		assertNotEquals(a1, a2);
		
	  }
	  
  }
