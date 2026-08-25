package utils;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JTextField;

import common.RoundedTextField;

public class UtilsBoardRoot extends UtilsBoardAdd{
	
	public void initializeCounters(){
		int size = numPlayers + 1;
		for(int x = 1; x < size; x++){
			int cards = rulesEngine.getCpuCards(x);
			numDeckCards.add(cards);
			numWinningCards.add(DEFAULT_WINNING_CARDS);
		}
	}
	
	public void createCpuLabels(){
		currentY = 109 + 5 + 15;
		currentX = (totalX / 2) - ((((DECK_DISPLAY_LABEL_HORIZONTAL + 
				WINNINGS_DISPLAY_LABEL_HORIZONTAL) * numPlayers) + 
				(100 * (numPlayers - 1))) / 2);
		for(int x = 1; x < numPlayers + 1; x++){
			JLabel deckLabel = new JLabel("Deck");
			deckLabel.setBounds(currentX, currentY, DECK_DISPLAY_LABEL_HORIZONTAL, DECK_DISPLAY_LABEL_VERTICAL);
			deckLabels.add(deckLabel);
			currentX += DECK_DISPLAY_LABEL_HORIZONTAL + 5;	
			
			JLabel winningLabel = new JLabel("Winnings");
			winningLabel.setBounds(currentX, currentY, WINNINGS_DISPLAY_LABEL_HORIZONTAL, WINNINGS_DISPLAY_LABEL_VERTICAL);
			winningLabel.setPreferredSize(new Dimension(WINNINGS_DISPLAY_LABEL_HORIZONTAL, WINNINGS_DISPLAY_LABEL_VERTICAL));
			winningLabels.add(winningLabel);
			currentX += WINNINGS_DISPLAY_LABEL_HORIZONTAL + 100;
		}
	}
	
	public void createCpuDisplay(){
		currentY += DECK_DISPLAY_LABEL_VERTICAL + 5;
		currentX = (totalX / 2) - ((((DECK_DISPLAY_HORIZONTAL + 
				WINNINGS_DISPLAY_HORIZONTAL) * numPlayers) + 
				(100 * (numPlayers - 1)))/2);
		for(int x = 1; x < numPlayers + 1; x++){
			JTextField deckCards = new RoundedTextField();
			deckCards.setBounds(currentX, currentY, DECK_DISPLAY_HORIZONTAL, DECK_DISPLAY_VERTICAL);
			int cards = numDeckCards.get(x);
			deckCards.setText(cards + "");
			deckCards.setEditable(false);
			deckCardsDisplay.add(deckCards);
			currentX += DECK_DISPLAY_HORIZONTAL + 5;
			
			JTextField winningCards = new RoundedTextField();
			winningCards.setBounds(currentX, currentY, WINNINGS_DISPLAY_HORIZONTAL, WINNINGS_DISPLAY_VERTICAL);
			cards = numWinningCards.get(x);
			winningCards.setText(cards + "");
			winningCards.setEditable(false);
			winningCardsDisplay.add(winningCards);
			currentX += WINNINGS_DISPLAY_HORIZONTAL + 100;
		}
	}
	
	public void createCpuDecks(){
		currentY += DECK_DISPLAY_VERTICAL + 5;
		currentX = (totalX / 2) - ((((DECK_DISPLAY_HORIZONTAL + 
				WINNINGS_DISPLAY_HORIZONTAL) * numPlayers) + 
				(100 * (numPlayers - 1)))/2);
		int underLabel = ((WINNINGS_DISPLAY_HORIZONTAL + DECK_DISPLAY_HORIZONTAL) / 2) 
				- (75 / 2);
		currentX += underLabel;
		for(int x = 0; x < numPlayers; x++){
			JLabel deck = new JLabel();
			deck.setIcon(cover);
			deck.setBounds(currentX, currentY, 75, 130);
			decks.add(deck);
			currentX += 75 + underLabel + 100 + underLabel;
		}
	}
	
	public void createFlipArea(){
		currentX = (totalX / 2) - (((75 * numPlayers + 75 + 75 * 2) 
				+ (5 * numPlayers + 5 * 2)) / 2);
		currentY += 130 + 5;		
		JLabel tieLabel = new JLabel(TIE_PILE_LABEL);
		tieLabel.setBounds(currentX, currentY, CARD_PILE_LABEL_HORIZONTAL, CARD_PILE_LABEL_VERTICAL);
		names.add(tieLabel);
		JLabel place = new JLabel();
		place.setIcon(empty);
		place.setBounds(currentX, currentY + CARD_PILE_LABEL_VERTICAL + 5, 75, 130);
		cardSpots.add(place);
		currentX += CARD_PILE_LABEL_HORIZONTAL + 5;
		
		JLabel playerLabel = new JLabel(PLAYER_PILE_LABEL);
		playerLabel.setBounds(currentX, currentY, CARD_PILE_LABEL_HORIZONTAL, CARD_PILE_LABEL_VERTICAL);
		names.add(playerLabel);		
		place = new JLabel();
		place.setIcon(empty);
		place.setBounds(currentX, currentY + CARD_PILE_LABEL_VERTICAL + 5, 75, 130);
		cardSpots.add(place);
		currentX += CARD_PILE_LABEL_HORIZONTAL + 5;
		
		int size = numPlayers + 2;
		for(int x = DEFAULT_FLIP_LOOP_OFFSET; x < size; x++){
			int adjustment = (x - 1);
			String display = CPU_PILE_PREFIX + adjustment;
			JLabel name = new JLabel(display);
			name.setBounds(currentX, currentY, CARD_PILE_LABEL_HORIZONTAL, CARD_PILE_LABEL_VERTICAL);
			names.add(name);
			
			place = new JLabel();
			place.setIcon(empty);
			place.setBounds(currentX, currentY + CARD_PILE_LABEL_VERTICAL + 5, 75, 130);
			cardSpots.add(place);
			currentX += CARD_PILE_LABEL_HORIZONTAL + 5;
		}
		
		tieLabel = new JLabel(TIE_PILE_LABEL);
		tieLabel.setBounds(currentX, currentY, CARD_PILE_LABEL_HORIZONTAL, CARD_PILE_LABEL_VERTICAL);
		names.add(tieLabel);
		
		place = new JLabel();
		place.setIcon(empty);
		place.setBounds(currentX, currentY + CARD_PILE_LABEL_VERTICAL + 5, 75, 130);
		cardSpots.add(place);
	}
}
