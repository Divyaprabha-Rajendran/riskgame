package ca.riskgamet31test.maincomps;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.riskgamet31.maincomps.Dice;

/**
 * 
 * @author Ishpreet singh
 * @version 1.1 Test class to check rolling functionality of dice In this we are
 *          get rolling dice 0.6 million times and getting probability of having
 *          1 to 6 as output then comparing that with specified range
 */
public class TestDice
  {
	/**
	 * test data member rollResult
	 */
	static int[] rollResult;
	/**
	 * test data member dice
	 */
	static Dice dice;
	/**
	 * test data member 1
	 */
	static ArrayList<Integer> one;
	/**
	 * test data member 2
	 */
	static ArrayList<Integer> two;
	/**
	 * test data member 3
	 */
	static ArrayList<Integer> three;
	/**
	 * test data member 4
	 */
	static ArrayList<Integer> four;
	/**
	 * test data member 5
	 */
	static ArrayList<Integer> five;
	/**
	 * test data member 6
	 */
	static ArrayList<Integer> six;
	
	/**
	 * getting dice and set up roll results.
	 * 
	 * @throws Exception throws exceptions
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	  {
		rollResult = new int[600000];
		dice = new Dice();
		one = new ArrayList<>();
		two = new ArrayList<>();
		three = new ArrayList<>();
		four = new ArrayList<>();
		five = new ArrayList<>();
		six = new ArrayList<>();
	  }
	  
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	  {
	  }
	  
	@Before
	public void setUp() throws Exception
	  {
		
	  }
	  
	@After
	public void tearDown() throws Exception
	  {
	  }
	  
	/**
	 * testing the roll method by rolling it by 0.6 million times
	 */
	@Test
	public void testRoll()
	  {
		
		rollResult = dice.roll(600000);
		for (int i = 0; i < rollResult.length; i++)
		  {
			if (rollResult[i] == 1)
			  {
				one.add(1);
			  } else if (rollResult[i] == 2)
			  {
				two.add(1);
			  } else if (rollResult[i] == 3)
			  {
				three.add(1);
			  } else if (rollResult[i] == 4)
			  {
				four.add(1);
			  } else if (rollResult[i] == 5)
			  {
				five.add(1);
			  } else if (rollResult[i] == 6)
			  {
				six.add(1);
			  }
		  }
		  
		float pOne = (float) one.size() / 600000;
		float pTwo = (float) two.size() / 600000;
		float pThree = (float) three.size() / 600000;
		float pFour = (float) four.size() / 600000;
		float pFive = (float) five.size() / 600000;
		float pSix = (float) six.size() / 600000;
		assertTrue(0.15 <= pOne && pOne <= 0.17);
		assertTrue(0.15 <= pTwo && pTwo <= 0.17);
		assertTrue(0.15 <= pThree && pThree <= 0.17);
		assertTrue(0.15 <= pFour && pFour <= 0.17);
		assertTrue(0.15 <= pFive && pFive <= 0.17);
		assertTrue(0.15 <= pSix && pSix <= 0.17);
	  }
  }