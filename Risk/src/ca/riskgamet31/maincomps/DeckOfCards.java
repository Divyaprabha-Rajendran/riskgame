package ca.riskgamet31.maincomps;

import java.util.ArrayList;
import java.util.Collections;

public class DeckOfCards {
	
private String[] typesOfCards;
	
	private ArrayList<Card> deckOfCards;
	private ArrayList<Country> countries;

	private Card drawCard;
	
	public DeckOfCards(ArrayList<Country> countries) {		
		
		Collections.shuffle(countries);
		
		typesOfCards = new String[]{ "Infantry", "Cavalry", "Artillery" };
		
		deckOfCards = new ArrayList<Card>();
		
		for (int i = 0, j = 0; i < countries.size(); i++, j++) {

			deckOfCards.add(new Card(typesOfCards[j], countries.get(i)));
			if(j == 2)
				j = 0;
		}
		Collections.shuffle(deckOfCards);
	}

	public void addNewCard(Card card) {
		
		deckOfCards.add(card);
	}
	
	public void shuffleCards() {
		
		Collections.shuffle(deckOfCards);
	}
	
	public Card drawCard() {
		
		drawCard = deckOfCards.get(0);
		deckOfCards.remove(0);
		
		return drawCard;
	}
}
