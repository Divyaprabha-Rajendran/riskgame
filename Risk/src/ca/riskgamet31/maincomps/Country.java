package ca.riskgamet31.maincomps;

import java.util.Observable;

import ca.riskgamet31.views.countryView;

/**
 * An entity representing a country
 * 
 * To be used for the Graph node data every country will create and register a
 * view Observable model is used.
 * 
 * @author Fareed Tayar
 * @version 1.1
 * @since 1.0
 */
public class Country extends Observable
  {
	
	/**
	 * Country name
	 */	
	private final String countryName;
	
	/**
	 * current player occupying the country
	 */
	private String currentOccupier;
	
	/**
	 * list of adjacent countries.
	 */
	private int armies;
	
	/**
	 * viewer of the counter (observer pattern)
	 */
	countryView viewer;
	
	/**
	 * creates new country object.
	 * 
	 * @param countryName name of county
	 * @throws NullPointerException NullPointerException
	 */
	public Country(String countryName) throws NullPointerException
	  {
		super();
		
		if (countryName == null)
		  throw new NullPointerException("Null country name or neighbours reference");
		this.countryName = countryName.toUpperCase();
		this.armies = 0;
		this.currentOccupier = "DUMY";
		this.viewer = new countryView(countryName.toUpperCase());
		
	  }
	  
	/**
	 * return the name of country.
	 * 
	 * @return the name of this country.
	 */
	public String getCountryName()
	  {
		
		return countryName;
	  }
	  
	/**
	 * return current player occupying this country.
	 * 
	 * @return current player occupying this country
	 */
	public String getCurrentOccupier()
	  {
		return currentOccupier;
	  }
	  
	/**
	 * changes current country occupier.
	 * 
	 * @param newOccupier new player won this country.
	 */
	public void setCurrentOccupier(String newOccupier)
	  {
		this.currentOccupier = newOccupier;
		this.setChanged();
		this.notifyObservers();
	  }
	  
	/**
	 * current armies in this country.
	 * 
	 * @return current armies in this country.
	 */
	public int getArmies()
	  {
		return armies;
	  }
	  
	/**
	 * to get country viewer object
	 * 
	 * @return country viewer object
	 */
	public countryView getViewer()
	  {
		return viewer;
	  }
	  
	/**
	 * to set country's viewer object.
	 * 
	 * @param viewer viewer object of this country.
	 */
	public void setViewer(countryView viewer)
	  {
		this.viewer = viewer;
	  }
	  
	/**
	 * increase number of armies in this country.
	 * 
	 * @param armiesNumber armies to increase.
	 * @return new total armies in country.
	 */
	public int increaseArmies(int armiesNumber)
	  {
		
		this.setArmies(this.getArmies() + armiesNumber);
		return this.getArmies();
		
	  }
	  
	/**
	 * reduce number of armies in the country.
	 * <code>if (this.getArmies() - armiesNumber)</code> is less than zero the
	 * method returns -99
	 * 
	 * @param armiesNumber armies to increase.
	 * @return new total armies in country, if total is .
	 */
	public int reduceArmies(int armiesNumber)
	  {
		
		int tempArmies = this.getArmies() - armiesNumber;
		
		if (tempArmies > 0)
		  {
			this.setArmies(tempArmies);
			return this.getArmies();
		  } else
		  return -99;
	  }
	  
	/**
	 * set number of armies
	 * 
	 * @param armies new number of armies
	 */
	public void setArmies(int armies)
	  {
		this.armies = armies;
		this.setChanged();
		this.notifyObservers();
	  }
	  
	/**
	 * Compares to countries by name. overrides Object's equal method.
	 * 
	 * @param otherCountry country to compare with.
	 * @return returns true if both country objects have the same name.
	 */
	@Override
	public boolean equals(Object otherCountry)
	  {
		
		if (otherCountry == null)
		  return false;
		if (!(otherCountry instanceof Country))
		  return false;
		if (otherCountry == this)
		  return true;
		return (this.getCountryName().equals(((Country) otherCountry)
		    .getCountryName()));
		
	  }
	  
	/**
	 * returns string representation of the country.
	 * 
	 * @return string representation of the country.
	 */
	@Override
	public String toString()
	  {
		
		return "[" + this.getCountryName() + ":" + this.getArmies() + ":" + this
		    .getCurrentOccupier() + "]";
	  }
	  
  }
