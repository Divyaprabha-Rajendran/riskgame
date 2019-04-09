package ca.riskgamet31test.utility;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test Suite class for testing saving and loading of gamestate.
 * @author Divyaprabha Rajendran
 * @version 1.0
 */
@RunWith(Suite.class)
/**
 * Run the all the test class at once.
 */
@SuiteClasses({ SaveGameTest.class,LoadGameTest.class  })
public class AllTests {

}
