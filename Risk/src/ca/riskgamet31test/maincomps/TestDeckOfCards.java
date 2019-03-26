package ca.riskgamet31test.maincomps;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.riskgamet31.maincomps.Card;
import ca.riskgamet31.maincomps.Country;
import ca.riskgamet31.maincomps.DeckOfCards;
import ca.riskgamet31.maincomps.GraphNode;

/**
 * test class for testin graph functions
 * 
 * @author Ishpreet Singh
 */
public class TestDeckOfCards
  {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	  {
		
	  }
	  
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	  {
	  }
	  
	/**
	 * setup method for deck of cards
	 * 
	 * @throws Exception if something went wrong
	 */
	@Before
	public void setUp() throws Exception
	  {
		ArrayList<GraphNode> neighbours = new ArrayList<>();
		ArrayList<Card> nei = new ArrayList<>();
		Country countr = new Country("AUSTRALIA");
	  }
	  
	@After
	public void tearDown() throws Exception
	  {
	  }
	  
	/**
	 * Test for deck of cards if add functionality is working properly
	 */
	@Test
	public void TestaddNewCard()
	  {
		ArrayList<GraphNode> neighbours = new ArrayList<>();
		ArrayList<Card> nei = new ArrayList<>();
		Country countr = new Country("AUSTRALIA");
		DeckOfCards doc = new DeckOfCards(neighbours);
		Card ca = new Card("Infantry", countr.getCountryName());
		System.out.println(ca.getCardType());
		doc.addNewCard(ca);
		assertEquals(ca, doc.drawCard());
		
	  }
	  
  }
