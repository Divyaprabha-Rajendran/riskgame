package ca.riskgamet31test.maincomps;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.riskgamet31.maincomps.Country;
import ca.riskgamet31.maincomps.GraphNode;


/**
* test class for testin graph functions
* @author Ishpreet Singh
*/
public class TestGraphNode {
  
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	  {
		
	  }
	  
	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	  {
	  }
	  
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	  {
		ArrayList<Country> neighbours = new ArrayList<>();
		Country c = new Country("India");
		GraphNode Gn = new GraphNode(c);
	  }
	  
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	  {
	  }
	  
	@Test
	public void testgetNodeData()
	  {
		ArrayList<Country> neighbours = new ArrayList<>();
		Country c = new Country("India");
		GraphNode Gn = new GraphNode(c);
		String con ="INDIA";
		assertEquals(con, Gn.getNodeData().getCountryName());
	  }
	//@Test
//	public void  TestgetNodeNeighbors()
//	{
//		ArrayList<Country> neighbours = new ArrayList<>();
//		neighbours.add()
//		Country c = new Country("India", neighbours );
//		GraphNode Gn = new GraphNode(c);
//	String neighbour[] =  {"pakistan","bangladesh","bhutan","nepal"};	
//	assertArrayEquals(neighbour,gn.getNodeNeighbors().toString());
//	}
	@Test
	public void TestgetDistance()
	  {
		ArrayList<Country> neighbours = new ArrayList<>();
		Country c = new Country("India");
		GraphNode Gn = new GraphNode(c);
		assertEquals(-1, Gn.getDistance());
	  }
	
@Test
public void TestgetParentNode()
{     
String con ="INDIA";
ArrayList<Country> neighbours = new ArrayList<>();
Country c = new Country("INDIA");
GraphNode Gn = new GraphNode(c);
assertEquals(con, Gn.getNodeData().getCountryName());
    
}
@Test 
public void TestAddNeighbour()
{
	String con ="INDIA";
	ArrayList<Country> neighbours = new ArrayList<>();
	Country c = new Country("INDIA");
	Country d= new Country("BANGLADESH");
	GraphNode Gn = new GraphNode(c);
	GraphNode Gn1 = new GraphNode(d);
	Gn1.addNeighbor(Gn);
	 assertEquals("INDIA",Gn1.getNodeNeighbors().get(0).getNodeData().getCountryName());
	}
@Test
public void TestRemoveNeighbour()
{
	String con ="INDIA";
	ArrayList<Country> neighbours = new ArrayList<>();
	Country c = new Country("INDIA");
	Country d= new Country("BANGLADESH");
	Country f= new Country("SRILANKA");
	GraphNode Gn = new GraphNode(c);
	GraphNode Gn1 = new GraphNode(d);
	GraphNode Gn2 = new GraphNode(f);
	Gn1.addNeighbor(Gn);
	
	Gn1.addNeighbor(Gn2);
	Gn1.removeNeighbor(Gn);
	assertNotSame("INDIA",Gn1.getNodeNeighbors().get(0).getNodeData().getCountryName());
	
	
	}
}

