package ca.riskgamet31test.controllers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses(
  { TestAggresivePlayer.class, TestBenevolentPlayer.class, TestCheaterPlayer.class, TestGameMainDriver.class, TestHumanPlayer.class, TestPlayerModel.class, TestRandomPlayer.class, TestStartupPhase.class, TestTournamentMode.class })
public class AllTests
  {
	
  }
