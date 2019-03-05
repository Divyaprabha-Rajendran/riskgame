package ca.riskgamet31test.maincomps;

import static org.junit.Assert.*;

import javax.naming.InvalidNameException;

import org.junit.BeforeClass;
import org.junit.Test;

import ca.riskgamet31.exceptions.InvalidPlayerNameException;
import ca.riskgamet31.maincomps.GameMap;
import ca.riskgamet31.maincomps.Player;

/**
 * Tests the GameMap  class
 * @author Chitra
 * @version 1.0
 * @since 1.0
 */
public class TestGameMap 
{
	static GameMap G1;
	static Player p1;
	@BeforeClass
	public static void testsetup()
    {
	    G1=new GameMap();
	    try {
			p1=new Player("P1",7);
		} catch (NullPointerException | InvalidNameException | InvalidPlayerNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	@Test
	public void testbonusArmiesForPlayer()
	{
		int expected=7;
		assertNotEquals(expected,G1.bonusArmiesForPlayer("P1"));
		
	}
	
}
