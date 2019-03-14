package ca.riskgamet31.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import ca.riskgamet31.exceptions.InvalidContinentException;
import ca.riskgamet31.exceptions.InvalidCountryException;
import ca.riskgamet31.maincomps.Continent;
import ca.riskgamet31.maincomps.Country;
import ca.riskgamet31.maincomps.Graph;
import ca.riskgamet31.maincomps.GraphNode;
import ca.riskgamet31.utility.ValidateMapInput;

/**
 * Creation of Map done from reading the xml file Creating continents and adding
 * countries to continents Creating and establishing links between countries
 * 
 * @author Divyaprabha Rajendran
 * @version 1.0
 * @since 1.0
 */
public class CreateMap
  {
	
	private File xmlFile = null;
	private Document xmlDoc = null;
	private HashSet<String> countrySet = null;
	private HashSet<String> continentSet = null;
	private HashMap<String, GraphNode> countryMap = null;
	private HashMap<String, Continent> mapData = null;
	private ValidateMapInput validateMap = null;
	
	/**
	 * constructor
	 */
	public CreateMap()
	  {
		
	  }
	  
	/**
	 * constructor
	 * 
	 * @param xmlPath xmlpath
	 */
	public CreateMap(String xmlPath)
	  {
		xmlFile = new File(xmlPath);
		countrySet = new HashSet<String>();
		continentSet = new HashSet<String>();
		countryMap = new HashMap<String, GraphNode>();
		mapData = new HashMap<String, Continent>();
		validateMap = new ValidateMapInput();
	  }
	  
	/**
	 * Create a document builder and factory to load the xml file and its
	 * contents.
	 *
	 */
	public void loadMapData()
	  {
		DocumentBuilderFactory mapFactory = DocumentBuilderFactory
		    .newInstance();
		DocumentBuilder mapBuilder;
		try
		  {
			mapBuilder = mapFactory.newDocumentBuilder();
			xmlDoc = mapBuilder.parse(xmlFile);
			xmlDoc.getDocumentElement().normalize();
			System.out.println("Document loaded successfully...");
		  } catch (ParserConfigurationException e)
		  {
			e.printStackTrace();
		  } catch (SAXException e)
		  {
			e.printStackTrace();
		  } catch (IOException e)
		  {
			e.printStackTrace();
		  }
		  
	  }
	  
	/**
	 * creates a GraphNode and creates a country and assign it to GraphNode
	 * 
	 * @param countryName of the country object
	 * @return graph Node
	 */
	public GraphNode createGraphNode(String countryName)
	  {
		validateMap.checkExistingCountry(countryName, countrySet);
		validateMap.checkContinentAgainstCountries(countryName, continentSet);
		countrySet.add(countryName);
		Country currentCountry = new Country(countryName);
		GraphNode newNode = new GraphNode(currentCountry);
		countryMap.put(countryName, newNode);
		return newNode;
	  }
	  
	/**
	 * Create a continent object, assign the name,additionalBonusArmies and
	 * country read from XML file.
	 * 
	 * @param continentName         ContinentName
	 * @param additionalBonusArmies additionalBonusArmies
	 * @param countriesList         countriesList
	 * @return continent Object continent Object
	 */
	public Continent createContinents(String continentName,
	    int additionalBonusArmies, ArrayList<GraphNode> countriesList)
	  {
		validateMap.checkExistingContinent(continentName, continentSet);
		validateMap.checkContinentAgainstCountries(continentName, countrySet);
		Continent currentContinent = null;
		continentSet.add(continentName);
		Graph continentGraph = new Graph(countriesList);
		currentContinent = new Continent(continentName, additionalBonusArmies, continentGraph);
		return currentContinent;
	  }
	  
	/**
	 * Get all countries belonging to a continent read from XML file.
	 * 
	 * @param continentElement continentElement
	 * @return countriesList an arraylist of countries.
	 *
	 */
	public ArrayList<GraphNode> getCountries(Element continentElement)
	  {
		ArrayList<GraphNode> countriesList = new ArrayList<GraphNode>();
		NodeList countryList = continentElement.getElementsByTagName("country");
		try
		  {
			for (int temp = 0; temp < countryList.getLength(); temp++)
			  {
				Node countryNode = countryList.item(temp);
				String country_name = countryNode.getTextContent();
				countriesList.add(createGraphNode(country_name));
			  }
		  } catch (Exception e)
		  {
			e = new InvalidCountryException("Multiple countries need to be present or continent element is malformed");
		  }
		return countriesList;
	  }
	  
	/**
	 * Get all the continent elements from XML file.
	 *
	 */
	public void getContinents()
	  {
		NodeList continentList = xmlDoc.getElementsByTagName("continent");
		try
		  {
			for (int temp = 0; temp < continentList.getLength(); temp++)
			  {
				Node continentNode = continentList.item(temp);
				if (continentNode.getNodeType() == Node.ELEMENT_NODE)
				  {
					Element continentElement = (Element) continentNode;
					String continentName = continentElement
					    .getElementsByTagName("name").item(0).getTextContent();
					int additionalBonusArmies = Integer
					    .parseInt(continentElement
					        .getElementsByTagName("bonus-armies").item(0)
					        .getTextContent());
					ArrayList<GraphNode> countriesList = getCountries(continentElement);
					mapData
					    .put(continentName, createContinents(continentName, additionalBonusArmies, countriesList));
				  }
			  }
			  
		  } catch (Exception e)
		  {
			e = new InvalidContinentException("Atleast a single continent must be present or continent element is malformed");
		  }
	  }
	  
	/**
	 * Get the links elements from the XML file and get the from-country and
	 * to-country.
	 *
	 */
	
	public void processLinks()
	  {
		NodeList linkList = xmlDoc.getElementsByTagName("link");
		for (int temp = 0; temp < linkList.getLength(); temp++)
		  {
			Node curr_node = linkList.item(temp);
			if (curr_node.getNodeType() == Node.ELEMENT_NODE)
			  {
				Element curr_element = (Element) curr_node;
				String from_country = curr_element
				    .getElementsByTagName("from-country").item(0)
				    .getTextContent();
				String to_country = curr_element
				    .getElementsByTagName("to-country").item(0)
				    .getTextContent();
				validateMap.checkLinks(from_country, to_country, continentSet);
				addLinks(from_country, to_country);
			  }
		  }
		  
	  }
	  
	/**
	 * Establish link from a country to another country by adding it to each
	 * others's neighbours list.
	 * 
	 * @param from_country from_country
	 * @param to_country   to country
	 */
	public void addLinks(String from_country, String to_country)
	  {
		String[] to_countries;
		GraphNode from_country_obj = countryMap.get(from_country);
		GraphNode to_country_obj;
		if (to_country.contains(","))
		  {
			to_countries = to_country.split(",");
			for (String country : to_countries)
			  {
				to_country_obj = countryMap.get(country);
				if (!(to_country_obj.getNodeNeighbors()
				    .contains(from_country_obj)))
				  to_country_obj.getNodeNeighbors().add(from_country_obj);
				if (!(from_country_obj.getNodeNeighbors()
				    .contains(to_country_obj)))
				  from_country_obj.getNodeNeighbors().add(to_country_obj);
			  }
		  } else
		  {
			to_country_obj = countryMap.get(to_country);
			if (!(to_country_obj.getNodeNeighbors().contains(from_country_obj)))
			  to_country_obj.getNodeNeighbors().add(from_country_obj);
			if (!(from_country_obj.getNodeNeighbors().contains(to_country_obj)))
			  from_country_obj.getNodeNeighbors().add(to_country_obj);
		  }
		  
	  }
	  
	/**
	 * returns all the GraphNode objects with countries as arraylist
	 *@return list of graph nodes
	 */
	public ArrayList<GraphNode> getAllCountryNodes()
	  {
		ArrayList<GraphNode> countryNodes = new ArrayList<GraphNode>(countryMap
		    .values());
		return countryNodes;
	  }
	  
	/**
	 * displays all the countries available within the continent
	 * 
	 * @param continent continent to display its graph nodes 
	 *
	 */
	public void displayContinentGraph(Continent continent)
	  {
		Graph continentGraph = continent.getContinentGraph();
		ArrayList<GraphNode> nodes = continentGraph.getGraphNodes();
		
		for (GraphNode node : nodes)
		  {
			String neighbours = "";
			String curr_country = node.getNodeData().getCountryName();
			ArrayList<GraphNode> neighbour_node = node.getNodeNeighbors();
			
			for (GraphNode n_node : neighbour_node)
			  {
				neighbours = neighbours + n_node.getNodeData()
				    .getCountryName() + ",";
			  }
			  
		  }
	  }
	  
	/**
	 * get continent by name
	 * 
	 * @param continentName name of the continent
	 * @return continent
	 */
	public Continent getContinentByName(String continentName)
	  {
		return mapData.get(continentName);
	  }
	  
	/**
	 * get country by name
	 * 
	 * @param countryName country name
	 * @return country
	 */
	public Country getCountryByName(String countryName)
	  {
		return countryMap.get(countryName).getNodeData();
	  }
	  
	/**
	 * displays the contents of the gameMap.
	 *
	 */
	public void displayMap()
	  {
		for (Entry<String, Continent> entry : mapData.entrySet())
		  {
			String key = entry.getKey().toString();
			Continent continent = entry.getValue();
			continent.viewContinent();
			System.out.println();
			System.out
			    .println("***************************************************************************");
		  }
	  }
	  
	/**
	 * geneart graph
	 * 
	 * @return hash map of graph
	 */
	public HashMap<String, Continent> generateGraph()
	  {
		loadMapData();
		getContinents();
		processLinks();
		
		for (Continent continent : mapData.values())
		  {
			if (!continent.getContinentGraph().isConnected())
			  throw new InvalidContinentException(continent
			      .getContinentName() + " is not a connected graph.");
		  }
		  
		return mapData;
	  }
	  
	/**
	 * get xml file
	 * 
	 * @return get file path
	 */
	public File getXmlFile()
	  {
		return xmlFile;
	  }
	  
	/**
	 * setting xml files
	 * 
	 * @param xmlFile xml file to be set
	 */
	public void setXmlFile(File xmlFile)
	  {
		this.xmlFile = xmlFile;
	  }
	  
	/**
	 * to get xml doc
	 * 
	 * @return document
	 */
	public Document getXmlDoc()
	  {
		return xmlDoc;
	  }
	  
	/**
	 * to set xml file
	 * 
	 * @param xmlDoc xml file to be set
	 */
	public void setXmlDoc(Document xmlDoc)
	  {
		this.xmlDoc = xmlDoc;
	  }
	  
	/**
	 * to get country set
	 * 
	 * @return hash map of countries
	 */
	public HashSet<String> getCountrySet()
	  {
		return countrySet;
	  }
	  
	/**
	 * to set country
	 * 
	 * @param countrySet countries to be set
	 */
	public void setCountrySet(HashSet<String> countrySet)
	  {
		this.countrySet = countrySet;
	  }
	  
	/**
	 * to get continents
	 * 
	 * @return hash map of strings
	 */
	public HashSet<String> getContinentSet()
	  {
		return continentSet;
	  }
	  
	/**
	 * to set continent
	 * 
	 * @param continentSet continent
	 */
	public void setContinentSet(HashSet<String> continentSet)
	  {
		this.continentSet = continentSet;
	  }
	  
	/**
	 * to get country map
	 * 
	 * @return country map
	 */
	public HashMap<String, GraphNode> getCountryMap()
	  {
		return countryMap;
	  }
	  
	/**
	 * to set country map
	 * 
	 * @param countryMap country map
	 */
	public void setCountryMap(HashMap<String, GraphNode> countryMap)
	  {
		this.countryMap = countryMap;
	  }
	  
	/**
	 * to get country map
	 * 
	 * @return country map
	 */
	public HashMap<String, Continent> getMapData()
	  {
		return mapData;
	  }
	  
	/**
	 * to set country map
	 * 
	 * @param mapData to set map data
	 */
	public void setMapData(HashMap<String, Continent> mapData)
	  {
		this.mapData = mapData;
	  }
	  
  }
