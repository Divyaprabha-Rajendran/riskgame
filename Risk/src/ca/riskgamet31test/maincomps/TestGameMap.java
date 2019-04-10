package ca.riskgamet31test.maincomps;

import static org.junit.Assert.assertNotEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import ca.riskgamet31.exceptions.InvalidPlayerNameException;
import ca.riskgamet31.maincomps.GameMap;
import ca.riskgamet31.maincomps.RandomPlayer;

/**
 * Tests the GameMap class
 * 
 * @author Chitra
 * @version 1.1
 * @since 1.0
 */
public class TestGameMap
  {
	/**
	 * GameMap Class Reference
	 */
	static GameMap G1;
	/**
	 * Player Class Reference
	 */
	static RandomPlayer p1;
	
	/**
	 * Object created before all the test method
	 * 
	 */
	@BeforeClass
	public static void testsetup()
	  {
		G1 = new GameMap();
		try
		  {
			p1 = new RandomPlayer("P1", 7);
		  } catch (NullPointerException | InvalidPlayerNameException e)
		  {
			e.printStackTrace();
		  } catch (ca.riskgamet31.exceptions.InvalidNameException e)
		  {
			e.printStackTrace();
		  }
	  }
	  
	/**
	 * Distribute armies among the player test method
	 * 
	 */
	@Test
	public void testbonusArmiesForPlayer()
	  {
		int expected = 7;
		assertNotEquals(expected, G1.bonusArmiesForPlayer("P1"));
		
	  }
	  
  }
