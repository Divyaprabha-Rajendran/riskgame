package ca.riskgamet31test.maincomps;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import ca.riskgamet31.maincomps.Player;
import ca.riskgamet31.maincomps.PlayerModel;

public class TestPlayerModel
{

	static Player p2;
	static PlayerModel p1;
    @BeforeClass
    public static void testsetup()
    {
    ArrayList<Player> PlayerList = new ArrayList<>();
    p1=new PlayerModel();
    p2=new Player("P1",6);
    	
    }
    @Test
    public  void testSetPlayerList()
    {
    	int A1=p1.getPlayerList().size();
    	p1.setPlayerList(p2);
    	int A2=p1.getPlayerList().size();
    	assertNotEquals(A1,A2);
    }
    
	

}
