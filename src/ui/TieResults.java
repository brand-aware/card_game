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

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import common.Card;
import config.ConfigTieResults;
import core.Properties;

public class TieResults extends ConfigTieResults{

	private JDialog tiePage;
	
	private int numCards;
	private ArrayList<Card> cards;
	
	private JTextArea display;
	private JScrollPane displayScroll;
	
	private JButton close;
	
	public TieResults(String winner, ArrayList<Card> results, int tieRounds, Properties properties, JFrame board){
		cards = new ArrayList<Card>(results);
		numCards = cards.size();
		Image company = Toolkit.getDefaultToolkit().getImage(properties.getCompany());
		tiePage = new JDialog(board, HEADER, true);
		tiePage.setIconImage(company);
		tiePage.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		ButtonHandler handler = new ButtonHandler();
		
		JLabel title = new JLabel(winner + " won after " + tieRounds + " tie "
				+ (tieRounds == 1 ? "round" : "rounds") + " and collects " + numCards + " cards.");
		
		display = new JTextArea();
		displayScroll = new JScrollPane(display);
		displayScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		displayScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		displayScroll.setPreferredSize(new Dimension(RESULTS_DISPLAY_HORIZONTAL, RESULTS_DISPLAY_VERTICAL));
		
		loadDisplay();
		
		close = new JButton(CLOSE_BUTTON_LABEL);
		close.addActionListener(handler);
		close.setPreferredSize(new Dimension(CLOSE_BUTTON_HORIZONTAL, CLOSE_BUTTON_VERTICAL));
		
		JPanel panel1, panel2, panel3;
		
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		
		panel1.add(title);
		
		panel2.add(displayScroll);
		
		panel3.add(close);
		
		Box box = Box.createVerticalBox();
		box.add(Box.createVerticalGlue());
		box.add(panel1);
		box.add(panel2);
		box.add(panel3);
		
		tiePage.add(box);
		tiePage.pack();
	}
	
	private class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == close){
				hide();
			}
		}
	}
	
	public void loadDisplay(){
		String buffer = "";
		int size = cards.size();
		for(int x = 0; x < size; x++){
			Card card = cards.get(x);
			buffer += card.getDetails() + "\n";
		}
		display.setText(buffer);
		display.setEditable(false);
	}
	
	public void show(JFrame board){
		tiePage.setLocationRelativeTo(board);
		tiePage.setVisible(true);
	}
	public void hide(){
		tiePage.setVisible(false);
	}
}
