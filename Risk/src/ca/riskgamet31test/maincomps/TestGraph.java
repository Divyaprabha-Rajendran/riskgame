package ca.riskgamet31test.maincomps;


import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import ca.riskgamet31.maincomps.Country;
import ca.riskgamet31.maincomps.Graph;
import ca.riskgamet31.maincomps.GraphNode;
/**
 * 
 * @author Fareed
 * @version 1.0
 * @since 1.0
 *
 */
public class TestGraph
  {
	/*
	 *  Graph Class Reference
	 */
	public Graph testGraph;
	/*
	 *  GraphNode Class Reference
	 */
	public GraphNode g1, g2, g3, g4, g5, g6, g7, g8, g9, g10, g11, g12, g13,
	    g14, g15;
	/*
	 * TestGraph Constructor defined
	 */
	public TestGraph()
	  {
		
		testGraph = new Graph();
		Country c1 = new Country("c1");
		Country c2 = new Country("c2");
		Country c3 = new Country("c3");
		Country c4 = new Country("c4");
		g1 = new GraphNode(c1);
		g2 = new GraphNode(c2);
		g3 = new GraphNode(c3);
		g4 = new GraphNode(c4);
		g1.addNeighbor(g2);
		g1.addNeighbor(g3);
		g1.addNeighbor(g4);
		g2.addNeighbor(g1);
		// g2.addNeighbor(g3);
		// g2.addNeighbor(g4);
		g3.addNeighbor(g1);
		// g3.addNeighbor(g2);
		g3.addNeighbor(g4);
		g4.addNeighbor(g1);
		// g4.addNeighbor(g2);
		g4.addNeighbor(g3);
		
		///////////////////
		Country c5 = new Country("c5");
		Country c6 = new Country("c6");
		Country c7 = new Country("c7");
		g5 = new GraphNode(c5);
		g6 = new GraphNode(c6);
		g7 = new GraphNode(c7);
		// g5.addNeighbor(g6);
		g5.addNeighbor(g7);
		// g6.addNeighbor(g5);
		g6.addNeighbor(g7);
		g7.addNeighbor(g5);
		g7.addNeighbor(g6);
		
		//////////////////////////////////
		Country c8 = new Country("c8");
		Country c9 = new Country("c9");
		g8 = new GraphNode(c8);
		g9 = new GraphNode(c9);
		g8.addNeighbor(g9);
		g9.addNeighbor(g8);
		
		////////////////
		Country c10 = new Country("c10");
		g10 = new GraphNode(c10);
		
		/////////////////////////
		Country c11 = new Country("c11");
		Country c12 = new Country("c12");
		Country c13 = new Country("c13");
		Country c14 = new Country("c14");
		Country c15 = new Country("c15");
		
		g11 = new GraphNode(c11);
		g12 = new GraphNode(c12);
		g13 = new GraphNode(c13);
		g14 = new GraphNode(c14);
		g15 = new GraphNode(c15);
		g11.addNeighbor(g12);
		g11.addNeighbor(g13);
		// g11.addNeighbor(g14);
		// g11.addNeighbor(g15);
		g12.addNeighbor(g11);
		// g12.addNeighbor(g13);
		g12.addNeighbor(g14);
		g12.addNeighbor(g15);
		g13.addNeighbor(g11);
		// g13.addNeighbor(g12);
		// g13.addNeighbor(g14);
		// g13.addNeighbor(g15);
		// g14.addNeighbor(g11);
		g14.addNeighbor(g12);
		// g14.addNeighbor(g13);
		// g14.addNeighbor(g15);
		// g15.addNeighbor(g11);
		g15.addNeighbor(g12);
		// g15.addNeighbor(g13);
		// g15.addNeighbor(g14);
		
		this.testGraph.addNode(g1);
		this.testGraph.addNode(g2);
		this.testGraph.addNode(g3);
		this.testGraph.addNode(g4);
		this.testGraph.addNode(g5);
		this.testGraph.addNode(g6);
		this.testGraph.addNode(g7);
		this.testGraph.addNode(g8);
		this.testGraph.addNode(g9);
		this.testGraph.addNode(g10);
		this.testGraph.addNode(g11);
		this.testGraph.addNode(g12);
		this.testGraph.addNode(g13);
		this.testGraph.addNode(g14);
		this.testGraph.addNode(g15);
	  }
	  /**
	   * Object created before all the test method 
	   * 
	   */
	@BeforeClass
public static void setUp() throws Exception
	  {
		
		TestGraph testG = new TestGraph();
		
	  }
	  /**
	   *  Finding the graph is connected graph or not
	   */
	@Test
public	void testIsConnectedFindPath()
	  {
		
		assertFalse(testGraph.isConnected());
		assertFalse(testGraph.findPath("c1", "c10"));
		assertFalse(testGraph.findPath("c4", "c5"));
		assertFalse(testGraph.findPath("c8", "c15"));
		assertFalse(testGraph.findPath("c6", "c2"));
		assertFalse(testGraph.findPath("c13", "c9"));
		assertFalse(testGraph.findPath("c13", "c6"));
		assertFalse(testGraph.findPath("c6", "c14"));
		
		assertTrue(testGraph.findPath("c13", "c14"));
		assertTrue(testGraph.findPath("c6", "c5"));
		assertTrue(testGraph.findPath("c9", "c8"));
		assertTrue(testGraph.findPath("c10", "c10"));
		assertTrue(testGraph.findPath("c4", "c2"));
		assertTrue(testGraph.findPath("c1", "c2"));
		assertTrue(testGraph.findPath("c3", "c2"));
		
		g4.addNeighbor(g7);
		g7.addNeighbor(g4);
		assertTrue(testGraph.findPath("c4", "c5"));
		assertTrue(testGraph.findPath("c2", "c5"));
		assertFalse(testGraph.findPath("c1", "c13"));
		assertFalse(testGraph.isConnected());
		
		g6.addNeighbor(g15);
		g15.addNeighbor(g6);
		assertTrue(testGraph.findPath("c13", "c6"));
		assertTrue(testGraph.findPath("c5", "c14"));
		assertTrue(testGraph.findPath("c1", "c13"));
		assertFalse(testGraph.findPath("c10", "c13"));
		assertFalse(testGraph.findPath("c9", "c13"));
		assertFalse(testGraph.isConnected());
		
		g14.addNeighbor(g8);
		g8.addNeighbor(g14);
		assertTrue(testGraph.findPath("c9", "c11"));
		assertTrue(testGraph.findPath("c8", "c6"));
		assertFalse(testGraph.findPath("c10", "c11"));
		assertFalse(testGraph.isConnected());
		
		// g2.addNeighbor(g8);
		// g8.addNeighbor(g2);
		
		g10.addNeighbor(g4);
		g4.addNeighbor(g10);
		assertTrue(testGraph.findPath("c10", "c11"));
		assertTrue(testGraph.findPath("c5", "c9"));
		assertTrue(testGraph.findPath("c1", "c8"));
		assertTrue(testGraph.isConnected());
	  }
	  
  }
