/**
 * @author mike802
 * @version 1.0 - 2/28/2013
 */
package core;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;

import common.Card;
import common.CommonDisplay;
import common.RoundedImageIcon;
import core.doc.IDeckActions;

public class DeckActions extends CommonDisplay implements IDeckActions{
		
	public DeckActions(){

	}
	
	public Card cpuFlipCard(int player){
		Card cpuCard = rulesEngine.cpuPlayCard(player);
		if(cpuCard == null){
			return null;
		}
		String path = cpuCard.getPath();
		ImageIcon card = new RoundedImageIcon(path);
		int playerIndex = player + 1;
		JLabel selectedCard = cardSpots.get(playerIndex);
		selectedCard.setIcon(card);
		winnings.add(cpuCard);
		int numCards = rulesEngine.getCpuCards(player);
		numDeckCards.set(player, numCards);
		numCards = numDeckCards.get(player);
		JTextField cardDisplay = deckCardsDisplay.get(player);
		cardDisplay.setText(numCards + "");
		
		return cpuCard;
	}
	
	public Card playerFlipCard(){
		Card playerCard = rulesEngine.playerPlayCard();
		if(playerCard == null){
			flip.setEnabled(false);
			return null;
		}
		String path = playerCard.getPath();
		ImageIcon card = new RoundedImageIcon(path);
		JLabel playerFlip = cardSpots.get(1);
		playerFlip.setIcon(card);
		winnings.add(playerCard);
		int numCards = rulesEngine.getPlayerCards();
		numDeckCards.set(0, numCards);
		JTextField cardDisplay = deckCardsDisplay.get(0);
		cardDisplay.setText(numCards + "");
		
		return playerCard;
	}
	
	public boolean oneCardLeft(){
		int size = numPlayers + 1;
		for(int x = 0; x < size; x++){
			int numCards = numDeckCards.get(x);
			boolean oneLeft = numCards == 1;
			
			int extraCards = numWinningCards.get(x);
			boolean noExtra = extraCards == 0;
			if(oneLeft && noExtra){
				return true;
			}
		}
		return false;
	}

	/**
	 * A tied player must recycle all winnings before placing tie cards.
	 * This can provide up to three face-down cards and one final face-up card.
	 */
	public void prepareTiePlayer(int player){
		int deckCards = player == 0 ? rulesEngine.getPlayerCards() : rulesEngine.getCpuCards(player);
		int winningCards = rulesEngine.getWinningsCards(player);
		if(winningCards > 0){
			rulesEngine.shuffleWinnings(player);
			deckCards = player == 0 ? rulesEngine.getPlayerCards() : rulesEngine.getCpuCards(player);
			winningCards = rulesEngine.getWinningsCards(player);
		}

		numDeckCards.set(player, deckCards);
		numWinningCards.set(player, winningCards);
		deckCardsDisplay.get(player).setText(deckCards + "");
		winningCardsDisplay.get(player).setText(winningCards + "");
	}

	public int getTieFaceDownCards(int player){
		int deckCards = numDeckCards.get(player);
		return Math.min(3, Math.max(0, deckCards - 1));
	}
	
	public boolean cpuNeedsShuffle(){
		int size = numDeckCards.size();
		for(int x = 1; x < size; x++){
			int numCards = numDeckCards.get(x);
			boolean oneLeft = numCards < 1;
			int extraCards = numWinningCards.get(x);
			boolean canShuffle = extraCards > 0;
			if(oneLeft && canShuffle){
				return true;
			}
		}
		return false;
	}
	
	public void cpuShuffle(){
		int size = numDeckCards.size();
		for(int x = 1; x < size - 1; x++){
			int numCards = rulesEngine.getCpuCards(x);
			int winnings = rulesEngine.getWinningsCards(x);
			boolean deckEmpty = numCards == 0;
			boolean shuffleRandomly = numCards > 0 && winnings > 0 && Math.random() < 0.15;
			if(winnings > 0 && (deckEmpty || shuffleRandomly)){
				rulesEngine.cpuShuffle(x);
				numCards = rulesEngine.getCpuCards(x);
				winnings = rulesEngine.getWinningsCards(x);
				numDeckCards.set(x, numCards);
				numWinningCards.set(x, winnings);

				JTextField winningDisplay = winningCardsDisplay.get(x);
				winningDisplay.setText(winnings + "");
				JTextField cardDisplay = deckCardsDisplay.get(x);
				cardDisplay.setText(numCards + "");
			}
			else if(deckEmpty){
				numWinningCards.set(x, 0);
				JTextField winningDisplay = winningCardsDisplay.get(x);
				int numExtra = numWinningCards.get(x);
				winningDisplay.setText(numExtra + "");
				numDeckCards.set(x, numCards);
				JTextField cardDisplay = deckCardsDisplay.get(x);
				cardDisplay.setText(numCards + "");
			}
		}
	}
	
	public void playerShuffle(){
		shuffle.setEnabled(false);
		flip.setEnabled(true);
		rulesEngine.playerShuffleChoice(boardPage);
		int extraCards = rulesEngine.getPlayerWinningCards();
		numWinningCards.set(0, extraCards);
		JTextField extraDisplay = winningCardsDisplay.get(0);
		extraDisplay.setText(extraCards + "");
		int numCards = rulesEngine.getPlayerCards();
		numDeckCards.set(0, numCards);
		JTextField cardDisplay = deckCardsDisplay.get(0);
		cardDisplay.setText(numCards + "");
	}
}
