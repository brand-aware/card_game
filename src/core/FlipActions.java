/**
 * @author mike802
 * @version 1.0 - 2/28/2013
 */
package core;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ui.TieResults;

import common.Card;
import core.doc.IFlipActions;

public class FlipActions extends DeckActions implements IFlipActions{

	private int tieRounds;
	private int moveCounter;
	public boolean flipFlag;
	
	public FlipActions(){
		moveCounter = 0;
		flipFlag = false;
	}
	
	//testing method
	public int getCardsOnBoard(){
		return numCardsOnBoard;
	}
	
	public void playerWins(JFrame board){
		boolean wonTie = showTie;
		if(showTie){
			showTie = false;
			showTieResults("player");
			JLabel flip = cardSpots.get(0);
			flip.setIcon(empty);
			int size = cardSpots.size();
			int index = size - 1;
			flip = cardSpots.get(index);
			flip.setIcon(empty);
		}
		if(!wonTie){
			JOptionPane.showMessageDialog(board, "You win!", "player wins", JOptionPane.PLAIN_MESSAGE, new ImageIcon(properties.getCompany()));
		}
		rulesEngine.saveWinnings(0, winnings);
		int numExtra = numWinningCards.get(0);
		int numPlayerWinningCards = numExtra + numCardsOnBoard;
		JTextField extraDisplay = winningCardsDisplay.get(0);
		extraDisplay.setText(numPlayerWinningCards + "");
		numWinningCards.set(0, numPlayerWinningCards);
		numCardsOnBoard = 0;
		cardsOnBoard.setText(numCardsOnBoard + "");
		winnings = new ArrayList<Card>();
	}
	
	public void cpuWins(int result, JFrame board){
		boolean wonTie = showTie;
		if(showTie){
			showTie = false;
			showTieResults("computer " + result);
			JLabel flip = cardSpots.get(0);
			flip.setIcon(empty);
			int size = cardSpots.size();
			int index = size - 1;
			flip = cardSpots.get(index);
			flip.setIcon(empty);
		}
		if(!wonTie){
			JOptionPane.showMessageDialog(board, "Computer " + result + " wins", "player lost", JOptionPane.PLAIN_MESSAGE, new ImageIcon(properties.getCompany()));
		}
		rulesEngine.saveWinnings(result, winnings);
		int numExtra = numWinningCards.get(result);
		int numCpuWinningCards = numExtra + numCardsOnBoard;
		JTextField extraDisplay = winningCardsDisplay.get(result);
		extraDisplay.setText(numCpuWinningCards + "");
		numWinningCards.set(result, numCpuWinningCards);
		numCardsOnBoard = 0;
		cardsOnBoard.setText(numCardsOnBoard + "");
		winnings = new ArrayList<Card>();
	}
	
