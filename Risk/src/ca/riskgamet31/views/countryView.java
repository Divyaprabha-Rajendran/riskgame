package ca.riskgamet31.views;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

/**
 * view object linked to one country using observer pattern
 * 
 * @author Fareed Tayar
 * @version 1.0
 * @since 1.0
 */
public class countryView implements Observer, Serializable
  {
	
	/**
	 * name of country is viewer is observing
	 */
	String countryName;
	
	/**
	 * constructor for country view
	 * 
	 * @param countryName constructs the viewer
	 */
	public countryView(String countryName)
	  {
		
		this.countryName = countryName;
		
	  }
	  
	/**
	 * to be called by the country object whenever an observed change occurred.
	 * 
	 * @param o   observable object
	 * @param arg data object
	 */
	@Override
	public void update(Observable o, Object arg)
	  {
		
		System.out.println(o.toString());
		
	  }
	  
  }
