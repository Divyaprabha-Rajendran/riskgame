package ca.riskgamet31test.maincomps;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.riskgamet31.maincomps.Card;


/**
 * test class for testin graph functions
 * 
 * @author Ishpreet Singh
 */
public class TestCard
  {
	 /**
	   * Test method for testing if we are getting correct card 
	   */
	@Test
	public void TestgetCardName()
	  {
		
		Card ca = new Card("Infantry", "india");
		String s = "Infantry";
		assertEquals(s, ca.getCardName());
	  }
	  /**
	   * Test method for testing if we are getting correct card type
	   */
	@Test
	public void TestgetCardType()
	  {
		
		Card ca = new Card("Infantry", "india");
		String s = "Infantry";
		assertEquals(s, ca.getCardType());
	  }
	  
  }
