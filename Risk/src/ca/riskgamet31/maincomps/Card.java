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
	private final Country country;
	
	/**
	 * constructs new card object
	 * 
	 * @param type  one of three types of card
	 * @param country country Object
	 */
	public Card(String type, Country country)
	  {
		this.type = type;
		this.country = country;
	  }
	  
	/**
	 * to get card name
	 * 
	 * @return card name
	 */
	public String getCardName()
	  {
		return country.getCountryName() + ", " + type;
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
	public Country getCountryObj()
	  {
		return country;
	  }
  }
