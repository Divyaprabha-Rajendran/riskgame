package ca.riskgamet31.views;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

import ca.riskgamet31.controllers.GameMainDriver;
import ca.riskgamet31.maincomps.Continent;
import ca.riskgamet31.maincomps.Country;
import ca.riskgamet31.maincomps.GraphNode;

/**
 * player world domination view class
 * 
 * @author Fareed Tayar
 * @version 1.2
 */
public class PlayersWorldDominationView implements Observer,Serializable
  {
	
	/**
	 * data structure hosting view information
	 */
	private Map<String, String> worldDominationViewInfo;
	
	/**
	 * method to calculate view statistics
	 * 
	 * @param gmd game object
	 */
	private void calculateWorldDominationInfo(GameMainDriver gmd)
	  {
		
		ArrayList<GraphNode> mapGraphNodes = gmd.getGameMap().getGameMapGraph()
		    .getGraphNodes();
		final int countryCount = mapGraphNodes.size();
		
		Map<String, String> countriesArmiesPerPlayer = mapGraphNodes.stream()
		    .map(i -> i.getNodeData()).collect(Collectors
		        .groupingBy(Country::getCurrentOccupier, Collectors
		            .collectingAndThen(Collectors
		                .summarizingInt(Country::getArmies), s -> String
		                    .format("%.2f%%    %d", ((float) s
		                        .getCount() * 100 / (float) countryCount), s
		                            .getSum()))));
		
		for (Map.Entry<String, String> mapEntry : countriesArmiesPerPlayer
		    .entrySet())
		  {
			
			for (Continent continent : gmd.getGameMap().getContinentsList()
			    .values())
			  {
				
				if ((continent.getContinentGraph().getGraphNodes().stream()
				    .map((c) -> c.getNodeData().getCurrentOccupier()).anyMatch((
				        o) -> !o.equals(mapEntry.getKey()))))
				  {
					
				  } else
				  {
					String mapV = mapEntry.getValue();
					mapV += "\t" + continent.getContinentName() + ",";
					mapEntry.setValue(mapV);
				  }
				  
			  }
			  
		  }
		  
		this.worldDominationViewInfo = countriesArmiesPerPlayer;
		
	  }
	  
	/**
	 * update method to be executed when view is triggered
	 * @param o observed object
	 * @param arg data object passed from the observable
	 */
	@Override
	public void update(Observable o, Object arg)
	  {
		
		GameMainDriver gmd = (GameMainDriver) o;
		calculateWorldDominationInfo(gmd);
		System.out.println("");
		System.out
		    .println("-------------------- Players World Domination Statistics --------------------");
		System.out
		    .printf("\t\t%-12s %%Owned     NOA  Owned Continents%n", "Player:");
		System.out
		    .printf("\t\t%-12s ------     ---  ----------------%n", "-------");
		worldDominationViewInfo.forEach((x, y) -> System.out
		    .printf("\t\t%-12s: %s%n", x, y));
		System.out.println("");
	  }
	  
  }
