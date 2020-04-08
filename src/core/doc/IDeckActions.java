package core.doc;

import common.Card;

public interface IDeckActions {
	
	/**
	 * For the computer player specified in the param,
	 * returns the top card in their hand.
	 * 
	 * @param int player
	 * @return Card card
	 */
	public Card cpuFlipCard(int player);
	
	/**
	 * Returns the top card in the hand of the
	 * human player.
	 * 
	 * @return Card card
	 */
	public Card playerFlipCard();
	
	/**
	 * Determines if any player involved in a tie
	 * have only one card in their hand, thus 
	 * requiring either a shuffle or end of game
	 * scenario.
	 * 
	 * @return boolean one_card
	 */
	public boolean oneCardLeft();
	
	/**
	 * Determines if any of the cpu players need
	 * to shuffle so they will have sufficient
	 * cards in their hand to finish the tie.
	 * 
	 * @return boolean needs_shuffle
	 */
	public boolean cpuNeedsShuffle();
	
	/**
	 * Shuffles all cards in cpu winnings pile.
	 * Will only occur when cpu has reached 0 or
	 * 1 cards in their hand.
	 */
	public void cpuShuffle();
	
	/**
	 * Allows player to shuffle their winning cards
	 * pile when they want.  Gives the choice of
	 * mixing all cards together, or just adding
	 * new cards to the bottom of their hand.
	 */
	public void playerShuffle();

}
