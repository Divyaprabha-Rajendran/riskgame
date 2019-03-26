package ca.riskgamet31test.maincomps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.riskgamet31.maincomps.Country;

/**
 * test class for testing country class
 * 
 * @author Ishpreet singh
 *
 */
public class TestCountry
  {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	  {
	  }
	  
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	  {
	  }
	  
	@Before
	public void setUp() throws Exception
	  {
	  }
	  
	@After
	public void tearDown() throws Exception
	  {
	  }
	  
	/**
	 * To test if no country is passed dose null pointer exception is raised or
	 * not
	 * 
	 * @throws NullPointerException if null is passed
	 */
	@Test(expected = NullPointerException.class)
	public void testCountry()
	  {
		Country cn = new Country(null);
	  }
	  
	/**
	 * To test if armies are increased
	 */
	@Test
	public void testincreaseArmies()
	  {
		Country cn = new Country("india");
		assertEquals(5, cn.increaseArmies(5));
	  }
	  
	/**
	 * To test if armies are reduced
	 */
	
	@Test
	public void testreduceArmies()
	  {
		Country cn = new Country("india");
		cn.increaseArmies(15);
		assertEquals(10, cn.reduceArmies(5));
	  }
	  
	/**
	 * To test if method equal work as desired
	 */
	@Test
	public void testequals()
	  {
		Country cn = new Country("INDIA");
		assertFalse(cn.getCountryName().equals("PAKISTAN"));
		Country cn1 = new Country("INDIA");
		assertFalse(cn1.getCountryName().equals(""));
		Country cn2 = new Country("INDIA");
		assertTrue(cn2.getCountryName().equals("INDIA"));
		
	  }
  }
