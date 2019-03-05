/**
 * 
 */
package ca.riskgamet31test.maincomps;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ca.riskgamet31.maincomps.Country;

/**
 * test class to generate folders structure
 * 
 * @author Ishpreet singh
 *
 */
public class    TestCountry
  {
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	  {
	  }
	  
	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	  {
	  }
	  
	/**
	 * @throws java.lang.Exception
	 */
	@Before
public	void setUp() throws Exception
	  {
	  }
	  
	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	  {
	  }
	  
	@Test
	 public void testincreaseArmies()
		{
		Country cn = new Country("india");
		  assertEquals(5,cn.increaseArmies(5));
		  }
	@Test
	public void testreduceArmies()
	{
		Country cn = new Country("india");
				 assertEquals(-99,cn.reduceArmies(5));
	}
	@Test
	 public void testequals()
	 {
		Country cn = new Country("india");
		assertFalse(cn.equals("pakistan"));
		}
	 }
	  
  
