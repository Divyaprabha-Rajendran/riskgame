package ca.riskgamet31.maincomps;

import java.util.HashMap;

/**
 * main map Object
 * 
 * @author Fareed Tayar
 * @version 1.0
 * @since 1.0
 */
public class GameMap
  {
	/**
	 * the map xml file, for info only 
	 */
	private String gameMapFile;
	/**
	 * a list of continent objects composing the map
	 */
	private HashMap<String,Continent> continentsList = new HashMap<>();
	/**
	 * a graph object consists of all map nodes
	 */
	private Graph gameMapGraph = new Graph();
	
	
	/**
	 * @param mapFile reference map xml file
	 * @param continentsList list of continent objects
	 * @param mapGraph map graph
	 */
	public GameMap(String gameMapFile, HashMap<String, Continent> continentsList,
	    Graph gameMapGraph)
	  {
		this.gameMapFile = gameMapFile;
		this.continentsList = continentsList;
		this.gameMapGraph = gameMapGraph;
	  }

	/**
	 * @return true only and only if the map is a connected graph.
	 */
	public boolean isConnected()
	
	{
	  return this.gameMapGraph.isConnected();
	}
	
	
	/**
	 * @return the name of map xml file
	 */
	public String getGameMapFile()
	  {
		return gameMapFile;
	  }

	/**
	 * @param mapFile sets the map xml file
	 */
	public void setGameMapFile(String mapFile)
	  {
		this.gameMapFile = mapFile;
	  }

	/**
	 * @return an ArrayList of map continents
	 */
	public HashMap<String, Continent> getContinentsList()
	  {
		return continentsList;
	  }

	/**
	 * @param continentsList updates maps arrayList of continents
	 */
	public void setContinentsList(HashMap<String, Continent> continentsList)
	  {
		this.continentsList = continentsList;
	  }

	/**
	 * @return map graph object
	 */
	public Graph getGameMapGraph()
	  {
		return gameMapGraph;
	  }

	/**
	 * @param mapGraph new map graph object
	 */
	public void setGameMapGraph(Graph mapGraph)
	  {
		this.gameMapGraph = mapGraph;
	  }
	
  }
