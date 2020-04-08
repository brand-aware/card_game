package core.doc;

public interface IFlipActions {

	/**
	 * Has all players involved in tie flip three
	 * cards and place then into the tie pile 
	 * (increasing the total amount of cards on the
	 * board).  If only computer players are tied,
	 * they will then each flip a card and a winner
	 * is chosen.
	 */
	public void showTie();
	
	/**
	 * Flips a card for the player and all computer
	 * players, if not in a tie scenario.  After
	 * cards are flipped, a winner is determined
	 * and cards are awarded accordingly, or a tie
	 * scenario is entered.  If currently in a tie,
	 * this will be called when the player is tied
	 * and will have all tied computer players flip
	 * a card.
	 */
	public void doMove();
	
}
