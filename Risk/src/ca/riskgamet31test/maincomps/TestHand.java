package ca.riskgamet31test.maincomps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import ca.riskgamet31.maincomps.Card;
import ca.riskgamet31.maincomps.Country;
import ca.riskgamet31.maincomps.Hand;

/**
 * 
 * @author Chitra
 * @version 1.0
 * @since 1.0
 *
 */

public class TestHand
  {
	/**
	 * Hand Class Reference
	 */
	static Hand H1, H2, H3, H4;
	/**
	 * Card Class Reference
	 */
	static Card C1, C2, C3, C4;
	/**
	 * Country Class Reference
	 */
	static Country c1, c2, c3, c4;
	/**
	 * Integer variable
	 */
	static int a1, a2;
	
	/**
	 * Object created before all the test method
	 * 
	 */
	@BeforeClass
	public static void setup()
	  {
		H1 = new Hand();
		H2 = new Hand();
		H3 = new Hand();
		H4 = new Hand();
		c1 = new Country("Dubai");
		c2 = new Country("Russia");
		c3 = new Country("Qator");
		c4 = new Country("China");
		C1 = new Card("Infantry", c1.getCountryName());
		C2 = new Card("Cavalry", c2.getCountryName());
		C3 = new Card("Artillery", c3.getCountryName());
		C4 = new Card("Infantry", c4.getCountryName());
		
	  }
	  
	/**
	 * Add card to hand
	 * 
	 */
	@Test
	public void testaddcard()
	  {
		a1 = H1.getCardsFromHand().size();
		H1.addCard(C1);
		a2 = H1.getCardsFromHand().size();
		assertNotEquals(a1, a2);
	  }
	  
	/**
	 * Check that whether cards must be turned in by user or not
	 * 
	 */
	@Test
	public void testmustTurnInCards()
	  {
		H2.addCard(C1);
		H2.addCard(C2);
		H2.addCard(C3);
		H2.addCard(C4);
		assertEquals(false, H2.mustTurnInCards());
	  }
	  
	/**
	 * Check that whether player can turn in cards or not
	 * 
	 */
	@Test
	public void testcanTurnInCards()
	  {
		H1.addCard(C1);
		H1.addCard(C2);
		H1.addCard(C3);
		assertEquals(true, H1.canTurnInCards(1, 2, 3));
	  }
	  
	/**
	 * Remove cards from hand
	 * 
	 */
	@Test
	public void testremoveCardsFromHand()
	  {
		H3.addCard(C1);
		H3.addCard(C2);
		H3.addCard(C3);
		H3.addCard(C4);
		a1 = H3.getCardsFromHand().size();
		H3.removeCardsFromHand(1, 2, 3);
		a2 = H3.getCardsFromHand().size();
		assertNotEquals(a1, a2);
	  }
	  
	/**
	 * Eligiblity is checked in order to exchange the cards
	 */
	@Test
	public void testisEligibleToExchange()
	  {
		H4.addCard(C1);
		H4.addCard(C2);
		H4.addCard(C3);
		assertEquals(true, H4.isEligibleToExchange());
	  }
  }
