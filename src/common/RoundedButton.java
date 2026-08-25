package common;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;

/** A JButton with a rounded background and border. */
public class RoundedButton extends JButton {

	private static final long serialVersionUID = 1L;

	public RoundedButton(String text) {
		super(text);
		setBorder(new RoundedBorder(Color.GRAY));
		setContentAreaFilled(false);
		setFocusPainted(false);
		setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		Graphics2D copy = (Graphics2D) graphics.create();
		copy.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Color color = getBackground();
		if (!isEnabled()) {
			color = color.darker();
		} else if (getModel().isPressed()) {
			color = color.darker();
		}
		copy.setColor(color);
		copy.fillRoundRect(0, 0, getWidth(), getHeight(), RoundedBorder.RADIUS, RoundedBorder.RADIUS);
		copy.dispose();
		super.paintComponent(graphics);
	}
}
