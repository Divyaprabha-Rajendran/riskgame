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
	/**
	 * types of cards
	 */
	private String[] typesOfCards;
	
	/**
	 * Arraylist of cards to store it in deck
	 */
	private ArrayList<Card> deckOfCards;
	
	/**
	 * DrawCard object of card type
	 */
	private Card drawCard;
	
	/**
	 * Constructor to create deck of cards and also to assign cards countries
	 * and type of cards
	 * 
	 * @param graphNodes ArrayList of countries
	 * 
	 */
	public DeckOfCards(ArrayList<GraphNode> graphNodes)
	  {
		
		Collections.shuffle(graphNodes);
		
		typesOfCards = new String[]
		  { "Infantry", "Cavalry", "Artillery" };
		  
		deckOfCards = new ArrayList<Card>();
		
		for (int i = 0, j = 0; i < graphNodes.size(); i++, j++)
		  {
			
			for (int k = 0; k< 20 ; k++) {
			deckOfCards.add(new Card(typesOfCards[j], graphNodes.get(i)
				    .getNodeData().getCountryName()));
			}
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
