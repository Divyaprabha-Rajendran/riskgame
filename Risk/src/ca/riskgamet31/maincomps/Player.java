package ca.riskgamet31.maincomps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * main Players class
 * 
 * @author Chitra
 * @version 1.0
 * @since 1.0
 */
public class Player
{
   
	private final String playersName;
	private int army; 
	
	
	ArrayList<Country> country;
	ArrayList<Continent> continent;
	
	public Player(String playersName,int army) throws NullPointerException
	{
		 if (playersName == null) throw new NullPointerException("Null Player name");
		 this.playersName=playersName;
		 this.army=army; 
		 
	}
	
	public String getplayersName() 
	{
		return playersName;
	}
		
	public void addCountry(ArrayList<Country> country)
	{
		this.country = country;
	}
	public ArrayList<Country> getCountry()
	{
		return country;
	}
	
	public void removeCountry(String countryName)
	{
		country.remove(countryName);
		
	}
	public void addContinent(ArrayList<Continent> continent)
	{
		this.continent=continent;
		
	}
	public void removeContinent(String continentname)
	{
		continent.remove(continentname);
		
	}
	public ArrayList<Continent> getContinent()
	{
		return continent;
	}
	
  public int army(int NoOfPlayers)
  {
	  switch (NoOfPlayers)
	  {
  	case 2:
		return 40;
	case 3:
		return 35;
	case 4:
		return 30;
	case 5:
		return 25;
	case 6:
		return 20;
	default:
		return -1;
  }
		
  }
  
//YD 
  public int reinforcementArmiesCalc()
  {
	  int armiesForCountries = 0;
	  int armiesForContinentsBonus = 0;
	  int armiesForCards = 0;
	  int totalArmiesToAdd = 0;
	  if(this.getCountry().size() < 12)
	  {
		  armiesForCountries = 3;
	  }
	  else
	  {
		  armiesForCountries = this.getCountry().size() / 3;
	  }
	  int continentCount = this.getContinent().size();
	  if(continentCount > 0)
	  {
		  for(int i = 0; i < continentCount; i++)
		  {
			  armiesForContinentsBonus = armiesForContinentsBonus + this.getContinent().get(i).getAdditionalBonusArmies();
		  }
	  }
	  totalArmiesToAdd = armiesForCountries + armiesForContinentsBonus + armiesForCards;
	  this.incrementArmies(totalArmiesToAdd);
	  return totalArmiesToAdd;
  }
  
  //YD 
  public void incrementArmies(int a)
  {
	  this.army += a; 
  }
  
  //YD
  public void decrementArmies(int a)
  {
	  this.army -= a; 
  }
  
  //YD
  public int getPlayerArmies()
  {
	  return this.army;
  }
}
