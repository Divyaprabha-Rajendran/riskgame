package ca.riskgamet31.mapdata;

import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.riskgamet31.maincomps.Continent;

/**
 * To validate the map input data.
 * 
 * @author Divyaprabha Rajendran
 * @version 1.1
 * @since 1.0
 */

public class ValidationMapInput 
{
  
  		private Pattern validCountryNamePattern ;
  		private Matcher matcher ;
	
  		public ValidationMapInput()
  		{
  		  
  		validCountryNamePattern = Pattern.compile("^[a-zA-Z0-9]*$");
  		  
  		}
  
    	/**
    	 * 	method to validate country names
    	 * 
    	 * @param countryName country name to check
    	 * @return true if valid country name
    	 */
       public boolean validateCountryorContinentName(String Name)
       {

    	 Matcher countryNameMatcher	= validCountryNamePattern.matcher(Name);

    	   return countryNameMatcher.matches();
       }
       
       /**
        * check if country exists
        * 
        * @param countryName country name to check
        * @param countryset existing list of countries 
        * @return true if country already exist
        */
       
       public boolean checkExistingCountry(String countryName , HashSet<String> countryset)
       {
    	  
    	 boolean exists = false;
    	 
    	 return countryset.stream().anyMatch((x) -> x.equals(countryName)); 
    	 
       }
       
       /**
        * check if both names are equal
        * 
        * @param countryName1
        * @param countryName2
        * @return true if both name are equal
        */
       public boolean checkSimilarNames(String countryName1,String countryName2)
       {
    	   
    	 return countryName1.equals(countryName2)? true : false; 
       }
       
       /**
        * check if any continent does not have countries associated
        * @param mapData continents objects
        * @return true if any continent does not have a country associate with it.
        */
       public boolean checkCountinentWithoutCountry(HashMap<String,Continent> mapData)
       
       {
    	 boolean invalid = false;
    	 
    	 for (Continent continent : mapData.values())
    	   {
    		 
    		 if (continent.getContinentGraph().getGraphNodes().size() == 0) 
    		   invalid = true;
    		 
    	   }
    	 
    	 return invalid;
    	 
       }
       
       /**
        * check if the country name is a continent name.
        * @param countryName
        * @param continentSet
        */

       public boolean checkCountryAgainstContinents(String countryName, HashSet<String> continentSet)
       {
    	
    	 return continentSet.contains(countryName);
    	 
       }
       
       /**
        * check if the continent name is a country name.
        * @param continentName
        * @param countrySet
        */
       
       public boolean checkContinentAgainstCountries(String continentName, HashSet<String> countrySet)
         {
      	
      	 return countrySet.contains(continentName);
      	 
         }
       
       /**
        * check that both from and to country are not same and not a continent name.
        * @param fromName
        * @param ToName
        * @param continentSet
        */
       
       public boolean checkLinks(String fromName, String ToName, HashSet<String> continentSet)
       
       {
    	 
    	 return continentSet.contains(ToName) || continentSet.contains(fromName)|| ToName.equals(fromName);
    	 
       }
}
