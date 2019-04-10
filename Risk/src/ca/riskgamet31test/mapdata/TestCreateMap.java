
package ca.riskgamet31test.mapdata;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import ca.riskgamet31.controllers.CreateMap;
import ca.riskgamet31.exceptions.InvalidContinentException;
import ca.riskgamet31.exceptions.InvalidCountryException;
import ca.riskgamet31.maincomps.Continent;
import ca.riskgamet31.maincomps.Country;
import ca.riskgamet31.maincomps.Graph;
import ca.riskgamet31.maincomps.GraphNode;

/**
 * Tests the CreateMap class
 * 
 * @author Chitra
 * @version 1.1
 * @since 1.0
 */
public class TestCreateMap
  {
	/**
	 * CreateMap Class Reference
	 */
	static CreateMap c1;
	/**
	 * GraphNode Class Reference
	 */
	static GraphNode g1;
	/**
	 * Country Class Reference
	 */
	static Country C1;
	/**
	 * Arraylist of country nodes
	 */
	static ArrayList<GraphNode> countriesList;
	/**
	 * Graph Class Reference
	 */
	static Graph G1;
	
	/**
	 * Object created before all the test method
	 * 
	 */
	@BeforeClass
	public static void testsetup()
	  {
		
		File xmlFile = new File(System
		    .getProperty("user.dir") + "\\Risk_MapData\\default_map.xml");
		c1 = new CreateMap(xmlFile.getPath());
		C1 = new Country("china");
		g1 = new GraphNode(C1);
		G1 = new Graph();
		countriesList = new ArrayList<>();
		countriesList.add(g1);
	  }
	  
	/**
	 * Create graph node test method
	 * 
	 * @throws InvalidCountryException                        if the country
	 *                                                        name is duplicate
	 * @throws InvalidContinentException                      If there is a
	 *                                                        duplicate
	 *                                                        continent
	 * @throws ca.riskgamet31.exceptions.InvalidNameException if the name is
	 *                                                        invalid
	 * 
	 */
	@Test
	public void TestcreateGraphNode() throws InvalidContinentException,
	    ca.riskgamet31.exceptions.InvalidNameException, InvalidCountryException
	  {
		int a1 = c1.getCountryMap().size();
		c1.createGraphNode("america");
		int a2 = c1.getCountryMap().size();
		assertEquals(1, a2);
	  }
	  
	/**
	 * Create continents test method
	 * 
	 * @throws InvalidContinentException                      If there is a
	 *                                                        duplicate
	 *                                                        continent
	 * @throws ca.riskgamet31.exceptions.InvalidNameException if the name is
	 *                                                        invalid
	 */
	@Test
	public void TestcreateContinents()
	    throws ca.riskgamet31.exceptions.InvalidNameException,
	    InvalidContinentException
	  {
		int a1 = c1.getContinentSet().size();
		Continent co1 = c1.createContinents("hongkong", 3, countriesList);
		c1.getContinentSet().add(co1.getContinentName());
		int a2 = c1.getContinentSet().size();
		assertEquals(a1 + 1, a2);
	  }
	  
  }
