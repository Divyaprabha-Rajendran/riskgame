package ca.riskgamet31test.maincomps;

import org.junit.runner.RunWith;

import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
/**
 * 
 * @author Chitra
 * @version 1.1
 * @since 1.0
 *
 */
@RunWith(Suite.class)
/**
 * Run the all the test class at once.
 */
@SuiteClasses(
  { TestContinent.class,TestCard.class,TestDeckOfCards.class, TestCountry.class, TestGameMap.class,TestGraph.class, TestGraphNode.class })
public class AllTests
  {
	
  }
