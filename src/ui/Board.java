/**
 * @author mike802
 * @version 1.0 - 2/28/2013
 */
package ui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import common.Card;
import utils.UtilsBoardRoot;

import core.Properties;
import core.RulesEngine;

public class Board extends UtilsBoardRoot {
	
	private ButtonHandler handler;
	private MenuListener menuListener;
	private JLabel logo;
	
	public Board(){
		handler = new ButtonHandler();
		menuListener = new MenuListener();
		desktopPane = new JDesktopPane();
		rulesEngine = new RulesEngine(this);
	}	
	
	public void createPage(){
		boardPage = new JFrame("card_game");
		boardPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		boardPage.setResizable(false);
		Image company = Toolkit.getDefaultToolkit().getImage(properties.getCompany());
		boardPage.setIconImage(company);
		
		menu = new JMenuBar();
		fileMenu = new JMenu("file");
		optionsMenu = new JMenu("options");
		helpMenu = new JMenu("help");
		
		start = new JMenuItem("start");
		start.addActionListener(menuListener);
		stop = new JMenuItem("stop");
		stop.addActionListener(menuListener);
		exit = new JMenuItem("exit");
		exit.addActionListener(menuListener);
		preferences = new JMenuItem("preferences");
		preferences.addActionListener(menuListener);
		about = new JMenuItem("about");
		about.addActionListener(menuListener);
		
		fileMenu.add(start);
		fileMenu.add(stop);
		fileMenu.add(exit);
		optionsMenu.add(preferences);
		helpMenu.add(about);
		menu.add(fileMenu);
		menu.add(optionsMenu);
		menu.add(helpMenu);		
		boardPage.setJMenuBar(menu);
		
		calcTotalX();
		calcTotalY();
		boardPage.setPreferredSize(new Dimension(totalX + (numPlayers * 10), totalY));
		
		String backgroundPath = properties.getBackground();
		ImageIcon backgroundIcon = new ImageIcon(backgroundPath);
		JLabel background = new JLabel();
		background.setIcon(backgroundIcon);
		background.setBounds(0, 0, 1300, 1100);
		desktopPane.add(background);
		
		String logoPath = properties.getLogo();
		ImageIcon logoIcon = new ImageIcon(logoPath);
		logo = new JLabel();
		logo.setIcon(logoIcon);
		currentX = (totalX / 2) - (376 / 2);
		logo.setBounds(currentX, 15, 376, 109);
		desktopPane.add(logo);
		desktopPane.moveToFront(logo);
		
		String coverPath = properties.getCoverPath();
		cover = new ImageIcon(coverPath);
		String emptyPath = properties.getEmptyPath();
		empty = new ImageIcon(emptyPath);
		
		rulesEngine.init(properties, numPlayers, this);
		rulesEngine.deal();
		
		int numCards = rulesEngine.getPlayerCards();
		numDeckCards.add(numCards);
		numWinningCards.add(DEFAULT_WINNING_CARDS);
		
		initializeCounters();
		createCpuLabels();
		createCpuDisplay();
		createCpuDecks();
		createFlipArea();
		
		createPlayerArea();
		
		addCpuLabelDisplay();
		addCpuCounterDisplay();
		addCpuDecks();
		addFlipPileLabels();
		desktopPane.add(cardsOnBoard);
		addFlipPiles();
	
		addPlayerArea();
		doStop();
		
		boardPage.add(desktopPane);
		boardPage.pack();
		boardPage.setVisible(true);
	}
	
