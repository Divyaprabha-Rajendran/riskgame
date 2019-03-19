package ca.riskgamet31test.controllers;

import static org.junit.Assert.assertNotEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.naming.InvalidNameException;

import org.junit.BeforeClass;
import org.junit.Test;

import ca.riskgamet31.controllers.StartUpPhase;
import ca.riskgamet31.exceptions.InvalidPlayerNameException;
import ca.riskgamet31.maincomps.Country;
import ca.riskgamet31.maincomps.GraphNode;
import ca.riskgamet31.maincomps.Player;

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
	static Player p1, p2;
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
	 */

	@BeforeClass
	public static void Testsetup() throws NullPointerException,
	    InvalidNameException, InvalidPlayerNameException, ca.riskgamet31.exceptions.InvalidNameException
	  {
		S1 = new StartUpPhase();
		p1 = new Player("P1", 4);
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
		S1.distributeArmies(p1);
		int a2 = p1.getPlayerArmies();
		assertNotEquals(a1, a2);

	  }
	  
  }
