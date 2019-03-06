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

/**
 * test class for testin graph functions
 * 
 * @author Ishpreet Singh
 */
public class TestDeckOfCards
  {
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
		
	  }
	  
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	  {
	  }
	  
	@Test
	public void TestaddNewCard()
	  {
		ArrayList<Country> neighbours = new ArrayList<>();
		ArrayList<Card> nei = new ArrayList<>();
		Country cn = new Country("india");
		Country con = new Country("bang");
		Country conu = new Country("myanmar");
		Country count = new Country("srilanka");
		Country countr = new Country("AUSTRALIA");
		// neighbours.add(cn);
		// neighbours.add(con);
		// neighbours.add(conu);
		// neighbours.add(count);
		
		// String[] expected={"india","bang","myanmar","srilanka","australia"};
		DeckOfCards doc = new DeckOfCards(neighbours);
		Card ca = new Card("Infantry", countr);
		System.out.println(ca.getCardType());
		doc.addNewCard(ca);
		assertEquals(ca, doc.drawCard());
		
	  }
	  
  }
