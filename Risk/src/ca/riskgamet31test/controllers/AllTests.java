package ca.riskgamet31test.controllers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(
  { TestGameMainDriver.class, TestPlayer.class, TestPlayerModel.class, TestStartupPhase.class })
public class AllTests
  {
	
  }
