package ca.riskgamet31.maincomps;

/**
 * Card class
 * 
 * @author YD
 * @version 1.0
 * @since 1.0
 */
public class Card {
	
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
	 * @param One of three types of card
	 * @param Country object
	 */
    public Card( String type, Country country ) {
		this.type = type;
		this.country = country;
    }
	
	public String getCardName() {
		return country.getCountryName() + ", " + type;
	}

    public String getCardType() {
		return type;
    }

    public Country getCountryObj() {
		return country;
    }
}
