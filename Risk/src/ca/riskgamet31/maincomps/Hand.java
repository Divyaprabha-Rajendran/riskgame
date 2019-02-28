package ca.riskgamet31.maincomps;

import java.util.ArrayList;

public class Hand {
	
	private boolean mustTurnInCards;
	private ArrayList<Card> hand;
	
	public Hand() {
		
		hand = new ArrayList<Card>();
	}
	
	public void addCard(Card card) {
		
		hand.add(card);
	}
	
	public ArrayList<Card> getCardsFromHand() {
		return hand;
	}
	
	public void removeCardsFromHand(int index1, int index2, int index3) {
		
		if (canTurnInCards(index1, index2, index3) == true) {
			hand.remove(index3);
			hand.remove(index2);
			hand.remove(index1);
		
		} else {
			System.out.println("");
		}
	}
	
	public boolean mustTurnInCards() {
		
		mustTurnInCards = false;
		
		if (hand.size() >= 5) {
			mustTurnInCards = true;
		}
		return mustTurnInCards;
	}
	
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
