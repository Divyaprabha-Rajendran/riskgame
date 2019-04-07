package ca.riskgamet31.maincomps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TestClass
  {
	
	public static void main(String[] args)
	  {
		
		String[] typesOfCards = new String[]
			  { "Infantry", "Cavalry", "Artillery" };
		ArrayList<Card> hand = new ArrayList<>();
		
		hand.add(new Card(typesOfCards[0], "India"));
		hand.add(new Card(typesOfCards[0], "India7"));
		//hand.add(new Card(typesOfCards[0], "India8"));
		
		hand.add(new Card(typesOfCards[1], "India2"));
		hand.add(new Card(typesOfCards[1], "India5"));
		//hand.add(new Card(typesOfCards[1], "India4"));
		hand.add(new Card(typesOfCards[1], "India6"));
		hand.add(new Card(typesOfCards[1], "India7"));
		
		//hand.add(new Card(typesOfCards[2], "India3"));
		//hand.add(new Card(typesOfCards[2], "India9"));
		
		System.out.println(hand.toString());
		
		Map<String,List<Card>> handsCards = hand.stream()
			.collect(Collectors.groupingBy(Card::getCardType,Collectors.toList()));
		
		/// 3 distinct cards
		if (handsCards.size() == 3) {
		 
		  for (List<Card> listOfCards : handsCards.values()) {
			  
			  hand.remove(listOfCards.get(0));
			}
		  
		}else {
		  
		  boolean continueDelete = true;
		  
		  for (List<Card> listOfCardsPerType : handsCards.values()) {
			  
			if (listOfCardsPerType.size() >= 3 && continueDelete) {
			  
			  hand.remove(listOfCardsPerType.get(0));
			  hand.remove(listOfCardsPerType.get(1));
			  hand.remove(listOfCardsPerType.get(2));
			  continueDelete = false;
			}
			}
		  
		}
		System.out.println("===========after delete==========");
       
		System.out.println(hand.toString());
		
	  }
	  
  }
