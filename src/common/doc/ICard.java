package common.doc;

import java.net.URL;

/**
 *  Stores all data associated with a standard playing card, along
 *  with known rules allowing for machine interpretation/comparison.
 *
 */
public interface ICard {
	
	/**
	 * Returns numerical value of card.  Face cards
	 * are ordered above 10 for comparison purposes.
	 * 
	 * @return int number
	 */
	public int getNumber();
	
	/**
	 * Returns clubs, diamonds, hearts, or spades.
	 * 
	 * @return String suit
	 */
	public String getSuit();
	
	/**
	 * Returns location of card image.
	 * 
	 * @return String path
	 */
	public URL getPath();
	
	/**
	 * Returns a textual description of the card,
	 * suit and value.
	 * 
	 * @return String details
	 */
	public String getDetails();

}