	private class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == flip){
				if(tie){
					rulesEngine.showTie();
				}else{
					move = true;
					flip.setEnabled(false);
				}
			}else if(event.getSource() == shuffle){
				rulesEngine.playerShuffle();
			}
		}
	}
	
	private class MenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == start){
				doStart();
			}else if(event.getSource() == stop){
				doStop();
			}else if(event.getSource() == exit){
				System.exit(0);
			}else if(event.getSource() == preferences){
				doPreferences();				
			}else if(event.getSource() == about){
				JOptionPane.showMessageDialog(null, "card_game\nproduct of - ???\n\n"
						+ "contact:\nmike.drummond.802@hotmail.com", 
						"about", 
						JOptionPane.PLAIN_MESSAGE, 
						new ImageIcon(properties.getCompany()));
			}
		}	
	}
	
	public void doPreferences(){
		Setup setup = new Setup(this);
		setup.init(properties);
		desktopPane.add(setup);
		desktopPane.moveToFront(setup);
		disableBoard();
	}
	
	public void doResetBoard(){
		clearBoard();
		init(properties, numPlayers);
		calcTotalX();
		calcTotalY();
		int boardX = totalX + (numPlayers * 10);
		int boardY = totalY;
		if(numPlayers == 1){
			boardX += 10;
		}
		if(numPlayers == 3){
			boardY += 10;
		}
		boardPage.setPreferredSize(new Dimension(boardX, boardY));
		boardPage.pack();
		createLogo();
		
		rulesEngine.init(properties, numPlayers, this);
		rulesEngine.deal();
		int numCards = rulesEngine.getPlayerCards();
		numDeckCards.add(numCards);
		numWinningCards.add(DEFAULT_WINNING_CARDS);
		initializeCounters();
		createCpuLabels();
		createCpuDisplay();
		createCpuDecks();
		createFlipArea();
		
		createPlayerArea();
		
		addCpuLabelDisplay();
		addCpuCounterDisplay();
		addCpuDecks();
		addFlipPileLabels();
		desktopPane.add(cardsOnBoard);
		addFlipPiles();
		
		addPlayerArea();
		doStop();
	}
	
	public void doStart(){
		rulesEngine.init(properties, numPlayers, this);
		rulesEngine.deal();
		int cards;
		for(int x = 1; x < numPlayers + 1; x++){
			cards = numDeckCards.get(x);
			deckCardsDisplay.get(x).setText(cards + "");;
			
			cards = numWinningCards.get(x);
			winningCardsDisplay.get(x).setText(cards + "");
		}
		cards = numDeckCards.get(0);
		deckCardsDisplay.get(0).setText(cards + "");
		
		cards = numWinningCards.get(0);
		winningCardsDisplay.get(0).setText(cards + "");
		
		playerDeck.setIcon(new ImageIcon(properties.getCoverPath()));
		for(int x = 0; x < decks.size(); x++){
			decks.get(x).setIcon(new ImageIcon(properties.getCoverPath()));
		}
		
		for(int x = 0; x < deckCardsDisplay.size(); x++){
			deckCardsDisplay.get(x).setEnabled(true);
			winningCardsDisplay.get(x).setEnabled(true);
		}
		flip.setEnabled(true);
		cardsOnBoard.setEnabled(true);
	}
	
	public void doStop(){
		rulesEngine.init(properties, numPlayers, this);
		for(int x = 1; x < numPlayers + 1; x++){
			deckCardsDisplay.get(x).setText("0");;
			
			winningCardsDisplay.get(x).setText("0");
		}
		deckCardsDisplay.get(0).setText("0");
		winningCardsDisplay.get(0).setText("0");
		
		playerDeck.setIcon(new ImageIcon(properties.getEmptyPath()));
		for(int x = 0; x < decks.size(); x++){
			decks.get(x).setIcon(new ImageIcon(properties.getEmptyPath()));
		}
		
		for(int x = 0; x < deckCardsDisplay.size(); x++){
			deckCardsDisplay.get(x).setEnabled(false);
			winningCardsDisplay.get(x).setEnabled(false);
		}
		flip.setEnabled(false);
		shuffle.setEnabled(false);
		cardsOnBoard.setEnabled(false);
	}
	
	public void clearBoard(){
		desktopPane.moveToBack(logo);
		for(int x = 0; x < deckCardsDisplay.size(); x++){
			desktopPane.moveToBack(deckCardsDisplay.get(x));
			desktopPane.moveToBack(deckLabels.get(x));
			desktopPane.moveToBack(winningCardsDisplay.get(x));
			desktopPane.moveToBack(winningLabels.get(x));
		}
		for(int x = 0; x < decks.size(); x++){
			desktopPane.moveToBack(decks.get(x));
		}
		desktopPane.moveToBack(playerDeck);
		
		for(int x = 0; x < cardSpots.size(); x++){
			desktopPane.moveToBack(cardSpots.get(x));
		}
		for(int x = 0; x < names.size(); x++){
			desktopPane.moveToBack(names.get(x));
		}
		desktopPane.moveToBack(flip);
		desktopPane.moveToBack(shuffle);
		desktopPane.moveToBack(cardsOnBoard);
		//desktopPane.moveToBack(cardSpots.get(DEFAULT_FLIP_LOOP_OFFSET));
	}
	
	public void createLogo(){
		String logoPath = properties.getLogo();
		ImageIcon logoIcon = new ImageIcon(logoPath);
		logo = new JLabel();
		logo.setIcon(logoIcon);
		currentX = (totalX / 2) - (376 / 2);
		logo.setBounds(currentX, 15, 376, 109);
		desktopPane.add(logo);
		desktopPane.moveToFront(logo);
	}
	
	public void createPlayerArea(){
		currentY += CARD_PILE_LABEL_VERTICAL + 5 + 130 + 5;
		currentX = (totalX / 2) - (75/2);
		
		playerDeck = new JLabel();
		playerDeck.setBounds(currentX, currentY, 75, 130);
		playerDeck.setIcon(cover);
		
		cardsOnBoard = new JTextField();
		int cobX = 25;
		if(numPlayers == 3){
			cobX = (totalX / 2) - (((75 * numPlayers + 75 + 75 * 2) 
					+ (5 * numPlayers + 5 * 2)) / 2);
			cobX -= 75 - 5;
		}
		cardsOnBoard.setBounds(cobX, currentY - (75 / 2) - 3, FLIPPED_CARDS_DISPLAY_HORIZONTAL, FLIPPED_CARDS_DISPLAY_VERTICAL);
		cardsOnBoard.setText(numCardsOnBoard + "");
		cardsOnBoard.setEditable(false);
				
		int tempY = currentY + (130 / 2) - (FLIP_BUTTON_VERTICAL / 2);
		currentX += 75 + 5;
		flip = new JButton(FLIP_BUTTON);
		flip.setBounds(currentX, tempY, FLIP_BUTTON_HORIZONTAL, FLIP_BUTTON_VERTICAL);
		flip.addActionListener(handler);
		
		currentX += FLIP_BUTTON_HORIZONTAL + 5;
		shuffle = new JButton(SHUFFLE_BUTTON);
		shuffle.setBounds(currentX, tempY, SHUFFLE_BUTTON_HORIZONTAL, SHUFFLE_BUTTON_VERTICAL);
		shuffle.addActionListener(handler);
		shuffle.setEnabled(false);
		currentY += 130 + 5;
		
		currentX = (totalX / 2) - ((DECK_DISPLAY_LABEL_HORIZONTAL + WINNINGS_DISPLAY_LABEL_HORIZONTAL) / 2);
		int cards = rulesEngine.getPlayerCards();
		numDeckCards.add(0, cards);
		numWinningCards.add(0, rulesEngine.getPlayerWinningCards());
		
		JLabel deckLabel = new JLabel("Deck");
		deckLabel.setBounds(currentX, currentY, DECK_DISPLAY_LABEL_HORIZONTAL, DECK_DISPLAY_LABEL_VERTICAL);
		deckLabels.add(0, deckLabel);
		currentX += DECK_DISPLAY_LABEL_HORIZONTAL + 5;	
		
		JLabel winningLabel = new JLabel("Winnings");
		winningLabel.setBounds(currentX, currentY, WINNINGS_DISPLAY_LABEL_HORIZONTAL, WINNINGS_DISPLAY_LABEL_VERTICAL);
		winningLabel.setPreferredSize(new Dimension(WINNINGS_DISPLAY_LABEL_HORIZONTAL, WINNINGS_DISPLAY_LABEL_VERTICAL));
		winningLabels.add(0, winningLabel);
		currentX += WINNINGS_DISPLAY_LABEL_HORIZONTAL + 100;
		
		currentY += WINNINGS_DISPLAY_LABEL_VERTICAL + 5;
		currentX = (totalX / 2) - ((DECK_DISPLAY_HORIZONTAL + WINNINGS_DISPLAY_HORIZONTAL) / 2);
		JTextField deckCards = new JTextField();
		deckCards.setBounds(currentX, currentY, DECK_DISPLAY_HORIZONTAL, DECK_DISPLAY_VERTICAL);
		cards = numDeckCards.get(0);
		deckCards.setText(cards + "");
		deckCards.setEditable(false);
		deckCardsDisplay.add(0, deckCards);
		currentX += DECK_DISPLAY_HORIZONTAL + 5;
		
		JTextField winningCards = new JTextField();
		winningCards.setBounds(currentX, currentY, WINNINGS_DISPLAY_HORIZONTAL, WINNINGS_DISPLAY_VERTICAL);
		cards = numWinningCards.get(0);
		winningCards.setText(cards + "");
		winningCards.setEditable(false);
		winningCardsDisplay.add(0, winningCards);
	}
	
	public void addPlayerArea(){
		desktopPane.add(playerDeck);
		desktopPane.add(flip);
		desktopPane.add(shuffle);
		desktopPane.add(deckLabels.get(DEFAULT_PLAYER_DECK_POSITION));
		desktopPane.add(winningLabels.get(DEFAULT_PLAYER_WINNINGS_POSITION));
		desktopPane.add(deckCardsDisplay.get(DEFAULT_PLAYER_DECK_DISPLAY_POSITION));
		desktopPane.add(winningCardsDisplay.get(DEFAULT_PLAYER_WINNINGS_DISPLAY_POSITION));
		
		desktopPane.moveToFront(cardsOnBoard);
		desktopPane.moveToFront(playerDeck);
		desktopPane.moveToFront(flip);
		desktopPane.moveToFront(shuffle);
		desktopPane.moveToFront(deckLabels.get(DEFAULT_PLAYER_DECK_POSITION));
		desktopPane.moveToFront(winningLabels.get(DEFAULT_PLAYER_WINNINGS_POSITION));
		desktopPane.moveToFront(deckCardsDisplay.get(DEFAULT_PLAYER_DECK_DISPLAY_POSITION));
		desktopPane.moveToFront(winningCardsDisplay.get(DEFAULT_PLAYER_WINNINGS_DISPLAY_POSITION));
	}
	
	/*public final void removePlayer(int player){
		names.remove(player);
		cardSpots.remove(player + 1);
		cards.remove(player);
		winnings.remove(player);
		deckCardsDisplay.remove(player);
		deckLabels.remove(player);
		winningCardsDisplay.remove(player);
		winningLabels.remove(player);
		numDeckCards.remove(player);
		numWinningCards.remove(player);
	}*/
	
	public void disableBoard(){
		fileMenu.setEnabled(false);
		optionsMenu.setEnabled(false);
		helpMenu.setEnabled(false);
		flip.setEnabled(false);
		shuffle.setEnabled(false);
	}
	
	public final void enableBoard(){
		fileMenu.setEnabled(true);
		optionsMenu.setEnabled(true);
		helpMenu.setEnabled(true);
		flip.setEnabled(true);
		shuffle.setEnabled(true);
	}
	
	public final Board getBoard(){
		return this;
	}
	
	public final void setNumPlayers(int num){
		numPlayers = num;
	}
	
	public final RulesEngine getRulesEngine(){
		return rulesEngine;
	}
	
	
	/*
	 * JUnit test code
	 */
	
	public final void createElements(){
		createLogo();
		
		rulesEngine.init(properties, numPlayers, this);
		rulesEngine.deal();
		int numCards = rulesEngine.getPlayerCards();
		numDeckCards.add(numCards);
		numWinningCards.add(DEFAULT_WINNING_CARDS);
		initializeCounters();
		createCpuLabels();
		createCpuDisplay();
		createCpuDecks();
		createFlipArea();
	}
	
	
	public final void init(Properties p){
		init(p, 2);
	}
	public final void init(Properties p, int numPlrys){
		properties = p;
		numPlayers = numPlrys;
		
		decks = new ArrayList<JLabel>();
		names = new ArrayList<JLabel>();
		cardSpots = new ArrayList<JLabel>();
		cards = new ArrayList<Card>();
		winnings = new ArrayList<Card>();
		deckCardsDisplay = new ArrayList<JTextField>();
		deckLabels = new ArrayList<JLabel>();
		winningCardsDisplay = new ArrayList<JTextField>();
		winningLabels = new ArrayList<JLabel>();
		numDeckCards = new ArrayList<Integer>();
		numWinningCards = new ArrayList<Integer>();
		
		if(!initialized){
			createPage();
			initialized = true;
		}
	}
}
