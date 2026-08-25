package common;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JTextField;

/** A JTextField with a rounded background and border. */
public class RoundedTextField extends JTextField {

	private static final long serialVersionUID = 1L;

	public RoundedTextField() {
		setBorder(new RoundedBorder(Color.GRAY));
		setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		Graphics2D copy = (Graphics2D) graphics.create();
		copy.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		copy.setColor(getBackground());
		copy.fillRoundRect(0, 0, getWidth(), getHeight(), RoundedBorder.RADIUS, RoundedBorder.RADIUS);
		copy.dispose();
		super.paintComponent(graphics);
	}
}
