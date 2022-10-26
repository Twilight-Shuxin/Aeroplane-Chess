package xyz.chengzi.aeroplanechess.Frame2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Heart extends JComponent implements ActionListener {
	public int state = 0;
	Timer timer = new Timer(100, this);

	public Heart() {
		setSize(40, 40);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			paintHeart(g);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void paintHeart(Graphics g) throws IOException {
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		BufferedImage bufImage;
		bufImage = ImageIO.read(new File("src/Pic2/Heart" + state + ".png"));
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.drawImage(bufImage, 0, 0, getWidth(), getHeight(), this);
		timer.start();
	}

	public void actionPerformed(ActionEvent e) {
		repaint();
	}
}
