package ca.riskgamet31.maincomps;

/**
 * Card class
 * 
 * @author YD
 * @version 1.0
 * @since 1.0
 */
public class Card
  {
	
	/**
	 * Type of card
	 */
	private final String type;
	
	/**
	 * Country object
	 */
	private final String countryName;
	
	/**
	 * constructs new card object
	 * 
	 * @param type  one of three types of card
	 * @param countryName country Name
	 */
	public Card(String type, String countryName)
	  {
		this.type = type;
		this.countryName = countryName;
	  }
	  
	/**
	 * to get card name
	 * 
	 * @return card name
	 */
	public String getCardName()
	  {
		return countryName + ":" + type;
	  }
	  
	/**
	 * to get card type
	 * 
	 * @return the type of the card
	 */
	public String getCardType()
	  {
		return type;
	  }
	  
	/**
	 * to get country object
	 * 
	 * @return country object
	 */
	public String getCountryName()
	  {
		return countryName;
	  }
	
	/**
	 * returns string representation of the card
	 * 
	 * @return string representation of the card
	 */
	@Override
	public String toString()
	  {
		return "[" + this.getCardName() + "]";
	  }
  }
