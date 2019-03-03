package ca.riskgamet31.mapdata;

import ca.riskgamet31.maincomps.*;
import jdk.nashorn.internal.ir.VarNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

/**
 * Creation of Map done from reading the xml file
 * Creating continents and adding countries to continents
 * Creating and establishing links between countries
 * 
 * @author Divyaprabha Rajendran
 * @version 1.0
 * @since 1.0
 */


/**
 * @author SONY
 *
 */

public class CreateMap 
{
	
	private File xmlFile=null;
	private Document xmlDoc=null;
	private HashSet<String> countrySet=null;
	private HashSet<String> continentSet = null;
	private HashMap<String,GraphNode> countryMap = null;
	private HashMap<String,Continent> mapData = null;
	private ValidateMapInput validateMap=null;
	
	public CreateMap() {
		
	}
	
	
	
	public CreateMap(String xmlPath)
	{
		xmlFile = new File(xmlPath);
		countrySet=new HashSet<String>();
		continentSet=new HashSet<String>();
		countryMap=new HashMap<String,GraphNode>();
		mapData=new HashMap<String,Continent>();
		validateMap = new ValidateMapInput();
	}
	
	/**
	 * Create a document builder and factory to load the xml file and its contents.
	 *
	 */
	
	public void loadMapData()
	{
		DocumentBuilderFactory mapFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder mapBuilder;
		try 
		{
			mapBuilder = mapFactory.newDocumentBuilder();
			xmlDoc = mapBuilder.parse(xmlFile);
	        xmlDoc.getDocumentElement().normalize();
	        System.out.println("Document loaded successfully...");
		} 
		catch (ParserConfigurationException e) 
		{
			e.printStackTrace();
		} 
		catch (SAXException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
        
	}
	
	/**
	 * creates a GraphNode and creates a country and assign it to GraphNode
	 * @param name of the country object
	 *
	 */
	public GraphNode createGraphNode(String countryName)
	{
		validateMap.checkExistingCountry(countryName, countrySet);
		validateMap.checkContinentAgainstCountries(countryName, continentSet);
		countrySet.add(countryName);
		Country currentCountry=new Country(countryName);
		GraphNode newNode = new GraphNode(currentCountry);
		countryMap.put(countryName,newNode);
		return newNode;
	}
	
	/**
	 * Create a continent object, assign the name,additionalBonusArmies and country read from XML file.
	 * @param ContinentName
	 * @param additionalBonusArmies
	 * @param countriesList
	 * @return continent Object 
	 */
	
	public Continent createContinents(String continentName, int additionalBonusArmies, ArrayList<GraphNode> countriesList)
	{
		validateMap.checkExistingContinent(continentName, continentSet);
		validateMap.checkContinentAgainstCountries(continentName, countrySet);
		Continent currentContinent=null;
		continentSet.add(continentName);
		Graph continentGraph = new Graph(countriesList);
		currentContinent = new Continent(continentName,additionalBonusArmies,continentGraph);
		return currentContinent;
	}
	
	/**
	 * Get all countries belonging to a continent read from XML file.
	 * @param continentElement 
	 * @return countriesList an arraylist of countries.
	 *
	 */
	public ArrayList<GraphNode> getCountries(Element continentElement) 
	{
		ArrayList<GraphNode> countriesList = new ArrayList<GraphNode>();
		NodeList countryList = continentElement.getElementsByTagName("country");
        System.out.println(countryList.getLength());
        for (int temp = 0; temp < countryList.getLength(); temp++)
		{
        	Node countryNode = countryList.item(temp);
        	String country_name=countryNode.getTextContent();
        	System.out.println("Creating country "+country_name);
        	countriesList.add(createGraphNode(country_name));
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
		for (int temp = 0; temp < continentList.getLength(); temp++)
		{
			Node continentNode = continentList.item(temp);	
			if (continentNode.getNodeType() == Node.ELEMENT_NODE)
			{
				Element continentElement = (Element) continentNode;
				String continentName=continentElement.getElementsByTagName("name").item(0).getTextContent();
				int additionalBonusArmies=Integer.parseInt(continentElement.getElementsByTagName("bonus-armies").item(0).getTextContent());
				System.out.println("Creating continent "+continentName);
				ArrayList<GraphNode> countriesList=getCountries(continentElement);
				mapData.put(continentName, createContinents(continentName, additionalBonusArmies, countriesList));
				System.out.println("**************************************************************************");
			}
		}
	}
	
	/**
	 * Get the links elements from the XML file and get the from-country and to-country.
	 *
	 */
	
	public void processLinks()
	{
		NodeList linkList = xmlDoc.getElementsByTagName("link");
		//System.out.println(linkList.getLength());
		for (int temp=0;temp<linkList.getLength();temp++)
		{
			Node curr_node = linkList.item(temp);
			if (curr_node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element curr_element = (Element) curr_node;
				String from_country=curr_element.getElementsByTagName("from-country").item(0).getTextContent();
				String to_country=curr_element.getElementsByTagName("to-country").item(0).getTextContent();
				System.out.println("processing links for "+to_country);
				addLinks(from_country, to_country);
			}
		}
		System.out.println("***********************************************************************************");
	}
	
	/**
	 * Establish link from a country to another country by adding it to each others's neighbours list. 
	 * @param from_country
	 * @param to_country
	 */
	
	public void addLinks(String from_country,String to_country)
	{
		String [] to_countries;
		GraphNode from_country_obj=countryMap.get(from_country);
		GraphNode to_country_obj;
		if (to_country.contains(","))
		{
			to_countries = to_country.split(",");
			for (String country : to_countries)
			{
				to_country_obj = countryMap.get(country);
				if (!(to_country_obj.getNodeNeighbors().contains(from_country_obj)))
					to_country_obj.getNodeNeighbors().add(from_country_obj);
				if (!(from_country_obj.getNodeNeighbors().contains(to_country_obj)))
					from_country_obj.getNodeNeighbors().add(to_country_obj);
			}
		}
		else
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
	 */

	public ArrayList<GraphNode> getAllCountryNodes()
	{
		ArrayList<GraphNode> countryNodes = new ArrayList<GraphNode>(countryMap.values());
		return countryNodes;
	}
	
	/**
	 * displays all the countries available within the continent
	 * @param contienent 
	 *
	 */

	public void displayContinentGraph(Continent continent)
	{ 
		Graph continentGraph = continent.getContinentGraph();
		ArrayList<GraphNode> nodes= continentGraph.getGraphNodes();
		
		for (GraphNode node : nodes)
		{
			String neighbours="";
			String curr_country=node.getNodeData().getCountryName();
			ArrayList<GraphNode> neighbour_node=node.getNodeNeighbors();
			
			for (GraphNode n_node : neighbour_node)
			{
				neighbours=neighbours+n_node.getNodeData().getCountryName()+",";
			}
			
			System.out.println(continent.getContinentName()+"--->"+curr_country+"--------->"+neighbours);
		}
	}
	
	public Continent getContinentByName(String continentName) {
		return mapData.get(continentName);
    }
	
	public Country getCountryByName(String countryName) {
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
			displayContinentGraph(continent);
			System.out.println("***************************************************************************");
		}
	}
	
	public HashMap<String,Continent> generateGraph() 
	{
		loadMapData();
		getContinents();
		processLinks();
		return mapData;
	}
	
	public File getXmlFile()
	  {
		return xmlFile;
	  }

	public void setXmlFile(File xmlFile)
	  {
		this.xmlFile = xmlFile;
	  }

	public Document getXmlDoc()
	  {
		return xmlDoc;
	  }

	public void setXmlDoc(Document xmlDoc)
	  {
		this.xmlDoc = xmlDoc;
	  }

	public HashSet<String> getCountrySet()
	  {
		return countrySet;
	  }

	public void setCountrySet(HashSet<String> countrySet)
	  {
		this.countrySet = countrySet;
	  }

	public HashSet<String> getContinentSet()
	  {
		return continentSet;
	  }

	public void setContinentSet(HashSet<String> continentSet)
	  {
		this.continentSet = continentSet;
	  }

	public HashMap<String, GraphNode> getCountryMap()
	  {
		return countryMap;
	  }

	public void setCountryMap(HashMap<String, GraphNode> countryMap)
	  {
		this.countryMap = countryMap;
	  }

	public HashMap<String, Continent> getMapData()
	  {
		return mapData;
	  }

	public void setMapData(HashMap<String, Continent> mapData)
	  {
		this.mapData = mapData;
	  }

	public static void main(String args[])
	{
		CreateMap cmap=new CreateMap("C:\\Users\\Yash Doshi\\git\\master\\Risk\\Risk_MapData\\map.xml");
		cmap.loadMapData();
		cmap.getContinents();
		System.out.println(cmap.mapData.size());
		cmap.displayMap();
		cmap.processLinks();
		cmap.displayMap();
	}
}
