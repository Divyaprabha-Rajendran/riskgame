package ca.riskgamet31test.maincomps;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.naming.InvalidNameException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.org.apache.bcel.internal.generic.POP2;

import ca.riskgamet31.exceptions.InvalidPlayerNameException;
import ca.riskgamet31.maincomps.Card;
import ca.riskgamet31.maincomps.Continent;
import ca.riskgamet31.maincomps.Country;
import ca.riskgamet31.maincomps.Graph;
import ca.riskgamet31.maincomps.GraphNode;
import ca.riskgamet31.maincomps.Player;
import junit.framework.Assert;
/**
 * Tests the Player class
 * @author Chitra
 * @version 1.0
 * @since 1.0
 */
public class TestPlayer
{
	static Country c1,c2,c3;
	static GraphNode g1,g2;
	static Player p1;
	static Graph G1;
	static Continent C1;
	
	static Card card1,card2,card3;
	int	A1,A2,A3;
	@BeforeClass
	public static void testsetup()
	{
	    c1=new Country("Dubai");
	    c2=new Country("Russia");
	    c3=new Country("Qator");
	  
	    g1=new GraphNode(c1);
	    g2=new GraphNode(c2);
	    try {
			p1=new Player("player1",7);
		} catch (NullPointerException | InvalidNameException | InvalidPlayerNameException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		G1=new Graph();
		C1=new Continent("Africa",3,G1);
		card1=new Card("Infantry",c1);
		card2=new Card("Cavalry",c1);
		card3=new Card("Artillery",c1);
	}
	/*@Test
	public void testreinforce()
	{ 
	
		p1.addCountry(g2);
		System.out.println(p1);
		A1=p1.getPlayerArmies();
		
		p1.reinforce("Russia");
		
		A2=p1.getPlayerArmies();
		System.out.println(A2);
		assertEquals(A1, A2);
	
	
		
	}*/
	@Test
	public void testCountry()
	{
		
		A1 =p1.getCountry().size();
		 p1.addCountry(g1);
		A2=p1.getCountry().size();
		assertNotEquals(A1,A2);
		p1.removeCountry(g1);
		A3=p1.getCountry().size();
		assertEquals(A1,A3);
	}
	
	@Test
	public  void testarmies()
	{
		int army1=12;
		//p1.incrementArmies(12);
		assertNotEquals(army1,p1);
		p1.decrementArmies(army1);
		assertNotEquals(army1, p1);
	}
	@Test
	public  void testcard()
	{ 
		A1=p1.getPlayerCards().size();
		p1.addNewCard(card1);
		A2=p1.getPlayerCards().size();
		assertNotEquals(A1,A2);
		p1.addNewCard(card2);
		p1.addNewCard(card3);
		int A4=p1.getPlayerCards().size();
		int[] A5= new int[A4];
		p1.removeCards(A5);
		A3=p1.getPlayerCards().size();
		assertEquals(0,A3);
		
	}
	
	/*@Test
	public void testfortification()
	{
	   A1=p1.getPlayerArmies();
		p1.fortification("Russia","Qator");
		A2=p1.getPlayerArmies();
		assertEquals(A1, A2);
		
	}*/
	
//	@Test
//	public void testreinforcementArmiesCalc()
//	{
//		int expected=3;
//		assertEquals(expected,p1.reinforcementArmiesCalc());
//	}
	

}
