package common.doc;

import java.util.ArrayList;

import common.Card;

public interface IPlayer {
	
	/**
	 * Returns all cards currently in this player's
	 * hand
	 * 
	 * @return ArrayList<Card> hand
	 */
	public ArrayList<Card> getHand();
	
	/**
	 * Adds a card to players current hand.
	 * 
	 * @param int number
	 * @param String suit
	 */
	public void addCard(int number, String suit);
	
	/**
	 * Returns the number of cards this player 
	 * currently has.
	 * 
	 * @return int size
	 */
	public int getHandSize();
	
	/**
	 * Returns the number of cards this player has
	 * accumulated, but have not been shuffled into
	 * player's hand.
	 * 
	 * @return int size
	 */
	public int getWinningsSize();
	
	/**
	 * Returns the top card from player's current
	 * hand.
	 * 
	 * @return Card card
	 */
	public Card getCard();
	
	/**
	 * After a hand is won, adds all cards to
	 * player's unshuffled, winnings pile.
	 * 
	 * @param ArrayList<Card> cards
	 */
	public void addWinnings(ArrayList<Card> cards);
	
	/**
	 * Shuffles all cards in player's pile of
	 * cards from winning hands.  Param determines
	 * if all cards in deck and winnings pile will
	 * be shuffled together.
	 * 
	 * @param boolean all
	 */
	public void shuffle(boolean all);
	
}
