package config;

public class ConfigBoard {
	
	//**
	//labels
	protected final String FLIP_BUTTON = "flip";
	protected final String SHUFFLE_BUTTON = "shuffle";
	protected final String DECK_LABEL = "deck";
	protected final String WINNINGS_LABEL = "winnings";
	protected final String TIE_PILE_LABEL = "tie";
	protected final String PLAYER_PILE_LABEL = "player";
	protected final String CPU_PILE_PREFIX = "cpu ";
	//**
	
	protected final int DEFAULT_WINNING_CARDS = 0;
	
	//board display
	protected final int FLIPPED_CARDS_DISPLAY_HORIZONTAL = 50;
	protected final int FLIPPED_CARDS_DISPLAY_VERTICAL = 30;
	
	//**
	//player display buttons
	protected final int FLIP_BUTTON_HORIZONTAL = 85;
	protected final int FLIP_BUTTON_VERTICAL = 30;

	protected final int SHUFFLE_BUTTON_HORIZONTAL = 85;
	protected final int SHUFFLE_BUTTON_VERTICAL = 30;
	//**	
	
	protected final int DECK_DISPLAY_LABEL_HORIZONTAL = 70;
	protected final int DECK_DISPLAY_LABEL_VERTICAL = 30;
	
	protected final int DECK_DISPLAY_HORIZONTAL = 70;
	protected final int DECK_DISPLAY_VERTICAL = 30;
	
	protected final int WINNINGS_DISPLAY_LABEL_HORIZONTAL = 70;
	protected final int WINNINGS_DISPLAY_LABEL_VERTICAL = 30;
	
	protected final int WINNINGS_DISPLAY_HORIZONTAL = 70;
	protected final int WINNINGS_DISPLAY_VERTICAL = 30;
	
	protected final int CARD_PILE_LABEL_HORIZONTAL = 75;
	protected final int CARD_PILE_LABEL_VERTICAL = 30;
	
	//default positions
	//**
	protected final int DEFAULT_PLAYER_DECK_POSITION = 0;
	protected final int DEFAULT_PLAYER_WINNINGS_POSITION = 0;
	protected final int DEFAULT_PLAYER_DECK_DISPLAY_POSITION = 0;
	protected final int DEFAULT_PLAYER_WINNINGS_DISPLAY_POSITION = 0;
	protected final int DEFAULT_LEFT_TIE_LABEL = 0;
	protected final int DEFAULT_PLAYER_LABEL = 1;
		
	protected final int DEFAULT_FLIP_LOOP_OFFSET = 2;
	//**
	
	protected int totalX, totalY, currentX, currentY;
	protected int numPlayers;
	
	protected void calcTotalX(){
		totalX = numPlayers * 75;
		totalX += numPlayers * 5;
		totalX += 75 + 5;
		totalX += 75 + 75 + 5;
		totalX += 100 * 2;
		totalX += 5;
		
		int totalX2 = (((DECK_DISPLAY_LABEL_HORIZONTAL + 
				WINNINGS_DISPLAY_LABEL_HORIZONTAL) * numPlayers) + 
				(100 * (numPlayers - 1))) ;
		//totalX2 += (FLIPPED_CARDS_DISPLAY_HORIZONTAL + 25) * 2;
		totalX2 += 100 *2;
		totalX2 += (5 + 5) * numPlayers;
		if(totalX2 > totalX){
			totalX = totalX2;
		}
	}
	
	protected void calcTotalY(){
		totalY = 109 + 15; 
		totalY += DECK_DISPLAY_LABEL_VERTICAL + 5;
		totalY += DECK_DISPLAY_VERTICAL + 5;
		totalY += 130 + 5;
		totalY += CARD_PILE_LABEL_VERTICAL + 5;
		totalY += 130 + 5;
		totalY += 130 + 5;
		totalY += DECK_DISPLAY_LABEL_VERTICAL + 5;
		totalY += DECK_DISPLAY_VERTICAL + 15;
		totalY += 65;
	}
}
