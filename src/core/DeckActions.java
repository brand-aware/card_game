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
import core.doc.IDeckActions;

public class DeckActions extends CommonDisplay implements IDeckActions{
		
	public DeckActions(){

	}
	
	public Card cpuFlipCard(int player){
		Card cpuCard = rulesEngine.cpuPlayCard(player);
		String path = cpuCard.getPath();
		ImageIcon card = new ImageIcon(path);
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
		String path = playerCard.getPath();
		ImageIcon card = new ImageIcon(path);
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
		for(int x = 1; x < size; x++){
			int numCards = numDeckCards.get(x);
			if(numCards < 1){
				numWinningCards.set(x, 0);
				JTextField winningDisplay = winningCardsDisplay.get(x);
				int numExtra = numWinningCards.get(x);
				winningDisplay.setText(numExtra + "");
				numCards = rulesEngine.getCpuCards(x);
				numDeckCards.set(x, numCards);
				JTextField cardDisplay = deckCardsDisplay.get(x);
				cardDisplay.setText(numCards + "");
			}
		}
	}
	
	public void playerShuffle(){
		shuffle.setEnabled(false);
		flip.setEnabled(true);
		rulesEngine.playerShuffleChoice();
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
