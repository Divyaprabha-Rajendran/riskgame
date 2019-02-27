package ca.riskgamet31test.maincomps;

import static org.junit.Assert.assertEquals;

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
	static void setUpBeforeClass() throws Exception
	  {
		
	  }
	  
	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	static void tearDownAfterClass() throws Exception
	  {
	  }
	  
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	void setUp() throws Exception
	  {
		ArrayList<Country> neighbours = new ArrayList<>();
		Country c = new Country("India", neighbours);
		GraphNode Gn = new GraphNode(c);
	  }
	  
	/**
	 * @throws java.lang.Exception
	 */
	@After
	void tearDown() throws Exception
	  {
	  }
	  
	@Test
	public void testgetNodeData()
	  {
		ArrayList<Country> neighbours = new ArrayList<>();
		Country c = new Country("India", neighbours);
		GraphNode Gn = new GraphNode(c);
		String con ="India";
		assertEquals(con, Gn.getNodeData().toString());
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
		Country c = new Country("India", neighbours);
		GraphNode Gn = new GraphNode(c);
		assertEquals(3, Gn.getDistance());
	  }
	
@Test
public void TestgetParentNode()
{     
String con ="asia";
ArrayList<Country> neighbours = new ArrayList<>();
Country c = new Country("India", neighbours);
GraphNode Gn = new GraphNode(c);
	assertEquals(con, Gn.getNodeData().toString());
}

}

