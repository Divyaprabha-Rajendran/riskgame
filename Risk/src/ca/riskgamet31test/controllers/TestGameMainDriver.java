
package ca.riskgamet31test.controllers;
import static org.junit.Assert.*;

import javax.naming.InvalidNameException;

import org.junit.BeforeClass;
import org.junit.Test;

import ca.riskgamet31.controllers.GameMainDriver;
import ca.riskgamet31.controllers.PlayerModel;
import ca.riskgamet31.controllers.StartUpPhase;
import ca.riskgamet31.maincomps.Player;

/**
 * Tests the TestGameMainDriver class
 * @author Chitra
 * @version 1.0
 * @since 1.0
 */

public class TestGameMainDriver 
{
	static GameMainDriver G1;
	static StartUpPhase s1;
	static PlayerModel P1;
	
	@BeforeClass
public  static void Testsetup()
	{
		G1=new GameMainDriver();
		s1=new StartUpPhase();
		P1=new PlayerModel();
	}
	
	@Test
public void TestcreatePlayer() throws NullPointerException, InvalidNameException
	{
		P1=G1.getPlayerList();
		int a1=P1.getPlayerList().size();
		G1.createPlayer();
		int a2=P1.getPlayerList().size();
	    assertNotEquals(a2, a1);
		
		
	 }

}
