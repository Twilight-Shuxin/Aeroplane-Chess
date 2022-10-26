package xyz.chengzi.aeroplanechess.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StarComponent extends JComponent {
	public int state = 0;
	public StarComponent() {
		setSize(146, 146);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			paintStar(g);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void paintStar(Graphics g) throws IOException {
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		BufferedImage bufImage;
		bufImage = ImageIO.read(new File("src/Pic2/number" + state + ".png"));
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(bufImage, 0, 0, getWidth(), getHeight(), this);
	}
}
