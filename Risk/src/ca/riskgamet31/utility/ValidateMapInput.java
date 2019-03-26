package ca.riskgamet31.utility;

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
		
		validCountryNamePattern = Pattern.compile("^[a-zA-Z]*$");
		
	  }
	  
	/**
	 * method to validate country names
	 * 
	 * @param Name country name to check
	 * @throws InvalidNameException If the name of continent or country has
	 *                              special characters or numbers
	 */
	public void validateCountryorContinentName(String Name)
	    throws InvalidNameException
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
	 * @throws InvalidCountryException If there is a duplicate country
	 */
	public void checkExistingCountry(String countryName,
	    HashSet<String> countryset) throws InvalidCountryException
	  {
		
		if (countryset.stream().anyMatch((x) -> x
		    .equalsIgnoreCase(countryName)) == true)
		  {
			throw new InvalidCountryException("Country exists already " + countryName);
		  }
		  
	  }
	  
	/**
	 * check if continent exists
	 * 
	 * @param continentName country name to check
	 * @param continentset  existing list of countries
	 * @throws InvalidContinentException If there is a duplicate continent.
	 */
	public void checkExistingContinent(String continentName,
	    HashSet<String> continentset) throws InvalidContinentException
	  {
		if (continentset.stream().anyMatch((x) -> x
		    .equalsIgnoreCase(continentName)) == true)
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
		
		return countryName1.equalsIgnoreCase(countryName2) ? true : false;
	  }
	  
	/**
	 * check if any continent does not have countries associated
	 * 
	 * @param mapData continents objects
	 * @throws InvalidContinentException If there is a duplicate continent.
	 */
	public void checkCountinentWithoutCountry(
	    HashMap<String, Continent> mapData) throws InvalidContinentException
	  
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
	 * @param countryName  country name
	 * @param continentSet continent list
	 * @throws InvalidCountryException If there is a duplicate country
	 */
	public void checkCountryAgainstContinents(String countryName,
	    HashSet<String> continentSet) throws InvalidCountryException
	  {
		if (continentSet.contains(countryName.toUpperCase()))
		  {
			throw new InvalidCountryException("country name is not valid as it is a continent name   " + countryName);
		  }
		  
	  }
	  
	/**
	 * check if the continent name is a country name.
	 * 
	 * @param continentName continent name
	 * @param countrySet    country list
	 * @throws InvalidContinentException If there is a duplicate continent.
	 */
	public void checkContinentAgainstCountries(String continentName,
	    HashSet<String> countrySet) throws InvalidContinentException
	  {
		
		if (countrySet.contains(continentName.toUpperCase()))
		  {
			throw new InvalidContinentException("continent name is not valid as it is a country name   " + continentName);
		  }
	  }
	  
	/**
	 * check that both from and to country are not same and not a continent
	 * name.
	 * 
	 * @param fromName     first country
	 * @param ToName       second country
	 * @param continentSet continent set
	 * @throws InvalidLinkException If from and to countries are same.
	 */
	public void checkLinks(String fromName, String ToName,
	    HashSet<String> continentSet) throws InvalidLinkException
	  
	  {
		if ((continentSet.contains(ToName.toUpperCase())) || (continentSet
		    .contains(fromName.toUpperCase())) || (ToName
		        .equalsIgnoreCase(fromName)))
		  {
			throw new InvalidLinkException("Countries may have a contient name or from and to country are same " + fromName + "---->" + ToName);
		  }
		  
	  }
  }
