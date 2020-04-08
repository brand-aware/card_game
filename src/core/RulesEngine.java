/**
 * @author mike802
 * @version 1.0 - 2/28/2013
 */
package core;

import common.Deck;
import common.Player;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import ui.Board;

import common.Card;

public class RulesEngine {
	
	private Properties properties;
	private ArrayList<Player> players = new ArrayList<Player>();
	private int numPlayers;
	private Board board;
	private Deck deck;
	
	public RulesEngine(Board game){
		super();
		board = game;
	}
	
	public void init(Properties p, int numPlyrs, Board game){
		properties = p;
		numPlayers = numPlyrs;
		
		deck = new Deck();
		players = new ArrayList<Player>();
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
		System.out.println(start);
		System.out.println(size3 + start);
		System.out.println("dealing...");
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
				System.out.print("player: " + turn + " ");
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
		int max = 0;
		boolean tie = false;
		
		int size = cards.size();
		for(int x = 0; x < size; x++){
			Card card = cards.get(x);
			if(card != null){
				int value = card.getNumber();
				System.out.println("number - " + value);
				Card maxCard = cards.get(max);
				int maxNumber = maxCard.getNumber();
				
				boolean same = value == maxNumber;
				boolean start = x == 0;
				if(value > maxNumber){
					max = x;
					tie = false;
				}else if(same && !start){
					tie = true;
				}
			}
		}
		
		if(tie){
			max = -1;
		}
		
		return max;
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
					System.out.println("tie player: " + y + ", card: " + number);
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
	public void cpuShuffle(int player){
		Player cpu = players.get(player);
		cpu.shuffle(false);
	}
	
	public void playerShuffle(){
		board.playerShuffle();
	}
	
	public void playerShuffleChoice(){
		int cards = getPlayerCards();
		if(cards > 0){
			int choice = JOptionPane.showConfirmDialog(null, "shuffle all cards?");
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
	
	public void gameover(){
		int numCards = getPlayerCards();
		boolean noCards = numCards == 0;
		
		Player player = players.get(0);
		int extraCards = player.getWinningsSize();
		boolean noExtra = extraCards == 0;
		if(noCards && noExtra){
			JOptionPane.showMessageDialog(null, "You have lost the game...");
			System.exit(0);
		}
		
		int size = players.size();
		for(int x = 1; x < size; x++){
			numCards = getCpuCards(x);
			noCards = numCards == 0;
			
			player = players.get(x);
			extraCards = player.getWinningsSize();
			noExtra = extraCards == 0;
			if(noCards && noExtra){
				removePlayer(x);
				JOptionPane.showMessageDialog(null, "Computer player " + x + " has been defeated");
				if(players.size() == 1){
					JOptionPane.showMessageDialog(null, "Congratulations!  You have won the game!");
					System.exit(0);
				}
			}
		}
	}
	
	private void removePlayer(int player){
		players.remove(player);
		//board.removePlayer(player);
	}
	
	public void showTie(){
		board.showTie();
	}
	public void doMove(){
		board.doMove();
	}
}
