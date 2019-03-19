
package ca.riskgamet31test.controllers;

import static org.junit.Assert.assertNotEquals;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import javax.naming.InvalidNameException;

import org.junit.BeforeClass;
import org.junit.Test;

import ca.riskgamet31.controllers.GameMainDriver;
import ca.riskgamet31.controllers.PlayerModel;
import ca.riskgamet31.controllers.StartUpPhase;

/**
 * Tests the GameMainDriver class for creating players
 * 
 * 
 * @author  Chitra 
 * @version 1.1
 * @since 1.0
 * 
 */

public class TestGameMainDriver
  {
	/**
	 * 
	 * GameMainDriver Class Reference 
	 * 
	 */
	static GameMainDriver G1;
	/**
	 * StartUpPhase Class Reference 
	 * 
	 */
	static StartUpPhase s1;
	/**
	 * PlayerModel Class Reference 
	 * 
	 */
	static PlayerModel P1;
	/**
	 * Object created before all the test method 
	 * 
	 */
	
	@BeforeClass
	public static void Testsetup()
	  {
		G1 = new GameMainDriver();
		s1 = new StartUpPhase();
		P1 = new PlayerModel();
		
	  }
	  
	/**
	 * Testing the Create Player method
	 * @throws ca.riskgamet31.exceptions.InvalidNameException 
	 */
	@Test
	public void TestcreatePlayer() throws NullPointerException,InvalidNameException, ca.riskgamet31.exceptions.InvalidNameException
	  {
		P1 = G1.getPlayerList();
		int a1 = P1.getPlayerList().size();
		String input = "3\np1\np2\np3\n";
		InputStream in = new ByteArrayInputStream(input.getBytes());
	    System.setIn(in);
	  	G1.createPlayer();
		int a2 = P1.getPlayerList().size();
		assertNotEquals(a2, a1);
		
	  }
	  
  }
