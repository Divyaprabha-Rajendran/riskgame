package ca.riskgamet31.maincomps;

public class Card {
	
	private final String type;
    private final Country country;

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
