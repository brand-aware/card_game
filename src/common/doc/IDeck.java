package common.doc;

/**
 * Stores data associated with a standard deck of
 * cards.
 *
 */
public interface IDeck {
	
	/**
	 * Formalizes the distribution of cards from the deck.
	 * 
	 * @param int number
	 * @param String suit
	 */
	public void deal(int number, String suit);
	
	/**
	 * Determines if card has already been dealt.
	 * 
	 * @param int number
	 * @param String suit
	 * @return boolean dealt
	 */
	public boolean hasBeenDealt(int number, String suit);
	
	/**
	 * Gets the entire deck of cards.
	 * 
	 * @return int[] deck
	 */
	public int[] getDeck();
	
	/**
	 * Returns all suits in the deck (for dealing
	 * purposes).
	 * 
	 * @return String[] suits
	 */
	public String[] getSuits();

}
