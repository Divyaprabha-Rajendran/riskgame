package ca.riskgamet31test.controllers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
/**
 * 
 * @author Chitra
 * @version 1.0
 * @since 1.0
 *
 */

@RunWith(Suite.class)
/**
 * Run the all the test class at once.
 */
@SuiteClasses(
  { TestGameMainDriver.class, TestPlayer.class, TestPlayerModel.class, TestStartupPhase.class })
public class AllTests
  {
	
  }
