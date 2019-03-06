package ca.riskgamet31test.controllers;

import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;

import javax.naming.InvalidNameException;

import org.junit.BeforeClass;
import org.junit.Test;

import ca.riskgamet31.controllers.PlayerModel;
import ca.riskgamet31.exceptions.InvalidPlayerException;
import ca.riskgamet31.exceptions.InvalidPlayerNameException;
import ca.riskgamet31.maincomps.Player;

/**
 * Tests the PlayerModel class
 * 
 * @author Chitra
 * @version 1.0
 * @since 1.0
 */
public class TestPlayerModel
  {
	
	static Player p2;
	static PlayerModel p1;
	
	@BeforeClass
	public static void testsetup()
	  {
		ArrayList<Player> PlayerList = new ArrayList<>();
		p1 = new PlayerModel();
		try
		  {
			p2 = new Player("P1", 6);
		  } catch (NullPointerException | InvalidNameException
		      | InvalidPlayerNameException e)
		  {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
		  
	  }
	  
	@Test
	public void testSetPlayerList()
	  {
		int A1 = p1.getPlayerList().size();
		try
		  {
			p1.setPlayerList(p2);
		  } catch (InvalidPlayerException e)
		  {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  }
		int A2 = p1.getPlayerList().size();
		assertNotEquals(A1, A2);
	  }
	  
  }
