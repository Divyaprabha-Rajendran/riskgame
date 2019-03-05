package ca.riskgamet31test.controllers;

import static org.junit.Assert.*;


import org.junit.Test;



import static org.junit.Assert.*;

import javax.naming.InvalidNameException;

import org.junit.BeforeClass;
import org.junit.Test;

import ca.riskgamet31.controllers.StartUpPhase;
import ca.riskgamet31.exceptions.InvalidPlayerNameException;
import ca.riskgamet31.maincomps.Country;
import ca.riskgamet31.maincomps.GraphNode;
import ca.riskgamet31.maincomps.Player;

/**
 * Tests the StartupPhase class
 * @author Chitra
 * @version 1.0
 * @since 1.0
 */

public class TestStartupPhase 
{
	static StartUpPhase S1;
	static Player p1,p2;
	static Country C1,C2;
	static GraphNode G1,G2;

	@BeforeClass
	public static void Testsetup() throws NullPointerException, InvalidNameException, InvalidPlayerNameException
	{
	S1=new StartUpPhase();
	p1=new Player("P1",4);
	C1=new Country("india");
	C2=new Country("china");
	G1= new GraphNode(C1);
	G2=new GraphNode(C2);
	p1.addCountry(G1);
	p1.addCountry(G2);
	
	}

	@Test
	public void TestdistributeArmies()
	{
		int a1=p1.getPlayerArmies();
		S1.distributeArmies(p1);
		int a2=p1.getPlayerArmies();
		assertNotEquals(a1, a2);
	}

}

