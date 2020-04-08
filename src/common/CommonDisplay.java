/**
 * @author mike802
 * @version 1.0 - 2/28/2013
 */
package common;

import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import config.ConfigBoard;
import core.Properties;
import core.RulesEngine;

public class CommonDisplay extends ConfigBoard{
	
	protected JFrame boardPage;
	protected Properties properties;
	protected RulesEngine rulesEngine;
	
	protected boolean initialized = false;
	
	protected JMenuBar menu;
	protected JMenu fileMenu, optionsMenu, helpMenu;
	protected JMenuItem start, stop, exit, preferences, about;
	
	protected JDesktopPane desktopPane;
	protected JLabel playerDeck = null;
	protected ArrayList<JLabel> decks;
	
	protected JTextField cardsOnBoard;
	protected int numCardsOnBoard = 0;
	protected ImageIcon empty;
	protected ImageIcon cover;
	
	protected ArrayList<JLabel> names;
	protected ArrayList<JLabel> cardSpots;
	protected ArrayList<Card> cards;
	protected ArrayList<Card> winnings;
	
	protected ArrayList<JTextField> deckCardsDisplay;
	protected ArrayList<JLabel> deckLabels; 
	protected ArrayList<JTextField> winningCardsDisplay;
	protected ArrayList<JLabel> winningLabels;
	
	protected ArrayList<Integer> numDeckCards;
	protected ArrayList<Integer> numWinningCards;
	
	protected JButton flip, shuffle;
	
	protected boolean move = false;
	protected boolean tie = false;
	protected boolean showTie = false;
			
}
