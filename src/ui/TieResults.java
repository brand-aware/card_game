/**
 * @author mike802
 * @version 1.0 - 2/28/2013
 */
package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import common.Card;
import config.ConfigTieResults;

public class TieResults extends ConfigTieResults{

	private TieResults tieResults = null;
	private JFrame tiePage = null;
	
	private int numCards;
	private String winner;
	private ArrayList<Card> cards;
	
	private JTextArea display;
	private JScrollPane displayScroll;
	
	private JButton close;
	
	public TieResults(){
		tiePage = new JFrame(HEADER);
		tiePage.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		ButtonHandler handler = new ButtonHandler();
		
		JLabel title = new JLabel(winner + MESSAGE1 + numCards + MESSAGE2);
		
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
		tiePage.setVisible(true);
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
	
	public  void init(String player, ArrayList<Card> results){
		cards = results;
		numCards = cards.size();
		winner = player;
		if(tieResults == null){
			tieResults = new TieResults();
		}
	}
	public void show(){
		tiePage.setVisible(true);
	}
	public void hide(){
		tiePage.setVisible(false);
	}
}
