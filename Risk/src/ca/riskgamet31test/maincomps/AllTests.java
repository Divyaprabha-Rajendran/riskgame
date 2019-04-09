package ca.riskgamet31test.maincomps;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(
  { TestCard.class, TestContinent.class, TestCountry.class, TestDeckOfCards.class, TestDice.class, TestGameMap.class, TestGraph.class, TestGraphNode.class, TestHand.class })
public class AllTests
  {
	
  }
