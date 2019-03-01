package ca.riskgamet31.maincomps;

import java.util.ArrayList;

/**
 * Hand class which contains cards that player has
 * 
 * @author YD
 * @version 1.0
 * @since 1.0
 */
public class Hand {
	
	/**
	 * whether player must turn in cards or not
	 */
	private boolean mustTurnInCards;
	
	/**
	 * ArrayList of cards which will be stored by player
	 */
	private ArrayList<Card> hand;
	
	/**
	 * Constructor to create Hand object
	 */
	public Hand() {
		
		hand = new ArrayList<Card>();
	}
	
	/**
	 * Add card to hand
	 * @param Card object
	 */
	public void addCard(Card card) {
		
		hand.add(card);
	}
	
	public ArrayList<Card> getCardsFromHand() {
		return hand;
	}
	
	/**
	 * Remove cards from hand
	 * @param index of cards to be removed
	 * @param index of cards to be removed
	 * @param index of cards to be removed 
	 */
	public void removeCardsFromHand(int index1, int index2, int index3) {
		
		if (canTurnInCards(index1, index2, index3) == true) {
			hand.remove(index3);
			hand.remove(index2);
			hand.remove(index1);
		
		} else {
			System.out.println("");
		}
	}
	
	/**
	 * Check that whether cards must be turned in by user or not
	 * @return whether cards must be turned in by user or not
	 */
	public boolean mustTurnInCards() {
		
		mustTurnInCards = false;
		
		if (hand.size() >= 5) {
			mustTurnInCards = true;
		}
		return mustTurnInCards;
	}
	
	/**
	 * Check that whether player can turn in cards or not
	 * @param index of card that can be turned in
	 * @param index of card that can be turned in
	 * @param index of card that can be turned in
	 * @return whether player can turn in cards or not
	 */
	public boolean canTurnInCards(int index1, int index2, int index3) {
		
		mustTurnInCards = false;
		
		if (hand.size() >= 3) {
			if (hand.get(index1).getCardType().equals(hand.get(index2).getCardType()) && hand.get(index1).getCardType().equals(hand.get(index3).getCardType())) {
			//If all three cards have the same type
				mustTurnInCards = true;
				
			} else if (
				!hand.get(index1).getCardType().equals(hand.get(index2).getCardType()) && !hand.get(index1).getCardType().equals(hand.get(index3).getCardType()) && !hand.get(index2).getCardType().equals(hand.get(index3).getCardType())) {
			//If all three cards have different types
				mustTurnInCards = true;
			}
		}
		return mustTurnInCards;
	}
}
