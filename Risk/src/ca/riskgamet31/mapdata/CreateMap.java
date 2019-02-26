package ca.riskgamet31.mapdata;

import ca.riskgamet31.maincomps.*;


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

public class CreateMap 
{
	
	File xmlFile=null;
	Document xmlDoc=null;
	HashSet<String> continentSet = null;
	HashMap<String,GraphNode> countryMap = null;
	HashMap<String,Continent> mapData = null;
	
	
	public CreateMap(String xmlPath)
	{
		xmlFile = new File(xmlPath);
		continentSet=new HashSet<String>();
		countryMap=new HashMap<String,GraphNode>();
		mapData=new HashMap<String,Continent>();
	}
	
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
	
	
	public GraphNode createGraphNode(String countryName)
	{
		Country currentCountry=new Country(countryName);
		GraphNode newNode = new GraphNode(currentCountry);
		countryMap.put(countryName,newNode);
		return newNode;
	}
	
	public Continent createContinents(String ContinentName, int additionalBonusArmies, ArrayList<GraphNode> countriesList)
	{
		continentSet.add(ContinentName);
		Graph continentGraph = new Graph(countriesList);
		Continent currentContinent = new Continent(ContinentName,additionalBonusArmies,continentGraph);
		return currentContinent;
	}
	
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
	
	
	public void displayMap(HashMap<String,Continent> mapData)
	{
		for (Entry<String, Continent> entry : mapData.entrySet())
		{
			String key = entry.getKey().toString();
			Continent continent = entry.getValue();
			displayContinentGraph(continent);
			System.out.println("***************************************************************************");
		}
	}
	
	public static void main(String args[])
	{
		CreateMap cmap=new CreateMap("C:\\Users\\Fareed Tayar\\git\\riskgame\\Risk\\Risk_MapData\\map.xml");
		cmap.loadMapData();
		cmap.getContinents();
		System.out.println(cmap.mapData.size());
		cmap.displayMap(cmap.mapData);
		cmap.processLinks();
		cmap.displayMap(cmap.mapData);
	}
}
