/**
 * @author mike802
 * @version 1.0 - 2/28/2013
 */
package core;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ui.TieResults;

import common.Card;
import core.doc.IFlipActions;

public class FlipActions extends DeckActions implements IFlipActions{

	private TieResults tieResults;
	
	public FlipActions(){

	}
	
	//testing method
	public int getCardsOnBoard(){
		return numCardsOnBoard;
	}
	
	public void playerWins(){
		if(showTie){
			showTie = false;
			tieResults = new TieResults();
			tieResults.init("player", winnings);
			tieResults.show();
			tieResults.loadDisplay();
			JLabel flip = cardSpots.get(0);
			flip.setIcon(empty);
			int size = cardSpots.size();
			int index = size - 1;
			flip = cardSpots.get(index);
			flip.setIcon(empty);
		}
		JOptionPane.showMessageDialog(null, "You win!", "player wins", JOptionPane.PLAIN_MESSAGE, new ImageIcon(properties.getCompany()));
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
	
	public void cpuWins(int result){
		if(showTie){
			showTie = false;
			tieResults = new TieResults();
			tieResults.init("computer " + result, winnings);
			tieResults.show();
			tieResults.loadDisplay();
			JLabel flip = cardSpots.get(0);
			flip.setIcon(empty);
			int size = cardSpots.size();
			int index = size - 1;
			flip = cardSpots.get(index);
			flip.setIcon(empty);
		}
		JOptionPane.showMessageDialog(null, "Computer " + result + " wins", "player lost", JOptionPane.PLAIN_MESSAGE, new ImageIcon(properties.getCompany()));
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
	
	public void tie(){
		JOptionPane.showMessageDialog(null, "Tie!\nFlip 3, then flip again");
		initTie();
		
		ArrayList<Integer> tieResults = rulesEngine.getTie(cards);
		int result = tieResults.get(0);
		if(result == 0){
			System.out.println("player flip 3");
			flip.setText("flip (3)");
			int numCards = numDeckCards.get(0);
			if(numCards < 3){
				flip.setEnabled(false);
			}
			tie = true;
		}else{
			System.out.println("just cpu");
			showTie();
			move = true;
			try {
				Thread.sleep(600);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			doMove();
		}
		
		if(numDeckCards.get(0) > 3){
			flip.setEnabled(false);
		}
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
			playerWins();
		}else if(result != -1){
			cpuWins(result);
		}
		
		rulesEngine.gameover();
		
		if(cpuNeedsShuffle()){
			cpuShuffle();
		}
		
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
		int size = tied.size();
		for(int x = 0; x < size; x++){
			int player = tied.get(x);
			if(player == 0){
				flip.setText("flip");
				justCpu = false;
				System.out.println("player in tie");
			}
			
			for(int y = 0; y < 3; y++){
				Card card;
				
				if(cpuNeedsShuffle()){
					cpuShuffle();
				}
				
				if(oneCardLeft()){
					showTie = true;
					break;
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
		
		if(justCpu){
			size = tied.size();
			for(int z = 0; z < size; z++){
				int player = tied.get(z);
				cpuFlipCard(player);
				
				getResults();
			}
		}
		showTie = true;
	}
	
	public void doMove(){
		if(move){
			move = false;
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
				int counter = 0;
				int size = numPlayers + 1;
				for(int x = 1; x < size; x++){
					int player = tieResults.get(counter);
					if(player == x){
						Card cpuCard = cpuFlipCard(player);
						cards.add(cpuCard);
						counter++;
						if(counter > tieResults.size()){
							break;
						}
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
			
			getResults();
		}
	}
}
