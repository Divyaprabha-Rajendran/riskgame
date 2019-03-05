package ca.riskgamet31test.maincomps;

import static org.junit.Assert.*;


import javax.naming.InvalidNameException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.riskgamet31.controllers.GameMainDriver;
import ca.riskgamet31.controllers.StartUpPhase;
import ca.riskgamet31.exceptions.InvalidPlayerNameException;
import ca.riskgamet31.maincomps.Continent;
import ca.riskgamet31.maincomps.Country;
import ca.riskgamet31.maincomps.Graph;
import ca.riskgamet31.maincomps.GraphNode;

public class TestContinent
{	
	static Continent C1,C2;
	static Country c1,c2,c3;
	static GraphNode g6,g5,g4;
	static Graph G1;
	@BeforeClass
	public static void Testsetup() throws NullPointerException, InvalidNameException, InvalidPlayerNameException 
	{
		G1=new Graph(); 
		c1=new Country("Dubai");
	    c2=new Country("Russia");
	    c3=new Country("Qator");
		g4 = new GraphNode(c1);
	    g5 = new GraphNode(c2);
	    g6 = new GraphNode(c3);
		G1.addNode(g4);
		G1.addNode(g5);
		G1.addNode(g6);
		C1=new Continent("africa",4,G1);
	}
	@Test
	public void testEqual()
	{
		C2=new Continent("africa",4,G1);
		assertEquals(true,C1.equals(C2));
		
	}

}
