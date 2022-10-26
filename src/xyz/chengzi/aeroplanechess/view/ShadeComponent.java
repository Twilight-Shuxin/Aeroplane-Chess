package xyz.chengzi.aeroplanechess.view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ShadeComponent extends JComponent {
	public int state = 0;
	public ShadeComponent() {
		setSize(54, 54);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			paintShade(g);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void paintShade(Graphics g) throws IOException {
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		BufferedImage bufImage;
		bufImage = ImageIO.read(new File("src/pic/shade" + state + ".png"));
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(bufImage, 0, 0, getWidth(), getHeight(), this);
	}
}
