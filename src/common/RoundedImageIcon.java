package common;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

/** An ImageIcon whose corners are clipped with a smooth, rounded edge. */
public class RoundedImageIcon extends ImageIcon {

	private static final long serialVersionUID = 1L;
	private static final int CORNER_RADIUS = 12;

	public RoundedImageIcon(String path) {
		super(path);
		setImage(createRoundedImage());
	}

	private BufferedImage createRoundedImage() {
		int width = getIconWidth();
		int height = getIconHeight();
		if (width <= 0 || height <= 0) {
			return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		}

		BufferedImage rounded = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = rounded.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.fillRoundRect(0, 0, width, height, CORNER_RADIUS, CORNER_RADIUS);
		graphics.setComposite(AlphaComposite.SrcIn);
		graphics.drawImage(getImage(), 0, 0, null);
		graphics.dispose();
		return rounded;
	}
}