	public void tie() {
		tieRounds++;
		JOptionPane.showMessageDialog(boardPage, "Tie " + tieRounds + "!\nEach tied player places 3 cards, then flips again.", CPU_PILE_PREFIX, moveCounter, new ImageIcon(properties.getCompany()));
		initTie();
		
		ArrayList<Integer> tieResults = rulesEngine.getTie(cards);
		for(int player : tieResults){
			prepareTiePlayer(player);
		}
		if(tieResults.contains(0)){
			flip.setText("flip (" + getTieFaceDownCards(0) + ")");
			int numCards = numDeckCards.get(0);
			if(numCards == 0){
				flip.setEnabled(false);
				return;
			}
			tie = true;
			flip.setEnabled(true);
		}else{
			showTie();
			move = true;
			try {
				Thread.sleep(600);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			doMove();
		}
		
		// The player must be able to start the next tie round after the dialog closes.
		//cards = new ArrayList<Card>();
	}
	
	public final void initTie(){
		JLabel flip = cardSpots.get(0);
		flip.setIcon(cover);
		
		int size = cardSpots.size();
		int index = size - 1;
		flip = cardSpots.get(index);
		flip.setIcon(cover);
		
		size--;
		for(int x = 1; x < size; x++){
			flip = cardSpots.get(x);
			flip.setIcon(empty);
		}
	}
	
	public void getResults(){
		int result = rulesEngine.result(cards);
		
		try {
			Thread.sleep(600);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(result == 0){
			playerWins(boardPage);
		}else if(result != -1){
			cpuWins(result, boardPage);
		}
		
		rulesEngine.gameover(boardPage);
		
		cpuShuffle();
		
		int numExtra = numWinningCards.get(0);
		if(numExtra > 0){
			shuffle.setEnabled(true);
		}
		
		int numCards = numDeckCards.get(0);
		if(numCards == 0){
			flip.setEnabled(false);
		}
		
		if(result == -1){
			tie();
		}
		
		int size = cardSpots.size();
		int index = size - 1;
		for(int y = 1; y < index; y++){
			JLabel flip = cardSpots.get(y);
			flip.setIcon(empty);
		}
		
		if(numCards > 0){
			flip.setEnabled(true);
		}
	}
	
	public void showTie(){
		tie = false;
		boolean justCpu = true;
		ArrayList<Integer> tied = rulesEngine.getTie(cards);
		for(int player : tied){
			prepareTiePlayer(player);
		}
		int size = tied.size();
		for(int x = 0; x < size; x++){
			int player = tied.get(x);
			if(player == 0){
				flip.setText("flip");
				justCpu = false;
			}
			
			int faceDownCards = getTieFaceDownCards(player);
			for(int y = 0; y < faceDownCards; y++){
				Card card;
				
				if(cpuNeedsShuffle()){
					cpuShuffle();
				}
				
				if(player == 0){
					card = rulesEngine.playerPlayCard();
					int numCards = rulesEngine.getPlayerCards();
					numDeckCards.set(player, numCards);
				}else{
					card = rulesEngine.cpuPlayCard(player);
					int numCards = rulesEngine.getCpuCards(player);
					numDeckCards.set(player, numCards);
				}
				winnings.add(card);
				int numCards = numDeckCards.get(player);
				JTextField cardDisplay = deckCardsDisplay.get(player);
				cardDisplay.setText(numCards + "");
				
				numCardsOnBoard = winnings.size();
				cardsOnBoard.setText(numCardsOnBoard + "");
			}
		}
		
		showTie = true;
		if(justCpu){
			cards = new ArrayList<Card>();
			for(int player = 0; player <= numPlayers; player++){
				cards.add(tied.contains(player) ? cpuFlipCard(player) : null);
			}
			getResults();
		}
	}
	
	public void doMove(){
		if(move && flipFlag){
			moveCounter = 0;
			ArrayList<Integer> tieResults = null;
			if(showTie){
				tieResults = rulesEngine.getTie(cards);
				cards = new ArrayList<Card>();
				int result = tieResults.get(0);
				if(result == 0){
					Card playerCard = playerFlipCard();
					cards.add(playerCard);
				}else{
					cards.add(null);
				}
			}else{
				cards = new ArrayList<Card>();
				Card playerCard = playerFlipCard();
				cards.add(playerCard);
			}
			if(showTie){
				int size = numPlayers + 1;
				for(int x = 1; x < size; x++){
					if(tieResults.contains(x)){
						Card cpuCard = cpuFlipCard(x);
						cards.add(cpuCard);
					}else{
						cards.add(null);
					}
				}
			}else{
				int size = numPlayers + 1;
				for(int x = 1; x < size; x++){
					Card cpuCard = cpuFlipCard(x);
					cards.add(cpuCard);
				}
			}
			
			numCardsOnBoard = winnings.size();
			cardsOnBoard.setText(numCardsOnBoard + "");
			flipFlag = false;
		}
		if(move && moveCounter == 3) {
			move = false;
			moveCounter = 0;
			getResults();
		}
		else {
			moveCounter++;
		}
	}

	private void showTieResults(String winner){
		new TieResults(winner, winnings, tieRounds, properties, boardPage).show(boardPage);
		tieRounds = 0;
	}
}
