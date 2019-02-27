package ca.riskgamet31.phases;

import java.util.Scanner;

import ca.riskgamet31.maincomps.Country;
import ca.riskgamet31.maincomps.Player;
import ca.riskgamet31.mapdata.CreateMap;

public class Reinforcement {
	Player p;
	public Reinforcement(Player p)
	{
		this.p = p;
	}
	public void reinforce(String countryName) {
		CreateMap mp = new CreateMap();
		Country co = mp.getCountryByName(countryName);
		if(co.getCurrentOccupier().equalsIgnoreCase("dummy") || p.getplayersName().equals(co.getCurrentOccupier()))
		{
			int armiesToPut = 0;
			//rnd = new Random();
			p.reinforcementArmiesCalc();
			int reinforcementArmiesCount = p.getPlayerArmies();
			System.out.println("Total available reinforcements are " + reinforcementArmiesCount);
			System.out.println("How many armies do you want to put to reinforce country " + countryName);
			Scanner s = new Scanner(System.in);
			try {
				armiesToPut = Integer.parseInt(s.nextLine());
			}
			catch(NumberFormatException e)
			{
				System.out.println("Please, enter a valid input");
			}
			if(armiesToPut > reinforcementArmiesCount)
			{
				System.out.println("Input should be less than reinforcements you have");
			}
			else
			{
				p.decrementArmies(armiesToPut);
				co.increaseArmies(armiesToPut);
			}
		}
		else
		{
			System.out.println("Please choose correct input");
		}
	}

}
