package ca.riskgamet31.maincomps;

import java.io.Serializable;

/**
 * main continent class
 * 
 * @author Fareed Tayar
 * @version 1.0
 * @since 1.0
 */
public class Continent implements Serializable
  {
	
	/**
	 * continent name
	 */
	private final String continentName;
	/**
	 * value of additional armies for this continent.
	 */
	private final int additionalBonusArmies;
	/**
	 * graph object representing this continent.
	 */
	private Graph continentGraph;
	
	/**
	 * constructs new continent object
	 * 
	 * @param continentName         name of continent
	 * @param additionalBonusArmies additional armies
	 * @param continentGraph        graph object
	 */
	public Continent(String continentName, int additionalBonusArmies,
	    Graph continentGraph)
	  {
		
		this.continentName = continentName.toUpperCase();
		this.additionalBonusArmies = additionalBonusArmies;
		this.continentGraph = continentGraph;
	  }
	  
	/**
	 * provides continent name
	 * 
	 * @return name of continent
	 */
	public String getContinentName()
	  {
		return continentName;
	  }
	  
	/**
	 * provides additional armies value.
	 * 
	 * @return provides additional armies value.
	 */
	public int getAdditionalBonusArmies()
	  {
		return additionalBonusArmies;
	  }
	  
	/**
	 * to get continent graph
	 * 
	 * @return continent graph
	 */
	public Graph getContinentGraph()
	  {
		return continentGraph;
	  }
	  
	/**
	 * to set continent graph
	 * 
	 * @param continentGraph continent graph
	 */
	public void setContinentGraph(Graph continentGraph)
	  {
		this.continentGraph = continentGraph;
	  }
	  
	/**
	 * checks whether this continent is connected graph or not.
	 * 
	 * @return true if connected graph.
	 */
	public boolean isConnected()
	  {
		
		return this.continentGraph.isConnected();
		
	  }
	  
	/**
	 * overrides Object's equal method.
	 * 
	 * @return true if both continents have similar names.
	 */
	@Override
	public boolean equals(Object otherContinent)
	  {
		if (otherContinent == null)
		  return false;
		if (!(otherContinent instanceof Continent))
		  return false;
		if (otherContinent == this)
		  return true;
		return (this.getContinentName().equals(((Continent) otherContinent)
		    .getContinentName()));
	  }
	  
	/**
	 * returns a string representation of the continent
	 */
	@Override
	public String toString()
	  {
		return "Continent [continent Name=" + continentName + ", Bonus Armies=" + additionalBonusArmies + "]";
	  }
	  
	/**
	 * prints a view of the continent graph elements
	 */
	public void viewContinent()
	  {
		
		System.out.println(this.toString());
		
		this.getContinentGraph().viewGraph();
		
	  }
	  
  }
