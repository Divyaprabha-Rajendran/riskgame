package ca.riskgamet31test.controllers;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * test suite for the contorllers test package
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
  { TestAggresivePlayer.class,TestBenevolentPlayer.class,TestCheaterPlayer.class,TestRandomPlayer.class,TestTournamentMode.class,TestGameMainDriver.class, TestPlayerModel.class, TestStartupPhase.class, TestHumanPlayer.class })
public class AllTests
  {
	
  }
