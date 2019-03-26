package ca.riskgamet31test.alltests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(
  { ca.riskgamet31test.mapdata.AllTests.class, ca.riskgamet31test.maincomps.AllTests.class, ca.riskgamet31test.controllers.AllTests.class })
public class OverAllTests
  {
	
  }
