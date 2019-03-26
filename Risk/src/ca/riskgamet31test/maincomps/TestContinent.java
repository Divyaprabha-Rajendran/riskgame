package ca.riskgamet31test.maincomps;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import ca.riskgamet31.maincomps.Continent;
import ca.riskgamet31.maincomps.Country;
import ca.riskgamet31.maincomps.Graph;
import ca.riskgamet31.maincomps.GraphNode;

/**
 * Tests the Continent class
 * 
 * @author Chitra
 * @version 1.0
 * @since 1.0
 */
public class TestContinent
  {
	/**
	 * Continent Class Reference
	 */
	static Continent C1, C2;
	/**
	 * Country Class Reference
	 */
	static Country c1, c2, c3;
	/**
	 * GraphNode Class Reference
	 */
	static GraphNode g6, g5, g4;
	/**
	 * Graph Class Reference
	 */
	static Graph G1;
	
	/**
	 * Object created before all the test method
	 * 
	 */
	@BeforeClass
	public static void Testsetup()
	  {
		G1 = new Graph();
		c1 = new Country("Dubai");
		c2 = new Country("Russia");
		c3 = new Country("Qator");
		g4 = new GraphNode(c1);
		g5 = new GraphNode(c2);
		g6 = new GraphNode(c3);
		G1.addNode(g4);
		G1.addNode(g5);
		G1.addNode(g6);
		C1 = new Continent("africa", 4, G1);
	  }
	  
	/**
	 * true if both continents have similar names test method
	 */
	@Test
	public void testEqual()
	  {
		C2 = new Continent("africa", 4, G1);
		assertEquals(true, C1.equals(C2));
		
	  }
	  
  }
