package ca.riskgamet31.views;

import java.util.Observable;
import java.util.Observer;

/**
 * view object linked to one country
 * 
 * @author Fareed Tayar
 * @version 1.0
 * @since 1.0
 */
public class countryView implements Observer
  {
	
	/**
	 * name of country is viewer is observing
	 */
	String countryName;
	
	/**
	 * @param countryName constructs the viewer
	 */
	public countryView(String countryName)
	  {
		
		this.countryName = countryName;
		
	  }
	  
	/**
	 * to be called by the country object whenever an observed change occurred.
	 */
	@Override
	public void update(Observable o, Object arg)
	  {
		
		System.out.println(o.toString());
		
	  }
	  
  }
