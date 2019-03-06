package ca.riskgamet31.maincomps;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Deck of Cards class to do some operations like, add a card to deck or draw
 * card or to shuffle cards
 * 
 * @author YD
 * @version 1.0
 * @since 1.0
 */
public class DeckOfCards
  {
	
	private String[] typesOfCards;
	
	/**
	 * Arraylist of cards to store it in deck
	 */
	private ArrayList<Card> deckOfCards;
	
	/**
	 * country arraylist
	 */
	private ArrayList<Country> countries;
	/**
	 * DrawCard object of card type
	 */
	private Card drawCard;
	
	/**
	 * Constructor to create deck of cards and also to assign cards countries
	 * and type of cards
	 * 
	 * @param countries ArrayList of countries
	 * 
	 */
	public DeckOfCards(ArrayList<Country> countries)
	  {
		
		Collections.shuffle(countries);
		
		typesOfCards = new String[]
		  { "Infantry", "Cavalry", "Artillery" };
		  
		deckOfCards = new ArrayList<Card>();
		
		for (int i = 0, j = 0; i < countries.size(); i++, j++)
		  {
			
			deckOfCards.add(new Card(typesOfCards[j], countries.get(i)));
			if (j == 2)
			  j = 0;
		  }
		Collections.shuffle(deckOfCards);
	  }
	  
	/**
	 * To add a new card to deck
	 * 
	 * @param card object
	 */
	public void addNewCard(Card card)
	  {
		
		deckOfCards.add(card);
	  }
	  
	/**
	 * To shuffle cards
	 */
	public void shuffleCards()
	  {
		
		Collections.shuffle(deckOfCards);
	  }
	  
	/**
	 * To draw a card from the deck
	 * 
	 * @return card that has been drawn
	 */
	public Card drawCard()
	  {
		
		drawCard = deckOfCards.get(0);
		deckOfCards.remove(0);
		
		return drawCard;
	  }
  }
