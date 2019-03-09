package ca.riskgamet31.maincomps;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;

/**
 * Class of Dice to get the result of rolling of dices
 * @author YD
 * @version 
 * @since
 * 
 */
public class Dice {
	
	/**
	 * Result of rolling of dices for the number of dices
	 */
	private int[] rollResultOfDices;
	
	public Dice()
	{
		
	}
	
	/**
	 * To roll the dices and get result of it randomly
	 * 
	 * @param numberOfDices, number of dices to be rolled
	 * @return result of rolling of dices for the number of dices
	 */
	public int[] roll(int numberOfDices)
	{
		int roll = 0;
		SecureRandom random;
		rollResultOfDices = new int[numberOfDices];
		for(int i = 0; i < rollResultOfDices.length; i++)
		{
			random = new SecureRandom();
			roll = random.nextInt(6) + 1;
			rollResultOfDices[i] = roll;
		}
		
		Arrays.sort(rollResultOfDices);
		Collections.reverse(Arrays.asList(rollResultOfDices));
		
		return rollResultOfDices;
	}
}
