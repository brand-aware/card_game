package common;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.border.Border;

/** A lightweight anti-aliased border shared by rounded Swing controls. */
public class RoundedBorder implements Border {

	public static final int RADIUS = 12;
	private final Color color;

	public RoundedBorder(Color color) {
		this.color = color;
	}

	@Override
	public Insets getBorderInsets(Component component) {
		return new Insets(4, 8, 4, 8);
	}

	@Override
	public boolean isBorderOpaque() {
		return false;
	}

	@Override
	public void paintBorder(Component component, Graphics graphics, int x, int y, int width, int height) {
		Graphics2D copy = (Graphics2D) graphics.create();
		copy.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		copy.setColor(color);
		copy.drawRoundRect(x, y, width - 1, height - 1, RADIUS, RADIUS);
		copy.dispose();
	}
}
