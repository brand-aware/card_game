package config;

import javax.swing.JInternalFrame;

public class ConfigSetup extends JInternalFrame {
	
	/**
	 * generated serialVersionUID
	 */
	private static final long serialVersionUID = 6934794954957259418L;

	protected final String[] PLAYER_OPTIONS = {"1", "2", "3"};

	protected final String DIRECTIONS = "Choose the number of computer players:";
	protected final String OK_BUTTON_LABEL = "ok";

	protected final int DIRECTIONS_HORIZONTAL = 350;
	protected final int DIRECTIONS_VERTICAL = 30;
	
	protected final int PLAYER_OPTIONS_COMBO_HORIZONTAL = 100;
	protected final int PLAYER_OPTIONS_COMBO_VERTICAL = 30;
	
	protected final int PLAYER_COMBO_SPACING_RIGHT_HORIZONTAL = 250;
	protected final int PLAYER_COMBO_SPACING_RIGHT_VERTICAL = 30;
	
	protected final int OK_BUTTON_HORIZONTAL = 75;
	protected final int OK_BUTTON_VERTICAL = 30;
	
	public ConfigSetup(){
		super("card_game setup");
	}

}
