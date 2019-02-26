package ca.riskgamet31.maincomps;

import java.util.ArrayList;

/**
 * main map Object
 * 
 * @author Fareed Tayar
 * @version 1.0
 * @since 1.0
 */
public class Map
  {
	/**
	 * the map xml file, for info only 
	 */
	private String mapFile;
	/**
	 * a list of continent objects composing the map
	 */
	private ArrayList<Continent> continentsList = new ArrayList<>();
	/**
	 * a graph object consists of all map nodes
	 */
	private Graph mapGraph = new Graph();
	
	
	/**
	 * @param mapFile reference map xml file
	 * @param continentsList list of continent objects
	 * @param mapGraph map graph
	 */
	public Map(String mapFile, ArrayList<Continent> continentsList,
	    Graph mapGraph)
	  {
		this.mapFile = mapFile;
		this.continentsList = continentsList;
		this.mapGraph = mapGraph;
	  }

	/**
	 * @return true only and only if the map is a connected graph.
	 */
	public boolean isConnected()
	
	{
	  return this.mapGraph.isConnected();
	}
	
	
	/**
	 * @return the name of map xml file
	 */
	public String getMapFile()
	  {
		return mapFile;
	  }

	/**
	 * @param mapFile sets the map xml file
	 */
	public void setMapFile(String mapFile)
	  {
		this.mapFile = mapFile;
	  }

	/**
	 * @return an ArrayList of map continents
	 */
	public ArrayList<Continent> getContinentsList()
	  {
		return continentsList;
	  }

	/**
	 * @param continentsList updates maps arrayList of continents
	 */
	public void setContinentsList(ArrayList<Continent> continentsList)
	  {
		this.continentsList = continentsList;
	  }

	/**
	 * @return map graph object
	 */
	public Graph getMapGraph()
	  {
		return mapGraph;
	  }

	/**
	 * @param mapGraph new map graph object
	 */
	public void setMapGraph(Graph mapGraph)
	  {
		this.mapGraph = mapGraph;
	  }
	
  }
