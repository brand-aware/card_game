/**
 * @author mike802
 * @version 1.0 - 2/28/2013
 */
package ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import config.ConfigSetup;

import core.Properties;
import common.RoundedImageIcon;
import common.RoundedButton;


public class Setup extends ConfigSetup{
	
	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = 3506855024254624590L;
	private Properties properties;
	private Board board;
	
	private JComboBox<String> cpuPlayers;
	private String[] playerList;
	private JButton ok, cancel;
	private boolean initialized = false;
	
	public Setup(Board b){
		super();
		board = b;
	}
	
	public void createPage(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ButtonHandler handler = new ButtonHandler();
		setFrameIcon(new RoundedImageIcon(properties.getCompanyIframe()));
		
		JLabel directions = new JLabel(DIRECTIONS);
		directions.setPreferredSize(new Dimension(DIRECTIONS_HORIZONTAL, DIRECTIONS_VERTICAL));
		
		playerList = PLAYER_OPTIONS;
		cpuPlayers = new JComboBox<String>(playerList);
		cpuPlayers.setPreferredSize(new Dimension(PLAYER_OPTIONS_COMBO_HORIZONTAL, PLAYER_OPTIONS_COMBO_VERTICAL));
		
		JLabel comboOffset = new JLabel();
		comboOffset.setPreferredSize(new Dimension(PLAYER_COMBO_SPACING_RIGHT_HORIZONTAL, PLAYER_COMBO_SPACING_RIGHT_VERTICAL));
		
		ok = new RoundedButton(OK_BUTTON_LABEL);
		ok.setPreferredSize(new Dimension(OK_BUTTON_HORIZONTAL, OK_BUTTON_VERTICAL));
		ok.addActionListener(handler);
		
		cancel = new RoundedButton("cancel");
		cancel.setPreferredSize(new Dimension(100, 30));
		cancel.addActionListener(handler);
		
		JPanel panel1, panel2, panel3;
		
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
				
		panel1.add(directions);
		
		panel2.add(Box.createGlue());
		panel2.add(cpuPlayers);
		panel2.add(comboOffset);
		
		panel3.add(ok);
		panel3.add(cancel);
		
		Box box = Box.createVerticalBox();
		box.add(Box.createVerticalGlue());
		box.add(panel1);
		box.add(panel2);
		box.add(panel3);
		
		add(box);
		pack();
		setVisible(true);
	}

	private class ButtonHandler implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource() == ok){
				int index = cpuPlayers.getSelectedIndex();
				String numPlayers = playerList[index];
				int num = Integer.parseInt(numPlayers);
				board.setNumPlayers(num);
				board.doResetBoard();
				board.enableBoard();
				board.doStop();
				dispose();
			}else if(event.getSource() == cancel){
				//board.init(properties);
				board.enableBoard();
				board.doStop();
				dispose();
			}
			
		}
	}
	
	public final void init(Properties p){
		properties = p;
		if(!initialized){
			createPage();
			initialized = true;
		}
	}
}
