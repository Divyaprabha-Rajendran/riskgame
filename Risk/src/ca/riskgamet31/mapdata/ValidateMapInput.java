package ca.riskgamet31.mapdata;

import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.riskgamet31.exceptions.InvalidContinentException;
import ca.riskgamet31.exceptions.InvalidCountryException;
import ca.riskgamet31.exceptions.InvalidLinkException;
import ca.riskgamet31.exceptions.InvalidNameException;
import ca.riskgamet31.maincomps.Continent;

/**
 * To validate the map input data.
 * 
 * @author Divyaprabha Rajendran
 * @version 1.1
 * @since 1.0
 */
public class ValidateMapInput
  {
	/**
	 * pattern field
	 */
	private Pattern validCountryNamePattern;
	/**
	 * matcher field
	 */
	private Matcher matcher;
	/**
	 * constructor with proper default pattern
	 */
	public ValidateMapInput()
	  {
		
		validCountryNamePattern = Pattern.compile("^[a-zA-Z0-9]*$");
		
	  }
	  
	/**
	 * method to validate country names
	 * 
	 * @param Name country name to check
	 * 
	 */
	public void validateCountryorContinentName(String Name)
	  {
		Matcher countryNameMatcher = validCountryNamePattern.matcher(Name);
		
		if (countryNameMatcher.find() == false)
		  {
			throw new InvalidNameException("Data is invalid. Special characters not allowed " + Name);
		  }
	  }
	  
	/**
	 * check if country exists
	 * 
	 * @param countryName country name to check
	 * @param countryset  existing list of countries
	 * 
	 */
	public void checkExistingCountry(String countryName,
	    HashSet<String> countryset)
	  {
		
		if (countryset.stream().anyMatch((x) -> x.equals(countryName)) == true)
		  {
			throw new InvalidCountryException("Country exists already " + countryName);
		  }
		  
	  }
	  
	/**
	 * check if continent exists
	 * 
	 * @param continentName country name to check
	 * @param continentset  existing list of countries
	 * 
	 */
	public void checkExistingContinent(String continentName,
	    HashSet<String> continentset)
	  {
		if (continentset.stream().anyMatch((x) -> x
		    .equals(continentName)) == true)
		  {
			throw new InvalidContinentException("Continent exists already " + continentName);
		  }
		  
	  }
	  
	/**
	 * check if both names are equal
	 * 
	 * @param countryName1 first country
	 * @param countryName2 second country
	 * @return true if both name are equal
	 */
	public boolean checkSimilarNames(String countryName1, String countryName2)
	  {
		
		return countryName1.equals(countryName2) ? true : false;
	  }
	  
	/**
	 * check if any continent does not have countries associated
	 * 
	 * @param mapData continents objects
	 * 
	 */
	public void checkCountinentWithoutCountry(
	    HashMap<String, Continent> mapData)
	  
	  {
		boolean invalid = false;
		
		for (Continent continent : mapData.values())
		  {
			
			if (continent.getContinentGraph().getGraphNodes().size() == 0)
			  throw new InvalidContinentException("Continent has no countries associated " + continent
			      .getContinentName());
			
		  }
		  
	  }
	  
	/**
	 * check if the country name is a continent name.
	 * 
	 * @param countryName country name
	 * @param continentSet continent list
	 */
	public void checkCountryAgainstContinents(String countryName,
	    HashSet<String> continentSet)
	  {
		
		if (continentSet.contains(countryName))
		  {
			throw new InvalidCountryException("country name is not valid as it is a continent name   " + countryName);
		  }
		  
	  }
	  
	/**
	 * check if the continent name is a country name.
	 * 
	 * @param continentName continent name
	 * @param countrySet country list
	 */
	public void checkContinentAgainstCountries(String continentName,
	    HashSet<String> countrySet)
	  {
		if (countrySet.contains(continentName))
		  {
			throw new InvalidContinentException("continent name is not valid as it is a country name   " + continentName);
		  }
	  }
	  
	/**
	 * check that both from and to country are not same and not a continent
	 * name.
	 * 
	 * @param fromName first country
	 * @param ToName second country
	 * @param continentSet continent set
	 */
	public void checkLinks(String fromName, String ToName,
	    HashSet<String> continentSet)
	  
	  {
		if (continentSet.contains(ToName) || continentSet
		    .contains(fromName) || ToName.equals(fromName))
		  {
			throw new InvalidLinkException("Countries may have a contient name or from and to country are same");
		  }
		  
	  }
  }
