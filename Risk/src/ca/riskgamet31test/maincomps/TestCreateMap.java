package ca.riskgamet31test.maincomps;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import javax.naming.InvalidNameException;

import org.junit.BeforeClass;
import org.junit.Test;

import ca.riskgamet31.controllers.GameMainDriver;
import ca.riskgamet31.maincomps.Continent;
import ca.riskgamet31.maincomps.Country;
import ca.riskgamet31.maincomps.Graph;
import ca.riskgamet31.maincomps.GraphNode;
import ca.riskgamet31.mapdata.CreateMap;

public class TestCreateMap 
{
	
	static CreateMap c1;
	static GraphNode g1;
	static Country C1;
	static ArrayList<GraphNode> countriesList;
	static Graph G1;
	@BeforeClass
	public static void testsetup()
	{
		
		File xmlFile = new File(System.getProperty("user.dir")+"\\Risk_MapData\\default_map.xml");
		c1=new CreateMap(xmlFile.getPath());
		C1=new Country("Africa");
		g1=new GraphNode(C1);
		G1=new Graph();
		countriesList= new ArrayList<>();
		countriesList.add(g1);
	}
	
	@Test
   public void TestcreateGraphNode() throws NullPointerException, InvalidNameException
   {
		int a1=c1.getCountryMap().size();
		c1.createGraphNode("america");
		int a2= c1.getCountryMap().size();
		assertNotEquals(a1,a2);
   }
	@Test
	public void TestcreateContinents()
	{
		int a1=c1.getContinentSet().size();
		c1.createContinents("Africa",3,countriesList);
		int a2=c1.getContinentSet().size();
		 assertNotEquals(a1,a2);
	}
}
