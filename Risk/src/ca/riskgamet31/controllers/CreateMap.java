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
import ca.riskgamet31.exceptions.InvalidGraphException;
import ca.riskgamet31.exceptions.InvalidLinkException;
import ca.riskgamet31.exceptions.InvalidNameException;
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
	/**
	 * xml map file
	 */
	private File xmlFile = null;
	/**
	 * document object for map file
	 */
	private Document xmlDoc = null;
	/**
	 * hashmap for countries
	 */
	private HashSet<String> countrySet = null;
	/**
	 * hashmap for continents
	 */
	private HashSet<String> continentSet = null;
	/**
	 * graph nodes for countries
	 */
	private HashMap<String, GraphNode> countryMap = null;
	/**
	 * graph nodes for continents
	 */
	private HashMap<String, Continent> mapData = null;
	/**
	 * map validate 
	 */
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
			System.out.println("Document loaded successfully from " + xmlFile
			    .getAbsolutePath() + "\n");
		  } catch (ParserConfigurationException e)
		  {
			System.out.println(e.getMessage());
			
		  } catch (SAXException e)
		  {
			
			System.out.println(e.getMessage());
		  } catch (IOException e)
		  {
			
			System.out.println(e.getMessage());
		  }
		  
	  }
	  
	/**
	 * creates a GraphNode and creates a country and assign it to GraphNode
	 * 
	 * @param countryName of the country object
	 * @return graph Node
	 * @throws InvalidNameException    If the name of continent or country has
	 *                                 special characters or numbers
	 * @throws InvalidCountryException If there is a duplicate country
	 */
	public GraphNode createGraphNode(String countryName)
	    throws InvalidNameException, InvalidCountryException
	  {
		GraphNode newNode = null;
		
		Country currentCountry = new Country(countryName);
		newNode = new GraphNode(currentCountry);
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
	 * @throws InvalidNameException      If the name of continent or country has
	 *                                   special characters or numbers
	 * @throws InvalidContinentException If there is a duplicate continent
	 */
	public Continent createContinents(String continentName,
	    int additionalBonusArmies, ArrayList<GraphNode> countriesList)
	    throws InvalidNameException, InvalidContinentException
	  {
		Continent currentContinent = null;
		
		Graph continentGraph = new Graph(countriesList);
		currentContinent = new Continent(continentName, additionalBonusArmies, continentGraph);
		
		return currentContinent;
	  }
	  
	/**
	 * Get all countries belonging to a continent read from XML file.
	 * 
	 * @param continentElement continentElement
	 * @return countriesList an arraylist of countries.
	 * @throws InvalidNameException    If the name of continent or country has
	 *                                 special characters or numbers
	 * @throws InvalidCountryException If there is a duplicate country
	 * @throws InvalidGraphException   If the graph is invalid
	 */
	public ArrayList<GraphNode> getCountries(Element continentElement)
	    throws InvalidNameException, InvalidCountryException,
	    InvalidGraphException
	  {
		ArrayList<GraphNode> countriesList = new ArrayList<GraphNode>();
		NodeList countryList = continentElement.getElementsByTagName("country");
		if (countryList.getLength() > 0)
		  {
			for (int temp = 0; temp < countryList.getLength(); temp++)
			  {
				Node countryNode = countryList.item(temp);
				String country_name = countryNode.getTextContent();
				if (country_name.length() == 0)
				  throw new NullPointerException();
				
				validateMap.validateCountryorContinentName(country_name);
				validateMap.checkExistingCountry(country_name, countrySet);
				validateMap
				    .checkCountryAgainstContinents(country_name, continentSet);
				countrySet.add(country_name.toUpperCase());
				countriesList.add(createGraphNode(country_name));
			  }
		  }
		  
		else
		  {
			throw new InvalidGraphException("Continent " + continentElement
			    .getElementsByTagName("name").item(0)
			    .getTextContent() + " has no countries present");
		  }
		return countriesList;
	  }
	  
	/**
	 * Get all the continent elements from XML file.
	 * 
	 * @throws InvalidGraphException If the graph is invalid
	 * @throws Exception             For handling null values and XML malformed
	 *                               exceptions.
	 */
	public void getContinents() throws InvalidGraphException, Exception
	  {
		NodeList continentList = xmlDoc.getElementsByTagName("continent");
		
		if (continentList.getLength() > 0)
		  {
			for (int temp = 0; temp < continentList.getLength(); temp++)
			  {
				Node continentNode = continentList.item(temp);
				if (continentNode.getNodeType() == Node.ELEMENT_NODE)
				  {
					Element continentElement = (Element) continentNode;
					String continentName = continentElement
					    .getElementsByTagName("name").item(0).getTextContent()
					    .trim();
					if (continentName.length() == 0)
					  throw new NullPointerException();
					
					int additionalBonusArmies = Integer
					    .parseInt(continentElement
					        .getElementsByTagName("bonus-armies").item(0)
					        .getTextContent());
					
					validateMap.validateCountryorContinentName(continentName);
					validateMap
					    .checkExistingContinent(continentName, continentSet);
					validateMap
					    .checkContinentAgainstCountries(continentName, countrySet);
					continentSet.add(continentName.toUpperCase());
					
					ArrayList<GraphNode> countriesList = getCountries(continentElement);
					mapData
					    .put(continentName, createContinents(continentName, additionalBonusArmies, countriesList));
				  }
			  }
		  }
		  
		else
		  {
			throw new InvalidGraphException("XML has no continents present");
		  }
		  
	  }
	  
	/**
	 * Get the links elements from the XML file and get the from-country and
	 * to-country.
	 * 
	 * @throws InvalidLinkException If from and to countries are same.
	 */
	public void processLinks() throws InvalidLinkException
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
	 * @throws InvalidLinkException If from and to countries are same.
	 */
	public void addLinks(String from_country, String to_country)
	    throws InvalidLinkException
	  {
		String[] to_countries;
		GraphNode from_country_obj = countryMap.get(from_country);
		GraphNode to_country_obj;
		if (to_country.contains(","))
		  {
			to_countries = to_country.split(",");
			for (String country : to_countries)
			  {
				validateMap.checkLinks(from_country, country, continentSet);
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
	 * 
	 * @return list of graph nodes
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
	 * generate graph
	 * 
	 * @return hash map of graph
	 * @throws InvalidLinkException If from and to countries are same.
	 * @throws Exception            For handling null values and XML malformed
	 *                              exceptions.
	 */
	public HashMap<String, Continent> generateGraph()
	    throws InvalidLinkException, Exception
	  {
		loadMapData();
		getContinents();
		processLinks();
		
		for (Continent continent : mapData.values())
		  {
			if (!continent.getContinentGraph().isConnected())
			  try
				{
				  throw new InvalidContinentException(continent
				      .getContinentName() + " is not a connected graph.");
				} catch (InvalidContinentException e)
				{
				  System.out.println(e.getMessage());
				  
				}
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
