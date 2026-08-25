import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/** Applies a non-destructive-to-content rounded alpha mask to every card PNG. */
public class RoundCardCorners {

	private static final int CORNER_DIAMETER = 16;
	private static final int MASK_SCALE = 4;

	public static void main(String[] args) throws IOException {
		File root = new File(args.length == 0 ? "cards" : args[0]);
		int count = process(root);
		System.out.println("Rounded " + count + " PNG files in " + root.getCanonicalPath());
	}

	private static int process(File file) throws IOException {
		if (file.isDirectory()) {
			int count = 0;
			File[] children = file.listFiles();
			if (children != null) {
				for (File child : children) {
					count += process(child);
				}
			}
			return count;
		}

		if (!file.getName().toLowerCase().endsWith(".png")) {
			return 0;
		}

		BufferedImage source = ImageIO.read(file);
		if (source == null) {
			throw new IOException("Could not read " + file);
		}

		int width = source.getWidth();
		int height = source.getHeight();
		BufferedImage output = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = output.createGraphics();
		graphics.drawImage(source, 0, 0, null);
		graphics.setComposite(AlphaComposite.DstIn);
		graphics.drawImage(createMask(width, height), 0, 0, null);
		graphics.dispose();

		if (!ImageIO.write(output, "png", file)) {
			throw new IOException("Could not write " + file);
		}
		return 1;
	}

	private static BufferedImage createMask(int width, int height) {
		int scaledWidth = width * MASK_SCALE;
		int scaledHeight = height * MASK_SCALE;
		BufferedImage largeMask = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = largeMask.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setColor(Color.WHITE);
		graphics.fillRoundRect(0, 0, scaledWidth, scaledHeight,
				CORNER_DIAMETER * MASK_SCALE, CORNER_DIAMETER * MASK_SCALE);
		graphics.dispose();

		BufferedImage mask = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		graphics = mask.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		graphics.drawImage(largeMask, 0, 0, width, height, null);
		graphics.dispose();
		return mask;
	}
}
