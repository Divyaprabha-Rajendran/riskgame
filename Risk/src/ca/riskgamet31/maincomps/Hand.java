package ca.riskgamet31.maincomps;

import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Hand class which contains cards that player has
 * 
 * @author YD
 * @version 1.0
 * @since 1.0
 */
public class Hand
  {
	
	/**
	 * whether player must turn in cards or not
	 */
	private boolean mustTurnInCards;
	
	/**
	 * ArrayList of cards which will be stored by player
	 */
	private ArrayList<Card> handWithCards;
	
	/**
	 * Constructor to create Hand object
	 */
	public Hand()
	  {
		
		handWithCards = new ArrayList<Card>();
	  }
	  
	/**
	 * Add card to hand
	 * 
	 * @param card card object
	 */
	public void addCard(Card card)
	  {
		
		handWithCards.add(card);
	  }
	  
	/**
	 * return array list of cards
	 * @return array list of cards
	 */
	public ArrayList<Card> getCardsFromHand()
	  {
		return handWithCards;
	  }
	  
	public boolean isElegibleToExchange()
	{
	  boolean isElegible = false;
	  
	  long numOfTypes = 0 , maxPerType = 0;
	  Map<String,Integer> handStats = this.getCardsFromHand().stream()
		  							.collect(Collectors.groupingBy(i-> i.getCardType(),Collectors.reducing(0, e-> 1, Integer::sum)));
	  

	 numOfTypes = handStats.size();
	 maxPerType = handStats.values().stream().max((x,y) -> x.intValue()-y.intValue()).get().longValue();
		  							
	if (numOfTypes ==3 || maxPerType >=3)
	  isElegible = true;
	 
	 
	  return isElegible;
	}
	
	
	/**
	 * Remove cards from hand
	 * 
	 * @param index1 index of cards to be removed
	 * @param index2 index of cards to be removed
	 * @param index3 index of cards to be removed
	 */
	public void removeCardsFromHand(int index1, int index2, int index3)
	  {
		
		if (canTurnInCards(index1, index2, index3) == true)
		  {
			handWithCards.remove(index3-1);
			handWithCards.remove(index2-1);
			handWithCards.remove(index1-1);
			
		  } else
		  {
			System.out.println("");
		  }
	  }
	  
	/**
	 * Check that whether cards must be turned in by user or not
	 * 
	 * @return whether cards must be turned in by user or not
	 */
	public boolean mustTurnInCards()
	  {
		
		mustTurnInCards = false;
		
		if (handWithCards.size() >= 5)
		  {
			mustTurnInCards = true;
		  }
		return mustTurnInCards;
	  }
	  
	/**
	 * Check that whether player can turn in cards or not
	 * 
	 * @param index1 index of card that can be turned in
	 * @param index2 index of card that can be turned in
	 * @param index3 index of card that can be turned in
	 * @return whether player can turn in cards or not
	 */
	public boolean canTurnInCards(int index1, int index2, int index3) throws IndexOutOfBoundsException
	  {
		
		index1--;
		index2--;
		index3--;
		
		mustTurnInCards = false;
		
		if (handWithCards.size() >= 3)
		  {
			if (handWithCards.get(index1).getCardType().equals(handWithCards.get(index2)
			    .getCardType()) && handWithCards.get(index1).getCardType().equals(handWithCards
			        .get(index3).getCardType()))
			  {
				// If all three cards have the same type
				mustTurnInCards = true;
				
			  } else if (!handWithCards.get(index1).getCardType().equals(handWithCards.get(index2)
			      .getCardType()) && !handWithCards.get(index1).getCardType().equals(handWithCards
			          .get(index3).getCardType()) && !handWithCards.get(index2)
			              .getCardType().equals(handWithCards.get(index3).getCardType()))
			  {
				// If all three cards have different types
				mustTurnInCards = true;
			  }
		  }
		return mustTurnInCards;
	  }
  }
