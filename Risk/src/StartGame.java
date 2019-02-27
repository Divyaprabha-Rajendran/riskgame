
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import ca.riskgamet31.*;
import ca.riskgamet31.maincomps.Continent;
import ca.riskgamet31.maincomps.GameMap;
import ca.riskgamet31.maincomps.Graph;
import ca.riskgamet31.maincomps.GraphNode;
import ca.riskgamet31.mapdata.CreateMap;

public class StartGame 
{
	GameMap Risk;
	
	public void createGameMap(String xmlpath)
	{
		CreateMap cmap=new CreateMap(xmlpath);
		HashMap<String,Continent> continentsList = cmap.generateGraph();
		Graph gameMapGraph = new Graph(cmap.getAllCountryNodes());
		
		Risk=new GameMap(xmlpath, continentsList, gameMapGraph);
		
		cmap.displayMap();
		
	}
    public static void main(String args[])
    {
    	System.out.println("Welcome to RISK...");
    	StartGame game=new StartGame();
    	Scanner scan = new Scanner(System.in);
    	String xmlpath=scan.nextLine();
    	game.createGameMap(xmlpath);
    }
}
