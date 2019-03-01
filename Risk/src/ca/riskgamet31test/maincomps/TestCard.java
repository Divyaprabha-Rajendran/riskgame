package ca.riskgamet31test.maincomps;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.riskgamet31.maincomps.Card;
import ca.riskgamet31.maincomps.Country;
import ca.riskgamet31.maincomps.GraphNode;
/**
* test class for testin graph functions
*@author Ishpreet Singh
*/ 
public class TestCard {
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
		Country cn =new Country("india");
		Card ca = new Card("Infantry",cn);
	  }
	  
	/**
	 * @throws java.lang.Exception
	 */
	@After
	void tearDown() throws Exception
	  {
	  }
	@Test
	public void TestgetCardName() 
	{ 
		Country cn =new Country("india");
		Card ca = new Card("Infantry",cn);
		String s = "india Infantry";
		assertEquals(s,ca.getCardName());
	}
	@Test
	public void TestgetCardType() 
	{ 
		Country cn =new Country("india");
		Card ca = new Card("Infantry",cn);
		String s = "Infantry";
		assertEquals(s,ca.getCardType());
	}

}
