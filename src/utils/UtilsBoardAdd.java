package utils;

import javax.swing.JLabel;
import javax.swing.JTextField;

import core.FlipActions;

public class UtilsBoardAdd extends FlipActions{

	public void addCpuLabelDisplay(){
		int size = numPlayers + 1;
		for(int x = 1; x < size; x++){
			JLabel deck = deckLabels.get(x);
			desktopPane.add(deck);
			desktopPane.moveToFront(deck);
			JLabel winnings = winningLabels.get(x);
			desktopPane.add(winnings);
			desktopPane.moveToFront(winnings);
		}
	}
	
	public void addCpuCounterDisplay(){
		int size = numPlayers + 1;
		for(int x = 1; x < size; x++){
			JTextField deck = deckCardsDisplay.get(x);
			desktopPane.add(deck);
			desktopPane.moveToFront(deck);
			JTextField winnings = winningCardsDisplay.get(x);
			desktopPane.add(winnings);
			desktopPane.moveToFront(winnings);
		}
	}
	
	public void addCpuDecks(){
		for(int x= 0; x < numPlayers; x++){
			JLabel deck = decks.get(x);
			desktopPane.add(deck);
			desktopPane.moveToFront(deck);
		}
	}
	
	public void addFlipPileLabels(){
		int size = names.size();
		for(int x = 0; x < size; x++){
			JLabel name = names.get(x);
			desktopPane.add(name);
			desktopPane.moveToFront(name);
		}
	}
	
	public void addFlipPiles(){
		int size = numPlayers + 3;
		for(int x = 0; x < size; x++){
			JLabel spot = cardSpots.get(x);
			desktopPane.add(spot);
			desktopPane.moveToFront(spot);
		}
	}
	
}
