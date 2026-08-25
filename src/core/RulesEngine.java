/**
 * @author mike802
 * @version 1.0 - 2/28/2013
 */
package core;

import common.Deck;
import common.Player;
import common.RoundedImageIcon;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ui.Board;

import common.Card;

public class RulesEngine {
	
	private Properties properties;
	private ArrayList<Player> players = new ArrayList<Player>();
	private int numPlayers;
	private Board board;
	private Deck deck;
	private volatile boolean gameFinished;
	
	public RulesEngine(Board game){
		super();
		board = game;
	}
	
	public void init(Properties p, int numPlyrs, Board game){
		properties = p;
		numPlayers = numPlyrs;
		
		deck = new Deck();
		players = new ArrayList<Player>();
		gameFinished = false;
		board = game;
		int size = numPlayers + 1;
		for(int x = 0; x < size; x++){
			Player player = new Player(properties);
			players.add(player);
		}
	}
	
	public void deal(){
		int[] cardDeck = deck.getDeck();
		String[] suits = deck.getSuits();
		
		int size1 = cardDeck.length;
		int size2 = suits.length;
		
		int size3 = size1 * size2;
		
		int start = (int) (Math.random() * numPlayers);
		int counter = start;
		while(counter < (size3 + start)){
			double random = Math.random();
			int selection1 = (int)(random * size1);
			random = Math.random();
			int selection2 = (int)(random * size2);
			
			int number = cardDeck[selection1];
			String suit = suits[selection2];
			if(!deck.hasBeenDealt(number, suit)){
				int size = numPlayers + 1;
				int turn = counter % size;
				deck.deal(number, suit);
				Player player = players.get(turn);
				player.addCard(number, suit);
				
				counter++;
			}
		}
	}
	
	public Card playerPlayCard(){
		Player player = players.get(0);
		Card card = player.getCard();
		return card;
	}
	
	public Card cpuPlayCard(int player){
		Player cpu = players.get(player);
		Card card = cpu.getCard();
		return card;
	}
	
	public int result(ArrayList<Card> cards){
		int max = -1;
		int maxValue = Integer.MIN_VALUE;
		boolean tie = false;

		for(int x = 0; x < cards.size(); x++){
			Card card = cards.get(x);
			if(card == null){
				continue;
			}

			int value = card.getNumber();
			if(value > maxValue){
				max = x;
				maxValue = value;
				tie = false;
			}else if(value == maxValue){
				tie = true;
			}
		}

		return tie ? -1 : max;
	}
	
	public ArrayList<Integer> getTie(ArrayList<Card> cards){
		int max = 0;
		int size = cards.size();
		for(int x = 0; x < size; x++){
			Card card = cards.get(x);
			if(card != null){
				int value = card.getNumber();
				Card maxCard = cards.get(max);
				if(maxCard != null){
					
					int maxValue = maxCard.getNumber();
					if(value > maxValue){
						max = x;
					}
				}else{
					max = x;
				}
			}
		}
		
		ArrayList<Integer> tie = new ArrayList<Integer>();
		Card maxCard = cards.get(max);
		int value = maxCard.getNumber();
		for(int y = 0; y < size; y++){
			Card card = cards.get(y);
			if(card != null){
				int number = card.getNumber();
				if(number == value){
					tie.add(y);
				}
			}
		}
		
		return tie;
	}
	
	public void saveWinnings(int result, ArrayList<Card> winnings){
		int size = winnings.size();
		for(int x = 0; x < size; x++){
			Card card = winnings.get(x);
			if(card == null){
				winnings.remove(x);
				winnings.trimToSize();
				x--;
			}
		}
		Player player = players.get(result);
		player.addWinnings(winnings);
	}
	
	public int getPlayerCards(){
		Player player = players.get(0);
		int size = player.getHandSize();
		return size;
	}
	public int getPlayerWinningCards(){
		Player player = players.get(0);
		int size = player.getWinningsSize();
		return size;
	}
	// testing method
	public int getCpuWinningCards(int cpu){
		int adjustment = cpu + 1;
		Player player = players.get(adjustment);
		int size = player.getWinningsSize();
		return size;
	}
	public int getCpuCards(int player){
		Player cpu = players.get(player);
		int size = cpu.getHandSize();
		return size;
	}
	public int getWinningsCards(int player){
		Player cpu = players.get(player);
		return cpu.getWinningsSize();
	}
	public void cpuShuffle(int player){
		Player cpu = players.get(player);
		cpu.shuffle(false);
	}
	public void shuffleWinnings(int player){
		Player gamePlayer = players.get(player);
		gamePlayer.shuffle(false);
	}
	
	public void playerShuffle(){
		board.playerShuffle();
	}
	
	public void playerShuffleChoice(JFrame board){
		int cards = getPlayerCards();
		if(cards > 0){
			int choice = JOptionPane.showConfirmDialog(board, "shuffle all cards?", null, 0, 0, new RoundedImageIcon(properties.getCompany()));
			if(choice == 0){
				Player player = players.get(0);
				player.shuffle(true);
			}else if(choice == 1){
				Player player = players.get(0);
				player.shuffle(false);
			}
		}else{
			Player player = players.get(0);
			player.shuffle(false);
		}
	}
	
	public void gameover(JFrame boardPage){
		if(gameFinished){
			return;
		}

		int remainingPlayers = 0;
		int winner = -1;
		for(int x = 0; x < players.size(); x++){
			Player player = players.get(x);
			if(player.getHandSize() + player.getWinningsSize() > 0){
				remainingPlayers++;
				winner = x;
			}
		}

		if(remainingPlayers == 1){
			gameFinished = true;
			board.stopForGameOver();
			String winnerName = winner == 0 ? "You are" : "Computer " + winner + " is";
			JOptionPane.showMessageDialog(boardPage, winnerName + " the last player with cards.\n"
					+ winnerName + " the winner!", winnerName, winner, new RoundedImageIcon(properties.getCompany()));
		}
	}

	public boolean isGameFinished(){
		return gameFinished;
	}
	
	public void showTie(){
		board.showTie();
	}
	public void doMove(){
		board.doMove();
	}
}
