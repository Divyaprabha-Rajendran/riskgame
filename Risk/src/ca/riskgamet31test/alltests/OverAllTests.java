package ca.riskgamet31test.alltests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * test suits consolidates all sub test suites
 * @author Fareed Tayar
 *
 */
@RunWith(Suite.class)
@SuiteClasses(
  { ca.riskgamet31test.mapdata.AllTests.class,ca.riskgamet31test.utility.AllTests.class, ca.riskgamet31test.maincomps.AllTests.class, ca.riskgamet31test.controllers.AllTests.class })
public class OverAllTests
  {
	
  }
