package common;
/**
 * @author mike802
 * @version 1.0 - 2/28/2013
 */


import java.util.ArrayList;

import common.doc.IPlayer;

import core.Properties;

public class Player implements IPlayer{

	private ArrayList<Card> hand;
	private ArrayList<Card> winnings;
	
	private Properties properties;
	
	public Player(Properties p){
		hand = new ArrayList<Card>();
		winnings = new ArrayList<Card>();
		properties = p;
	}
	
	public ArrayList<Card> getHand(){
		return hand;
	}
	
	public void addCard(int number, String suit){
		Card card = new Card(number, suit, properties);
		hand.add(card);
	}
	
	public int getHandSize(){
		return hand.size();
	}
	public int getWinningsSize(){
		return winnings.size();
	}
	
	public Card getCard(){
		if(hand.size() > 0){
			Card card = hand.get(0);
			hand.remove(0);
			hand.trimToSize();
			return card;
		}
		return null;
	}
	
	public void addWinnings(ArrayList<Card> cards){
		for(int x = 0; x < cards.size(); x++){
			winnings.add(cards.get(x));
		}
	}
	
	public void shuffle(boolean all){
		if(all){
			shuffleAll();
		}else{
			shuffle();
		}
	}
	
	public void shuffle(){
		int num = (int)(Math.random() * winnings.size());
		while(winnings.size() > 0){
			num = (int)(Math.random() * winnings.size());
			hand.add(winnings.get(num));
			winnings.remove(num);
			winnings.trimToSize();
		}
	}
	
	public void shuffleAll(){
		for(int x = 0; x < hand.size(); x++){
			winnings.add(hand.get(x));
		}
		hand = new ArrayList<Card>();
		shuffle();
	}
}
